package com.medalmanager.controller;

import com.medalmanager.service.CountryService;
import com.medalmanager.service.ModalityService;
import com.medalmanager.view.MainView;
import com.medalmanager.view.CountrySelectionView;
import com.medalmanager.view.ModalitySelectionView;

import javax.swing.*;

public class MainController {
    private final MainView mainView;
    private final CountryService countryService;
    private final ModalityService modalityService;

    public MainController(MainView mainView, CountryService countryService, ModalityService modalityService) {
        this.mainView = mainView;
        this.countryService = countryService;
        this.modalityService = modalityService;

        initializeListeners();
    }

    private void initializeListeners() {
        System.out.println("Initializing main controller listeners");

        mainView.addCountrySelectionListener(e -> {
            System.out.println("Opening country selection dialog");
            showCountrySelection();
        });

        mainView.addModalitySelectionListener(e -> {
            System.out.println("Opening modality selection dialog");
            showModalitySelection();
        });
    }

    private void showCountrySelection() {
        try {
            CountrySelectionView view = new CountrySelectionView(countryService.getAllCountries());
            new CountryController(view, countryService);
            view.setLocationRelativeTo(mainView);
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainView,
                    "Error opening country selection: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(mainView,
                    "Error opening modality selection: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}