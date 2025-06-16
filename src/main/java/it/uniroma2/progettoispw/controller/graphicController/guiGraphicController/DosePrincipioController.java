package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;

import it.uniroma2.progettoispw.model.domain.DosePrincipioAttivo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DosePrincipioController {
    private DosePrincipioAttivo dosePrincipio;
    private TerapiaGui terapiaGui;

    public void inizialize(DosePrincipioAttivo dose, TerapiaGui terapiaGui) {
        this.terapiaGui = terapiaGui;
        this.dosePrincipio = dose;
        update();
    }

    @FXML
    private HBox item;

    @FXML
    private Label nome;

    @FXML
    private Label orario;

    @FXML
    private Label quantita;

    @FXML
    private Label tipo;

    @FXML
    private Label unitaDiMisura;

    @FXML
    void assunta(ActionEvent event) {

    }

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void getInfo(ActionEvent event) {

    }

    @FXML
    void sostituisci(ActionEvent event) {

    }

    private void update() {
        nome.setText(dosePrincipio.getPrincipioAttivo().getNome());
        orario.setText(dosePrincipio.getOrario().toString());
        quantita.setText(String.valueOf(dosePrincipio.getQuantita()));
        tipo.setText("Principio");
    }

}
