package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.medicographic;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.controller.applicativi.SendPrescriptionBundleController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.DoseAccepter;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.FinalAccepter;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.MenuWindowManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RecapRichiestaGraphicController extends GuiGraphicController implements DoseAccepter, FinalAccepter {
    private SendPrescriptionBundleController sendPrescriptionBundleController;
    private String gruppo;
    private ObservableList<Object> dati;
    private PrescriptionBundleBean prescriptionBundleBean = new PrescriptionBundleBean();
    private AuthenticationBean authenticationBean;
    private Prescription prescription = new Prescription();
    private MenuWindowManager menuWindowManager;
    @FXML
    private Label cFLabel;

    @FXML
    private Label cognomeLabel;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label numeroLabel;

    @FXML
    private TableView<Object> recapTable;

    @FXML
    void indietro(ActionEvent event) {
        menuWindowManager.deleteAndcomeBack(gruppo);
    }

    @FXML
    void aggiungi(ActionEvent event) throws IOException {
        this.prescription = new Prescription();
        menuWindowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/RicercaConfezione.fxml", this, gruppo, menuWindowManager);
    }

    @FXML
    void invia(ActionEvent event) {
        sendPrescriptionBundleController.invia(authenticationBean.getCodice(), prescriptionBundleBean);
        menuWindowManager.deleteTop(gruppo);
        menuWindowManager.show(gruppo);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.sendPrescriptionBundleController = (SendPrescriptionBundleController) args[1];
        this.gruppo = (String) args[0];
        this.authenticationBean = (AuthenticationBean) args[2];
        this.prescriptionBundleBean.setRicevente((InformazioniUtente) args[3]);
        this.menuWindowManager = (MenuWindowManager) args[4];
        recapTable.getColumns().clear();

        TableColumn<Object, String> nome = new TableColumn<>("Nome");
        nome.setCellValueFactory(data -> new ReadOnlyStringWrapper(((Prescription)data.getValue()).getDose().getNome()));

        TableColumn<Object, String> quantita = new TableColumn<>("quantita");
        quantita.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((Prescription)data.getValue()).getDose().getQuantita())));

        TableColumn<Object, String> unitaDiMisura = new TableColumn<>("unita di misura");
        unitaDiMisura.setCellValueFactory(data -> new ReadOnlyStringWrapper(((Prescription)data.getValue()).getDose().getUnitaMisura()));

        TableColumn<Object, String> inizio = new TableColumn<>("Data inizio");
        inizio.setCellValueFactory(data -> new ReadOnlyStringWrapper(((Prescription)data.getValue()).getInizio().toString()));

        TableColumn<Object, String> numGiorni = new TableColumn<>("numero di volte");
        numGiorni.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((Prescription)data.getValue()).getNumRipetizioni())));

        TableColumn<Object, String> rate = new TableColumn<>("ogni tot giorni");
        rate.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((Prescription)data.getValue()).getRateGiorni())));

        TableColumn<Object, String> orario = new TableColumn<>("orario");
        orario.setCellValueFactory(data -> new ReadOnlyStringWrapper(((Prescription)data.getValue()).getDose().getOrario().toString()));

        TableColumn<Object, String> descrizione = new TableColumn<>("descrizione");
        descrizione.setCellValueFactory(data -> new ReadOnlyStringWrapper(((Prescription)data.getValue()).getDose().getDescrizione()));

        TableColumn<Object, Void> aggiungiCol = new TableColumn<>("elimina");
        aggiungiCol.setCellFactory(col -> new EliminaDoseCostructorButtonCell());

        recapTable.getColumns().addAll(nome, quantita, unitaDiMisura, descrizione, numGiorni, rate, orario);
        this.dati = FXCollections.observableArrayList();
        recapTable.setItems(dati);
        menuWindowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/RicercaConfezione.fxml", this, gruppo, menuWindowManager);
    }

    @Override
    public void setFinalInformation(FinalStepBean finalStep) throws IOException {
        prescription.setInizio(finalStep.getInizio());
        prescription.setNumRipetizioni(finalStep.getNumRipetizioni());
        prescription.setRateGiorni(finalStep.getRateGiorni());
        prescription.getDose().setDescrizione(finalStep.getDescrizioneMedica());
        prescription.getDose().setOrario(finalStep.getOrario());
        prescription.getDose().setUnitaMisura(finalStep.getUnitaMisura());
        prescription.getDose().setQuantita(finalStep.getQuantita());
        prescription.getDose().setAssunta(false);
        prescriptionBundleBean.addDoseCostructor(prescription);
        update();
        menuWindowManager.deleteTop(gruppo);
        menuWindowManager.show(gruppo);
    }

    public void update(){
        dati = FXCollections.observableArrayList(prescriptionBundleBean.getDosi());
        recapTable.setItems(dati);
    }

    @Override
    public void setDose(DoseBean dose) {
        this.prescription.setDose(dose);
        menuWindowManager.deleteTop(gruppo);
        try {
            menuWindowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/AggiungiView.fxml", this,gruppo, menuWindowManager);
        } catch (IOException e) {
            e.printStackTrace();
            ((Stage)recapTable.getScene().getWindow()).close();
        }
    }

    private class EliminaDoseCostructorButtonCell extends TableCell<Object, Void> {
        private final Button btn = new Button("elimina");

        public EliminaDoseCostructorButtonCell() {
            btn.setOnAction(event -> {
                Prescription selezione = (Prescription)getTableView().getItems().get(getIndex());
                prescriptionBundleBean.deleteDoseCostructor(selezione);
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(btn);
            }
        }
    }
}
