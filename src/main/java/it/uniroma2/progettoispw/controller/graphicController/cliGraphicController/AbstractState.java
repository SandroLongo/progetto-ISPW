package it.uniroma2.progettoispw.controller.graphicController.cliGraphicController;

public abstract class AbstractState {
    protected String initialMessage;

    public abstract String goNext(Receiver stateMachine, String command);

    public String comeBackAction(Receiver stateMachine) {
        return initialMessage;
    }
}
