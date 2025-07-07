package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.medicographic;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.controller.applicativi.SendPrescriptionBundleController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.DoseAccepter;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.FinalAccepter;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.WindowManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RecapPacchettoGraphicController extends GuiGraphicController implements DoseAccepter, FinalAccepter {
    private SendPrescriptionBundleController sendPrescriptionBundleController;
    private String gruppo;
    private ObservableList<Object> dati;
    private PrescriptionBundleBean prescriptionBundleBean = new PrescriptionBundleBean();
    private AuthenticationBean authenticationBean;
    private PrescriptionBean prescriptionBean = new PrescriptionBean();
    private WindowManager windowManager;
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
        windowManager.deleteAndcomeBack(gruppo);
    }

    @FXML
    void aggiungi(ActionEvent event) throws IOException {
        this.prescriptionBean = new PrescriptionBean();
        windowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/RicercaConfezione.fxml", this, gruppo, windowManager);
    }

    @FXML
    void invia(ActionEvent event) {
        sendPrescriptionBundleController.send(authenticationBean.getCodice(), prescriptionBundleBean);
        windowManager.deleteTop(gruppo);
        windowManager.show(gruppo);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.sendPrescriptionBundleController = (SendPrescriptionBundleController) args[1];
        this.gruppo = (String) args[0];
        this.authenticationBean = (AuthenticationBean) args[2];
        this.prescriptionBundleBean.setReceiver((UserInformation) args[3]);
        this.windowManager = (WindowManager) args[4];
        recapTable.getColumns().clear();

        TableColumn<Object, String> nome = new TableColumn<>("Nome");
        nome.setCellValueFactory(data -> new ReadOnlyStringWrapper(((PrescriptionBean)data.getValue()).getDose().getName()));

        TableColumn<Object, String> quantita = new TableColumn<>("quantita");
        quantita.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((PrescriptionBean)data.getValue()).getDose().getQuantity())));

        TableColumn<Object, String> unitaDiMisura = new TableColumn<>("unita di misura");
        unitaDiMisura.setCellValueFactory(data -> new ReadOnlyStringWrapper(((PrescriptionBean)data.getValue()).getDose().getMeausurementUnit()));

        TableColumn<Object, String> inizio = new TableColumn<>("Data inizio");
        inizio.setCellValueFactory(data -> new ReadOnlyStringWrapper(((PrescriptionBean)data.getValue()).getStartDate().toString()));

        TableColumn<Object, String> numGiorni = new TableColumn<>("numero di volte");
        numGiorni.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((PrescriptionBean)data.getValue()).getRepetitionNumber())));

        TableColumn<Object, String> rate = new TableColumn<>("ogni tot giorni");
        rate.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((PrescriptionBean)data.getValue()).getDayRate())));

        TableColumn<Object, String> orario = new TableColumn<>("orario");
        orario.setCellValueFactory(data -> new ReadOnlyStringWrapper(((PrescriptionBean)data.getValue()).getDose().getScheduledTime().toString()));

        TableColumn<Object, String> descrizione = new TableColumn<>("descrizione");
        descrizione.setCellValueFactory(data -> new ReadOnlyStringWrapper(((PrescriptionBean)data.getValue()).getDose().getDescription()));

        TableColumn<Object, Void> aggiungiCol = new TableColumn<>("elimina");
        aggiungiCol.setCellFactory(col -> new EliminaDoseCostructorButtonCell());

        recapTable.getColumns().addAll(nome, quantita, unitaDiMisura, descrizione, numGiorni, rate, orario);
        this.dati = FXCollections.observableArrayList();
        recapTable.setItems(dati);
        windowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/RicercaConfezione.fxml", this, gruppo, windowManager);
    }

    @Override
    public void setFinalInformation(FinalStepBean finalStep) throws IOException {
        prescriptionBean.setLastInformation(finalStep);
        prescriptionBundleBean.addPrescription(prescriptionBean);
        update();
        windowManager.deleteTop(gruppo);
        windowManager.show(gruppo);
    }

    public void update(){
        dati = FXCollections.observableArrayList(prescriptionBundleBean.getPrescriptions());
        recapTable.setItems(dati);
    }

    @Override
    public void setDose(DoseBean dose) {
        this.prescriptionBean.setDose(dose);
        windowManager.deleteTop(gruppo);
        try {
            windowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/AggiungiView.fxml", this,gruppo, windowManager);
        } catch (IOException e) {
            e.printStackTrace();
            ((Stage)recapTable.getScene().getWindow()).close();
        }
    }

    private class EliminaDoseCostructorButtonCell extends TableCell<Object, Void> {
        private final Button btn = new Button("elimina");

        public EliminaDoseCostructorButtonCell() {
            btn.setOnAction(event -> {
                PrescriptionBean selezione = (PrescriptionBean)getTableView().getItems().get(getIndex());
                prescriptionBundleBean.removePrescription(selezione);
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
