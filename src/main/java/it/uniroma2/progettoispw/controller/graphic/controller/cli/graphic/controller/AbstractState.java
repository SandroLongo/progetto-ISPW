package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller;

public abstract class AbstractState {
    protected String initialMessage;

    public abstract String goNext(Receiver stateMachine, String command);

    public String getInitialMessage() {
        return initialMessage;
    }

    public String comeBackAction(Receiver stateMachine) {
        return stateMachine.getInitialMessage();
    }
}
