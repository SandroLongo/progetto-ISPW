package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.InvalidFormatException;
import it.uniroma2.progettoispw.controller.bean.UserRegistrationData;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.controller.bean.DoctorRegistrationData;
import it.uniroma2.progettoispw.controller.bean.PatientRegistrationData;
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
    private WindowManager windowManager;

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
    void back(ActionEvent event) {
        windowManager.showLogin();
    }

    @FXML
    void handleRegistration(ActionEvent event) throws IOException {
        String messaggio;
        try {
            if (dottoreCheckBox.isSelected()) {
                DoctorRegistrationData drd = new DoctorRegistrationData();
                setInformation(drd);
                int id = logInController.registerDoctor(drd);
                messaggio = "Il tuo codice è: " + id;
            } else {
                PatientRegistrationData prd= new PatientRegistrationData();
                setInformation(prd);
                logInController.registerPatient(prd);
                messaggio = "";
            }
        } catch (InvalidFormatException | DaoException e) {
            showAlert(e.getMessage());
            return;
        }

        try {
            Stage stage = windowManager.getMainStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/RegistrationSuccessView.fxml"));
            Parent root = loader.load();
            ((GuiGraphicController)loader.getController()).initialize(new Object[]{messaggio, windowManager});
            stage.setScene(new Scene(root));
            stage.show();
        } catch (InvalidFormatException | DaoException e) {
            showAlert(e.getMessage());
        }


    }

    private void setInformation(UserRegistrationData urd) throws InvalidFormatException {
        try {
            urd.setTaxCode(codiceFiscaleField.getText());
            urd.setSurname(cognomeField.getText());
            urd.setEmail(emailField.getText());
            urd.setName(nomeField.getText());
            urd.setPassword(passwordField.getText());
            urd.setPhoneNumber(telefonoField.getText());
            urd.setBirthDate(dataNascitaField.getValue());
        } catch (InvalidFormatException e) {
            showAlert(e.getMessage());
        }
    }

    @FXML
    void returnToLogin(ActionEvent event){
        windowManager.showLogin();
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.windowManager = (WindowManager) args[0];
    }
}
