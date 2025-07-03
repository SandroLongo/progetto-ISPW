package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.pazientegraphic;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.controller.applicativi.ManageRequestController;
import it.uniroma2.progettoispw.controller.graphic.controller.Notificator;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.MenuWindowManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class RichiesteViewController extends Notificator implements GuiGraphicController {
    private String gruppo;
    private ManageRequestController manageRequestController;
    private AuthenticationBean authenticationBean;
    private ListaRichiesteBean listaRichiesteBean;
    private MenuWindowManager menuWindowManager;

    @FXML
    private TableView<Object> listaRichieste;
    private ObservableList<Object> dati;

    @Override
    public void initialize(Object[] args) throws IOException {
        this.gruppo = (String) args[0];
        this.authenticationBean = (AuthenticationBean) args[1];
        this.menuWindowManager = (MenuWindowManager) args[2];
        this.manageRequestController = new ManageRequestController();
        this.listaRichiesteBean = manageRequestController.getRichieste(authenticationBean.getCodice());
        listaRichiesteBean.setNotificator(this);
        listaRichieste.getColumns().clear();

        TableColumn<Object, String> nomeDottore = new TableColumn<>("Dottore");
        nomeDottore.setCellValueFactory(data -> new ReadOnlyStringWrapper(((RichiestaMandata)data.getValue()).getInviante().getNome() + " " +
                ((RichiestaMandata)data.getValue()).getInviante().getCognome()));

        TableColumn<Object, String> dataInvio = new TableColumn<>("inviata");
        dataInvio.setCellValueFactory(data -> new ReadOnlyStringWrapper(((RichiestaMandata)data.getValue()).getInvio().toString()));

        TableColumn<Object, Void> aggiungiCol = new TableColumn<>("seleziona");
        aggiungiCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("seleziona");

            {
                btn.setOnAction(event -> {
                    RichiestaMandata selezione =  (RichiestaMandata) getTableView().getItems().get(getIndex());
                    try {
                        menuWindowManager.addSceneAndShow(gruppo, "/it/uniroma2/progettoispw/view/VisualizzaDettagliRichiestaView.fxml", gruppo, manageRequestController, authenticationBean, selezione, menuWindowManager);
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


        listaRichieste.getColumns().addAll(nomeDottore, dataInvio,aggiungiCol);
        this.dati = FXCollections.observableArrayList(listaRichiesteBean.getLista());
        listaRichieste.setItems(dati);

    }

    @Override
    public void notifica() {
        dati = FXCollections.observableArrayList(listaRichiesteBean.getLista());
        listaRichieste.setItems(dati);
    }
}
