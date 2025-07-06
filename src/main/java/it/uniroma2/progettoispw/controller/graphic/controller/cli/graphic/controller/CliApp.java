package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class CliApp {

    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/viewCli/Terminale.fxml"));
        Parent root = fxmlLoader.load();
        PromptController controller = fxmlLoader.getController();
        controller.initialize(new Object[]{new LogInReceiver(controller), stage});
        stage.setScene(new Scene(root));
        stage.show();
    }
}

