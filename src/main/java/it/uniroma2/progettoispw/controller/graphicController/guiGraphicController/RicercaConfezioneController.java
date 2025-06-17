package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;

import it.uniroma2.progettoispw.controller.bean.ListNomiPABean;
import it.uniroma2.progettoispw.controller.controllerApplicativi.InformazioniMedicinaleController;
import it.uniroma2.progettoispw.model.domain.Confezione;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class RicercaConfezioneController {
    private final InformazioniMedicinaleController informazioniMedicinaleController = new InformazioniMedicinaleController();
    @FXML
    private TextField codiceATC;

    @FXML
    private TextField nomeConfezione;

    @FXML
    private TextField nomePrincipio;

    @FXML
    private TextField quantitaConfezione;

    @FXML
    private TableView<Object> risultatiTable;

    //bottone per confezioni
    @FXML
    void searchConfezione(ActionEvent event) {
        risultatiTable.getColumns().clear();
        List<Confezione> confezioni = informazioniMedicinaleController.getConfezioniByNomeParziale(nomeConfezione.getText());

        TableColumn<Object, String> denominazione = new TableColumn<>("Nomi");
        denominazione.setCellValueFactory(data -> new ReadOnlyStringWrapper(((Confezione)data.getValue()).getDenominazione()));

        TableColumn<Object, String> desc = new TableColumn<>("Nomi");
        nomi.setCellValueFactory(data -> new ReadOnlyStringWrapper(((Confezione)data.getValue()).getDenominazione()));


        TableColumn<Object, Void> aggiungiCol = new TableColumn<>("Aggiungi");
        aggiungiCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("aggiungi");

            {
                btn.setOnAction(event -> {
                    String selezione = (String)getTableView().getItems().get(getIndex());
                    // Puoi chiamare qui il tuo controller applicativo
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
    }

    //bottone per Principi
    @FXML
    void searchPrincipio(ActionEvent event) {
        risultatiTable.getColumns().clear();
        ListNomiPABean nomiCompleti = informazioniMedicinaleController.getPABynomeParziale(nomePrincipio.getText());

        TableColumn<Object, String> nomi = new TableColumn<>("Nomi");
        nomi.setCellValueFactory(data -> new ReadOnlyStringWrapper((String)data.getValue()));

        TableColumn<Object, Void> aggiungiCol = new TableColumn<>("Aggiungi");
        aggiungiCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("aggiungi");

            {
                btn.setOnAction(event -> {
                    String selezione = (String)getTableView().getItems().get(getIndex());
                    // Puoi chiamare qui il tuo controller applicativo
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

        TableColumn<Object, Void> cercaCol = new TableColumn<>("Cerca");
        cercaCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("cerca");

            {
                btn.setOnAction(event -> {
                    String selezione = (String)getTableView().getItems().get(getIndex());
                    // Puoi chiamare qui il tuo controller applicativo
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

        risultatiTable.getColumns().addAll(nomi, aggiungiCol, cercaCol);
        ObservableList<Object> dati = FXCollections.observableArrayList(nomiCompleti.getNomiPa());
        risultatiTable.setItems(dati);
    }



}
