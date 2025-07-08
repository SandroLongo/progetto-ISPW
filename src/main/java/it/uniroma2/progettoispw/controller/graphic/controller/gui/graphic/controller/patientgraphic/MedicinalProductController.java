package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.patientgraphic;

import it.uniroma2.progettoispw.controller.bean.DoseBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MedicinalProductController {
    private DoseBean bean;
    private TherapyViewController therapyViewController;

    public void inizialize(DoseBean dose, TherapyViewController therapyViewController) {
        this.therapyViewController = therapyViewController;
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

    private void update() {
        nome.setText(bean.getName());
        orario.setText(bean.getScheduledTime().toString());
        quantita.setText(String.valueOf(bean.getQuantity()));
        tipo.setText("Confezione");
    }

}
