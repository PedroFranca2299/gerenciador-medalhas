package com.medalmanager.view;

import com.medalmanager.util.StyleConstants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JButton btnCountrySelection;
    private JButton btnModalitySelection;
    private JButton btnResults;
    private JButton btnModalityRanking;
    private JPanel mainPanel;

    public MainView() {
        initializeComponents();
        setupLayout();
        setupFrame();
    }

    private void initializeComponents() {
        // Inicializa botões com ícones e texto
        btnCountrySelection = createStyledButton("Selecionar Países", "Gerenciar países participantes");
        btnModalitySelection = createStyledButton("Selecionar Modalidades", "Gerenciar modalidades ativas");
        btnResults = createStyledButton("Inserir Resultados", "Registrar resultados de competições");
        btnModalityRanking = createStyledButton("Ranking de Medalhas", "Visualizar ranking de medalhas");
    }

    private JButton createStyledButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        StyleConstants.styleButton(button);
        button.setMaximumSize(new Dimension(300, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private void setupLayout() {
        // Configura o painel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(StyleConstants.BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Adiciona espaçamento entre os botões
        addComponentWithSpacing(mainPanel, btnCountrySelection, 15);
        addComponentWithSpacing(mainPanel, btnModalitySelection, 15);
        addComponentWithSpacing(mainPanel, btnResults, 15);
        addComponentWithSpacing(mainPanel, btnModalityRanking, 15);

        // Configura o layout principal
        setLayout(new BorderLayout());
        add(createTitlePanel(), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void addComponentWithSpacing(JPanel panel, JComponent component, int spacing) {
        panel.add(Box.createRigidArea(new Dimension(0, spacing)));
        panel.add(component);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(StyleConstants.SECONDARY_COLOR);
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel titleLabel = new JLabel("Gerenciador de Medalhas");
        titleLabel.setFont(StyleConstants.TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);

        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private void setupFrame() {
        setTitle("Medal Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setMinimumSize(new Dimension(400, 500));
        getContentPane().setBackground(StyleConstants.BACKGROUND_COLOR);
    }

    // Métodos para adicionar listeners e mostrar mensagens
    public void addCountrySelectionListener(ActionListener listener) {
        btnCountrySelection.addActionListener(listener);
    }

    public void addModalitySelectionListener(ActionListener listener) {
        btnModalitySelection.addActionListener(listener);
    }

    public void addResultsListener(ActionListener listener) {
        btnResults.addActionListener(listener);
    }

    public void addModalityRankingListener(ActionListener listener) {
        btnModalityRanking.addActionListener(listener);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Erro",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void showWarning(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
    }
}