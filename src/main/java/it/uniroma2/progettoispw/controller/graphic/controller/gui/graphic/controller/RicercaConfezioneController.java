package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.DoseBean;
import it.uniroma2.progettoispw.controller.bean.ListNomiPABean;
import it.uniroma2.progettoispw.controller.controller.applicativi.MedicationInformationController;
import it.uniroma2.progettoispw.model.domain.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class RicercaConfezioneController extends GuiGraphicController {
    private final MedicationInformationController medicationInformationController = new MedicationInformationController();
    private DoseAccepter doseAccepter;
    private String gruppo;
    private Prescription prescription = new Prescription();
    private static final String SELEZIONA = "SELEZIONA";
    private MenuWindowManager menuWindowManager;
    @FXML
    private TextField codiceATC;

    @FXML
    private TextField nomeConfezione;

    @FXML
    private TextField nomePrincipio;

    @FXML
    private TableView<Object> risultatiTable;

    @FXML
    void indietro(ActionEvent event) {
        menuWindowManager.deleteAndcomeBack(gruppo);
    }

    public void initialize(Object[] args) {
        this.doseAccepter = (DoseAccepter) args[0];
        this.gruppo = (String) args[1];
        this.menuWindowManager = (MenuWindowManager) args[2];
    }

    //bottone per confezioni
    @FXML
    void searchConfezione(ActionEvent event) {
        List<MedicinalProduct> confezioni = medicationInformationController.getConfezioniByNomeParziale(nomeConfezione.getText());

        setConfezioni(confezioni);
    }

    //bottone per Principi
    @FXML
    void searchPrincipio(ActionEvent event) {
        risultatiTable.getColumns().clear();
        ListNomiPABean nomiCompleti = medicationInformationController.getPABynomeParziale(nomePrincipio.getText());

        TableColumn<Object, String> nomi = new TableColumn<>("Nomi");
        nomi.setCellValueFactory(data -> new ReadOnlyStringWrapper((String)data.getValue()));

        TableColumn<Object, Void> aggiungiCol = new TableColumn<>(SELEZIONA);
        aggiungiCol.setCellFactory(col -> new SelezionaPrincipioButtonCell());

        TableColumn<Object, Void> cercaCol = new TableColumn<>("Cerca");
        cercaCol.setCellFactory(col -> new CercaConfezioniDalPrincipioButtonCell());

        risultatiTable.getColumns().addAll(nomi, aggiungiCol, cercaCol);
        ObservableList<Object> dati = FXCollections.observableArrayList(nomiCompleti.getNomiPa());
        risultatiTable.setItems(dati);
    }

    private void setConfezioni(List<MedicinalProduct> confezioni) {
        risultatiTable.getColumns().clear();
        TableColumn<Object, String> denominazione = new TableColumn<>("Nomi");
        denominazione.setCellValueFactory(data -> new ReadOnlyStringWrapper(((MedicinalProduct)data.getValue()).getName()));

        TableColumn<Object, String> descrizione = new TableColumn<>("Descrizione");
        descrizione.setCellValueFactory(data -> new ReadOnlyStringWrapper(((MedicinalProduct)data.getValue()).getDescrizione()));

        TableColumn<Object, String> forma = new TableColumn<>("Forma");
        forma.setCellValueFactory(data -> new ReadOnlyStringWrapper(((MedicinalProduct)data.getValue()).getForma()));

        TableColumn<Object, String> codiceAtc = new TableColumn<>("codiceATC");
        codiceAtc.setCellValueFactory(data -> new ReadOnlyStringWrapper(((MedicinalProduct)data.getValue()).getCodiceAtc()));

        TableColumn<Object, String> paAssociati = new TableColumn<>("paAssociati");
        paAssociati.setCellValueFactory(data -> new ReadOnlyStringWrapper(((MedicinalProduct)data.getValue()).getPaAssociati()));

        TableColumn<Object, String> codiceAIC = new TableColumn<>("codiceAIC");
        codiceAIC.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((MedicinalProduct)data.getValue()).getId())));

        TableColumn<Object, Void> aggiungiCol = new TableColumn<>(SELEZIONA);
        aggiungiCol.setCellFactory(col -> new SelezionaConfezioneButtonCell());

        risultatiTable.getColumns().addAll(denominazione, descrizione,forma,codiceAtc,paAssociati,codiceAIC, aggiungiCol);
        ObservableList<Object> dati = FXCollections.observableArrayList(confezioni);
        risultatiTable.setItems(dati);


    }

    private class SelezionaPrincipioButtonCell extends TableCell<Object, Void> {
        private final Button btn = new Button(SELEZIONA);

        public SelezionaPrincipioButtonCell() {
            btn.setOnAction(event -> {
                String selezione = (String) getTableView().getItems().get(getIndex());
                ActiveIngridient activeIngridient = medicationInformationController.getPrincipioAttvoByNome(selezione);
                DoseBean doseBean = new DoseBean(MedicationType.PRINCIPIOATTIVO);
                doseBean.setCodice(activeIngridient.getId());
                doseBean.setNome(activeIngridient.getName());
                doseAccepter.setDose(doseBean);
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : btn);
        }
    }

    private class CercaConfezioniDalPrincipioButtonCell extends TableCell<Object, Void> {
        private final Button btn = new Button("cerca");

        public CercaConfezioniDalPrincipioButtonCell() {
            btn.setOnAction(event -> {
                String selezione =  (String)getTableView().getItems().get(getIndex());
                ActiveIngridient activeIngridient = medicationInformationController.getPrincipioAttvoByNome(selezione);
                String codiceAtc = activeIngridient.getId();
                List<MedicinalProduct> confezioni = medicationInformationController.getConfezioniByCodiceAtc(codiceAtc);
                setConfezioni(confezioni);
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

    private class SelezionaConfezioneButtonCell extends TableCell<Object, Void> {
        private final Button btn = new Button(SELEZIONA);

        public SelezionaConfezioneButtonCell() {
            btn.setOnAction(event -> {
                MedicinalProduct medicinalProduct = (MedicinalProduct)getTableView().getItems().get(getIndex());
                DoseBean doseBean = new DoseBean(MedicationType.CONFEZIONE);
                doseBean.setCodice(String.valueOf(medicinalProduct.getId()));
                doseBean.setNome(medicinalProduct.getName());
                doseAccepter.setDose(doseBean);
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
