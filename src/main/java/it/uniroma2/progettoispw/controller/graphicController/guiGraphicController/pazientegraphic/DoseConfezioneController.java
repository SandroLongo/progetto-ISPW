package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.pazientegraphic;

import it.uniroma2.progettoispw.controller.bean.DoseBean;
import it.uniroma2.progettoispw.model.domain.DoseConfezione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DoseConfezioneController {
    private DoseBean bean;
    private TerapiaGui terapiaGui;

    public void inizialize(DoseBean dose, TerapiaGui terapiaGui) {
        this.terapiaGui = terapiaGui;
        this.bean = dose;
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
        nome.setText(bean.getNome());
        orario.setText(bean.getOrario().toString());
        quantita.setText(String.valueOf(bean.getQuantita()));
        tipo.setText("Confezione");
    }

}
