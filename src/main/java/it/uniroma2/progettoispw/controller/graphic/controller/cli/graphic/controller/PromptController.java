package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.GuiGraphicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class PromptController extends GuiGraphicController {
    private Receiver receiver;
    private AuthenticationBean authenticationBean;
    private Stage stage;

    @FXML
    private TextField commandLine;

    @FXML
    private TextArea terminalOutput;

    @FXML
    void processCommand(ActionEvent event) {
        String command = commandLine.getText().trim();
        terminalOutput.appendText("> " + command + "\n");
        terminalOutput.appendText(receiver.receive(command));
        commandLine.clear();

    }

    public String setReceiver(Receiver receiver) {
        this.receiver = receiver;
        return receiver.comeBackAction();
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }

    public void setLogout(AuthenticationBean aB) {
        stage.setOnCloseRequest(event -> {
            LogInController logOutController = new LogInController();
            logOutController.logOut(aB.getCodice());
        });
    }

    public void resetLogout(){
        stage.setOnCloseRequest(null);
    }

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    @Override
    public void initialize(Object[] args) throws IOException {
        terminalOutput.appendText(setReceiver((Receiver) args[0]));
        this.stage = (Stage)args[1];
    }
}
