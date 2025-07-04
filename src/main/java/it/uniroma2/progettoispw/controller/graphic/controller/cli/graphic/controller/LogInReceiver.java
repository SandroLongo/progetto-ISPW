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
            this.initialMessage  = """
                    Benvenuto nell'applicazione, scegli cosa vuoi fare
                    paziente --> entra come paziente
                    dottore --> entra come dottore
                    registrazione --> registrati
                    """;
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "paziente":
                    return stateMachine.goNext(new CFPazienteState());
                case "dottore":
                    return stateMachine.goNext(new CfMedicoState());
                case "registrati":
                    return stateMachine.getPromptController().setReceiver(new Registrazione(stateMachine));
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
            utenteLogInData.setRuolo(Ruolo.DOTTORE);
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
            utenteLogInData.setPassword(command);
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
        private UtenteLogInData utenteLogInData;
        public CFPazienteState(){
            super();
            this.utenteLogInData = new UtenteLogInData();
            this.utenteLogInData.setRuolo(Ruolo.PAZIENTE);
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
