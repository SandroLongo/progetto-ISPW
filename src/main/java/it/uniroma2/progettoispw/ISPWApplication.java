package it.uniroma2.progettoispw;

import it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.CliApp;
import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.GuiApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ISPWApplication{

    public static void main(String[] args) {
        try {
            CliApp.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //javafx.application.Application.launch(GuiApp.class, args);
    }
}