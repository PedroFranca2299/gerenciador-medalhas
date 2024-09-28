package gerenciadorMedalhas.view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuView extends JFrame {

    private JButton btnEscolherParticipantes;
    private JButton btnEscolherModalidades;
    private JButton btnInformarResultados;
    private JButton btnRankingModalidades;
    private JButton btnRankingGeral;

    public MenuView() {
        // Configura o frame principal
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

    // Método para exibir um novo frame
    public void showNewFrame(String titulo, List<String> paises) {
        JFrame newFrame = new JFrame(titulo);
        newFrame.setSize(400, 300);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setLayout(null);

        JLabel label = new JLabel("Esta é a página de " + titulo, SwingConstants.CENTER);
        label.setBounds(50, 20, 300, 30);
        newFrame.add(label);

        JComboBox<String> paisesComboBox = new JComboBox<>(paises.toArray(new String[0]));
        paisesComboBox.setBounds(100, 60, 200, 30);
        newFrame.add(paisesComboBox);

        JButton btnVoltar = new JButton("Voltar ao Menu Principal");
        btnVoltar.setBounds(100, 200, 200, 30);
        btnVoltar.addActionListener(e -> {
            newFrame.dispose();
            this.setVisible(true); // Retorna ao menu principal
        });
        newFrame.add(btnVoltar);

        newFrame.setVisible(true);
    }
}
