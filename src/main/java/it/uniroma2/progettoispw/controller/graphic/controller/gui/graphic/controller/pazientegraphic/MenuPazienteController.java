package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.pazientegraphic;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.MenuWindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MenuPazienteController implements GuiGraphicController {
    private static final String RichiestaPaziente = "RICHIESTAPAZIENTE";
    private static final String Terapia = "TERAPIA";
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
        menuWindowManager.show(RichiestaPaziente);
    }

    @FXML
    void terapia(ActionEvent event){
        menuWindowManager.show(Terapia);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        authenticationBean = (AuthenticationBean) args[0];
        this.menuWindowManager = (MenuWindowManager) args[1];
        menuWindowManager.createNewStack(Terapia);
        menuWindowManager.addScene(Terapia, "/it/uniroma2/progettoispw/view/TerapiaView.fxml", Terapia, authenticationBean, menuWindowManager);
        menuWindowManager.createNewStack(RichiestaPaziente);
        menuWindowManager.addScene(RichiestaPaziente, "/it/uniroma2/progettoispw/view/RichiesteView.fxml", RichiestaPaziente, authenticationBean, menuWindowManager);
    }
}
