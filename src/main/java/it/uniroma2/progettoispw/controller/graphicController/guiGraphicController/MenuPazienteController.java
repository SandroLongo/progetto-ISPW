package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MenuPazienteController {
    @FXML
    private BorderPane boarderPane;

    @FXML
    void cercaMedicinali(ActionEvent event) {

    }

    @FXML
    void home(ActionEvent event) {

    }

    @FXML
    void richieste(ActionEvent event) {

    }

    @FXML
    void terapia(ActionEvent event) throws IOException {
        GuiWindowManager.getInstance().loadTerapia();
    }
}
