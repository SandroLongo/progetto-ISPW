package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.medicographic;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MenuMedicoController extends GuiGraphicController {
    AuthenticationBean authenticationBean;
    private WindowManager windowManager;
    private static final String GRUPPO_RICHIESTA = "RICHIESTA";

    @FXML
    void logOut(ActionEvent event) {
        LogInController logInController = new LogInController();
        logInController.logOut(authenticationBean.getCodice());
        windowManager.getMainStage().setOnCloseRequest(null);
        windowManager.resetStacks();
        windowManager.resetExit();
        windowManager.showLogin();
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
        windowManager.show(GRUPPO_RICHIESTA);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.authenticationBean = (AuthenticationBean) args[0];
        this.windowManager = (WindowManager) args[1];
        windowManager.createNewStack(GRUPPO_RICHIESTA);
        windowManager.addScene(GRUPPO_RICHIESTA, "/it/uniroma2/progettoispw/view/InviaRichiestaView.fxml", GRUPPO_RICHIESTA, authenticationBean, windowManager);
    }
}