package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.medicographic;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.MenuWindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MenuMedicoController implements GuiGraphicController {
    AuthenticationBean authenticationBean;
    private MenuWindowManager menuWindowManager;
    private static final String gruppoRichiesta = "RICHIESTA";

    @FXML
    void home(ActionEvent event) {
        //ancora non implementato
    }

    @FXML
    void searchMedicinale(ActionEvent event) {
        //ancora non implementato
    }

    @FXML
    void sendRequest(ActionEvent event) {
        menuWindowManager.show(gruppoRichiesta);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.authenticationBean = (AuthenticationBean) args[0];
        this.menuWindowManager = (MenuWindowManager) args[1];
        menuWindowManager.createNewStack(gruppoRichiesta);
        menuWindowManager.addScene(gruppoRichiesta, "/it/uniroma2/progettoispw/view/InviaRichiestaView.fxml", gruppoRichiesta, authenticationBean, menuWindowManager);
    }
}