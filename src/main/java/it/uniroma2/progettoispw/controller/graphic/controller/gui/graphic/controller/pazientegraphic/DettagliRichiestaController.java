package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.pazientegraphic;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.controller.applicativi.ManageSentPrescriptionBundleController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.MenuWindowManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class DettagliRichiestaController extends GuiGraphicController {
    private String gruppo;
    private AuthenticationBean authenticationBean;
    private ManageSentPrescriptionBundleController manageSentPrescriptionBundleController;
    private SentPrescriptionBundleBean richiestaBean;
    private ObservableList<Object> dati;
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
    private TableView<Object> listDosi;

    @FXML
    private Label numeroLabel;

    @FXML
    void indietro(ActionEvent event) {
        menuWindowManager.deleteAndcomeBack(gruppo);
    }
    @FXML
    void aggiungi() {
        manageSentPrescriptionBundleController.acceptPrescriptionBundle(authenticationBean.getCodice(), richiestaBean.getId());
        menuWindowManager.deleteTop(gruppo);
        menuWindowManager.show(gruppo);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.manageSentPrescriptionBundleController = (ManageSentPrescriptionBundleController) args[1];
        this.gruppo = (String) args[0];
        this.authenticationBean = (AuthenticationBean) args[2];
        this.richiestaBean = (SentPrescriptionBundleBean) args[3];
        this.menuWindowManager = (MenuWindowManager) args[4];

        listDosi.getColumns().clear();
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

        listDosi.getColumns().addAll(nome, quantita, unitaDiMisura, descrizione, numGiorni, rate, orario);
        this.dati = FXCollections.observableArrayList(richiestaBean.getPrescriptions());
        listDosi.setItems(dati);
    }
}
