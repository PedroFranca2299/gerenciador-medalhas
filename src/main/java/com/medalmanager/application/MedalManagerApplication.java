// application/MedalManagerApplication.java
package com.medalmanager.application;

import com.medalmanager.config.DatabaseConfig;
import com.medalmanager.controller.MainController;
import com.medalmanager.repository.CountryRepository;
import com.medalmanager.repository.ModalityRepository;
import com.medalmanager.service.CountryService;
import com.medalmanager.service.ModalityService;
import com.medalmanager.util.DependencyContainer;
import com.medalmanager.view.MainView;

public class MedalManagerApplication {
    public static void main(String[] args) {
        try {
            // Initialize configuration
            DatabaseConfig.initialize();

            // Initialize repositories
            CountryRepository countryRepository = new CountryRepository();
            ModalityRepository modalityRepository = new ModalityRepository();

            // Initialize services
            CountryService countryService = new CountryService(countryRepository);
            ModalityService modalityService = new ModalityService(modalityRepository);

            // Register dependencies
            DependencyContainer.register(CountryRepository.class, countryRepository);
            DependencyContainer.register(ModalityRepository.class, modalityRepository);
            DependencyContainer.register(CountryService.class, countryService);
            DependencyContainer.register(ModalityService.class, modalityService);

            // Initialize main view and controller
            MainView mainView = new MainView();
            MainController mainController = new MainController(
                    mainView,
                    countryService,
                    modalityService
            );

            // Display main view
            mainView.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}