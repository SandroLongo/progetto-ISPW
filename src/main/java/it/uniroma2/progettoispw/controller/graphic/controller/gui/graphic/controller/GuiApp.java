package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiApp {

    public void start(Stage stage) throws Exception {
        stage.setTitle("application");
        MenuWindowManager menuWindowManager = new MenuWindowManager(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/LogInview.fxml"));
        Parent root = loader.load();
        ((GuiGraphicController)loader.getController()).initialize(new Object[]{menuWindowManager});
        stage.setScene(new Scene(root));
        stage.show();
    }

}
