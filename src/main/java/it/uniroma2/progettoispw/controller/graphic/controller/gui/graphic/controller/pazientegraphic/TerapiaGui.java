package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.pazientegraphic;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.bean.Prescription;
import it.uniroma2.progettoispw.controller.controller.applicativi.TherapyController;
import it.uniroma2.progettoispw.controller.graphic.controller.Notificator;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.DoseAccepter;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.FinalAccepter;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.MenuWindowManager;
import it.uniroma2.progettoispw.model.domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class TerapiaGui extends GuiGraphicController implements DoseAccepter, FinalAccepter, Notificator {
    private DailyTherapyBean terapiaGiornaliera;
    private TherapyController therapyController;
    private String gruppo;
    private AuthenticationBean authBean;
    private Prescription prescription;
    private MenuWindowManager menuWindowManager;

    @FXML
    private DatePicker datePicker;

    @FXML
    private VBox doseList;

    @FXML
    void addMedication(ActionEvent event) throws IOException {
        prescription = new Prescription();
        menuWindowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/RicercaConfezione.fxml", this, gruppo, menuWindowManager);
    }

    @FXML
    void dateChanged(ActionEvent event) throws IOException {
        terapiaGiornaliera.deleteNotificator(this);
        terapiaGiornaliera = therapyController.getTerapiaGiornaliera(authBean.getCodice(), datePicker.getValue());
        terapiaGiornaliera.addNotificator(this);
        update();
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        terapiaGiornaliera.deleteNotificator(this);
        terapiaGiornaliera = therapyController.getTerapiaGiornaliera(authBean.getCodice(), datePicker.getValue());
        terapiaGiornaliera.addNotificator(this);
        update();
    }

    public void initialize(Object[] args) throws IOException {
        this.authBean = (AuthenticationBean) args[1];
        this.gruppo = (String) args[0];
        this.menuWindowManager = (MenuWindowManager) args[2];
        therapyController = new TherapyController();
        datePicker.setValue(LocalDate.now());
        terapiaGiornaliera = therapyController.getTerapiaGiornaliera(authBean.getCodice(), datePicker.getValue());
        terapiaGiornaliera.addNotificator(this);
        update();

    }

    public void update() {
        doseList.getChildren().clear();
        for (List<DoseBean> dosiPerOrario: this.terapiaGiornaliera.getDosiPerOrario().values()){
            for (DoseBean dose: dosiPerOrario){
                MediccationType tipo = dose.getTipo();
                if (Objects.requireNonNull(tipo) == MediccationType.CONFEZIONE) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/DoseConfezioneItem.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                        ((Stage) datePicker.getScene().getWindow()).close();
                        return;
                    }
                    DoseConfezioneController doseConfezioneController = loader.getController();
                    doseConfezioneController.inizialize(dose, this);
                    doseList.getChildren().add(root);
                } else if (tipo == MediccationType.PRINCIPIOATTIVO) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/DosePrincipioItem.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                        ((Stage) datePicker.getScene().getWindow()).close();
                        return;
                    }
                    DosePrincipioController dosePrincipioController = loader.getController();
                    dosePrincipioController.inizialize(dose, this);
                    doseList.getChildren().add(root);
                }

            }
        }
    }

    @Override
    public void setFinalInformation(FinalStepBean finalStep) {
        prescription.setInizio(finalStep.getInizio());
        prescription.setNumRipetizioni(finalStep.getNumRipetizioni());
        prescription.setRateGiorni(finalStep.getRateGiorni());
        prescription.getDose().setDescrizione(finalStep.getDescrizioneMedica());
        prescription.getDose().setOrario(finalStep.getOrario());
        prescription.getDose().setUnitaMisura(finalStep.getUnitaMisura());
        prescription.getDose().setQuantita(finalStep.getQuantita());
        prescription.getDose().setAssunta(false);
        therapyController.addDose(authBean.getCodice(), prescription);
        menuWindowManager.deleteTop(gruppo);
        menuWindowManager.show(gruppo);
        update();
    }

    @Override
    public void setDose(DoseBean dose) {
        this.prescription.setDose(dose);
        menuWindowManager.deleteTop(gruppo);
        try {
            menuWindowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/AggiungiView.fxml", this,gruppo, menuWindowManager);
        } catch (IOException e) {
            e.printStackTrace();
            ((Stage)datePicker.getScene().getWindow()).close();
        }
    }

    @Override
    public void notifica() {
        update();
    }
}
