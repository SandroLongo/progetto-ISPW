package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;

import it.uniroma2.progettoispw.controller.bean.ListNomiPABean;
import it.uniroma2.progettoispw.controller.controllerApplicativi.InformazioniMedicinaleController;
import it.uniroma2.progettoispw.controller.controllerApplicativi.TerapiaController;
import it.uniroma2.progettoispw.model.domain.Confezione;
import it.uniroma2.progettoispw.model.domain.PrincipioAttivo;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class RicercaConfezioneController {
    private final InformazioniMedicinaleController informazioniMedicinaleController = new InformazioniMedicinaleController();
    private TerapiaController terapiaController;
    @FXML
    private TextField codiceATC;

    @FXML
    private TextField nomeConfezione;

    @FXML
    private TextField nomePrincipio;

    @FXML
    private TableView<Object> risultatiTable;

    public void inizialize(TerapiaController terapiaController) {
        this.terapiaController = terapiaController;
        terapiaController.createNewDoseInviata();
    }

    //bottone per confezioni
    @FXML
    void searchConfezione(ActionEvent event) {
        List<Confezione> confezioni = informazioniMedicinaleController.getConfezioniByNomeParziale(nomeConfezione.getText());

        setConfezioni(confezioni);
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
                    String selezione =  (String)getTableView().getItems().get(getIndex());
                    PrincipioAttivo principioAttivo= informazioniMedicinaleController.getPrincipioAttvoByNome(selezione);
                    terapiaController.setPrincipio(principioAttivo);
                    try {
                        GuiWindowManager.getInstance().loadFinalStep(terapiaController);
                    } catch (IOException e) {
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

        TableColumn<Object, Void> cercaCol = new TableColumn<>("Cerca");
        cercaCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("cerca");

            {
                btn.setOnAction(event -> {
                    String selezione =  (String)getTableView().getItems().get(getIndex());
                    PrincipioAttivo principioAttivo= informazioniMedicinaleController.getPrincipioAttvoByNome(selezione);
                    String codiceAtc = principioAttivo.getCodice_atc();
                    System.out.println(codiceAtc);
                    List<Confezione> confezioni = informazioniMedicinaleController.getConfezioniByCodiceAtc(codiceAtc);
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
        });

        risultatiTable.getColumns().addAll(nomi, aggiungiCol, cercaCol);
        ObservableList<Object> dati = FXCollections.observableArrayList(nomiCompleti.getNomiPa());
        risultatiTable.setItems(dati);
    }

    private void setConfezioni(List<Confezione> confezioni) {
        risultatiTable.getColumns().clear();
        TableColumn<Object, String> denominazione = new TableColumn<>("Nomi");
        denominazione.setCellValueFactory(data -> new ReadOnlyStringWrapper(((Confezione)data.getValue()).getDenominazione()));

        TableColumn<Object, String> descrizione = new TableColumn<>("Descrizione");
        descrizione.setCellValueFactory(data -> new ReadOnlyStringWrapper(((Confezione)data.getValue()).getDescrizione()));

        TableColumn<Object, String> forma = new TableColumn<>("Forma");
        forma.setCellValueFactory(data -> new ReadOnlyStringWrapper(((Confezione)data.getValue()).getForma()));

        TableColumn<Object, String> codiceATC = new TableColumn<>("codiceATC");
        codiceATC.setCellValueFactory(data -> new ReadOnlyStringWrapper(((Confezione)data.getValue()).getCodice_atc()));

        TableColumn<Object, String> paAssociati = new TableColumn<>("paAssociati");
        paAssociati.setCellValueFactory(data -> new ReadOnlyStringWrapper(((Confezione)data.getValue()).getPa_associati()));

        TableColumn<Object, String> codiceAIC = new TableColumn<>("codiceAIC");
        codiceAIC.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((Confezione)data.getValue()).getCodice_aic())));

        TableColumn<Object, Void> aggiungiCol = new TableColumn<>("Aggiungi");
        aggiungiCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("aggiungi");

            {
                btn.setOnAction(event -> {
                    Confezione confezione = (Confezione)getTableView().getItems().get(getIndex());
                    terapiaController.setConfezione(confezione);
                    try {
                        GuiWindowManager.getInstance().loadFinalStep(terapiaController);
                    } catch (IOException e) {
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

        risultatiTable.getColumns().addAll(denominazione, descrizione,forma,codiceATC,paAssociati,codiceAIC, aggiungiCol);
        ObservableList<Object> dati = FXCollections.observableArrayList(confezioni);
        risultatiTable.setItems(dati);
    }



}
