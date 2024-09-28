package gerenciadorMedalhas.app;

import gerenciadorMedalhas.controller.MenuController;
import gerenciadorMedalhas.model.PaisService;
import gerenciadorMedalhas.view.MenuView;

public class MySwingApp {
    public static void main(String[] args) {
        // Instancia o service
        PaisService paisService = new PaisService();

        // Instancia a view
        MenuView menuView = new MenuView();

        // Instancia o controller e passa a view e o service
        MenuController menuController = new MenuController(menuView, paisService);

        // Exibe a view principal
        menuView.setVisible(true);
    }
}
