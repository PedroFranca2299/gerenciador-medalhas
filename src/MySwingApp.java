import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MySwingApp {

    public static void main(String[] args) {
        // Cria o frame principal
        JFrame mainFrame = new JFrame("Menu Principal");
        mainFrame.setSize(400, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);

        // Botão 1: Escolher Participantes
        JButton btnEscolherParticipantes = new JButton("Escolher Participantes");
        btnEscolherParticipantes.setBounds(100, 30, 200, 30);
        btnEscolherParticipantes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Fecha o frame atual e abre um novo frame
                mainFrame.dispose();
                abrirNovoFrame("Escolher Participantes");
            }
        });

        // Botão 2: Escolher Modalidades
        JButton btnEscolherModalidades = new JButton("Escolher Modalidades");
        btnEscolherModalidades.setBounds(100, 70, 200, 30);
        btnEscolherModalidades.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                abrirNovoFrame("Escolher Modalidades");
            }
        });

        // Botão 3: Informar Resultados
        JButton btnInformarResultados = new JButton("Informar Resultados");
        btnInformarResultados.setBounds(100, 110, 200, 30);
        btnInformarResultados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                abrirNovoFrame("Informar Resultados");
            }
        });

        // Botão 4: Ranking Modalidades
        JButton btnRankingModalidades = new JButton("Ranking Modalidades");
        btnRankingModalidades.setBounds(100, 150, 200, 30);
        btnRankingModalidades.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                abrirNovoFrame("Ranking Modalidades");
            }
        });

        // Botão 5: Ranking Geral
        JButton btnRankingGeral = new JButton("Ranking Geral");
        btnRankingGeral.setBounds(100, 190, 200, 30);
        btnRankingGeral.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                abrirNovoFrame("Ranking Geral");
            }
        });

        // Adiciona os botões ao frame principal
        mainFrame.add(btnEscolherParticipantes);
        mainFrame.add(btnEscolherModalidades);
        mainFrame.add(btnInformarResultados);
        mainFrame.add(btnRankingModalidades);
        mainFrame.add(btnRankingGeral);

        // Exibe o frame principal
        mainFrame.setVisible(true);
    }

    // Método para abrir um novo frame com o botão "Voltar ao Menu Principal"
    public static void abrirNovoFrame(String titulo) {
        JFrame newFrame = new JFrame(titulo);
        newFrame.setSize(400, 300);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Esta é a página de " + titulo, SwingConstants.CENTER);
        label.setBounds(50, 100, 300, 30);
        newFrame.add(label);

        // Adiciona o botão "Voltar ao Menu Principal"
        JButton btnVoltar = new JButton("Voltar ao Menu Principal");
        btnVoltar.setBounds(100, 200, 200, 30);
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newFrame.dispose(); // Fecha o novo frame
                main(null); // Volta ao frame principal
            }
        });
        newFrame.add(btnVoltar);

        // Exibe o novo frame
        newFrame.setLayout(null);
        newFrame.setVisible(true);
    }
}
