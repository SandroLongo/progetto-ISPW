package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.DottoreRegistrationData;
import it.uniroma2.progettoispw.controller.bean.FomatoInvalidoException;
import it.uniroma2.progettoispw.controller.bean.PazienteRegistrationData;
import it.uniroma2.progettoispw.controller.bean.UtenteRegistrationData;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.domain.Ruolo;

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
                    paziente --> registrati come paziente
                    dottore --> registrati come dottore
                    indietro --> torna al login
                    """;
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "paziente":
                    UtenteRegistrationData prd = new PazienteRegistrationData();
                    return stateMachine.goNext(new InserisciCf(prd));
                case "dottore":
                    UtenteRegistrationData drd = new DottoreRegistrationData();
                    return stateMachine.goNext(new InserisciCf(drd));
                case "indietro":
                    return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
                default:
                    return OPZIONE_NON_VALIDA_ERROR;
            }
        }
    }

    private class InserisciCf extends AbstractState{
        private UtenteRegistrationData urd;

        public InserisciCf(UtenteRegistrationData urd) {
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
        private UtenteRegistrationData urd;

        public InserisciPass(UtenteRegistrationData urd) {
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
        private UtenteRegistrationData urd;

        public InserisciNome(UtenteRegistrationData urd) {
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
        private UtenteRegistrationData urd;

        public InserisciCognome(UtenteRegistrationData urd) {
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
        private UtenteRegistrationData urd;

        public InserisciEmail(UtenteRegistrationData urd) {
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
        private UtenteRegistrationData urd;

        public InserisciTelefono(UtenteRegistrationData urd) {
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
        private UtenteRegistrationData urd;

        public InserisciDataNascita(UtenteRegistrationData urd) {
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
                if (urd.isType() == Ruolo.PAZIENTE){
                    logInController.registerPaziente((PazienteRegistrationData) urd);
                } else {
                    messaggio += "il tuo codice è " +logInController.registerDottore((DottoreRegistrationData) urd);
                }
                return messaggio + stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
            } catch (DaoException e){
                return e.getMessage() + "\n" + stateMachine.goNext(new OpzioniIniziali());
            }
        }
    }

    private class OpzioniIntermedie extends AbstractState{
        private UtenteRegistrationData urd;
        private AbstractState precedente;

        public OpzioniIntermedie(UtenteRegistrationData urd, AbstractState precedente) {
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
