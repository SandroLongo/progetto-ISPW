package it.uniroma2.progettoispw.controller.graphic.controller.cliGraphicController;

public abstract class Receiver {
    protected PromptController promptController;
    protected AbstractState currentState;
    protected Receiver previousReceiver;

    public String receive(String msg) {
        return this.currentState.goNext(this, msg);
    }

    public String getInitialMessage(){
        return currentState.comeBackAction(this);
    }

    public String goNext(AbstractState nextState) {
        this.currentState = nextState;
        return nextState.getInitialMessage();
    }

    public PromptController getPromptController() {
        return promptController;
    }

    public void setPromptController(PromptController promptController) {
        this.promptController = promptController;
    }

    public AbstractState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(AbstractState currentState) {
        this.currentState = currentState;
    }

    public Receiver getPreviousReceiver() {
        return previousReceiver;
    }

    public void setPreviousReceiver(Receiver previousReceiver) {
        this.previousReceiver = previousReceiver;
    }
}


