package gerenciadorMedalhas.view;

import gerenciadorMedalhas.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuView extends JFrame {

    private JButton btnEscolherParticipantes;
    private JButton btnEscolherModalidades;
    private JButton btnInformarResultados;
    private JButton btnRankingModalidades;
    private JButton btnRankingGeral;
    private boolean changesSaved = true; // Flag para monitorar se as mudanças foram salvas
    private JLabel contadorLabel; // Label para o contador

    public MenuView() {
        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        criarBotoes();
    }

    private void criarBotoes() {
        btnEscolherParticipantes = criarBotao("Escolher Participantes", 30);
        btnEscolherModalidades = criarBotao("Escolher Modalidades", 70);
        btnInformarResultados = criarBotao("Informar Resultados", 110);
        btnRankingModalidades = criarBotao("Ranking Modalidades", 150);
        btnRankingGeral = criarBotao("Ranking Geral", 190);
    }

    private JButton criarBotao(String texto, int y) {
        JButton botao = new JButton(texto);
        botao.setBounds(100, y, 200, 30);
        add(botao);
        return botao;
    }

    public void addEscolherParticipantesListener(ActionListener listener) {
        btnEscolherParticipantes.addActionListener(listener);
    }

    public void addEscolherModalidadesListener(ActionListener listener) {
        btnEscolherModalidades.addActionListener(listener);
    }

    public void showCheckboxFrame(String titulo, List<String> opcoes, List<Integer> ids) {
        JFrame newFrame = new JFrame(titulo);
        newFrame.setSize(400, 400);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.setLayout(new BorderLayout());
        newFrame.setLocationRelativeTo(null);

        changesSaved = true;

        JLabel label = new JLabel("Escolha as opções:", SwingConstants.CENTER);
        newFrame.add(label, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        List<JCheckBox> checkboxes = new ArrayList<>();

        // Inicializa a label do contador com 0/16
        contadorLabel = new JLabel("Selecionados: 0/16", SwingConstants.CENTER);

        // Adiciona o contador logo abaixo da lista de checkboxes
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(panel), BorderLayout.CENTER);
        centerPanel.add(contadorLabel, BorderLayout.SOUTH);
        newFrame.add(centerPanel, BorderLayout.CENTER);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, participando FROM paises WHERE id = ?")) {

            for (int i = 0; i < opcoes.size(); i++) {
                String opcao = opcoes.get(i);
                Integer id = ids.get(i);

                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                JCheckBox checkbox = new JCheckBox(opcao);
                checkbox.putClientProperty("id", id);

                if (rs.next()) {
                    checkbox.setSelected(rs.getInt("participando") == 1);
                }

                checkboxes.add(checkbox);
                panel.add(checkbox);

                checkbox.addActionListener(e -> atualizarContador(checkboxes));

                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(newFrame, "Erro ao carregar dados do banco: " + e.getMessage());
        }

        JPanel buttonPanel = new JPanel();
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> voltar(newFrame, checkboxes));
        buttonPanel.add(btnVoltar);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvar(newFrame, checkboxes));
        buttonPanel.add(btnSalvar);

        newFrame.add(buttonPanel, BorderLayout.SOUTH);
        newFrame.setVisible(true);

        atualizarContador(checkboxes); // Atualiza o contador na inicialização
    }

    private void atualizarContador(List<JCheckBox> checkboxes) {
        long selecionados = checkboxes.stream().filter(JCheckBox::isSelected).count();
        contadorLabel.setText("Selecionados: " + selecionados + "/16");
    }

    private void salvar(JFrame frame, List<JCheckBox> checkboxes) {
        List<Integer> idsPaises = new ArrayList<>();

        for (JCheckBox checkbox : checkboxes) {
            if (checkbox.isSelected()) {
                Integer id = (Integer) checkbox.getClientProperty("id");
                idsPaises.add(id);
            }
        }

        if (idsPaises.size() != 16) {
            JOptionPane.showMessageDialog(frame, "É necessário selecionar exatamente 16 opções para salvar.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement resetStmt = conn.prepareStatement("UPDATE paises SET participando = 0")) {
                resetStmt.executeUpdate();
            }

            try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE paises SET participando = 1 WHERE id = ?")) {
                for (Integer idPais : idsPaises) {
                    updateStmt.setInt(1, idPais);
                    updateStmt.addBatch();
                }
                updateStmt.executeBatch();
            }

            changesSaved = true;
            JOptionPane.showMessageDialog(frame, "Dados salvos com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erro ao salvar dados: " + e.getMessage());
        }
    }

    private void voltar(JFrame frame, List<JCheckBox> checkboxes) {
        if (!changesSaved) {
            int resposta = JOptionPane.showConfirmDialog(frame,
                    "Você tem opções selecionadas que não foram salvas. Deseja sair sem salvar?",
                    "Confirmar saída",
                    JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.NO_OPTION) {
                return;
            }
        }

        frame.dispose();
        this.setVisible(true);
    }
}
