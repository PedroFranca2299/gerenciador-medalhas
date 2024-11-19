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

    public MainView() {
        initializeComponents();
        setupLayout();
        setupFrame();
    }

    private void initializeComponents() {
        btnCountrySelection = new JButton("Selecionar Países");
        btnModalitySelection = new JButton("Selecionar Modalidades");
        btnResults = new JButton("Inserir resultados");
        btnModalityRanking = new JButton("Ranking por Modalidades");
        btnGeneralRanking = new JButton("Ranking Geral");
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.ipadx = 50;

        gbc.gridy = 0;
        add(btnCountrySelection, gbc);

        gbc.gridy = 1;
        add(btnModalitySelection, gbc);

        gbc.gridy = 2;
        add(btnResults, gbc);

        gbc.gridy = 3;
        add(btnModalityRanking, gbc);

        gbc.gridy = 4;
        add(btnGeneralRanking, gbc);
    }

    private void setupFrame() {
        setTitle("Medal Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void addCountrySelectionListener(ActionListener listener) {
        btnCountrySelection.addActionListener(listener);
    }

    public void addModalitySelectionListener(ActionListener listener) {
        btnModalitySelection.addActionListener(listener);
    }

    // Novo método para adicionar listener ao botão de resultados
    public void addResultsListener(ActionListener listener) {
        btnResults.addActionListener(listener);
    }
}