package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class RegistrationSuccessViewController extends GuiGraphicController {
    private WindowManager windowManager;

    @FXML
    private Label codiceField;

    @FXML
    private Label messageLabel;

    @FXML
    void returnToLogin() {
        windowManager.showLogin();
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.codiceField.setText((String) args[0]);
        this.windowManager = (WindowManager) args[1];
    }
}
