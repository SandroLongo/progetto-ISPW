package it.uniroma2.progettoispw;

import it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.CliApp;
import it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.FakeTerminalApp;
import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.GuiApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ISPWApplication {

    public static void main(String[] args) {
        javafx.application.Application.launch(CliApp.class, args);
        //javafx.application.Application.launch(GuiApp.class, args);
    }
}