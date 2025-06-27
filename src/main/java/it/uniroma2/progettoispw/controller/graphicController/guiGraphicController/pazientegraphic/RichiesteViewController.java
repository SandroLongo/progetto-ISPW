package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.pazientegraphic;

import it.uniroma2.progettoispw.controller.controllerApplicativi.RichiesteController;
import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.GuiGraphicController;
import it.uniroma2.progettoispw.model.domain.Richiesta;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.List;

public class RichiesteViewController implements GuiGraphicController {
    private String gruppo;
    private RichiesteController richiesteController;

    @FXML
    private TableView<Object> listaRichieste;

    @Override
    public void initialize(Object[] args) throws IOException {
        this.gruppo = (String) args[0];
        this.richiesteController = new RichiesteController();
        List<Richiesta> lista = richiesteController.getRichiesteVacanti();

    }
}
