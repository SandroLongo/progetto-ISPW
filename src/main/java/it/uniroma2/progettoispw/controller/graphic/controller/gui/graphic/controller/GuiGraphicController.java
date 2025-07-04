package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller;

import javafx.scene.control.Alert;

import java.io.IOException;

public abstract class GuiGraphicController {

    public abstract void initialize(Object[] args) throws IOException;

    public void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
