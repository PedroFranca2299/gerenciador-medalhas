package com.medalmanager.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JButton btnCountrySelection;
    private JButton btnModalitySelection;
    private JButton btnResults;
    private JButton btnModalityRanking;
    private JButton btnGeneralRanking;
    private JPanel mainPanel;

    public MainView() {
        initializeComponents();
        setupLayout();
        setupFrame();
    }

    private void initializeComponents() {
        btnCountrySelection = createButton("Selecionar Países", "Gerenciar países participantes");
        btnModalitySelection = createButton("Selecionar Modalidades", "Gerenciar modalidades ativas");
        btnResults = createButton("Inserir Resultados", "Registrar resultados de competições");
        btnModalityRanking = createButton("Ranking de Medalhas", "Visualizar ranking de medalhas");
    }

    private JButton createButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(250, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private void setupLayout() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addComponentWithSpacing(mainPanel, btnCountrySelection, 10);
        addComponentWithSpacing(mainPanel, btnModalitySelection, 10);
        addComponentWithSpacing(mainPanel, btnResults, 10);
        addComponentWithSpacing(mainPanel, btnModalityRanking, 10);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);
    }

    private void addComponentWithSpacing(JPanel panel, JComponent component, int spacing) {
        panel.add(Box.createRigidArea(new Dimension(0, spacing)));
        panel.add(component);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(51, 51, 51));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("Gerenciador de Medalhas");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private void setupFrame() {
        setTitle("Medal Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setMinimumSize(new Dimension(350, 450));
    }

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

    public void addGeneralRankingListener(ActionListener listener) {
        btnGeneralRanking.addActionListener(listener);
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