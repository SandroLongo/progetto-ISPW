package it.uniroma2.progettoispw.controller.graphic.controller.cliGraphicController.paziente;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.graphic.controller.cliGraphicController.AbstractState;
import it.uniroma2.progettoispw.controller.graphic.controller.cliGraphicController.Receiver;

public class MenuPaziente extends Receiver {
    private AuthenticationBean authenticationBean;
    private Receiver terapiaReceiver;
    private Receiver richiesteReceiver;

    public MenuPaziente(AuthenticationBean authenticationBean, Receiver receiver) {
        this.promptController = receiver.getPromptController();
        this.authenticationBean = authenticationBean;
        this.previousReceiver = receiver;
        this.terapiaReceiver = new Terapia(authenticationBean, this);
        this.richiesteReceiver = new Richieste(authenticationBean, this);
        currentState = new MenuState();
    }

    private class MenuState extends AbstractState {

        public MenuState() {
            this.initialMessage = "MENU DEL PAZIENTE, scegli cosa vuoi fare\n"+
                    "terapia --> visualizza la tua terapia\n"+
                    "richieste --> visualizza le tue richieste\n";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "terapia": return stateMachine.getPromptController().setReceiver(terapiaReceiver);
                case "richieste": return stateMachine.getPromptController().setReceiver(richiesteReceiver);
                default: return "scelta non trovata";
            }
        }
    }

}
