package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.patientgraphic;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.controller.applicativi.ManageSentPrescriptionBundleController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.WindowManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class BundleDetailViewController extends GuiGraphicController {
    private String group;
    private AuthenticationBean authenticationBean;
    private ManageSentPrescriptionBundleController manageSentPrescriptionBundleController;
    private SentPrescriptionBundleBean bundleBean;
    private ObservableList<Object> dataResult;
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
    private TableView<Object> listDosi;

    @FXML
    private Label numeroLabel;

    @FXML
    void back(ActionEvent event) {
        windowManager.deleteAndcomeBack(group);
    }
    @FXML
    void add() {
        manageSentPrescriptionBundleController.acceptPrescriptionBundle(authenticationBean.getCodice(), bundleBean.getId());
        windowManager.deleteTop(group);
        windowManager.show(group);
    }

    @FXML
    void remove(ActionEvent event) {
        manageSentPrescriptionBundleController.rejectPrescriptionBundle(authenticationBean.getCodice(), bundleBean.getId());
        windowManager.deleteTop(group);
        windowManager.show(group);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.manageSentPrescriptionBundleController = (ManageSentPrescriptionBundleController) args[1];
        this.group = (String) args[0];
        this.authenticationBean = (AuthenticationBean) args[2];
        this.bundleBean = (SentPrescriptionBundleBean) args[3];
        UserInformation userInformation = bundleBean.getSender();
        nomeLabel.setText(userInformation.getName());
        cognomeLabel.setText(userInformation.getSurname());
        emailLabel.setText(userInformation.getEmail());
        numeroLabel.setText(userInformation.getPhoneNumber().toString());
        this.windowManager = (WindowManager) args[4];

        listDosi.getColumns().clear();
        TableColumn<Object, String> nome = new TableColumn<>("Nome");
        nome.setCellValueFactory(data -> new ReadOnlyStringWrapper(((PrescriptionBean)data.getValue()).getDose().getName()));

        TableColumn<Object, String> quantita = new TableColumn<>("quantità");
        quantita.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((PrescriptionBean)data.getValue()).getDose().getQuantity())));

        TableColumn<Object, String> unitaDiMisura = new TableColumn<>("unità");
        unitaDiMisura.setCellValueFactory(data -> new ReadOnlyStringWrapper(((PrescriptionBean)data.getValue()).getDose().getMeausurementUnit()));

        TableColumn<Object, String> inizio = new TableColumn<>("Data di inizio");
        inizio.setCellValueFactory(data -> new ReadOnlyStringWrapper(((PrescriptionBean)data.getValue()).getStartDate().toString()));

        TableColumn<Object, String> numGiorni = new TableColumn<>("per");
        numGiorni.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((PrescriptionBean)data.getValue()).getRepetitionNumber())+ " giorni"));

        TableColumn<Object, String> rate = new TableColumn<>("ogni");
        rate.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((PrescriptionBean)data.getValue()).getDayRate()) + " giorni"));

        TableColumn<Object, String> orario = new TableColumn<>("orario");
        orario.setCellValueFactory(data -> new ReadOnlyStringWrapper(((PrescriptionBean)data.getValue()).getDose().getScheduledTime().toString()));

        TableColumn<Object, String> descrizione = new TableColumn<>("descrizione");
        descrizione.setCellValueFactory(data -> new ReadOnlyStringWrapper(((PrescriptionBean)data.getValue()).getDose().getDescription()));

        listDosi.getColumns().addAll(nome, quantita, unitaDiMisura, inizio, descrizione, numGiorni, rate, orario);
        this.dataResult = FXCollections.observableArrayList(bundleBean.getPrescriptions());
        listDosi.setItems(dataResult);
    }
}
