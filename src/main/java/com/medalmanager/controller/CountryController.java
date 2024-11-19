package com.medalmanager.controller;

import com.medalmanager.service.CountryService;
import com.medalmanager.view.CountrySelectionView;
import java.util.List;

public class CountryController {
    private final CountrySelectionView view;
    private final CountryService service;

    public CountryController(CountrySelectionView view, CountryService service) {
        this.view = view;
        this.service = service;

        initializeListeners();
    }

    private void initializeListeners() {
        view.addSaveListener(e -> saveSelectedCountries());
    }

    private void saveSelectedCountries() {
        try {
            List<Long> selectedIds = view.getSelectedIds();
            service.updateParticipatingCountries(selectedIds);
            view.showSuccess("Países participantes atualizados com sucesso!");
        } catch (Exception e) {
            view.showError("Erro ao atualizar lista de países participantes: " + e.getMessage());
        }
    }
}