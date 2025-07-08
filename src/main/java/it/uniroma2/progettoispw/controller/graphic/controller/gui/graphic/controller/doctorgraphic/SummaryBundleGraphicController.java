package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.doctorgraphic;

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

public class SummaryBundleGraphicController extends GuiGraphicController implements DoseAccepter, FinalAccepter {
    private SendPrescriptionBundleController sendPrescriptionBundleController;
    private String group;
    private ObservableList<Object> dataResult;
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
    void back(ActionEvent event) {
        windowManager.deleteAndcomeBack(group);
    }

    @FXML
    void add(ActionEvent event) throws IOException {
        this.prescriptionBean = new PrescriptionBean();
        windowManager.addSceneAndShow(group, "/it/uniroma2/progettoispw/view/RicercaConfezione.fxml", this, group, windowManager);
    }

    @FXML
    void send(ActionEvent event) {
        sendPrescriptionBundleController.send(authenticationBean.getCodice(), prescriptionBundleBean);
        showInformation("il tuo pacchetto è stato inviato con successo");
        windowManager.deleteTop(group);
        windowManager.show(group);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.sendPrescriptionBundleController = (SendPrescriptionBundleController) args[1];
        this.group = (String) args[0];
        this.authenticationBean = (AuthenticationBean) args[2];
        UserInformation userInformation = (UserInformation) args[3];
        this.prescriptionBundleBean.setReceiver(userInformation);
        cFLabel.setText(userInformation.getTaxCode());
        nomeLabel.setText(userInformation.getName());
        cognomeLabel.setText(userInformation.getSurname());
        emailLabel.setText(userInformation.getEmail());
        numeroLabel.setText(userInformation.getPhoneNumber().toString());
        this.windowManager = (WindowManager) args[4];
        recapTable.getColumns().clear();

        TableColumn<Object, String> nome = new TableColumn<>("Nome");
        nome.setCellValueFactory(data -> new ReadOnlyStringWrapper(((PrescriptionBean)data.getValue()).getDose().getName()));

        TableColumn<Object, String> quantita = new TableColumn<>("quantità");
        quantita.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((PrescriptionBean)data.getValue()).getDose().getQuantity())));

        TableColumn<Object, String> unitaDiMisura = new TableColumn<>("unità");
        unitaDiMisura.setCellValueFactory(data -> new ReadOnlyStringWrapper(((PrescriptionBean)data.getValue()).getDose().getMeausurementUnit()));

        TableColumn<Object, String> inizio = new TableColumn<>("Data inizio");
        inizio.setCellValueFactory(data -> new ReadOnlyStringWrapper(((PrescriptionBean)data.getValue()).getStartDate().toString()));

        TableColumn<Object, String> numGiorni = new TableColumn<>("per");
        numGiorni.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((PrescriptionBean)data.getValue()).getRepetitionNumber()) + " giorni"));

        TableColumn<Object, String> rate = new TableColumn<>("ogni");
        rate.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((PrescriptionBean)data.getValue()).getDayRate() + " giorni")));

        TableColumn<Object, String> orario = new TableColumn<>("orario");
        orario.setCellValueFactory(data -> new ReadOnlyStringWrapper(((PrescriptionBean)data.getValue()).getDose().getScheduledTime().toString()));

        TableColumn<Object, String> descrizione = new TableColumn<>("descrizione");
        descrizione.setCellValueFactory(data -> new ReadOnlyStringWrapper(((PrescriptionBean)data.getValue()).getDose().getDescription()));

        TableColumn<Object, Void> aggiungiCol = new TableColumn<>("elimina");
        aggiungiCol.setCellFactory(col -> new EliminaDoseCostructorButtonCell());

        recapTable.getColumns().addAll(nome, quantita, unitaDiMisura, descrizione, numGiorni, rate, orario);
        this.dataResult = FXCollections.observableArrayList();
        recapTable.setItems(dataResult);
        windowManager.addSceneAndShow(group, "/it/uniroma2/progettoispw/view/RicercaConfezione.fxml", this, group, windowManager);
    }

    @Override
    public void setFinalInformation(FinalStepBean finalStep) throws IOException {
        prescriptionBean.setLastInformation(finalStep);
        prescriptionBundleBean.addPrescription(prescriptionBean);
        update();
        windowManager.deleteTop(group);
        windowManager.show(group);
    }

    public void update(){
        dataResult = FXCollections.observableArrayList(prescriptionBundleBean.getPrescriptions());
        recapTable.setItems(dataResult);
    }

    @Override
    public void setDose(MedicationBean medicationBean) {
        this.prescriptionBean.setDose(medicationBean);
        windowManager.deleteTop(group);
        try {
            windowManager.addSceneAndShow(group, "/it/uniroma2/progettoispw/view/AggiungiView.fxml", this, group, windowManager);
        } catch (IOException e) {
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
