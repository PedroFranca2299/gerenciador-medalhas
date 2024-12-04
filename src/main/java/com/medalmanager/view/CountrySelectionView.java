package com.medalmanager.view;

import com.medalmanager.model.dto.CountryDTO;
import javax.swing.*;
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
        for (CountryDTO country : countries) {
            JCheckBox checkbox = new JCheckBox(country.getName());
            checkbox.setSelected(country.isParticipating());
            checkbox.putClientProperty("countryId", country.getId());
            checkbox.addActionListener(e -> updateCount());
            checkboxes.add(checkbox);
        }

        btnSave = new JButton("Salvar");
        btnCancel = new JButton("Cancelar");
        btnRandom = new JButton("16 aleatórios");
        btnClear = new JButton("Limpar Seleção");

        btnRandom.addActionListener(e -> selectRandom16());
        btnClear.addActionListener(e -> clearAll());

        countLabel = new JLabel("Selecionados: 0/16");
        updateCount();
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        topPanel.add(btnRandom);
        topPanel.add(btnClear);

        JPanel checkboxPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        checkboxes.forEach(checkboxPanel::add);

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        bottomPanel.add(countLabel);
        bottomPanel.add(btnSave);
        bottomPanel.add(btnCancel);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void selectRandom16() {
        if (checkboxes.size() >= 16) {
            List<JCheckBox> shuffled = new ArrayList<>(checkboxes);
            Collections.shuffle(shuffled);

            checkboxes.forEach(cb -> cb.setSelected(false));

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
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void updateCount() {
        long selectedCount = checkboxes.stream()
                .filter(AbstractButton::isSelected)
                .count();
        countLabel.setText(String.format("Selecionados: %d/16", selectedCount));

        btnRandom.setEnabled(checkboxes.size() >= 16);
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
        JOptionPane.showMessageDialog(this, message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}