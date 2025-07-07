package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.medico;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.AbstractState;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.Receiver;

public class DoctorMenu extends Receiver {
    private AuthenticationBean authenticationBean;
    private Receiver terapiaReceiver;
    private Receiver inviarichiesteReceiver;

    public DoctorMenu(AuthenticationBean authenticationBean, Receiver receiver) {
        this.authenticationBean = authenticationBean;
        this.previousReceiver = receiver;
        this.promptController = receiver.getPromptController();
        this.inviarichiesteReceiver = new SendBundle(authenticationBean, this);
        currentState = new MenuState();
    }

    private class MenuState extends AbstractState {

        public MenuState() {
            this.initialMessage = """
                    MENU DEL DOTTORE, scegli cosa vuoi fare
                    send --> send una sentPrescriptionBundle
                    logout --> torna al login
                    """;
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "send": return stateMachine.getPromptController().setReceiver(inviarichiesteReceiver);
                case "logout": stateMachine.getPromptController().resetLogout();
                    LogInController logInController = new LogInController();
                    logInController.logOut(authenticationBean.getCodice());
                    return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
                default: return "scelta non trovata";
            }
        }
    }
}
