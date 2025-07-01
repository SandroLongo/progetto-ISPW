package it.uniroma2.progettoispw.controller.graphicController.cliGraphicController;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class PromptController {
    private Receiver receiver;
    private AuthenticationBean authenticationBean;

    @FXML
    private TextField commandLine;

    @FXML
    private TextArea terminalOutput;

    @FXML
    void processCommand(ActionEvent event) {
        String command = commandLine.getText().trim();
        terminalOutput.appendText("> " + command + "\n");
        terminalOutput.appendText(receiver.receive(command + "\n"));
        commandLine.clear();

    }

    public String setReceiver(Receiver receiver) {
        this.receiver = receiver;
        return receiver.getInitialMessage();
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }
}
