package com.medalmanager.controller;

import com.medalmanager.service.*;
import com.medalmanager.util.DependencyContainer;
import com.medalmanager.view.*;
import javax.swing.JOptionPane;

public class MainController {
    private final MainView mainView;
    private final CountryService countryService;
    private final ModalityService modalityService;
    private final EtapaService etapaService;
    private final ResultadoService resultadoService;
    private final RankingService rankingService;

    public MainController(MainView mainView,
                          CountryService countryService,
                          ModalityService modalityService,
                          EtapaService etapaService,
                          ResultadoService resultadoService,
                          RankingService rankingService) {
        this.mainView = mainView;
        this.countryService = countryService;
        this.modalityService = modalityService;
        this.etapaService = etapaService;
        this.resultadoService = resultadoService;
        this.rankingService = rankingService;

        initializeListeners();
    }

    private void initializeListeners() {
        System.out.println("Initializing main controller listeners");
        mainView.addCountrySelectionListener(e -> showCountrySelection());
        mainView.addModalitySelectionListener(e -> showModalitySelection());
        mainView.addResultsListener(e -> showResultsSelection());
        mainView.addModalityRankingListener(e -> showModalityRanking());
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
            view.setModalidades(modalityService.getAllModalities());
            view.setEtapas(etapaService.getAllEtapas());
            view.setPaises(countryService.getAllCountries());

            ResultadoController controller = new ResultadoController(view, resultadoService);
            view.setLocationRelativeTo(mainView);
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error opening results selection: " + e.getMessage());
        }
    }

    private void showModalityRanking() {
        System.out.println("Attempting to show modality ranking...");
        try {
            RankingModalidadeView view = new RankingModalidadeView();
            new RankingModalidadeController(view, rankingService, modalityService);
            view.setLocationRelativeTo(mainView);
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error opening modality ranking: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(mainView,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}