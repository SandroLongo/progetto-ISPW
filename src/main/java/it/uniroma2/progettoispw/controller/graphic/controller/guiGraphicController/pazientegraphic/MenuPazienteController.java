package it.uniroma2.progettoispw.controller.graphic.controller.guiGraphicController.pazientegraphic;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.graphic.controller.guiGraphicController.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.guiGraphicController.MenuWindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MenuPazienteController implements GuiGraphicController {
    private final String richiestapaziente = "RICHIESTAPAZIENTE";
    private final String terapia = "TERAPIA";
    private AuthenticationBean authenticationBean;
    private MenuWindowManager menuWindowManager;

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
        menuWindowManager.show(richiestapaziente);
    }

    @FXML
    void terapia(ActionEvent event) throws IOException {
        menuWindowManager.show(terapia);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        authenticationBean = (AuthenticationBean) args[0];
        this.menuWindowManager = (MenuWindowManager) args[1];
        System.out.println(authenticationBean.getCodice());
        menuWindowManager.createNewStack(terapia);
        menuWindowManager.addScene(terapia, "/it/uniroma2/progettoispw/view/TerapiaView.fxml", terapia, authenticationBean, menuWindowManager);
        menuWindowManager.createNewStack(richiestapaziente);
        menuWindowManager.addScene(richiestapaziente, "/it/uniroma2/progettoispw/view/RichiesteView.fxml", richiestapaziente, authenticationBean, menuWindowManager);
    }
}
