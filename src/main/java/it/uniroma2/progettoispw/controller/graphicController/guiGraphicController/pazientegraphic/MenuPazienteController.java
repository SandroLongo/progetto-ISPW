package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.pazientegraphic;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.MenuWindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MenuPazienteController implements GuiGraphicController {
    private final String richiestapaziente = "RICHIESTAPAZIENTE";
    private final String terapia = "TERAPIA";
    private AuthenticationBean authenticationBean;
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
        MenuWindowManager.getInstance().show(richiestapaziente);
    }

    @FXML
    void terapia(ActionEvent event) throws IOException {
        MenuWindowManager.getInstance().show(terapia);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        authenticationBean = (AuthenticationBean) args[0];
        MenuWindowManager.getInstance().createNewStack(terapia);
        MenuWindowManager.getInstance().addScene(terapia, "/it/uniroma2/progettoispw/view/TerapiaView.fxml", terapia, authenticationBean);
        MenuWindowManager.getInstance().createNewStack(richiestapaziente);
        MenuWindowManager.getInstance().addScene(richiestapaziente, "/it/uniroma2/progettoispw/view/RichiesteView.fxml", richiestapaziente, authenticationBean);
    }
}
