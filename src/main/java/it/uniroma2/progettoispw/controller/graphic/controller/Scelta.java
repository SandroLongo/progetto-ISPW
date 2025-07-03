package it.uniroma2.progettoispw.controller.graphic.controller;

import it.uniroma2.progettoispw.controller.graphic.controller.cliGraphicController.CliApp;
import it.uniroma2.progettoispw.controller.graphic.controller.guiGraphicController.GuiApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Scelta{

    @FXML
    private Label label;

    @FXML
    void cliGui(ActionEvent event) {
        Stage stage = (Stage) label.getScene().getWindow();
        CliApp cliApp = new CliApp();
        try {
            cliApp.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void normalGui(ActionEvent event) {
        Stage stage = (Stage) label.getScene().getWindow();
        GuiApp guiApp = new GuiApp();
        try {
            guiApp.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}