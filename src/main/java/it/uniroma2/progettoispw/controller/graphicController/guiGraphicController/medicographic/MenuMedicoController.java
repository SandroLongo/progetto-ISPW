package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.medicographic;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.MenuWindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MenuMedicoController implements GuiGraphicController {
    AuthenticationBean authenticationBean;

    @FXML
    void home(ActionEvent event) {

    }

    @FXML
    void searchMedicinale(ActionEvent event) {

    }

    @FXML
    void sendRequest(ActionEvent event) {
        MenuWindowManager.getInstance().show("RICHIESTA");
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.authenticationBean = (AuthenticationBean) args[0];
        MenuWindowManager.getInstance().createNewStack("RICHIESTA");
        MenuWindowManager.getInstance().addScene("RICHIESTA", "/it/uniroma2/progettoispw/view/InviaRichiestaView.fxml", "RICHIESTA", authenticationBean);
    }
}