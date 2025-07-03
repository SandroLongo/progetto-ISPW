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

    @FXML
    void home(ActionEvent event) {

    }

    @FXML
    void searchMedicinale(ActionEvent event) {

    }

    @FXML
    void sendRequest(ActionEvent event) {
        menuWindowManager.show("RICHIESTA");
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.authenticationBean = (AuthenticationBean) args[0];
        this.menuWindowManager = (MenuWindowManager) args[1];
        menuWindowManager.createNewStack("RICHIESTA");
        menuWindowManager.addScene("RICHIESTA", "/it/uniroma2/progettoispw/view/InviaRichiestaView.fxml", "RICHIESTA", authenticationBean, menuWindowManager);
    }
}