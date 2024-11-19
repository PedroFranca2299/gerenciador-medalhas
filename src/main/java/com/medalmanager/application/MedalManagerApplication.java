package com.medalmanager.application;

import com.medalmanager.config.DatabaseConfig;
import com.medalmanager.controller.MainController;
import com.medalmanager.repository.*;
import com.medalmanager.service.*;
import com.medalmanager.util.DependencyContainer;
import com.medalmanager.view.MainView;

public class MedalManagerApplication {
    public static void main(String[] args) {
        try {
            System.out.println("Starting Medal Manager Application...");

            // Initialize configuration
            DatabaseConfig.initialize();
            System.out.println("Database configuration initialized successfully");

            // Initialize repositories
            CountryRepository countryRepository = new CountryRepository();
            ModalityRepository modalityRepository = new ModalityRepository();
            EtapaRepository etapaRepository = new EtapaRepository();  // Novo repository

            // Initialize services
            CountryService countryService = new CountryService(countryRepository);
            ModalityService modalityService = new ModalityService(modalityRepository);
            EtapaService etapaService = new EtapaService(etapaRepository);  // Novo service

            // Register dependencies
            DependencyContainer.register(CountryRepository.class, countryRepository);
            DependencyContainer.register(ModalityRepository.class, modalityRepository);
            DependencyContainer.register(EtapaRepository.class, etapaRepository);
            DependencyContainer.register(CountryService.class, countryService);
            DependencyContainer.register(ModalityService.class, modalityService);
            DependencyContainer.register(EtapaService.class, etapaService);

            // Initialize main view and controller
            MainView mainView = new MainView();
            MainController mainController = new MainController(
                    mainView,
                    countryService,
                    modalityService,
                    etapaService  // Novo serviço adicionado ao controller
            );

            // Display main view
            mainView.setVisible(true);
            System.out.println("Application started successfully");
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}