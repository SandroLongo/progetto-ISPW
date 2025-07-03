package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.medico;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.AbstractState;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.Receiver;

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
            this.initialMessage = "MENU DEL DOTTORE, scegli cosa vuoi fare\n"+
                    "invia --> invia una richiesta\n" +
                    "menu --> torna al menu\n";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "invia": terapiaReceiver = stateMachine;
                case "menu": stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
                default: return "scelta non trovata";
            }
        }
    }
}
