package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.medicographic;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.bean.UserInformation;
import it.uniroma2.progettoispw.controller.controller.applicativi.SendPrescriptionBundleController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SelezionaPazienteViewController extends GuiGraphicController {
    private SendPrescriptionBundleController sendPrescriptionBundleController;
    private String gruppo;
    private AuthenticationBean authenticationBean;
    private UserInformation utente;
    private WindowManager windowManager;

    @FXML
    private TextField cFField;

    @FXML
    private Label cognomeLabel;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label numeroLabel;

    @FXML
    private Button confermaButton;


    @FXML
    void cerca(ActionEvent event) {
        this.utente = sendPrescriptionBundleController.getPatientInformation(authenticationBean.getCodice(), cFField.getText());
        cognomeLabel.setText(utente.getSurname());
        nomeLabel.setText(utente.getName());
        emailLabel.setText(utente.getEmail());
        numeroLabel.setText(utente.getPhoneNumber());
        confermaButton.setDisable(false);
    }

    @FXML
    void confermaDati() throws IOException {
        windowManager.addScene(gruppo, "/it/uniroma2/progettoispw/view/RecapRichiestaView.fxml", gruppo, sendPrescriptionBundleController, authenticationBean, utente, windowManager);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.gruppo = (String) args[0];
        this.authenticationBean = (AuthenticationBean) args[1];
        this.windowManager = (WindowManager) args[2];
        this.sendPrescriptionBundleController = new SendPrescriptionBundleController();
    }
}