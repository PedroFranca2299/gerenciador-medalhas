package gerenciadorMedalhas.controller;

import gerenciadorMedalhas.model.Pais;
import gerenciadorMedalhas.model.PaisService;
import gerenciadorMedalhas.model.Modalidade;
import gerenciadorMedalhas.model.ModalidadeService;
import gerenciadorMedalhas.view.MenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MenuController {
    private PaisService paisService;
    private ModalidadeService modalidadeService;
    private MenuView menuView;

    public MenuController(MenuView menuView, PaisService paisService, ModalidadeService modalidadeService) {
        this.menuView = menuView;
        this.paisService = paisService;
        this.modalidadeService = modalidadeService;

        // Adiciona listeners
        this.menuView.addEscolherParticipantesListener(e -> escolherParticipantes());
        this.menuView.addEscolherModalidadesListener(e -> escolherModalidades());

        // Mostra a view
        this.menuView.setVisible(true);
    }

    private void escolherParticipantes() {
        List<Pais> paises = paisService.fetchPaises(); // Metodo para buscar países
        List<String> paisesNomes = new ArrayList<>();
        List<Integer> paisesIds = new ArrayList<>();

        for (Pais pais : paises) {
            paisesNomes.add(pais.getNome());
            paisesIds.add(pais.getId());
        }

        menuView.showCheckboxFrame("Escolher Participantes", paisesNomes, paisesIds);
    }

    private void escolherModalidades() {
        List<Modalidade> modalidades = modalidadeService.fetchModalidades(); // Metodo para buscar modalidades
        List<String> modalidadesNomes = new ArrayList<>();
        List<Integer> modalidadesIds = new ArrayList<>();

        for (Modalidade modalidade : modalidades) {
            modalidadesNomes.add(modalidade.getNome());
            modalidadesIds.add(modalidade.getId()); // Supondo que você tenha um metodo getId()
        }

        menuView.showCheckboxFrame("Escolher Modalidades", modalidadesNomes, modalidadesIds);
    }
}