package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.doctorgraphic;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MenuDoctorViewController extends GuiGraphicController {
    AuthenticationBean authenticationBean;
    private WindowManager windowManager;
    private static final String GROUP_BUNDLE = "BUNDLE";

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
    void searchMedication(ActionEvent event) {
        //ancora non implementato
    }

    @FXML
    void sendBundle(ActionEvent event) {
        windowManager.show(GROUP_BUNDLE);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.authenticationBean = (AuthenticationBean) args[0];
        this.windowManager = (WindowManager) args[1];
        windowManager.createNewStack(GROUP_BUNDLE);
        windowManager.addScene(GROUP_BUNDLE, "/it/uniroma2/progettoispw/view/InviaRichiestaView.fxml", GROUP_BUNDLE, authenticationBean, windowManager);
    }
}