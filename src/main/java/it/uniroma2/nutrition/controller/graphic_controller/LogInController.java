package it.uniroma2.nutrition.controller.graphic_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import it.uniroma2.nutrition.model.domain.Credential;

public class LogInController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginWelcome;


    private Credential cred;

    public void submit(ActionEvent event) {

        cred = new Credential(username.getText(), password.getText());
        System.out.println("your credential are " + cred.toString());
    }

}
