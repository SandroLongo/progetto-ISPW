package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.bean.UserLogInData;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.medico.MenuDottore;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.paziente.MenuPaziente;
import it.uniroma2.progettoispw.model.domain.Role;

public class LogInReceiver extends Receiver {

    public LogInReceiver(PromptController promptController) {
        this.promptController = promptController;
        this.currentState = new WelcomeState();
    }

    private class WelcomeState extends AbstractState {

        public WelcomeState() {
            this.initialMessage  = """
                    Benvenuto nell'applicazione, scegli cosa vuoi fare
                    patient --> entra come patient
                    doctor --> entra come doctor
                    registrazione --> registrati
                    """;
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "patient":
                    return stateMachine.goNext(new CFPazienteState());
                case "doctor":
                    return stateMachine.goNext(new CfMedicoState());
                case "registrati":
                    return stateMachine.getPromptController().setReceiver(new Registrazione(stateMachine));
                default:
                    return "scelta non valida\n" + initialMessage;
            }
        }
    }

    private class CfMedicoState extends AbstractState{
        private UserLogInData userLogInData;
        public CfMedicoState() {
            super();
            this.userLogInData = new UserLogInData();
            userLogInData.setRuolo(Role.DOTTORE);
            this.initialMessage = "inserisci il codice fiscale\n";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            this.userLogInData.setCodiceFiscale(command);
            return stateMachine.goNext(new PassMedicoState(userLogInData));
        }
    }

    private class PassMedicoState extends AbstractState{
        private UserLogInData userLogInData;
        public PassMedicoState(UserLogInData userLogInData) {
            super();
            this.initialMessage = "inserisci la password\n";
            this.userLogInData = userLogInData;
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            userLogInData.setPassword(command);
            return stateMachine.goNext(new CodiceMedicoState(userLogInData));
        }
    }

    private class CodiceMedicoState extends AbstractState{
        private UserLogInData userLogInData;

        public CodiceMedicoState(UserLogInData userLogInData) {
            super();
            this.userLogInData = userLogInData;
            this.initialMessage = "inserisci il codice di sicurezza";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            userLogInData.setCodiceDottore(command);
            LogInController logInController = new LogInController();
            AuthenticationBean authenticationBean = logInController.logIn(userLogInData);
            stateMachine.getPromptController().setLogout(authenticationBean);
            stateMachine.getPromptController().setAuthenticationBean(authenticationBean);
            stateMachine.setCurrentState(new WelcomeState());
            switch (authenticationBean.getRuolo()){
                case DOTTORE -> {
                    return stateMachine.getPromptController().setReceiver(new MenuDottore(authenticationBean, stateMachine));
                }
                case PAZIENTE -> {
                    return stateMachine.getPromptController().setReceiver(new MenuPaziente(authenticationBean, stateMachine));
                }
                default -> {
                    return "ruolo non riconosciuto" + initialMessage;
                }
            }
        }
    }

    private class CFPazienteState extends AbstractState{
        private UserLogInData userLogInData;
        public CFPazienteState(){
            super();
            this.userLogInData = new UserLogInData();
            this.userLogInData.setRuolo(Role.PAZIENTE);
            this.initialMessage = "inserisci il codice fiscale";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            userLogInData.setCodiceFiscale(command);
            return stateMachine.goNext(new PassPazienteState(userLogInData));
        }
    }

    private class PassPazienteState extends AbstractState{
        private UserLogInData userLogInData;

        public PassPazienteState(UserLogInData userLogInData) {
            super();
            this.userLogInData = userLogInData;
            this.initialMessage = "inserisci la password";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            userLogInData.setPassword(command);
            LogInController logInController = new LogInController();
            AuthenticationBean authenticationBean = logInController.logIn(userLogInData);
            stateMachine.getPromptController().setAuthenticationBean(authenticationBean);
            stateMachine.setCurrentState(new WelcomeState());
            return "login effettuato con successo \n" + stateMachine.getPromptController().setReceiver(new MenuPaziente(authenticationBean, stateMachine));
        }
    }


}
