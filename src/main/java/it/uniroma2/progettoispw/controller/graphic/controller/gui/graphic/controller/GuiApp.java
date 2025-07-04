package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiApp {


    public void start(Stage stage) throws Exception {
        stage.setTitle("application");
        MenuWindowManager menuWindowManager = new MenuWindowManager(stage);
        menuWindowManager.setLoginScene("/it/uniroma2/progettoispw/view/LogInview.fxml", menuWindowManager);
        menuWindowManager.setRegisterScene("/it/uniroma2/progettoispw/view/Registrazione.fxml", menuWindowManager);
        menuWindowManager.showLogin();
    }

}
