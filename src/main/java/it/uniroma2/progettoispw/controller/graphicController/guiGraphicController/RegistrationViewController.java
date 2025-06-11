package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;

import it.uniroma2.progettoispw.controller.controllerApplicativi.LogInController;
import it.uniroma2.progettoispw.model.bean.DottoreRegistrationData;
import it.uniroma2.progettoispw.model.bean.PazienteRegistrationData;
import it.uniroma2.progettoispw.model.bean.UtenteRegistrationData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegistrationViewController {
    private LogInController logInController = new LogInController();

    @FXML
    private TextField codiceFiscaleField;
    @FXML
    private TextField codiceField;

    @FXML
    private TextField cognomeField;

    @FXML
    private CheckBox dottoreCheckBox;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField telefonoField;

    @FXML
    private DatePicker dataNascitaField;

    @FXML
    private Label errorLabel;

    @FXML
    void handleRegistration(ActionEvent event) {
        try {
            UtenteRegistrationData urd;
            if (dottoreCheckBox.isSelected()) {
                urd = new DottoreRegistrationData(codiceFiscaleField.getText(), nomeField.getText(), cognomeField.getText(),
                            emailField.getText(), telefonoField.getText(), dataNascitaField.getValue(), Integer.parseInt(codiceField.getText()),
                        passwordField.getText());
            } else {
                urd = new PazienteRegistrationData(codiceFiscaleField.getText(), nomeField.getText(), cognomeField.getText(),
                        emailField.getText(), telefonoField.getText(), dataNascitaField.getValue(),
                        passwordField.getText());
            }
            logInController.register(urd);
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    private void showAlert(String message) {
        errorLabel.setText(message);
    }

}
