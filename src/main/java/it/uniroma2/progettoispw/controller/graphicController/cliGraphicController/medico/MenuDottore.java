package it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.medico;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.AbstractState;
import it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.Receiver;
import it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.paziente.MenuPaziente;
import it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.paziente.Richieste;
import it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.paziente.Terapia;

public class MenuDottore extends Receiver {
    private AuthenticationBean authenticationBean;
    private Receiver terapiaReceiver;
    private Receiver inviarichiesteReceiver;

    public MenuDottore(AuthenticationBean authenticationBean, Receiver receiver) {
        this.authenticationBean = authenticationBean;
        this.previousReceiver = receiver;
        this.promptController = receiver.getPromptController();
        this.inviarichiesteReceiver = new InviaRichiesta(authenticationBean, this);
        currentState = new MenuState();
    }

    private class MenuState extends AbstractState {

        public MenuState() {
            this.initialMessage = "MENU DEL PAZIENTE, scegli cosa vuoi fare\n"+
                    "invia --> invia una richiesta\n";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "invia": terapiaReceiver = stateMachine;
                default: return "scelta non trovata";
            }
        }
    }
}
