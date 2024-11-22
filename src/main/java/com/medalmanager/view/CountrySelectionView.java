package com.medalmanager.view;

import com.medalmanager.model.dto.CountryDTO;
import com.medalmanager.util.StyleConstants;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CountrySelectionView extends JDialog {
    private final List<JCheckBox> checkboxes = new ArrayList<>();
    private JButton btnSave;
    private JButton btnCancel;
    private JButton btnRandom;
    private JButton btnClear;
    private JLabel countLabel;
    private final List<CountryDTO> countries;

    public CountrySelectionView(List<CountryDTO> countries) {
        this.countries = countries;
        initializeComponents();
        setupLayout();
        setupDialog();
        btnCancel.addActionListener(e -> dispose());
    }

    private void initializeComponents() {
        // Criar checkboxes estilizados
        for (CountryDTO country : countries) {
            JCheckBox checkbox = new JCheckBox(country.getName());
            checkbox.setSelected(country.isParticipating());
            checkbox.putClientProperty("countryId", country.getId());
            checkbox.addActionListener(e -> updateCount());
            checkbox.setFont(StyleConstants.REGULAR_FONT);
            checkbox.setBackground(StyleConstants.BACKGROUND_COLOR);
            checkboxes.add(checkbox);
        }

        // Botões principais
        btnSave = new JButton("Salvar");
        btnCancel = new JButton("Cancelar");
        btnRandom = new JButton("16 aleatórios");
        btnClear = new JButton("Limpar Seleção");

        // Estilizar botões
        StyleConstants.styleButton(btnSave);
        StyleConstants.styleSecondaryButton(btnCancel);
        StyleConstants.styleButton(btnRandom);
        StyleConstants.styleButton(btnClear);

        btnRandom.addActionListener(e -> selectRandom16());
        btnClear.addActionListener(e -> clearAll());

        // Label contador
        countLabel = new JLabel("Selecionados: 0/16");
        countLabel.setFont(StyleConstants.HEADER_FONT);
        countLabel.setForeground(StyleConstants.SECONDARY_COLOR);
        updateCount();
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Painel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(StyleConstants.SECONDARY_COLOR);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel titleLabel = new JLabel("Seleção de Países");
        titleLabel.setFont(StyleConstants.HEADER_FONT);
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Painel de controle superior
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        topPanel.setBackground(StyleConstants.BACKGROUND_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        topPanel.add(btnRandom);
        topPanel.add(btnClear);

        // Painel de checkboxes
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        checkboxPanel.setBackground(StyleConstants.BACKGROUND_COLOR);
        checkboxPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Adicionar checkboxes em grupos para melhor organização
        for (JCheckBox checkbox : checkboxes) {
            JPanel checkboxRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            checkboxRow.setBackground(StyleConstants.BACKGROUND_COLOR);
            checkboxRow.add(checkbox);
            checkboxPanel.add(checkboxRow);
        }

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(StyleConstants.BACKGROUND_COLOR);

        // Painel inferior
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        bottomPanel.setBackground(StyleConstants.BACKGROUND_COLOR);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);

        bottomPanel.add(countLabel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(StyleConstants.BACKGROUND_COLOR);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        bottomPanel.add(buttonPanel, gbc);

        // Adicionar todos os painéis
        add(titlePanel, BorderLayout.NORTH);
        add(topPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void selectRandom16() {
        if (checkboxes.size() >= 16) {
            List<JCheckBox> shuffled = new ArrayList<>(checkboxes);
            Collections.shuffle(shuffled);

            // First uncheck all
            checkboxes.forEach(cb -> cb.setSelected(false));

            // Select first 16 from shuffled list
            shuffled.subList(0, 16).forEach(cb -> cb.setSelected(true));
            updateCount();
        } else {
            showError("Não tem países o suficiente para selecionar 16!");
        }
    }

    private void clearAll() {
        checkboxes.forEach(cb -> cb.setSelected(false));
        updateCount();
    }

    private void setupDialog() {
        setTitle("Selecionar Países");
        setModal(true);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(StyleConstants.BACKGROUND_COLOR);
        setMinimumSize(new Dimension(400, 500));
    }

    private void updateCount() {
        long selectedCount = checkboxes.stream()
                .filter(AbstractButton::isSelected)
                .count();
        countLabel.setText(String.format("Selecionados: %d/16", selectedCount));
        countLabel.setForeground(selectedCount == 16 ?
                StyleConstants.SUCCESS_COLOR : StyleConstants.SECONDARY_COLOR);
    }


    public List<Long> getSelectedIds() {
        return checkboxes.stream()
                .filter(AbstractButton::isSelected)
                .map(cb -> (Long) cb.getClientProperty("countryId"))
                .collect(Collectors.toList());
    }

    public void addSaveListener(ActionListener listener) {
        btnSave.addActionListener(listener);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Erro",
                JOptionPane.ERROR_MESSAGE);
    }
}