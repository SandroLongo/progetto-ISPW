package it.uniroma2.progettoispw;

import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.WindowManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        WindowCounter.getInstance().increment();
        stage.setTitle("application");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/sceltaGui.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        WindowManager windowManager = new WindowManager(stage);
        windowManager.resetExit();
        ((GuiGraphicController)loader.getController()).initialize(new Object[]{windowManager});
        stage.show();
    }

    public static void openNewWindow() {
        WindowCounter.getInstance().increment();
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/it/uniroma2/progettoispw/sceltaGui.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            return;
        }
        newStage.setScene(new Scene(root));
        newStage.show();
    }
}
