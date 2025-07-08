package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.patientgraphic;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MenuPatientController extends GuiGraphicController {
    private static final String BUNDLES = "BUNDLES";
    private static final String THERAPY = "THERAPY";
    private AuthenticationBean authenticationBean;
    private WindowManager windowManager;

    @FXML
    private BorderPane boarderPane;

    @FXML
    void searchMedication(ActionEvent event) {
        //ancora non implementato
    }

    @FXML
    void home(ActionEvent event) {
        //ancora non implementato
    }

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
    void bundles(ActionEvent event) {
        windowManager.show(BUNDLES);
    }

    @FXML
    void teraphy(ActionEvent event){
        windowManager.show(THERAPY);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        authenticationBean = (AuthenticationBean) args[0];
        this.windowManager = (WindowManager) args[1];
        windowManager.createNewStack(THERAPY);
        windowManager.addScene(THERAPY, "/it/uniroma2/progettoispw/view/TerapiaView.fxml", THERAPY, authenticationBean, windowManager);
        windowManager.createNewStack(BUNDLES);
        windowManager.addScene(BUNDLES, "/it/uniroma2/progettoispw/view/RichiesteView.fxml", BUNDLES, authenticationBean, windowManager);
    }
}
