package it.uniroma2.progettoispw.controller.graphicController.cliGraphicController;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.bean.DottoreRegistrationData;
import it.uniroma2.progettoispw.controller.bean.UtenteLogInData;
import it.uniroma2.progettoispw.controller.bean.UtenteRegistrationData;
import it.uniroma2.progettoispw.controller.controllerApplicativi.LogInController;
import it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.paziente.MenuPaziente;
import it.uniroma2.progettoispw.model.domain.Ruolo;

public class LogInReceiver extends Receiver {

    public LogInReceiver(PromptController promptController) {
        this.promptController = promptController;
        this.currentState = new WelcomeState();
    }

    private class WelcomeState extends AbstractState {

        @Override
        public String goNext(Receiver stateMachine, String command) {
            int opzione;
            try {
                opzione = Integer.parseInt(command);
            } catch (NumberFormatException e) {
                return "la scelta deve essere un numero";
            }
            switch (opzione) {
                case 1: return (stateMachine.goNext(new CfMedicoState()));
                case 2: return "";
                default: return "opzione non valida";
            }
        }
    }

    private class CfMedicoState extends AbstractState{
        private UtenteLogInData utenteLogInData;
        public CfMedicoState() {
            super();
            this.utenteLogInData = new UtenteLogInData();
            utenteLogInData.setRuolo(Ruolo.Dottore);
            this.initialMessage = "inserisci il codice fiscale";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            this.utenteLogInData.setCodiceFiscale(command);
            return stateMachine.goNext(new PassMedicoState(utenteLogInData));
        }
    }

    private class PassMedicoState extends AbstractState{
        private UtenteLogInData utenteLogInData;
        public PassMedicoState(UtenteLogInData utenteLogInData) {
            super();
            this.initialMessage = "inserisci la password";
            this.utenteLogInData = utenteLogInData;
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            return stateMachine.goNext(new CodiceMedicoState(utenteLogInData));
        }
    }

    private class CodiceMedicoState extends AbstractState{
        private UtenteLogInData utenteLogInData;

        public CodiceMedicoState(UtenteLogInData utenteLogInData) {
            super();
            this.utenteLogInData = utenteLogInData;
            this.initialMessage = "inserisci il codice di sicurezza";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            utenteLogInData.setCodiceDottore(command);
            LogInController logInController = new LogInController();
            AuthenticationBean authenticationBean = logInController.logIn(utenteLogInData);
            stateMachine.getPromptController().setAuthenticationBean(authenticationBean);
            stateMachine.setCurrentState(new WelcomeState());
            return "login effettuato \n" + stateMachine.getPromptController().setReceiver();;
        }
    }

    private class CFPazienteState extends AbstractState{
        private UtenteLogInData utenteLogInData;
        public CFPazienteState(){
            super();
            this.utenteLogInData = new UtenteLogInData();
            this.utenteLogInData.setRuolo(Ruolo.Paziente);
            this.initialMessage = "inserisci il codice fiscale";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            utenteLogInData.setCodiceFiscale(command);
            return stateMachine.goNext(new PassPazienteState(utenteLogInData));
        }
    }

    private class PassPazienteState extends AbstractState{
        private UtenteLogInData utenteLogInData;
        public PassPazienteState(UtenteLogInData utenteLogInData) {
            super();
            this.utenteLogInData = utenteLogInData;
            this.initialMessage = "inserisci la password";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            LogInController logInController = new LogInController();
            AuthenticationBean authenticationBean = logInController.logIn(utenteLogInData);
            stateMachine.getPromptController().setAuthenticationBean(authenticationBean);
            stateMachine.setCurrentState(new WelcomeState());
            return "login effettuato con successo \n" + stateMachine.getPromptController().setReceiver(new MenuPaziente(authenticationBean, stateMachine));
        }
    }


}
