package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.DoctorRegistrationData;
import it.uniroma2.progettoispw.controller.bean.FomatoInvalidoException;
import it.uniroma2.progettoispw.controller.bean.PatientRegistrationData;
import it.uniroma2.progettoispw.controller.bean.UserRegistrationData;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.domain.Role;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Registrazione extends Receiver{
    private static final String OPZIONE_NON_VALIDA_ERROR = "opzione non valida\n";

    public Registrazione(Receiver prevoiusReceiver) {
        this.previousReceiver = prevoiusReceiver;
        this.promptController = prevoiusReceiver.getPromptController();
        this.currentState = new OpzioniIniziali();
    }

    private class OpzioniIniziali extends AbstractState{
        private static final String OPZIONE_NON_VALIDA_ERROR = "opzione non valida\n";

        public OpzioniIniziali() {
            this.initialMessage = """
                    scelgi cosa vuoi fare:
                    patient --> registrati come patient
                    doctor --> registrati come doctor
                    indietro --> torna al login
                    """;
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "patient":
                    UserRegistrationData prd = new PatientRegistrationData();
                    return stateMachine.goNext(new InserisciCf(prd));
                case "doctor":
                    UserRegistrationData drd = new DoctorRegistrationData();
                    return stateMachine.goNext(new InserisciCf(drd));
                case "indietro":
                    return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
                default:
                    return OPZIONE_NON_VALIDA_ERROR;
            }
        }
    }

    private class InserisciCf extends AbstractState{
        private UserRegistrationData urd;

        public InserisciCf(UserRegistrationData urd) {
            this.urd = urd;
            this.initialMessage = "Inserisci cf\n";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                urd.setCodiceFiscale(command.toUpperCase());
            } catch (FomatoInvalidoException e) {
                return e.getMessage() + stateMachine.goNext(new OpzioniIntermedie(urd, this));
            }
            return stateMachine.goNext(new InserisciPass(urd));
        }
    }

    private class InserisciPass extends AbstractState{
        private UserRegistrationData urd;

        public InserisciPass(UserRegistrationData urd) {
            this.urd = urd;
            this.initialMessage = "Inserisci una password\n";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                urd.setPassword(command);
            } catch (FomatoInvalidoException e) {
                return e.getMessage() + stateMachine.goNext(new OpzioniIntermedie(urd, this));
            }
            return stateMachine.goNext(new InserisciNome(urd));
        }
    }

    private class InserisciNome extends AbstractState{
        private UserRegistrationData urd;

        public InserisciNome(UserRegistrationData urd) {
            this.urd = urd;
            this.initialMessage = "Inserisci nome\n";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                urd.setNome(command);
            } catch (FomatoInvalidoException e) {
                return e.getMessage()  + stateMachine.goNext(new OpzioniIntermedie(urd, this));
            }
            return stateMachine.goNext(new InserisciCognome(urd));
        }
    }

    private class InserisciCognome extends AbstractState{
        private UserRegistrationData urd;

        public InserisciCognome(UserRegistrationData urd) {
            this.urd = urd;
            this.initialMessage = "Inserisci cognome\n";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                urd.setCognome(command);
            } catch (FomatoInvalidoException e) {
                return e.getMessage() + stateMachine.goNext(new OpzioniIntermedie(urd, this));
            }
            return stateMachine.goNext(new InserisciEmail(urd));
        }
    }

    private class InserisciEmail extends AbstractState{
        private UserRegistrationData urd;

        public InserisciEmail(UserRegistrationData urd) {
            this.urd = urd;
            this.initialMessage = "Inserisci email\n";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                urd.setEmail(command);
            } catch (FomatoInvalidoException e) {
                return e.getMessage() + stateMachine.goNext(new OpzioniIntermedie(urd, this));
            }
            return stateMachine.goNext(new InserisciTelefono(urd));
        }
    }

    private class InserisciTelefono extends AbstractState{
        private UserRegistrationData urd;

        public InserisciTelefono(UserRegistrationData urd) {
            this.urd = urd;
            this.initialMessage = "Inserisci il tuo telefono\n";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                urd.setTelefono(command);
            } catch (FomatoInvalidoException e) {
                return e.getMessage() + stateMachine.goNext(new OpzioniIntermedie(urd, this));
            }
            return stateMachine.goNext(new InserisciDataNascita(urd));
        }
    }

    private class InserisciDataNascita extends AbstractState{
        private UserRegistrationData urd;

        public InserisciDataNascita(UserRegistrationData urd) {
            this.urd = urd;
            this.initialMessage = "Inserisci data di nascita(dd-MM-yyyy)\n";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(command, formatter);
                urd.setDataNascita(date);
            } catch (DateTimeParseException e) {
                return "devi inserire una data come 31-1-2002(gg-mm-aaaa)\n" + initialMessage;
            } catch (FomatoInvalidoException e) {
                return e.getMessage()  + stateMachine.goNext(new OpzioniIntermedie(urd, this));
            }
            try{
                String messaggio = "registrazione effettuata con successo";
                LogInController logInController = new LogInController();
                if (urd.isType() == Role.PAZIENTE){
                    logInController.registerPaziente((PatientRegistrationData) urd);
                } else {
                    messaggio += "il tuo codice è " +logInController.registerDottore((DoctorRegistrationData) urd);
                }
                return messaggio + stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
            } catch (DaoException e){
                return e.getMessage() + "\n" + stateMachine.goNext(new OpzioniIniziali());
            }
        }
    }

    private class OpzioniIntermedie extends AbstractState{
        private UserRegistrationData urd;
        private AbstractState precedente;

        public OpzioniIntermedie(UserRegistrationData urd, AbstractState precedente) {
            this.urd = urd;
            this.precedente = precedente;
            this.initialMessage = """
                    scegli cosa vuoi fare:
                    continua --> continua con la registrazine
                    indietro --> torna al login
                    """;
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "continua":
                    return stateMachine.goNext(precedente);
                case "indietro":
                    return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
                default:
                    return OPZIONE_NON_VALIDA_ERROR;
            }
        }
    }


}
