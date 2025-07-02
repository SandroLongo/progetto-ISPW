package it.uniroma2.progettoispw.controller.graphicController.cliGraphicController;

import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.GuiGraphicController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class CliApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/viewCli/Terminale.fxml"));
        Parent root = fxmlLoader.load();
        PromptController controller = fxmlLoader.getController();
        controller.initialize(new Object[]{new LogInReceiver(controller)});
        stage.setScene(new Scene(root));
        stage.show();
    }
}

