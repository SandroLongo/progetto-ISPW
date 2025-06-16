package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiApp extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("application");
        GuiWindowManager.getInstance().setMainStage(stage);
        GuiWindowManager.getInstance().loadLogin();
    }

}
