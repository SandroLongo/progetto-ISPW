package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiApp extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("application");
        MenuWindowManager.getInstance().setMainStage(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/LogInview.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
