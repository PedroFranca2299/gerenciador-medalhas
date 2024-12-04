package com.medalmanager.application;

import com.medalmanager.config.DatabaseConfig;
import com.medalmanager.controller.MainController;
import com.medalmanager.repository.*;
import com.medalmanager.service.*;
import com.medalmanager.util.DatabaseInitializer;
import com.medalmanager.util.DependencyContainer;
import com.medalmanager.view.MainView;

public class MedalManagerApplication {
    public static void main(String[] args) {
        try {
            System.out.println("Starting Medal Manager Application...");

            DatabaseConfig.initialize();
            System.out.println("Database configuration initialized successfully");

            DatabaseInitializer.initialize();
            System.out.println("Database structure check completed");

            initializeComponents();

        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void initializeComponents() {
        CountryRepository countryRepository = new CountryRepository();
        ModalityRepository modalityRepository = new ModalityRepository();
        EtapaRepository etapaRepository = new EtapaRepository();
        ResultadoRepository resultadoRepository = new ResultadoRepository();

        CountryService countryService = new CountryService(countryRepository);
        ModalityService modalityService = new ModalityService(modalityRepository);
        EtapaService etapaService = new EtapaService(etapaRepository);

        ResultadoService resultadoService = new ResultadoService(
                resultadoRepository,
                modalityService,
                countryService,
                etapaService,
                countryRepository
        );

        RankingService rankingService = new RankingService(
                resultadoRepository,
                modalityService,
                countryService
        );

        registerDependencies(
                countryRepository, modalityRepository, etapaRepository,
                resultadoRepository, countryService, modalityService,
                etapaService, resultadoService, rankingService
        );

        launchMainApplication(
                countryService, modalityService, etapaService,
                resultadoService, rankingService
        );
    }

    private static void registerDependencies(
            CountryRepository countryRepository,
            ModalityRepository modalityRepository,
            EtapaRepository etapaRepository,
            ResultadoRepository resultadoRepository,
            CountryService countryService,
            ModalityService modalityService,
            EtapaService etapaService,
            ResultadoService resultadoService,
            RankingService rankingService) {

        DependencyContainer.register(CountryRepository.class, countryRepository);
        DependencyContainer.register(ModalityRepository.class, modalityRepository);
        DependencyContainer.register(EtapaRepository.class, etapaRepository);
        DependencyContainer.register(ResultadoRepository.class, resultadoRepository);
        DependencyContainer.register(CountryService.class, countryService);
        DependencyContainer.register(ModalityService.class, modalityService);
        DependencyContainer.register(EtapaService.class, etapaService);
        DependencyContainer.register(ResultadoService.class, resultadoService);
        DependencyContainer.register(RankingService.class, rankingService);
    }

    private static void launchMainApplication(
            CountryService countryService,
            ModalityService modalityService,
            EtapaService etapaService,
            ResultadoService resultadoService,
            RankingService rankingService) {

        MainView mainView = new MainView();
        MainController mainController = new MainController(
                mainView,
                countryService,
                modalityService,
                etapaService,
                resultadoService,
                rankingService
        );

        mainView.setVisible(true);
        System.out.println("Application started successfully");
    }
}