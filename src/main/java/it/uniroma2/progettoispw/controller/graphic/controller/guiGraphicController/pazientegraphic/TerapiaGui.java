package it.uniroma2.progettoispw.controller.graphic.controller.guiGraphicController.pazientegraphic;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.controller.applicativi.TerapiaController;
import it.uniroma2.progettoispw.controller.graphic.controller.Notificator;
import it.uniroma2.progettoispw.controller.graphic.controller.guiGraphicController.DoseAccepter;
import it.uniroma2.progettoispw.controller.graphic.controller.guiGraphicController.FinalAccepter;
import it.uniroma2.progettoispw.controller.graphic.controller.guiGraphicController.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.guiGraphicController.MenuWindowManager;
import it.uniroma2.progettoispw.model.domain.*;
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

public class TerapiaGui extends Notificator implements DoseAccepter, FinalAccepter, GuiGraphicController{
    private TerapiaGiornalieraBean terapiaGiornaliera;
    private TerapiaController terapiaController;
    private String gruppo;
    private AuthenticationBean authBean;
    private DoseCostructor doseCostructor;
    private MenuWindowManager menuWindowManager;

    @FXML
    private DatePicker datePicker;

    @FXML
    private VBox doseList;

    @FXML
    void addMedication(ActionEvent event) throws IOException {
        doseCostructor = new DoseCostructor();
        menuWindowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/RicercaConfezione.fxml", this, gruppo);
    }

    @FXML
    void dateChanged(ActionEvent event) throws IOException {
        terapiaGiornaliera.deleteNotificator(this);
        terapiaGiornaliera = terapiaController.getTerapiaGiornaliera(authBean.getCodice(), datePicker.getValue());
        terapiaGiornaliera.addNotificator(this);
        update();
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        terapiaGiornaliera.deleteNotificator(this);
        terapiaGiornaliera = terapiaController.getTerapiaGiornaliera(authBean.getCodice(), datePicker.getValue());
        terapiaGiornaliera.addNotificator(this);
        update();
    }

    public void initialize(Object[] args) throws IOException {
        this.authBean = (AuthenticationBean) args[1];
        this.gruppo = (String) args[0];
        this.menuWindowManager = (MenuWindowManager) args[2];
        terapiaController = new TerapiaController();
        datePicker.setValue(LocalDate.now());
        terapiaGiornaliera = terapiaController.getTerapiaGiornaliera(authBean.getCodice(), datePicker.getValue());
        terapiaGiornaliera.addNotificator(this);
        update();

    }
    public void setTerapia(LocalDate date){

    }

    public void update() {
        doseList.getChildren().clear();
        HBox doseItem;
        for (List<DoseBean> dosiPerOrario: this.terapiaGiornaliera.getDosiPerOrario().values()){
            for (DoseBean dose: dosiPerOrario){
                System.out.println(dose);
                TipoDose tipo = dose.getTipo();
                switch (tipo) {
                    case Confezione -> {FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/DoseConfezioneItem.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        DoseConfezioneController doseConfezioneController= loader.getController();
                        doseConfezioneController.inizialize(dose, this);
                        doseList.getChildren().add(root);}
                    case PrincipioAttivo -> {FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/DosePrincipioItem.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        DosePrincipioController dosePrincipioController= loader.getController();
                        dosePrincipioController.inizialize(dose, this);
                        doseList.getChildren().add(root);}
                    default -> {throw new RuntimeException("default switchcase");}
                }

            }
        }
    }

    @Override
    public void setFinalInformation(FinalStepBean finalStep) {
        doseCostructor.setInizio(finalStep.getInizio());
        doseCostructor.setNumRipetizioni(finalStep.getNumRipetizioni());
        doseCostructor.setRateGiorni(finalStep.getRateGiorni());
        doseCostructor.getDose().setDescrizione(finalStep.getDescrizioneMedica());
        doseCostructor.getDose().setOrario(finalStep.getOrario());
        doseCostructor.getDose().setUnitaMisura(finalStep.getUnitaMisura());
        doseCostructor.getDose().setQuantita(finalStep.getQuantita());
        doseCostructor.getDose().setAssunta(false);
        terapiaController.addDose(authBean.getCodice(), doseCostructor);
        menuWindowManager.deleteTop(gruppo);
        menuWindowManager.show(gruppo);
        update();
    }

    @Override
    public void setDose(DoseBean dose) {
        this.doseCostructor.setDose(dose);
        menuWindowManager.deleteTop(gruppo);
        try {
            menuWindowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/AggiungiView.fxml", this,gruppo, menuWindowManager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifica() {
        update();
    }
}
