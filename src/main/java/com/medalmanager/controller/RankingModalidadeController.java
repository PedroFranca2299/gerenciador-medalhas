package com.medalmanager.controller;

import com.medalmanager.service.RankingService;
import com.medalmanager.service.ModalityService;
import com.medalmanager.view.RankingModalidadeView;
import com.medalmanager.model.dto.RankingDTO;
import java.util.List;

public class RankingModalidadeController {
    private final RankingModalidadeView view;
    private final RankingService rankingService;
    private final ModalityService modalityService;

    public RankingModalidadeController(
            RankingModalidadeView view,
            RankingService rankingService,
            ModalityService modalityService) {
        this.view = view;
        this.rankingService = rankingService;
        this.modalityService = modalityService;

        initializeView();
        initializeListeners();
    }

    private void initializeView() {
        try {
            view.setModalidades(modalityService.getAllModalities());
            updateRanking();
        } catch (Exception e) {
            e.printStackTrace();
            view.showError("Erro ao carregar modalidades: " + e.getMessage());
        }
    }

    private void initializeListeners() {
        view.addModalityChangeListener(e -> updateRanking());
    }

    private void updateRanking() {
        try {
            String modalidade = view.getSelectedModalidade();
            if (modalidade != null) {
                List<RankingDTO> rankingData = rankingService.getRankingByModality(modalidade);
                view.updateRanking(rankingData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            view.showError("Erro ao atualizar ranking: " + e.getMessage());
        }
    }
}