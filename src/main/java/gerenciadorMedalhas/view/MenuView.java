package gerenciadorMedalhas.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuView extends JFrame {

    private JButton btnEscolherParticipantes;
    private JButton btnEscolherModalidades;
    private JButton btnInformarResultados;
    private JButton btnRankingModalidades;
    private JButton btnRankingGeral;

    public MenuView() {
        // Configura o menu principal
        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Cria botões
        btnEscolherParticipantes = new JButton("Escolher Participantes");
        btnEscolherParticipantes.setBounds(100, 30, 200, 30);
        add(btnEscolherParticipantes);

        btnEscolherModalidades = new JButton("Escolher Modalidades");
        btnEscolherModalidades.setBounds(100, 70, 200, 30);
        add(btnEscolherModalidades);

        btnInformarResultados = new JButton("Informar Resultados");
        btnInformarResultados.setBounds(100, 110, 200, 30);
        add(btnInformarResultados);

        btnRankingModalidades = new JButton("Ranking Modalidades");
        btnRankingModalidades.setBounds(100, 150, 200, 30);
        add(btnRankingModalidades);

        btnRankingGeral = new JButton("Ranking Geral");
        btnRankingGeral.setBounds(100, 190, 200, 30);
        add(btnRankingGeral);
    }

    // Métodos para adicionar listeners nos botões
    public void addEscolherParticipantesListener(ActionListener listener) {
        btnEscolherParticipantes.addActionListener(listener);
    }

    public void addEscolherModalidadesListener(ActionListener listener) {
        btnEscolherModalidades.addActionListener(listener);
    }

    // metodo para exibir um novo frame com checkboxes
    public void showCheckboxFrame(String titulo, List<String> opcoes) {
        JFrame newFrame = new JFrame(titulo);
        newFrame.setSize(400, 300);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setando para exibir em colunas para ficar melhor a vizualização
        int maxCheckboxesPorColuna = 10;
        int numColunas = (int) Math.ceil((double) opcoes.size() / maxCheckboxesPorColuna);

        // setado para as colunas possuirem 10 itens por coluna
        newFrame.setLayout(new GridLayout(maxCheckboxesPorColuna + 2, numColunas));

        JLabel label = new JLabel("Escolha as opções:", SwingConstants.CENTER);
        newFrame.add(label);

        // cria os checkbox
        for (int i = 0; i < opcoes.size(); i++) {
            JCheckBox checkbox = new JCheckBox(opcoes.get(i));
            newFrame.add(checkbox);
        }

        // adiciona botões vazios para preencher o layout, se necessário
        for (int i = opcoes.size(); i < maxCheckboxesPorColuna * numColunas; i++) {
            newFrame.add(new JLabel());
        }

        JButton btnVoltar = new JButton("Voltar ao Menu Principal");
        btnVoltar.addActionListener(e -> {
            newFrame.dispose();
            this.setVisible(true); // retorna ao menu principal
        });
        newFrame.add(btnVoltar);

        newFrame.setVisible(true);
    }
}
