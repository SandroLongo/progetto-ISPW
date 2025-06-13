package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;

import it.uniroma2.progettoispw.controller.controllerApplicativi.LogInController;
import it.uniroma2.progettoispw.model.bean.DottoreLogInData;
import it.uniroma2.progettoispw.model.bean.PazienteLogInData;
import it.uniroma2.progettoispw.model.bean.UtenteLogInData;
import it.uniroma2.progettoispw.model.domain.Dottore;
import it.uniroma2.progettoispw.model.domain.Paziente;
import it.uniroma2.progettoispw.model.domain.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInViewController {
    private LogInController logInController = new LogInController();
    private Stage stage;
    @FXML
    private Label errorLabel;
    @FXML
    private CheckBox checkMedico;
    @FXML
    private TextField codiceField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField codiceMedicoField;

    @FXML
    void handleLogIn() throws IOException {
        String codice = codiceField.getText();
        String password = passwordField.getText();
        UtenteLogInData utenteLogInData;
        if (checkMedico.isSelected()) {
            int codiceMedico = Integer.parseInt(codiceMedicoField.getText());
            utenteLogInData = new DottoreLogInData(codice, password, codiceMedico);
        } else {
            utenteLogInData = new PazienteLogInData(codice, password);
        }

        Utente utente = logInController.validate(utenteLogInData);

        FXMLLoader loader;
        switch (utente.isType()) {
            case Dottore -> loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/MenuDottore"));
            case Paziente -> loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/MenuPaziente.fxml"));
            default -> {
                showAlert("Login fallito, credenziali errate");
                return;
            }
        }

        Parent root = loader.load();
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void handleRegistration(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/Registrazione.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(String message) {
        errorLabel.setText(message);
    }
}
