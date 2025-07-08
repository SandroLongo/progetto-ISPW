package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.controller.applicativi.MedicationInformationController;
import it.uniroma2.progettoispw.controller.controller.applicativi.PercistencyFailedException;
import it.uniroma2.progettoispw.model.domain.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class SelectMedicationViewController extends GuiGraphicController {
    private final MedicationInformationController medicationInformationController = new MedicationInformationController();
    private DoseAccepter doseAccepter;
    private String gruppo;
    private Prescription prescription = new Prescription();
    private static final String SELEZIONA = "SELEZIONA";
    private WindowManager windowManager;
    @FXML
    private TextField codiceATC;

    @FXML
    private TextField nomeConfezione;

    @FXML
    private TextField nomePrincipio;

    @FXML
    private TableView<Object> risultatiTable;

    @FXML
    void back(ActionEvent event) {
        windowManager.deleteAndcomeBack(gruppo);
    }

    public void initialize(Object[] args) {
        this.doseAccepter = (DoseAccepter) args[0];
        this.gruppo = (String) args[1];
        this.windowManager = (WindowManager) args[2];
    }


    @FXML
    void searchMedicinalProductByName(ActionEvent event) {
        List<MedicinalProductBean> confezioni = null;
        try {
            confezioni = medicationInformationController.getMedicinalProductsByPartialName(nomeConfezione.getText());
        } catch (PercistencyFailedException e) {
            showAlert(e.getMessage());
            return;
        }
        setConfezioni(confezioni);
    }


    @FXML
    void searchActiveIngredientByName(ActionEvent event) {
        risultatiTable.getColumns().clear();
        List<String> nomiCompleti = null;
        try {
            nomiCompleti = medicationInformationController.getActiveIngridientsNameByPartialName(nomePrincipio.getText());
        } catch (PercistencyFailedException e) {
            showAlert(e.getMessage());
            return;
        }
        if (nomiCompleti.isEmpty()) {
            showInformation("risultati non trovati");
            risultatiTable.getColumns().clear();
            return;
        }

        TableColumn<Object, String> nomi = new TableColumn<>("Nomi");
        nomi.setCellValueFactory(data -> new ReadOnlyStringWrapper((String)data.getValue()));

        TableColumn<Object, Void> aggiungiCol = new TableColumn<>(SELEZIONA);
        aggiungiCol.setCellFactory(col -> new SelectActiveIngredientButtonCell());

        TableColumn<Object, Void> cercaCol = new TableColumn<>("Cerca");
        cercaCol.setCellFactory(col -> new SelectAItoSearchMPButtonCell());

        risultatiTable.getColumns().addAll(nomi, aggiungiCol, cercaCol);
        ObservableList<Object> dati = FXCollections.observableArrayList(nomiCompleti);
        risultatiTable.setItems(dati);
    }

    private void setConfezioni(List<MedicinalProductBean> confezioni) {
        if (confezioni.isEmpty()) {
            showInformation("risultati non trovati");
            risultatiTable.getColumns().clear();
            return;
        }
        risultatiTable.getColumns().clear();
        TableColumn<Object, String> denominazione = new TableColumn<>("Nomi");
        denominazione.setCellValueFactory(data -> new ReadOnlyStringWrapper(((MedicinalProductBean)data.getValue()).getName()));

        TableColumn<Object, String> descrizione = new TableColumn<>("Descrizione");
        descrizione.setCellValueFactory(data -> new ReadOnlyStringWrapper(((MedicinalProductBean)data.getValue()).getDescription()));

        TableColumn<Object, String> forma = new TableColumn<>("Forma");
        forma.setCellValueFactory(data -> new ReadOnlyStringWrapper(((MedicinalProductBean)data.getValue()).getForm()));

        TableColumn<Object, String> codiceAtc = new TableColumn<>("codiceATC");
        codiceAtc.setCellValueFactory(data -> new ReadOnlyStringWrapper(((MedicinalProductBean)data.getValue()).getActiveIngridientId()));

        TableColumn<Object, String> paAssociati = new TableColumn<>("paAssociati");
        paAssociati.setCellValueFactory(data -> new ReadOnlyStringWrapper(((MedicinalProductBean)data.getValue()).getActiveIngridientName()));

        TableColumn<Object, String> codiceAIC = new TableColumn<>("codiceAIC");
        codiceAIC.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(((MedicinalProductBean)data.getValue()).getId())));

        TableColumn<Object, Void> aggiungiCol = new TableColumn<>(SELEZIONA);
        aggiungiCol.setCellFactory(col -> new SelectMedicinalProductButtonCell());

        risultatiTable.getColumns().addAll(denominazione, descrizione,forma,codiceAtc,paAssociati,codiceAIC, aggiungiCol);
        ObservableList<Object> dati = FXCollections.observableArrayList(confezioni);
        risultatiTable.setItems(dati);


    }

    private class SelectActiveIngredientButtonCell extends TableCell<Object, Void> {
        private final Button btn = new Button(SELEZIONA);

        public SelectActiveIngredientButtonCell() {
            btn.setOnAction(event -> {
                String selezione = (String) getTableView().getItems().get(getIndex());
                ActiveIngredientBean activeIngredient = null;
                try {
                    activeIngredient = medicationInformationController.getActiveIngridientByName(selezione);
                } catch (PercistencyFailedException e) {
                    showAlert(e.getMessage());
                    return;
                }
                doseAccepter.setDose(new MedicationBean(activeIngredient));
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            setGraphic(empty ? null : btn);
        }
    }

    private class SelectAItoSearchMPButtonCell extends TableCell<Object, Void> {
        private final Button btn = new Button("cerca");

        public SelectAItoSearchMPButtonCell() {
            btn.setOnAction(event -> {
                String selezione =  (String)getTableView().getItems().get(getIndex());
                ActiveIngredientBean activeIngredient = null;
                try {
                    activeIngredient = medicationInformationController.getActiveIngridientByName(selezione);
                } catch (PercistencyFailedException e) {
                    showAlert(e.getMessage());
                    return;
                }
                String codiceAtc = activeIngredient.getId();
                List<MedicinalProductBean> confezioni = null;
                try {
                    confezioni = medicationInformationController.getMedicinalProductByActiveIngridient(codiceAtc);
                } catch (PercistencyFailedException e) {
                    showAlert(e.getMessage());
                    return;
                }
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

    private class SelectMedicinalProductButtonCell extends TableCell<Object, Void> {
        private final Button btn = new Button(SELEZIONA);

        public SelectMedicinalProductButtonCell() {
            btn.setOnAction(event -> {
                MedicinalProductBean medicinalProduct = (MedicinalProductBean) getTableView().getItems().get(getIndex());
                doseAccepter.setDose(new MedicationBean(medicinalProduct));
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
