package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.bean.FomatoInvalidoException;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.controller.bean.UserLogInData;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInFailedException;
import it.uniroma2.progettoispw.model.domain.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
        UserLogInData userLogInData;
        if (checkMedico.isSelected()) {
            userLogInData = new UserLogInData(codice, password, Role.DOCTOR, codiceMedicoField.getText());
        } else {
            userLogInData = new UserLogInData(codice, password, Role.PATIENT);
        }

        AuthenticationBean authenticationBean;
        try {
            authenticationBean = logInController.logIn(userLogInData);
        } catch (FomatoInvalidoException | LogInFailedException e ) {
            showAlert(e.getMessage());
            e.printStackTrace();
            return;
        }

        FXMLLoader loader;
        switch (authenticationBean.getRuolo()) {
            case DOCTOR -> loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/MenuDottore.fxml"));
            case PATIENT -> loader = new FXMLLoader(getClass().getResource("/it/uniroma2/progettoispw/view/MenuPaziente.fxml"));
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
