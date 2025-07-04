package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.FomatoInvalidoException;
import it.uniroma2.progettoispw.controller.bean.UtenteRegistrationData;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.controller.bean.DottoreRegistrationData;
import it.uniroma2.progettoispw.controller.bean.PazienteRegistrationData;
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

public class RegistrationViewController extends GuiGraphicController {
    private LogInController logInController = new LogInController();
    private MenuWindowManager menuWindowManager;

    @FXML
    private TextField codiceFiscaleField;


    @FXML
    private Label codiceField;

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
    void indietro(ActionEvent event) {
        menuWindowManager.showLogin();
    }

    @FXML
    void handleRegistration(ActionEvent event) throws IOException {
        String messaggio;
        try {
            if (dottoreCheckBox.isSelected()) {
                DottoreRegistrationData drd = new DottoreRegistrationData();
                setInformation(drd);
                int id = logInController.registerDottore(drd);
                messaggio = "Il tuo codice è: " + id;
            } else {
                PazienteRegistrationData prd= new PazienteRegistrationData();
                setInformation(prd);
                logInController.registerPaziente(prd);
                messaggio = "";
            }
        } catch (FomatoInvalidoException | DaoException e) {
            showAlert(e.getMessage());
            e.printStackTrace();
            return;
        }

        try {
            Stage stage = menuWindowManager.getMainStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/RegistrationSuccessView.fxml"));
            Parent root = loader.load();
            ((GuiGraphicController)loader.getController()).initialize(new Object[]{messaggio, menuWindowManager});
            stage.setScene(new Scene(root));
            stage.show();
        } catch (FomatoInvalidoException | DaoException e) {
            showAlert(e.getMessage());
        }


    }

    private void setInformation(UtenteRegistrationData urd) throws FomatoInvalidoException {
        urd.setCodiceFiscale(codiceFiscaleField.getText());
        urd.setCognome(cognomeField.getText());
        urd.setEmail(emailField.getText());
        urd.setNome(nomeField.getText());
        urd.setPassword(passwordField.getText());
        urd.setTelefono(telefonoField.getText());
        urd.setDataNascita(dataNascitaField.getValue());
    }

    @FXML
    void returnToLogin(ActionEvent event){
        menuWindowManager.showLogin();
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.menuWindowManager = (MenuWindowManager) args[0];
    }
}
