package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationSuccessViewController extends GuiGraphicController {
    private MenuWindowManager menuWindowManager;

    @FXML
    private Label codiceField;

    @FXML
    private Label messageLabel;

    @FXML
    void returnToLogin() {
        menuWindowManager.showLogin();
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.codiceField.setText((String) args[0]);
        this.menuWindowManager = (MenuWindowManager) args[1];
    }
}
