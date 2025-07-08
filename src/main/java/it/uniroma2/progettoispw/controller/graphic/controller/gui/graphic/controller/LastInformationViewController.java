package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.FinalStepBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class LastInformationViewController extends GuiGraphicController {
    private FinalAccepter accepter;
    private String gruppo;
    private WindowManager windowManager;

    @FXML
    private DatePicker dataIniziale;

    @FXML
    private TextArea descrizione;

    @FXML
    private Spinner<Integer> minutiPicker;

    @FXML
    private TextField numGiorni;

    @FXML
    private Spinner<Integer> oraPicker;

    @FXML
    private TextField quantita;

    @FXML
    private TextField rateGiorni;

    @FXML
    private TextField unitaMisura;

    @FXML
    void back(ActionEvent event) {
        windowManager.deleteAndcomeBack(gruppo);
    }

    @Override
    public void initialize(Object[] args) {
        this.accepter = (FinalAccepter) args[0];
        this.gruppo = (String)args[1];
        this.windowManager = (WindowManager) args[2];
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12);

        oraPicker.setValueFactory(valueFactory);
        oraPicker.setEditable(true);
        minutiPicker.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0)
        );
        minutiPicker.setEditable(true);
        rateGiorni.setText("1");
        numGiorni.setText("1");
        dataIniziale.setValue(LocalDate.now());
    }



    @FXML
    void add(ActionEvent event) throws IOException {
        FinalStepBean finalStepBean = new FinalStepBean(dataIniziale.getValue(), Integer.parseInt(numGiorni.getText()), Integer.parseInt(rateGiorni.getText()),
                                                            Integer.parseInt(quantita.getText()), unitaMisura.getText(), LocalTime.of(oraPicker.getValue(), minutiPicker.getValue()),
                                                            descrizione.getText());
        accepter.setFinalInformation(finalStepBean);
    }
}

