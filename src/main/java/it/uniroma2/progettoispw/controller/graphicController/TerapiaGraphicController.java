package it.uniroma2.progettoispw.controller.graphicController;

import it.uniroma2.progettoispw.ISPWApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TerapiaGraphicController {

    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(ISPWApplication.class.getResource("TerapiaView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
