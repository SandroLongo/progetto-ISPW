package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.pazientegraphic;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.controller.applicativi.ManageSentPrescriptionBundleController;
import it.uniroma2.progettoispw.controller.graphic.controller.Notificator;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.WindowManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class PacchettoViewController extends GuiGraphicController implements Notificator {
    private String gruppo;
    private ManageSentPrescriptionBundleController manageSentPrescriptionBundleController;
    private AuthenticationBean authenticationBean;
    private ListPrescriptionBundleBean listPrescriptionBundleBean;
    private WindowManager windowManager;

    @FXML
    private TableView<Object> listaRichieste;
    private ObservableList<Object> dati;

    @Override
    public void initialize(Object[] args) throws IOException {
        this.gruppo = (String) args[0];
        this.authenticationBean = (AuthenticationBean) args[1];
        this.windowManager = (WindowManager) args[2];
        this.manageSentPrescriptionBundleController = new ManageSentPrescriptionBundleController();
        this.listPrescriptionBundleBean = manageSentPrescriptionBundleController.getPendingPrescriptionBundles(authenticationBean.getCodice());
        listPrescriptionBundleBean.addNotificator(this);
        listaRichieste.getColumns().clear();

        TableColumn<Object, String> nomeDottore = new TableColumn<>("DOCTOR");
        nomeDottore.setCellValueFactory(data -> new ReadOnlyStringWrapper(((SentPrescriptionBundleBean)data.getValue()).getSender().getName() + " " +
                ((SentPrescriptionBundleBean)data.getValue()).getSender().getSurname()));

        TableColumn<Object, String> dataInvio = new TableColumn<>("inviata");
        dataInvio.setCellValueFactory(data -> new ReadOnlyStringWrapper(((SentPrescriptionBundleBean)data.getValue()).getSubmissionDate().toString()));

        TableColumn<Object, Void> aggiungiCol = new TableColumn<>("seleziona");
        aggiungiCol.setCellFactory(col -> new SelezionaRichiestaButtonCell());


        listaRichieste.getColumns().addAll(nomeDottore, dataInvio,aggiungiCol);
        this.dati = FXCollections.observableArrayList(listPrescriptionBundleBean.getList());
        listaRichieste.setItems(dati);

    }

    @Override
    public void notifyChanges() {
        dati = FXCollections.observableArrayList(listPrescriptionBundleBean.getList());
        listaRichieste.setItems(dati);
    }

    private class SelezionaRichiestaButtonCell extends TableCell<Object, Void> {
        private final Button btn = new Button("seleziona");

        public SelezionaRichiestaButtonCell() {
            btn.setOnAction(event -> {
                SentPrescriptionBundleBean selezione =  (SentPrescriptionBundleBean) getTableView().getItems().get(getIndex());
                try {
                    windowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/VisualizzaDettagliRichiestaView.fxml", gruppo, manageSentPrescriptionBundleController, authenticationBean, selezione, windowManager);
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
    }
}
