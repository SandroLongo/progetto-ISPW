package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiApp extends Application {
    private Stage stage;


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/LogInview.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("appliation");
        stage.show();

    }

    public void SwitchToScene(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
