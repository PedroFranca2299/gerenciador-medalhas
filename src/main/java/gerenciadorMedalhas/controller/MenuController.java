package gerenciadorMedalhas.controller;

import gerenciadorMedalhas.model.Pais;
import gerenciadorMedalhas.model.PaisService;
import gerenciadorMedalhas.model.Modalidade;
import gerenciadorMedalhas.model.ModalidadeService;
import gerenciadorMedalhas.view.MenuView;

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

        this.menuView.addEscolherParticipantesListener(e -> escolherParticipantes());
        this.menuView.addEscolherModalidadesListener(e -> escolherModalidades());
        this.menuView.setVisible(true);
    }

    private void escolherParticipantes() {
        List<Pais> paises = paisService.fetchPaises();
        List<String> paisesNomes = new ArrayList<>();
        List<Integer> paisesIds = new ArrayList<>();

        for (Pais pais : paises) {
            paisesNomes.add(pais.getNome());
            paisesIds.add(pais.getId());
        }

        menuView.showCheckboxFrame("Escolher Participantes", paisesNomes, paisesIds, true);
    }

    private void escolherModalidades() {
        List<Modalidade> modalidades = modalidadeService.fetchModalidades();
        List<String> modalidadesNomes = new ArrayList<>();
        List<Integer> modalidadesIds = new ArrayList<>();

        for (Modalidade modalidade : modalidades) {
            modalidadesNomes.add(modalidade.getNome());
            modalidadesIds.add(modalidade.getId());
        }

        menuView.showCheckboxFrame("Escolher Modalidades", modalidadesNomes, modalidadesIds, false);
    }
}
            modalidadesIds.add(modalidade.getId()); // Supondo que você tenha um metodo getId()
        }

        menuView.showCheckboxFrame("Escolher Modalidades", modalidadesNomes, modalidadesIds);
    }
}
