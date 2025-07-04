package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.bean.FomatoInvalidoException;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.controller.bean.UtenteLogInData;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInFailedException;
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

public class LogInViewController extends GuiGraphicController {
    private final LogInController logInController = new LogInController();
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
            utenteLogInData = new UtenteLogInData(codice, password, Ruolo.DOTTORE, codiceMedicoField.getText());
        } else {
            utenteLogInData = new UtenteLogInData(codice, password, Ruolo.PAZIENTE);
        }

        AuthenticationBean authenticationBean;
        try {
            authenticationBean = logInController.logIn(utenteLogInData);
        } catch (FomatoInvalidoException | LogInFailedException e ) {
            showAlert(e.getMessage());
            return;
        }

        FXMLLoader loader;
        switch (authenticationBean.getRuolo()) {
            case DOTTORE -> loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/MenuDottore.fxml"));
            case PAZIENTE -> loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/MenuPaziente.fxml"));
            default -> {
                showAlert("Login fallito, credenziali errate");
                return;
            }
        }
        BorderPane root = (BorderPane) loader.load();
        Stage stage = menuWindowManager.getMainStage();
        AuthenticationBean finalAuthenticationBean = authenticationBean;
        stage.setOnCloseRequest(event -> {
            LogInController logOutController = new LogInController();
            logOutController.logOut(finalAuthenticationBean.getCodice());
        });
        ((GuiGraphicController)loader.getController()).initialize(new Object[]{authenticationBean, menuWindowManager});
        menuWindowManager.setMenu(root);

    }

    @FXML
    void handleRegistration(ActionEvent event){
        menuWindowManager.showRegisterScene();
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        this.menuWindowManager = (MenuWindowManager) args[0];
    }
}
