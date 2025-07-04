package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.medicographic;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.controller.applicativi.RichiesteController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.DoseAccepter;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.FinalAccepter;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.MenuWindowManager;
import it.uniroma2.progettoispw.model.domain.Confezione;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RecapRichiestaGraphicController extends GuiGraphicController implements DoseAccepter, FinalAccepter {
    private RichiesteController richiesteController;
    private String gruppo;
    private ObservableList<Object> dati;
    private RichiestaBean richiestaBean = new RichiestaBean();
    private AuthenticationBean authenticationBean;
    private DoseCostructor doseCostructor = new DoseCostructor();
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
    void aggiungi(ActionEvent event) throws IOException {
        this.doseCostructor = new DoseCostructor();
        menuWindowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/RicercaConfezione.fxml", this, gruppo, menuWindowManager);
    }

    @FXML
    void invia(ActionEvent event) {
        richiesteController.invia(authenticationBean.getCodice(), richiestaBean);
        menuWindowManager.deleteTop(gruppo);
        menuWindowManager.show(gruppo);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.richiesteController = (RichiesteController) args[1];
        this.gruppo = (String) args[0];
        this.authenticationBean = (AuthenticationBean) args[2];
        this.richiestaBean.setRicevente((InformazioniUtente) args[3]);
        this.menuWindowManager = (MenuWindowManager) args[4];
        recapTable.getColumns().clear();

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

        TableColumn<Object, Void> aggiungiCol = new TableColumn<>("elimina");
        aggiungiCol.setCellFactory(col -> new EliminaDoseCostructorButtonCell());

        recapTable.getColumns().addAll(nome, quantita, unitaDiMisura, descrizione, numGiorni, rate, orario);
        this.dati = FXCollections.observableArrayList();
        recapTable.setItems(dati);
        menuWindowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/RicercaConfezione.fxml", this, gruppo, menuWindowManager);
    }

    @Override
    public void setFinalInformation(FinalStepBean finalStep) throws IOException {
        doseCostructor.setInizio(finalStep.getInizio());
        doseCostructor.setNumRipetizioni(finalStep.getNumRipetizioni());
        doseCostructor.setRateGiorni(finalStep.getRateGiorni());
        doseCostructor.getDose().setDescrizione(finalStep.getDescrizioneMedica());
        doseCostructor.getDose().setOrario(finalStep.getOrario());
        doseCostructor.getDose().setUnitaMisura(finalStep.getUnitaMisura());
        doseCostructor.getDose().setQuantita(finalStep.getQuantita());
        doseCostructor.getDose().setAssunta(false);
        richiestaBean.addDoseCostructor(doseCostructor);
        update();
        menuWindowManager.deleteTop(gruppo);
        menuWindowManager.show(gruppo);
    }

    public void update(){
        dati = FXCollections.observableArrayList(richiestaBean.getDosi());
        recapTable.setItems(dati);
    }

    @Override
    public void setDose(DoseBean dose) {
        this.doseCostructor.setDose(dose);
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
                DoseCostructor selezione = (DoseCostructor)getTableView().getItems().get(getIndex());
                richiestaBean.deleteDoseCostructor(selezione);
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
