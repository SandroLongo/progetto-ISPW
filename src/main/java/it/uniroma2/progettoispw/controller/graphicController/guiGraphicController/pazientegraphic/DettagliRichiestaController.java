package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.pazientegraphic;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.controllerApplicativi.ManageRequestController;
import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.MenuWindowManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class DettagliRichiestaController implements GuiGraphicController {
    private String gruppo;
    private AuthenticationBean authenticationBean;
    private ManageRequestController manageRequestController;
    private RichiestaMandata richiestaBean;
    private ObservableList<Object> dati;
    private MenuWindowManager menuWindowManager;

    @FXML
    private Label CFLabel;

    @FXML
    private Label CognomeLabel;

    @FXML
    private Label NomeLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private TableView<Object> listDosi;

    @FXML
    private Label numeroLabel;

    @FXML
    void aggiungi() {
        manageRequestController.accettaRichiesta(authenticationBean.getCodice(), richiestaBean.getIdRichiesta());
        menuWindowManager.deleteTop(gruppo);
        menuWindowManager.show(gruppo);
    }

    @FXML
    void elimina() {
        //manageRequestController.
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.manageRequestController = (ManageRequestController) args[1];
        this.gruppo = (String) args[0];
        this.authenticationBean = (AuthenticationBean) args[2];
        this.richiestaBean = (RichiestaMandata) args[3];
        this.menuWindowManager = (MenuWindowManager) args[4];

        listDosi.getColumns().clear();
        TableColumn<Object, String> nome = new TableColumn<>("Nome");
        nome.setCellValueFactory(data -> new ReadOnlyStringWrapper(((DoseCostructor)data.getValue()).getDose().getNome()));

        TableColumn<Object, String> quantita = new TableColumn<>("quantita");
        quantita.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((DoseCostructor)data.getValue()).getDose().getQuantita())));

        TableColumn<Object, String> unitaDiMisura = new TableColumn<>("unita di misura");
        unitaDiMisura.setCellValueFactory(data -> new ReadOnlyStringWrapper(((DoseCostructor)data.getValue()).getDose().getUnitaMisura()));

        TableColumn<Object, String> inizio = new TableColumn<>("Data inizio");
        inizio.setCellValueFactory(data -> new ReadOnlyStringWrapper(((DoseCostructor)data.getValue()).getInizio().toString()));

        TableColumn<Object, String> numGiorni = new TableColumn<>("numero di volte");
        numGiorni.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((DoseCostructor)data.getValue()).getNumRipetizioni())));

        TableColumn<Object, String> rate = new TableColumn<>("ogni tot giorni");
        rate.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((DoseCostructor)data.getValue()).getRateGiorni())));

        TableColumn<Object, String> orario = new TableColumn<>("orario");
        orario.setCellValueFactory(data -> new ReadOnlyStringWrapper(((DoseCostructor)data.getValue()).getDose().getOrario().toString()));

        TableColumn<Object, String> descrizione = new TableColumn<>("descrizione");
        descrizione.setCellValueFactory(data -> new ReadOnlyStringWrapper(((DoseCostructor)data.getValue()).getDose().getDescrizione()));

        listDosi.getColumns().addAll(nome, quantita, unitaDiMisura, descrizione, numGiorni, rate, orario);
        this.dati = FXCollections.observableArrayList(richiestaBean.getDosi());
        listDosi.setItems(dati);
    }
}
