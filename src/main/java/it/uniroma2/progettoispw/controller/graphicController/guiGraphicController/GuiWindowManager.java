package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;

import it.uniroma2.progettoispw.controller.controllerApplicativi.TerapiaController;
import it.uniroma2.progettoispw.controller.graphicController.WindowManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiWindowManager implements WindowManager {
    private static GuiWindowManager instance;
    private Stage mainStage;
    private BorderPane menu;
    private Scene mainScene;
    private VBox terapiaScene;
    private VBox richiestaScene;
    private Scene loginScene;
    private Scene registrationScene;
    private AnchorPane ricercaMedicinali;

    private GuiWindowManager() {}

    public static GuiWindowManager getInstance() {
        if (instance == null) {
            instance = new GuiWindowManager();
        }
        return instance;
    }


    public void loadMenu(){
        mainStage.setScene(new Scene(menu));
        mainStage.show();
    }

    public void loadLogin() throws IOException {
        if (loginScene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/LogInview.fxml"));
            loginScene = new Scene(fxmlLoader.load());
        }
        mainStage.setScene(loginScene);
        mainStage.show();
    }

    public void loadRegistration() throws IOException {
        if (registrationScene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/Registrazione.fxml"));
            registrationScene = new Scene(fxmlLoader.load());
        }
        mainStage.setScene(registrationScene);
        mainStage.show();
    }

    public void loadRichiesta() throws IOException {}

    public void loadTerapia() throws IOException {
        if (terapiaScene == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/TerapiaView.fxml"));
            terapiaScene = loader.load();
            TerapiaGui terapiaGui = loader.getController();
            terapiaGui.inizialize();
        }
        menu.setCenter(terapiaScene);
    }

    public void loadFinalStep(TerapiaController terapiaController) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/AggiungiView.fxml"));
        AnchorPane anchorPane = loader.load();
        AggiungiFinalStepController finalStepController = loader.getController();
        finalStepController.initialize(terapiaController);
        menu.setCenter(anchorPane);
    }
    public void loadRicercaMedicinali(TerapiaController terapiaController) throws IOException {
        if (ricercaMedicinali == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/RicercaConfezione.fxml"));
            ricercaMedicinali = loader.load();
            RicercaConfezioneController ricercaConfezioneController = loader.getController();
            ricercaConfezioneController.inizialize(terapiaController);
        }
        menu.setCenter(ricercaMedicinali);
    }

    public void loadAggiungiFinalSteo() throws IOException {

    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setMenu(BorderPane menu) {
        this.menu = menu;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setTerapiaScene(VBox terapiaScene) {
        this.terapiaScene = terapiaScene;
    }

    public void setRichiestaScene(VBox richiestaScene) {
        this.richiestaScene = richiestaScene;
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    public void setRegistrationScene(Scene registrationScene) {
        this.registrationScene = registrationScene;
    }


}

