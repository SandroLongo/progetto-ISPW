package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;

import it.uniroma2.progettoispw.controller.controllerApplicativi.TerapiaController;
import it.uniroma2.progettoispw.model.domain.Dose;
import it.uniroma2.progettoispw.model.domain.DoseConfezione;
import it.uniroma2.progettoispw.model.domain.DosePrincipioAttivo;
import it.uniroma2.progettoispw.model.domain.TerapiaGiornaliera;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class TerapiaGui {
    private TerapiaGiornaliera terapiaGiornaliera;
    private TerapiaController terapiaController;

    @FXML
    private DatePicker datePicker;

    @FXML
    private VBox doseList;

    @FXML
    void addMedication(ActionEvent event) throws IOException {
        GuiWindowManager.getInstance().loadRicercaMedicinali(terapiaController);
    }

    @FXML
    void dateChanged(ActionEvent event) throws IOException {
        terapiaGiornaliera = terapiaController.switchTo(datePicker.getValue());
        update();
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        //terapiaGiornaliera = terapiaController.switchTo(datePicker.getValue());
        update();
    }

    public void inizialize() throws IOException {
        terapiaController = new TerapiaController();
        datePicker.setValue(LocalDate.now());
        terapiaGiornaliera = terapiaController.switchTo(datePicker.getValue());
        update();

    }
    public void setTerapia(LocalDate date){

    }

    public void update() throws IOException {
        doseList.getChildren().clear();
        HBox doseItem;
        for (List<Dose<?>> dosiPerOrario: this.terapiaGiornaliera.getDosiPerOrario().values()){
            for (Dose<?> dose: dosiPerOrario){
                System.out.println(dose);
                switch (dose.isType()) {
                    case Confezione -> {FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/DoseConfezioneItem.fxml"));
                                        Parent root = loader.load();
                                        DoseConfezioneController doseConfezioneController= loader.getController();
                                        doseConfezioneController.inizialize((DoseConfezione) dose, this);
                                        doseList.getChildren().add(root);}
                    case PrincipioAttivo -> {FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/DosePrincipioItem.fxml"));
                        Parent root = loader.load();
                        DosePrincipioController dosePrincipioController= loader.getController();
                        dosePrincipioController.inizialize((DosePrincipioAttivo) dose, this);
                        doseList.getChildren().add(root);}
                    default -> {throw new RuntimeException("default switchcase");}
                }

            }
        }
    }
}
