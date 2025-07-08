package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.doctorgraphic;

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

public class SelectPatientViewController extends GuiGraphicController {
    private SendPrescriptionBundleController sendPrescriptionBundleController;
    private String group;
    private AuthenticationBean authenticationBean;
    private UserInformation userInformation;
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
    void find(ActionEvent event) {
        this.userInformation = sendPrescriptionBundleController.getPatientInformation(authenticationBean.getCodice(), cFField.getText());
        cognomeLabel.setText(userInformation.getSurname());
        nomeLabel.setText(userInformation.getName());
        emailLabel.setText(userInformation.getEmail());
        numeroLabel.setText(userInformation.getPhoneNumber());
        confermaButton.setDisable(false);
    }

    @FXML
    void confirm() throws IOException {
        windowManager.addScene(group, "/it/uniroma2/progettoispw/view/RecapRichiestaView.fxml", group, sendPrescriptionBundleController, authenticationBean, userInformation, windowManager);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.group = (String) args[0];
        this.authenticationBean = (AuthenticationBean) args[1];
        this.windowManager = (WindowManager) args[2];
        this.sendPrescriptionBundleController = new SendPrescriptionBundleController();
    }
}