package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.bean.UtenteLogInData;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.medico.MenuDottore;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.paziente.MenuPaziente;
import it.uniroma2.progettoispw.model.domain.Ruolo;

public class LogInReceiver extends Receiver {

    public LogInReceiver(PromptController promptController) {
        this.promptController = promptController;
        this.currentState = new WelcomeState();
    }

    private class WelcomeState extends AbstractState {

        public WelcomeState() {
            this.initialMessage  = "Benvenuto nell'applicazione, scegli in che ruolo vuoi accedere\n" +
                    "paziente --> entra come paziente\n" +
                    "dottore --> entra come dottore\n";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "paziente":
                    return stateMachine.goNext(new CFPazienteState());
                case "dottore":
                    return stateMachine.goNext(new CfMedicoState());
                default:
                    return "scelta non valida\n" + initialMessage;
            }
        }
    }

    private class CfMedicoState extends AbstractState{
        private UtenteLogInData utenteLogInData;
        public CfMedicoState() {
            super();
            this.utenteLogInData = new UtenteLogInData();
            utenteLogInData.setRuolo(Ruolo.Dottore);
            this.initialMessage = "inserisci il codice fiscale\n";
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
            this.initialMessage = "inserisci la password\n";
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
            switch (authenticationBean.getRuolo()){
                case Dottore -> {
                    return stateMachine.getPromptController().setReceiver(new MenuDottore(authenticationBean, stateMachine));
                }
                case Paziente -> {
                    return stateMachine.getPromptController().setReceiver(new MenuPaziente(authenticationBean, stateMachine));
                }
                default -> {
                    return "ruolo non riconosciuto" + initialMessage;
                }
            }
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
            utenteLogInData.setPassword(command);
            LogInController logInController = new LogInController();
            AuthenticationBean authenticationBean = logInController.logIn(utenteLogInData);
            stateMachine.getPromptController().setAuthenticationBean(authenticationBean);
            stateMachine.setCurrentState(new WelcomeState());
            return "login effettuato con successo \n" + stateMachine.getPromptController().setReceiver(new MenuPaziente(authenticationBean, stateMachine));
        }
    }


}
