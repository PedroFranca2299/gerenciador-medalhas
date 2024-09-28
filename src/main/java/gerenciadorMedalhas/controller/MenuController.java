package gerenciadorMedalhas.controller;

import gerenciadorMedalhas.model.PaisService;
import gerenciadorMedalhas.view.MenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuController {

    private MenuView menuView;
    private PaisService paisService;

    public MenuController(MenuView menuView, PaisService paisService) {
        this.menuView = menuView;
        this.paisService = paisService;

        // Adiciona ação ao botão Escolher Participantes
        this.menuView.addEscolherParticipantesListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirNovoFrame("Escolher Participantes");
            }
        });

        // Adiciona ação ao botão Escolher Modalidades
        this.menuView.addEscolherModalidadesListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirNovoFrame("Escolher Modalidades");
            }
        });

        // Adiciona ação aos outros botões conforme necessário
    }

    private void abrirNovoFrame(String titulo) {
        List<String> paises = paisService.fetchPaises();
        menuView.showNewFrame(titulo, paises);
    }
}
