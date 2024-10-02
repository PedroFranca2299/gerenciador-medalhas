package gerenciadorMedalhas.controller;

import gerenciadorMedalhas.model.Pais;
import gerenciadorMedalhas.model.PaisService;
import gerenciadorMedalhas.model.Modalidade;
import gerenciadorMedalhas.model.ModalidadeService;
import gerenciadorMedalhas.view.MenuView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Pais> paises = paisService.fetchPaises(); // Método para buscar países
        List<String> paisesNomes = paises.stream().map(Pais::getNome).collect(Collectors.toList());
        menuView.showCheckboxFrame("Escolher Participantes", paisesNomes);
    }

    private void escolherModalidades() {
        List<Modalidade> modalidades = modalidadeService.fetchModalidades(); // Método para buscar modalidades
        List<String> modalidadesNomes = modalidades.stream().map(Modalidade::getNome).collect(Collectors.toList());
        menuView.showCheckboxFrame("Escolher Modalidades", modalidadesNomes);
    }
}
