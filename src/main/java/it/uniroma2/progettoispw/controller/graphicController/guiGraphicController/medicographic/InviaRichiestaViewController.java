package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.medicographic;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.bean.InformazioniUtente;
import it.uniroma2.progettoispw.controller.controllerApplicativi.RichiesteController;
import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.MenuWindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class InviaRichiestaViewController implements GuiGraphicController {
    private RichiesteController richiesteController;
    private String gruppo;
    private AuthenticationBean authenticationBean;
    private InformazioniUtente utente;

    @FXML
    private TextField CFField;

    @FXML
    private Label CognomeLabel;

    @FXML
    private Label NomeLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label numeroLabel;

    @FXML
    private Button ConfermaButton;


    @FXML
    void conferma(ActionEvent event) {
        this.utente = richiesteController.getInformazioniPaziente(authenticationBean.getCodice(), CFField.getText());
        CognomeLabel.setText(utente.getCognome());
        NomeLabel.setText(utente.getNome());
        emailLabel.setText(utente.getEmail());
        numeroLabel.setText(utente.getTelefono());
        ConfermaButton.setDisable(false);
    }

    @FXML
    void ConfermaDati() throws IOException {
        MenuWindowManager.getInstance().addScene(gruppo, "/it/uniroma2/progettoispw/view/RecapRichiestaView.fxml", gruppo, richiesteController, authenticationBean, utente);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.gruppo = (String) args[0];
        this.authenticationBean = (AuthenticationBean) args[1];
        this.richiesteController = new RichiesteController();
    }
}