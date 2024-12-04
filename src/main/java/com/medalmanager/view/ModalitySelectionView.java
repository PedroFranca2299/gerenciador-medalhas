package com.medalmanager.view;

import com.medalmanager.model.dto.ModalityDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModalitySelectionView extends JDialog {
    private final List<JCheckBox> checkboxes = new ArrayList<>();
    private JButton btnSave;
    private JButton btnCancel;
    private JButton btnToggleAll;
    private final List<ModalityDTO> modalities;
    private JLabel selectionLabel;
    private boolean allSelected = false;

    public ModalitySelectionView(List<ModalityDTO> modalities) {
        this.modalities = modalities;
        initializeComponents();
        setupLayout();
        setupDialog();
        btnCancel.addActionListener(e -> dispose());
    }

    private void initializeComponents() {
        for (ModalityDTO modality : modalities) {
            JCheckBox checkbox = new JCheckBox(modality.getName());
            checkbox.setSelected(modality.isActive());
            checkbox.putClientProperty("modalityId", modality.getId());
            checkbox.addActionListener(e -> updateSelectionCount());
            checkboxes.add(checkbox);
        }

        btnSave = new JButton("Salvar");
        btnCancel = new JButton("Cancelar");
        btnToggleAll = new JButton("Selecionar Todos");
        btnToggleAll.addActionListener(e -> toggleAll());

        selectionLabel = new JLabel("Selected: 0");
        updateSelectionCount();
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(btnToggleAll);

        JPanel checkboxPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        checkboxes.forEach(checkboxPanel::add);

        JScrollPane scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        bottomPanel.add(selectionLabel);
        bottomPanel.add(btnSave);
        bottomPanel.add(btnCancel);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void toggleAll() {
        allSelected = !allSelected;
        checkboxes.forEach(cb -> cb.setSelected(allSelected));
        btnToggleAll.setText(allSelected ? "Desselecionar Todos" : "Selecionar Todos");
        updateSelectionCount();
    }

    private void setupDialog() {
        setTitle("Selecionar Modalidades");
        setModal(true);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(300, 400));
    }

    private void updateSelectionCount() {
        long selectedCount = checkboxes.stream()
                .filter(AbstractButton::isSelected)
                .count();
        selectionLabel.setText(String.format("Selecionados: %d", selectedCount));
    }

    public List<Long> getSelectedIds() {
        return checkboxes.stream()
                .filter(AbstractButton::isSelected)
                .map(cb -> (Long) cb.getClientProperty("modalityId"))
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