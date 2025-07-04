package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.medicographic;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.MenuWindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MenuMedicoController implements GuiGraphicController {
    AuthenticationBean authenticationBean;
    private MenuWindowManager menuWindowManager;
    private static final String GRUPPO_RICHIESTA = "RICHIESTA";

    @FXML
    void logOut(ActionEvent event) {
        LogInController logInController = new LogInController();
        logInController.logOut(authenticationBean.getCodice());
        menuWindowManager.getMainStage().setOnCloseRequest(null);
        menuWindowManager.resetStacks();
        menuWindowManager.showLogin();
    }

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
        menuWindowManager.show(GRUPPO_RICHIESTA);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.authenticationBean = (AuthenticationBean) args[0];
        this.menuWindowManager = (MenuWindowManager) args[1];
        menuWindowManager.createNewStack(GRUPPO_RICHIESTA);
        menuWindowManager.addScene(GRUPPO_RICHIESTA, "/it/uniroma2/progettoispw/view/InviaRichiestaView.fxml", GRUPPO_RICHIESTA, authenticationBean, menuWindowManager);
    }
}