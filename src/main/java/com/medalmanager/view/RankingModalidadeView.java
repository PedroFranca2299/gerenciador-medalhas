package com.medalmanager.view;

import com.medalmanager.model.dto.ModalityDTO;
import com.medalmanager.model.dto.RankingDTO;
import com.medalmanager.service.RankingService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class RankingModalidadeView extends JDialog {
    private JComboBox<String> modalidadeCombo;
    private JTable rankingTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JButton btnClose;

    // Botões de ordenação
    private JButton btnTotalMedals;
    private JButton btnGoldMedals;
    private JButton btnSilverMedals;
    private JButton btnBronzeMedals;
    private JButton btnCountryName;

    // Estado da ordenação
    private boolean totalMedalsAsc = true;
    private boolean goldMedalsAsc = true;
    private boolean silverMedalsAsc = true;
    private boolean bronzeMedalsAsc = true;
    private boolean countryNameAsc = true;

    public RankingModalidadeView() {
        initializeComponents();
        setupLayout();
        setupDialog();
    }

    private void initializeComponents() {
        modalidadeCombo = new JComboBox<>();

        // Configuração da tabela
        String[] columnNames = {"País", "Ouro", "Prata", "Bronze", "Total"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) return String.class;
                return Integer.class;
            }
        };

        rankingTable = new JTable(tableModel);
        rankingTable.setFillsViewportHeight(true);
        rankingTable.setRowHeight(25);
        rankingTable.getTableHeader().setReorderingAllowed(false);

        sorter = new TableRowSorter<>(tableModel);
        rankingTable.setRowSorter(sorter);

        // Configuração dos botões de ordenação
        btnCountryName = createSortButton("Nome do País");
        btnGoldMedals = createSortButton("Ouro");
        btnSilverMedals = createSortButton("Prata");
        btnBronzeMedals = createSortButton("Bronze");
        btnTotalMedals = createSortButton("Total de Medalhas");

        // Botão de fechar
        btnClose = new JButton("Fechar");

        // Adiciona listeners aos botões de ordenação
        btnCountryName.addActionListener(e -> toggleSort(0, countryNameAsc));
        btnGoldMedals.addActionListener(e -> toggleSort(1, goldMedalsAsc));
        btnSilverMedals.addActionListener(e -> toggleSort(2, silverMedalsAsc));
        btnBronzeMedals.addActionListener(e -> toggleSort(3, bronzeMedalsAsc));
        btnTotalMedals.addActionListener(e -> toggleSort(4, totalMedalsAsc));

        btnClose.addActionListener(e -> dispose());
    }

    private JButton createSortButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setMargin(new Insets(5, 10, 5, 10));
        return button;
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Painel superior
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        topPanel.add(new JLabel("Modalidade:"));
        topPanel.add(modalidadeCombo);

        // Painel de botões de ordenação
        JPanel sortButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        sortButtonsPanel.add(new JLabel("Ordenar por:"));
        sortButtonsPanel.add(btnCountryName);
        sortButtonsPanel.add(btnGoldMedals);
        sortButtonsPanel.add(btnSilverMedals);
        sortButtonsPanel.add(btnBronzeMedals);
        sortButtonsPanel.add(btnTotalMedals);

        // Painel da tabela
        JScrollPane tableScrollPane = new JScrollPane(rankingTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Painel inferior
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        bottomPanel.add(btnClose);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(sortButtonsPanel, BorderLayout.CENTER);
        mainPanel.add(tableScrollPane, BorderLayout.SOUTH);

        // Adiciona os painéis ao layout principal
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupDialog() {
        setTitle("Ranking por Modalidade");
        setModal(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(700, 500));
    }

    private void toggleSort(int column, boolean currentAsc) {
        // Inverte a ordem atual
        boolean newOrder = !currentAsc;

        // Atualiza o estado da ordenação
        switch (column) {
            case 0: countryNameAsc = newOrder; break;
            case 1: goldMedalsAsc = newOrder; break;
            case 2: silverMedalsAsc = newOrder; break;
            case 3: bronzeMedalsAsc = newOrder; break;
            case 4: totalMedalsAsc = newOrder; break;
        }

        // Aplica a ordenação
        sorter.setSortKeys(List.of(new RowSorter.SortKey(
                column,
                newOrder ? SortOrder.ASCENDING : SortOrder.DESCENDING
        )));

        // Atualiza o texto do botão para indicar a direção da ordenação
        JButton currentButton = switch (column) {
            case 0 -> btnCountryName;
            case 1 -> btnGoldMedals;
            case 2 -> btnSilverMedals;
            case 3 -> btnBronzeMedals;
            case 4 -> btnTotalMedals;
            default -> null;
        };

        if (currentButton != null) {
            String baseText = currentButton.getText().replace(" ▲", "").replace(" ▼", "");
            currentButton.setText(baseText + (newOrder ? " ▲" : " ▼"));
        }
    }

    public void setModalidades(List<ModalityDTO> modalidades) {
        modalidadeCombo.removeAllItems();

        modalidadeCombo.addItem(RankingService.ALL_MODALITIES);

        for (ModalityDTO modalidade : modalidades) {
            if (modalidade.isActive()) {
                modalidadeCombo.addItem(modalidade.getName());
            }
        }

        modalidadeCombo.setSelectedItem(RankingService.ALL_MODALITIES);
    }

    public String getSelectedModalidade() {
        return (String) modalidadeCombo.getSelectedItem();
    }

    public void updateRanking(List<RankingDTO> rankingData) {
        tableModel.setRowCount(0);
        for (RankingDTO ranking : rankingData) {
            tableModel.addRow(new Object[]{
                    ranking.getPaisNome(),
                    ranking.getOuro(),
                    ranking.getPrata(),
                    ranking.getBronze(),
                    ranking.getTotal()
            });
        }
    }

    public void addModalityChangeListener(ActionListener listener) {
        modalidadeCombo.addActionListener(listener);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Erro",
                JOptionPane.ERROR_MESSAGE);
    }
}