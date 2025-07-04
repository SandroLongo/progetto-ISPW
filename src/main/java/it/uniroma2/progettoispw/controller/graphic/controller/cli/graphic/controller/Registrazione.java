package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller;

public class Registrazione extends Receiver{

    public Registrazione(Receiver prevoiusReceiver) {
        this.previousReceiver = prevoiusReceiver;
        this.promptController = prevoiusReceiver.getPromptController();
        this.currentState
    }

    private class opzioniIniziali extends AbstractState{
        private static final String OPZIONE_NON_VALIDA_ERROR = "opzione non valida\n";

        public opzioniIniziali() {
            this.initialMessage = """
                    scelgi cosa vuoi fare:
                    paziente --> registrati come paziente
                    dottore --> registrati come dottore
                    indietro --> torna al login
                    """;
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "paziente":
                    return;
                case "dottore":
                    return;
                case "indietro":
                    return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
                default:
                    return OPZIONE_NON_VALIDA_ERROR;
            }
        }
    }

    private class Inserisci
}
