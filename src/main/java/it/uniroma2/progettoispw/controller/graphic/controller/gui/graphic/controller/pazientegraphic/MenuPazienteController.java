package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.pazientegraphic;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.MenuWindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MenuPazienteController implements GuiGraphicController {
    private static final String RICHIESTA_PAZIENTE = "RICHIESTAPAZIENTE";
    private static final String TERAPIA = "TERAPIA";
    private AuthenticationBean authenticationBean;
    private MenuWindowManager menuWindowManager;

    @FXML
    private BorderPane boarderPane;

    @FXML
    void cercaMedicinali(ActionEvent event) {
        //ancora non implementato
    }

    @FXML
    void home(ActionEvent event) {
        //ancora non implementato
    }

    @FXML
    void richieste(ActionEvent event) {
        menuWindowManager.show(RICHIESTA_PAZIENTE);
    }

    @FXML
    void terapia(ActionEvent event){
        menuWindowManager.show(TERAPIA);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        authenticationBean = (AuthenticationBean) args[0];
        this.menuWindowManager = (MenuWindowManager) args[1];
        menuWindowManager.createNewStack(TERAPIA);
        menuWindowManager.addScene(TERAPIA, "/it/uniroma2/progettoispw/view/TerapiaView.fxml", TERAPIA, authenticationBean, menuWindowManager);
        menuWindowManager.createNewStack(RICHIESTA_PAZIENTE);
        menuWindowManager.addScene(RICHIESTA_PAZIENTE, "/it/uniroma2/progettoispw/view/RichiesteView.fxml", RICHIESTA_PAZIENTE, authenticationBean, menuWindowManager);
    }
}
