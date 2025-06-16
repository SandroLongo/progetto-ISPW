package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;

import it.uniroma2.progettoispw.model.domain.DoseConfezione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DoseConfezioneController {
    private DoseConfezione doseConfezione;
    private TerapiaGui terapiaGui;

    public void inizialize(DoseConfezione dose, TerapiaGui terapiaGui) {
        this.terapiaGui = terapiaGui;
        this.doseConfezione = dose;
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

    private void update() {
        nome.setText(doseConfezione.getConfezione().getDenominazione());
        orario.setText(doseConfezione.getOrario().toString());
        quantita.setText(String.valueOf(doseConfezione.getQuantita()));
        tipo.setText("Confezione");
    }

}
