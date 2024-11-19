package com.medalmanager.controller;

import com.medalmanager.service.ResultadoService;
import com.medalmanager.view.ResultadoSelectionView;
import com.medalmanager.model.dto.ResultadoDTO;

public class ResultadoController {
    private final ResultadoSelectionView view;
    private final ResultadoService service;

    public ResultadoController(ResultadoSelectionView view, ResultadoService service) {
        this.view = view;
        this.service = service;

        initializeListeners();
    }

    private void initializeListeners() {
        view.addSaveListener(e -> saveResultado());
    }

    private void saveResultado() {
        try {
            ResultadoDTO resultado = view.getResultado();
            service.saveResultado(resultado);
            view.showSuccess("Resultado salvo com sucesso!");
            view.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            view.showError("Erro ao salvar resultado: " + ex.getMessage());
        }
    }
}