package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;

import it.uniroma2.progettoispw.controller.bean.FinalStepBean;
import it.uniroma2.progettoispw.controller.controllerApplicativi.TerapiaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AggiungiFinalStepController {
    private TerapiaController terapiaController;


    @FXML
    private DatePicker dataIniziale;

    @FXML
    private TextArea descrizione;

    @FXML
    private Spinner<Integer> minutiPicker;

    @FXML
    private TextField numGiorni;

    @FXML
    private Spinner<Integer> oraPIcker;

    @FXML
    private TextField quantita;

    @FXML
    private TextField rateGiorni;

    @FXML
    private TextField unitaMisura;

    public void initialize(TerapiaController terapiaController) {
        this.terapiaController = terapiaController;
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12);

        oraPIcker.setValueFactory(valueFactory);
        oraPIcker.setEditable(true);
        minutiPicker.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0)
        );
        minutiPicker.setEditable(true);
        rateGiorni.setText("1");
        numGiorni.setText("1");
        dataIniziale.setValue(LocalDate.now());
    }

    @FXML
    void aggiungi(ActionEvent event) throws IOException {
        FinalStepBean finalStepBean = new FinalStepBean(dataIniziale.getValue(), Integer.parseInt(numGiorni.getText()), Integer.parseInt(rateGiorni.getText()),
                                                            Integer.parseInt(quantita.getText()), unitaMisura.getText(), LocalTime.of(oraPIcker.getValue(), minutiPicker.getValue()),
                                                            descrizione.getText());
        terapiaController.setFinalInformation(finalStepBean);
        terapiaController.loadDoseInviata();
        GuiWindowManager.getInstance().loadTerapia();
    }

}

