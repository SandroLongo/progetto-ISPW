package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller;

import javafx.stage.Stage;

public class GuiApp {


    public void start(Stage stage, WindowManager windowManager) throws Exception {
        stage.setTitle("application");
        windowManager.setLoginScene("/it/uniroma2/progettoispw/view/LogInview.fxml", windowManager);
        windowManager.setRegisterScene("/it/uniroma2/progettoispw/view/Registrazione.fxml", windowManager);
        windowManager.showLogin();
    }

}
