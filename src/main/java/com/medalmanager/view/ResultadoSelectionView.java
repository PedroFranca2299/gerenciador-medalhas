package com.medalmanager.view;

import com.medalmanager.model.dto.CountryDTO;
import com.medalmanager.model.dto.ModalityDTO;
import com.medalmanager.model.dto.EtapaDTO;
import com.medalmanager.model.dto.ResultadoDTO;

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

    public void setPaises(List<CountryDTO> paises) {
        this.paisesDisponiveis = new ArrayList<>(paises); // Atualiza a lista mantida
        for (JComboBox<String> combo : paisesComboBoxes) {
            combo.removeAllItems();
            for (CountryDTO pais : paises) {
                combo.addItem(pais.getName());
            }
        }
    }

    // ... outros métodos existentes ...

    public ResultadoDTO getResultado() {
        ResultadoDTO resultado = new ResultadoDTO();
        resultado.setModalidadeNome(getSelectedModalidade());
        resultado.setEtapaNome(getSelectedEtapa());
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

                // Só adiciona se um país foi selecionado
                if (paisNome != null && !paisNome.isEmpty()) {
                    ParticipacaoResultadoDTO participacao = new ParticipacaoResultadoDTO(
                            null, // ID será gerado pelo banco
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