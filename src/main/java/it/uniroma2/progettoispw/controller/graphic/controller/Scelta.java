package it.uniroma2.progettoispw.controller.graphic.controller;

import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.CliApp;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiApp;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Scelta extends GuiGraphicController {
    private WindowManager windowManager;

    @FXML
    private Label label;

    @FXML
    void cliGui(ActionEvent event) {
        Stage stage = (Stage) label.getScene().getWindow();
        CliApp cliApp = new CliApp();
        try {
            cliApp.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
            stage.close();
        }
    }

    @FXML
    void normalGui(ActionEvent event) {
        Stage stage = (Stage) label.getScene().getWindow();
        GuiApp guiApp = new GuiApp();
        try {
            guiApp.start(stage,windowManager);
        } catch (Exception e) {
            e.printStackTrace();
            stage.close();
        }
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        windowManager = (WindowManager) args[0];
    }
}