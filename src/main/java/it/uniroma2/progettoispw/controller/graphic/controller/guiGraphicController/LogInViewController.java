package it.uniroma2.progettoispw.controller.graphic.controller.guiGraphicController;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.controller.applicativi.FomatoInvalidoException;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.controller.bean.UtenteLogInData;
import it.uniroma2.progettoispw.model.domain.Ruolo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInViewController implements GuiGraphicController {
    private LogInController logInController = new LogInController();
    MenuWindowManager menuWindowManager;

    @FXML
    private Label errorLabel;
    @FXML
    private CheckBox checkMedico;
    @FXML
    private TextField codiceField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField codiceMedicoField;

    @FXML
    void handleLogIn() throws IOException {
        String codice = codiceField.getText();
        String password = passwordField.getText();
        UtenteLogInData utenteLogInData;
        if (checkMedico.isSelected()) {
            utenteLogInData = new UtenteLogInData(codice, password, Ruolo.Dottore, codiceMedicoField.getText());
        } else {
            utenteLogInData = new UtenteLogInData(codice, password, Ruolo.Paziente);
        }

        AuthenticationBean authenticationBean = null;
        try {
            authenticationBean = logInController.logIn(utenteLogInData);
        } catch (FomatoInvalidoException e) {
            throw new FomatoInvalidoException(e.getMessage());
            //showAlert(e.getMessage());
        }

        FXMLLoader loader;
        switch (authenticationBean.getRuolo()) {
            case Dottore -> loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/MenuDottore.fxml"));
            case Paziente -> loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/MenuPaziente.fxml"));
            default -> {
                showAlert("Login fallito, credenziali errate");
                return;
            }
        }
        BorderPane root = (BorderPane) loader.load();
        Stage stage = (Stage)errorLabel.getScene().getWindow();
        MenuWindowManager menuWindowManager = new MenuWindowManager(stage);
        ((GuiGraphicController)loader.getController()).initialize(new Object[]{authenticationBean, menuWindowManager});
        menuWindowManager.setMenu(root);

    }

    @FXML
    void handleRegistration(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/Registrazione.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)passwordField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(String message) {
        errorLabel.setText(message);
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.menuWindowManager = (MenuWindowManager) args[0];
    }
}
