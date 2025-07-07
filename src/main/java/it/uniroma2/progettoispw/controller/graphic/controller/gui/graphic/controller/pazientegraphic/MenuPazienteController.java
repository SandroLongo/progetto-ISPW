package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.pazientegraphic;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MenuPazienteController extends GuiGraphicController {
    private static final String RICHIESTA_PAZIENTE = "RICHIESTAPAZIENTE";
    private static final String TERAPIA = "TERAPIA";
    private AuthenticationBean authenticationBean;
    private WindowManager windowManager;

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
    void logOut(ActionEvent event) {
        LogInController logInController = new LogInController();
        logInController.logOut(authenticationBean.getCodice());
        windowManager.getMainStage().setOnCloseRequest(null);
        windowManager.resetStacks();
        windowManager.resetExit();
        windowManager.showLogin();
    }

    @FXML
    void richieste(ActionEvent event) {
        windowManager.show(RICHIESTA_PAZIENTE);
    }

    @FXML
    void terapia(ActionEvent event){
        windowManager.show(TERAPIA);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        authenticationBean = (AuthenticationBean) args[0];
        this.windowManager = (WindowManager) args[1];
        windowManager.createNewStack(TERAPIA);
        windowManager.addScene(TERAPIA, "/it/uniroma2/progettoispw/view/TerapiaView.fxml", TERAPIA, authenticationBean, windowManager);
        windowManager.createNewStack(RICHIESTA_PAZIENTE);
        windowManager.addScene(RICHIESTA_PAZIENTE, "/it/uniroma2/progettoispw/view/RichiesteView.fxml", RICHIESTA_PAZIENTE, authenticationBean, windowManager);
    }
}
