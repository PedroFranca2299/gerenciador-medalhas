package gerenciadorMedalhas.app;

import gerenciadorMedalhas.controller.MenuController;
import gerenciadorMedalhas.model.PaisService;
import gerenciadorMedalhas.model.ModalidadeService;
import gerenciadorMedalhas.view.MenuView;

public class MySwingApp {
    public static void main(String[] args) {
        try {
            // Instancia os services
            PaisService paisService = new PaisService();
            ModalidadeService modalidadeService = new ModalidadeService();

            // Instancia a view
            MenuView menuView = new MenuView();

            // Instancia o controller e passa a view e os services
            MenuController menuController = new MenuController(menuView, paisService, modalidadeService);

            // Exibe a view principal
            menuView.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
