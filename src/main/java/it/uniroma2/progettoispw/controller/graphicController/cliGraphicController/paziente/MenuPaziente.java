package it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.paziente;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.AbstractState;
import it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.Receiver;

public class MenuPaziente extends Receiver {
    private AuthenticationBean authenticationBean;
    private Receiver terapiaReceiver;
    private Receiver richiesteReceiver;

    public MenuPaziente(AuthenticationBean authenticationBean, Receiver receiver) {
        this.authenticationBean = authenticationBean;
        this.previousReceiver = receiver;
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
                case "terapia": return;
                case "richieste": terapiaReceiver = stateMachine;
                default: return "scelta non trovata";
            }
        }
    }

}
