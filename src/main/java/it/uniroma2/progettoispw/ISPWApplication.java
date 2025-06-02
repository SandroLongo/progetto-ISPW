package it.uniroma2.progettoispw;

import it.uniroma2.progettoispw.controller.graphicController.LoginGraphicController;
import it.uniroma2.progettoispw.controller.graphicController.TerapiaGraphicController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ISPWApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Application");
//            LoginGraphicController loginGraphicController = new LoginGraphicController();
//            loginGraphicController.start(stage);
            TerapiaGraphicController terapiaGraphicController = new TerapiaGraphicController();
            terapiaGraphicController.start(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}