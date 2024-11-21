package com.medalmanager.view;

import com.medalmanager.model.dto.CountryDTO;
import com.medalmanager.model.dto.ModalityDTO;
import com.medalmanager.model.dto.EtapaDTO;
import com.medalmanager.model.dto.ResultadoDTO;
import com.medalmanager.model.dto.ParticipacaoResultadoDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class ResultadoSelectionView extends JDialog {
    private JComboBox<String> modalidadeCombo;
    private JComboBox<String> etapaCombo;
    private JPanel participantesPanel;
    private List<JComboBox<String>> paisesComboBoxes;
    private JButton btnAddParticipante;
    private JButton btnSave;
    private JButton btnCancel;
    private List<CountryDTO> paisesDisponiveis;

    public ResultadoSelectionView() {
        paisesDisponiveis = new ArrayList<>();
        initializeComponents();
        setupLayout();
        setupDialog();
    }

    private void initializeComponents() {
        modalidadeCombo = new JComboBox<>();
        etapaCombo = new JComboBox<>();
        participantesPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        paisesComboBoxes = new ArrayList<>();

        btnAddParticipante = new JButton("Adicionar Participante");
        btnAddParticipante.addActionListener(e -> addParticipanteRow());

        btnSave = new JButton("Salvar");
        btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(e -> dispose());

        addParticipanteRow();
        addParticipanteRow();
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Painel superior para seleção de modalidade e etapa
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        topPanel.add(new JLabel("Modalidade:"));
        topPanel.add(modalidadeCombo);
        topPanel.add(new JLabel("Etapa:"));
        topPanel.add(etapaCombo);

        // Painel central para participantes
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel participantesLabel = new JLabel("Participantes:");
        participantesLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        centerPanel.add(participantesLabel, BorderLayout.NORTH);

        // Painel de scroll para os participantes
        JScrollPane scrollPane = new JScrollPane(participantesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Botão de adicionar participante
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButtonPanel.add(btnAddParticipante);
        centerPanel.add(addButtonPanel, BorderLayout.SOUTH);

        // Painel inferior para botões de ação
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        bottomPanel.add(btnSave);
        bottomPanel.add(btnCancel);

        // Adiciona todos os painéis ao layout principal
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupDialog() {
        setTitle("Registrar Resultado");
        setModal(true);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(400, 300));
    }

    private void addParticipanteRow() {
        JPanel row = new JPanel(new GridLayout(1, 2, 5, 5));

        JComboBox<String> paisCombo = new JComboBox<>();
        // Preenche o novo combo com os países disponíveis
        for (CountryDTO pais : paisesDisponiveis) {
            paisCombo.addItem(pais.getName());
        }
        paisesComboBoxes.add(paisCombo);

        JComboBox<String> posicaoCombo = new JComboBox<>(
                new String[]{"Não Classificado", "1º Lugar", "2º Lugar", "3º Lugar"});

        row.add(paisCombo);
        row.add(posicaoCombo);

        participantesPanel.add(row);
        participantesPanel.revalidate();
        participantesPanel.repaint();
    }

    public void setModalidades(List<ModalityDTO> modalidades) {
        modalidadeCombo.removeAllItems();
        for (ModalityDTO modalidade : modalidades) {
            modalidadeCombo.addItem(modalidade.getName());
        }
    }

    public void setEtapas(List<EtapaDTO> etapas) {
        etapaCombo.removeAllItems();
        for (EtapaDTO etapa : etapas) {
            etapaCombo.addItem(etapa.getNome());
        }
    }

    public void setPaises(List<CountryDTO> paises) {
        this.paisesDisponiveis = new ArrayList<>(paises);
        for (JComboBox<String> combo : paisesComboBoxes) {
            combo.removeAllItems();
            for (CountryDTO pais : paises) {
                combo.addItem(pais.getName());
            }
        }
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

    public ResultadoDTO getResultado() {
        ResultadoDTO resultado = new ResultadoDTO();
        resultado.setModalidadeNome((String) modalidadeCombo.getSelectedItem());
        resultado.setEtapaNome((String) etapaCombo.getSelectedItem());
        resultado.setDataResultado(LocalDateTime.now());

        List<ParticipacaoResultadoDTO> participacoes = new ArrayList<>();
        Component[] rows = participantesPanel.getComponents();

        for (Component row : rows) {
            if (row instanceof JPanel) {
                JPanel participanteRow = (JPanel) row;
                JComboBox<String> paisCombo = (JComboBox<String>) participanteRow.getComponent(0);
                JComboBox<String> posicaoCombo = (JComboBox<String>) participanteRow.getComponent(1);

                String paisNome = (String) paisCombo.getSelectedItem();
                int posicaoIndex = posicaoCombo.getSelectedIndex();

                if (paisNome != null && !paisNome.isEmpty()) {
                    ParticipacaoResultadoDTO participacao = new ParticipacaoResultadoDTO(
                            null,
                            paisNome,
                            posicaoIndex > 0 ? posicaoIndex : null
                    );
                    participacoes.add(participacao);
                }
            }
        }

        resultado.setParticipacoes(participacoes);
        return resultado;
    }
}