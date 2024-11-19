package com.medalmanager.controller;

import com.medalmanager.service.ModalityService;
import com.medalmanager.view.ModalitySelectionView;
import java.util.List;

public class ModalityController {
    private final ModalitySelectionView view;
    private final ModalityService service;

    public ModalityController(ModalitySelectionView view, ModalityService service) {
        this.view = view;
        this.service = service;

        initializeListeners();
    }

    private void initializeListeners() {
        view.addSaveListener(e -> saveSelectedModalities());
    }

    private void saveSelectedModalities() {
        try {
            List<Long> selectedIds = view.getSelectedIds();
            service.updateActiveModalities(selectedIds);
            view.showSuccess("Modalidades ativas atualizadas com sucesso");
        } catch (Exception e) {
            view.showError("Erro ao atualizar lista de modalidades ativas: " + e.getMessage());
        }
    }
}