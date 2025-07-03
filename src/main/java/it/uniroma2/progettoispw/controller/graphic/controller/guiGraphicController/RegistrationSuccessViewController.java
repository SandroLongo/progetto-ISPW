package it.uniroma2.progettoispw.controller.graphic.controller.guiGraphicController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationSuccessViewController implements GuiGraphicController{

    @FXML
    private Label codiceField;

    @FXML
    private Label messageLabel;

    @FXML
    void returnToLogin() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/LogInview.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) codiceField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        codiceField.setText((String) args[0]);
    }
}
