package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;

import it.uniroma2.progettoispw.controller.controllerApplicativi.FomatoInvalidoException;
import it.uniroma2.progettoispw.controller.controllerApplicativi.LogInController;
import it.uniroma2.progettoispw.controller.bean.DottoreRegistrationData;
import it.uniroma2.progettoispw.controller.bean.PazienteRegistrationData;
import it.uniroma2.progettoispw.controller.bean.UtenteRegistrationData;
import it.uniroma2.progettoispw.model.dao.DaoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
    private Label messageLabel;

    @FXML
    void handleRegistration(ActionEvent event) throws IOException {
        UtenteRegistrationData urd;
        try {

            if (dottoreCheckBox.isSelected()) {
                urd = new DottoreRegistrationData(codiceFiscaleField.getText(), nomeField.getText(), cognomeField.getText(),
                            emailField.getText(), telefonoField.getText(), dataNascitaField.getValue(), Integer.parseInt(codiceField.getText()),
                        passwordField.getText());
            } else {
                urd = new PazienteRegistrationData(codiceFiscaleField.getText(), nomeField.getText(), cognomeField.getText(),
                        emailField.getText(), telefonoField.getText(), dataNascitaField.getValue(),
                        passwordField.getText());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            logInController.register(urd);
            Stage stage = (Stage) errorLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/RegistrationSuccessView.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (FomatoInvalidoException | DaoException e) {
            showAlert(e.getMessage());
        }


    }

    private void showAlert(String message) {
        errorLabel.setText(message);
    }

    @FXML
    void returnToLogin(ActionEvent event) throws IOException {
        GuiWindowManager.getInstance().loadLogin();
    }
}
