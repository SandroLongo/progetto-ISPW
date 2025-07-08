package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.patientgraphic;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.bean.PrescriptionBean;
import it.uniroma2.progettoispw.controller.controller.applicativi.TherapyController;
import it.uniroma2.progettoispw.controller.graphic.controller.Notificator;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.DoseAccepter;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.FinalAccepter;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.WindowManager;
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

public class TherapyViewController extends GuiGraphicController implements DoseAccepter, FinalAccepter, Notificator {
    private DailyTherapyBean terapiaGiornaliera;
    private TherapyController therapyController;
    private String gruppo;
    private AuthenticationBean authBean;
    private PrescriptionBean prescriptionBean;
    private WindowManager windowManager;

    @FXML
    private DatePicker datePicker;

    @FXML
    private VBox doseList;

    @FXML
    void addMedication(ActionEvent event) throws IOException {
        prescriptionBean = new PrescriptionBean();
        windowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/RicercaConfezione.fxml", this, gruppo, windowManager);
    }

    @FXML
    void dateChanged(ActionEvent event) throws IOException {
        DailyTherapyBean dailyTherapyBean = therapyController.getDailyTherapy(authBean.getCodice(), datePicker.getValue());
        changeList(dailyTherapyBean);
    }

    public void initialize(Object[] args) throws IOException {
        this.authBean = (AuthenticationBean) args[1];
        this.gruppo = (String) args[0];
        this.windowManager = (WindowManager) args[2];
        therapyController = new TherapyController();
        datePicker.setValue(LocalDate.now());
        DailyTherapyBean dailyTherapyBean = therapyController.getDailyTherapy(authBean.getCodice(), datePicker.getValue());
        changeList(dailyTherapyBean);

    }
    public void changeList(DailyTherapyBean dailyTherapyBean){
        if (terapiaGiornaliera != null) {
            terapiaGiornaliera.deleteNotificator(this);
        }
        this.terapiaGiornaliera = dailyTherapyBean;
        terapiaGiornaliera.addNotificator(this);
        updateList();
    }
    public void updateList(){
        doseList.getChildren().clear();
        for (List<DoseBean> dosiPerOrario: this.terapiaGiornaliera.getDosiPerOrario().values()){
            for (DoseBean dose: dosiPerOrario){
                MedicationType tipo = dose.getType();
                if (Objects.requireNonNull(tipo) == MedicationType.MEDICINALPRODUCT) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/DoseConfezioneItem.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        ((Stage) datePicker.getScene().getWindow()).close();
                        return;
                    }
                    MedicinalProductController medicinalProductController = loader.getController();
                    medicinalProductController.inizialize(dose, this);
                    doseList.getChildren().add(root);
                } else if (tipo == MedicationType.ACRIVEINGREDIENT) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/DosePrincipioItem.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        ((Stage) datePicker.getScene().getWindow()).close();
                        return;
                    }
                    ActiveIngredientController activeIngredientController = loader.getController();
                    activeIngredientController.inizialize(dose, this);
                    doseList.getChildren().add(root);
                }

            }
        }
    }

    @Override
    public void setFinalInformation(FinalStepBean finalStep) {
        prescriptionBean.setLastInformation(finalStep);
        therapyController.addDose(authBean.getCodice(), prescriptionBean);
        windowManager.deleteTop(gruppo);
        windowManager.show(gruppo);
    }

    @Override
    public void setDose(MedicationBean medicationBean) {
        this.prescriptionBean.setDose(medicationBean);
        windowManager.deleteTop(gruppo);
        try {
            windowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/AggiungiView.fxml", this,gruppo, windowManager);
        } catch (IOException e) {
            ((Stage)datePicker.getScene().getWindow()).close();
        }
    }

    @Override
    public void notifyChanges() {
        updateList();
    }
}
