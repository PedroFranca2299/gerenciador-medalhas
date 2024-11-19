package com.medalmanager.controller;

import com.medalmanager.service.CountryService;
import com.medalmanager.service.ModalityService;
import com.medalmanager.service.EtapaService;
import com.medalmanager.service.ResultadoService;
import com.medalmanager.view.MainView;
import com.medalmanager.view.CountrySelectionView;
import com.medalmanager.view.ModalitySelectionView;
import com.medalmanager.view.ResultadoSelectionView;
import javax.swing.JOptionPane;

public class MainController {
    private final MainView mainView;
    private final CountryService countryService;
    private final ModalityService modalityService;
    private final EtapaService etapaService;
    private final ResultadoService resultadoService;

    public MainController(MainView mainView,
                          CountryService countryService,
                          ModalityService modalityService,
                          EtapaService etapaService,
                          ResultadoService resultadoService) {
        this.mainView = mainView;
        this.countryService = countryService;
        this.modalityService = modalityService;
        this.etapaService = etapaService;
        this.resultadoService = resultadoService;

        initializeListeners();
    }

    private void initializeListeners() {
        System.out.println("Initializing main controller listeners");
        mainView.addCountrySelectionListener(e -> showCountrySelection());
        mainView.addModalitySelectionListener(e -> showModalitySelection());
        mainView.addResultsListener(e -> showResultsSelection());
    }

    private void showCountrySelection() {
        try {
            CountrySelectionView view = new CountrySelectionView(countryService.getAllCountries());
            new CountryController(view, countryService);
            view.setLocationRelativeTo(mainView);
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error opening country selection: " + e.getMessage());
        }
    }

    private void showModalitySelection() {
        try {
            ModalitySelectionView view = new ModalitySelectionView(modalityService.getAllModalities());
            new ModalityController(view, modalityService);
            view.setLocationRelativeTo(mainView);
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error opening modality selection: " + e.getMessage());
        }
    }

    private void showResultsSelection() {
        try {
            ResultadoSelectionView view = new ResultadoSelectionView();

            // Carrega os dados necessários
            view.setModalidades(modalityService.getAllModalities());
            view.setEtapas(etapaService.getAllEtapas());
            view.setPaises(countryService.getAllCountries());

            // Cria e configura o controller
            ResultadoController controller = new ResultadoController(view, resultadoService);

            // Centraliza em relação à tela principal
            view.setLocationRelativeTo(mainView);
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error opening results selection: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(mainView,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}