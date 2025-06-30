package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.medicographic;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.controllerApplicativi.RichiesteController;
import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.DoseAccepter;
import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.FinalAccepter;
import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.MenuWindowManager;
import it.uniroma2.progettoispw.model.domain.Confezione;
import it.uniroma2.progettoispw.model.domain.DoseInviata;
import it.uniroma2.progettoispw.model.domain.PrincipioAttivo;
import it.uniroma2.progettoispw.model.domain.Richiesta;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class RecapRichiestaGraphicController implements GuiGraphicController, DoseAccepter, FinalAccepter {
    private RichiesteController richiesteController;
    private String gruppo;
    private ObservableList<Object> dati;
    private RichiestaBean richiestaBean = new RichiestaBean();
    private AuthenticationBean authenticationBean;
    private DoseCostructor doseCostructor = new DoseCostructor();
    @FXML
    private Label CFLabel;

    @FXML
    private Label CognomeLabel;

    @FXML
    private Label NomeLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label numeroLabel;

    @FXML
    private TableView<Object> recapTable;

    @FXML
    void aggiungi(ActionEvent event) throws IOException {
        this.doseCostructor = new DoseCostructor();
        MenuWindowManager.getInstance().addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/RicercaConfezione.fxml", this, gruppo);
    }

    @FXML
    void invia(ActionEvent event) {
        richiesteController.invia(authenticationBean.getCodice(), richiestaBean);
        MenuWindowManager.getInstance().deleteTop(gruppo);
        MenuWindowManager.getInstance().show(gruppo);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.richiesteController = (RichiesteController) args[1];
        this.gruppo = (String) args[0];
        this.authenticationBean = (AuthenticationBean) args[2];
        this.richiestaBean.setRicevente((InformazioniUtente) args[3]);
        recapTable.getColumns().clear();

        TableColumn<Object, String> nome = new TableColumn<>("Nome");
        nome.setCellValueFactory(data -> new ReadOnlyStringWrapper(((DoseCostructor)data.getValue()).getDose().getNome()));

        TableColumn<Object, String> quantita = new TableColumn<>("quantita");
        quantita.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((DoseCostructor)data.getValue()).getDose().getQuantita())));

        TableColumn<Object, String> unitaDiMisura = new TableColumn<>("unita di misura");
        unitaDiMisura.setCellValueFactory(data -> new ReadOnlyStringWrapper(((DoseCostructor)data.getValue()).getDose().getUnita_misura()));

        TableColumn<Object, String> inizio = new TableColumn<>("Data inizio");
        inizio.setCellValueFactory(data -> new ReadOnlyStringWrapper(((DoseCostructor)data.getValue()).getInizio().toString()));

        TableColumn<Object, String> numGiorni = new TableColumn<>("numero di volte");
        numGiorni.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((DoseCostructor)data.getValue()).getNum_ripetizioni())));

        TableColumn<Object, String> rate = new TableColumn<>("ogni tot giorni");
        rate.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((DoseCostructor)data.getValue()).getRate_giorni())));

        TableColumn<Object, String> orario = new TableColumn<>("orario");
        orario.setCellValueFactory(data -> new ReadOnlyStringWrapper(((DoseCostructor)data.getValue()).getDose().getOrario().toString()));

        TableColumn<Object, String> descrizione = new TableColumn<>("descrizione");
        descrizione.setCellValueFactory(data -> new ReadOnlyStringWrapper(((DoseCostructor)data.getValue()).getDose().getDescrizione_medica()));

        TableColumn<Object, Void> aggiungiCol = new TableColumn<>("elimina");
        aggiungiCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("elimina");

            {
                btn.setOnAction(event -> {
                    Confezione confezione = (Confezione)getTableView().getItems().get(getIndex());
                    try {
                       //richiesteController.elimina((DoseInviata)getTableView().getItems().get(getIndex()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
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
        });

        recapTable.getColumns().addAll(nome, quantita, unitaDiMisura, descrizione, numGiorni, rate, orario);
        this.dati = FXCollections.observableArrayList();
        recapTable.setItems(dati);
        MenuWindowManager.getInstance().addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/RicercaConfezione.fxml", this, gruppo);
    }

    @Override
    public void setFinalInformation(FinalStepBean finalStep) throws IOException {
        doseCostructor.setInizio(finalStep.getInizio());
        doseCostructor.setNum_ripetizioni(finalStep.getNum_ripetizioni());
        doseCostructor.setRate_giorni(finalStep.getRate_giorni());
        doseCostructor.getDose().setDescrizione_medica(finalStep.getDescrizione_medica());
        doseCostructor.getDose().setOrario(finalStep.getOrario());
        doseCostructor.getDose().setUnita_misura(finalStep.getUnita_misura());
        doseCostructor.getDose().setQuantita(finalStep.getQuantita());
        doseCostructor.getDose().setAssunta(false);
        richiestaBean.addDoseCostructor(doseCostructor);
        update();
        MenuWindowManager.getInstance().deleteTop(gruppo);
        MenuWindowManager.getInstance().show(gruppo);
    }

    public void update(){
        dati = FXCollections.observableArrayList(richiestaBean.getDosi());
        recapTable.setItems(dati);
    }

    @Override
    public void setDose(DoseBean dose) {
        this.doseCostructor.setDose(dose);
        MenuWindowManager.getInstance().deleteTop(gruppo);
        try {
            MenuWindowManager.getInstance().addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/AggiungiView.fxml", this,gruppo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
