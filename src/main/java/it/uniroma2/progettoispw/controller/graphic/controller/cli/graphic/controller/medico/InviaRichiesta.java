package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.medico;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.controller.applicativi.RichiesteController;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.AbstractState;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.Receiver;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.SelezionaMedicinale;

public class InviaRichiesta extends Receiver {
    private AuthenticationBean authenticationBean;

    public InviaRichiesta(AuthenticationBean authenticationBean, Receiver receiver) {
        this.previousReceiver = receiver;
        this.promptController = receiver.getPromptController();
        this.authenticationBean = authenticationBean;
        this.currentState = new Opzioni(authenticationBean);
    }

    private class Opzioni extends AbstractState {
        private AuthenticationBean authenticationBean;
        public Opzioni(AuthenticationBean authenticationBean) {
            this.authenticationBean = authenticationBean;
            this.initialMessage = "scegli cosa vuoi fare:\n" +
                    "menu -->  torna al menu\n" +
                    "inizia --> inizia l'invio della richiesta";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "inizia":
                    return stateMachine.goNext(new GetCfPaziente(authenticationBean));
                case "menu":
                    return stateMachine.getPromptController().setReceiver(getPreviousReceiver());
                default:
                    return "opzione non valida\n" + initialMessage;
            }
        }
    }

    private class GetCfPaziente extends AbstractState {
        private AuthenticationBean authenticationBean;
        private RichiesteController richiesteController;

        public GetCfPaziente(AuthenticationBean authenticationBean) {
            this.authenticationBean = authenticationBean;
            this.initialMessage = "inserisci il codice fiscale del paziente a cui vuoi mandare la richiesta\n";
        }
        @Override
        public String goNext(Receiver stateMachine, String command) {
            InformazioniUtente informazioniUtente = richiesteController.getInformazioniPaziente(authenticationBean.getCodice(), command);
            if (informazioniUtente == null) {
                return "paziente non trovato\n" + stateMachine.goNext(new Opzioni(authenticationBean));
            } else {
                return stateMachine.goNext(new MostraInformazionePaziente(authenticationBean, richiesteController, informazioniUtente));
            }
        }
    }

    private class MostraInformazionePaziente extends AbstractState {
        private AuthenticationBean authenticationBean;
        private RichiesteController richiesteController;
        private InformazioniUtente informazioniUtente;
        public MostraInformazionePaziente(AuthenticationBean authenticationBean, RichiesteController richiesteController, InformazioniUtente informazioniUtente) {
            this.authenticationBean = authenticationBean;
            this.richiesteController = richiesteController;
            this.informazioniUtente = informazioniUtente;
            this.initialMessage =this.informazioniUtente.toString() + "Verifica le informazioni e:\n" +
                    "conferma --> procedi alla costruzione della richiesta\n" +
                    "indietro --> inserisci un altro codice fiscale\n";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "conferma":
                    DoseCostructor doseCostructor = new DoseCostructor();
                    DoseBean doseBean = doseCostructor.getDose();
                    RichiestaBean richiestaBean = new RichiestaBean();
                    stateMachine.goNext(new AggiungiState1(authenticationBean, richiesteController, doseCostructor, richiestaBean));
                    return stateMachine.getPromptController().setReceiver(new SelezionaMedicinale(doseBean, stateMachine));
                case "indietro": return stateMachine.goNext(new GetCfPaziente(authenticationBean));
                default: return "comando non riconosciuto";
            }
        }
    }

    private class RecapRichiesta extends AbstractState {
        private AuthenticationBean authenticationBean;
        private RichiesteController richiesteController;
        private RichiestaBean richiestaBean;
        private DoseCostructor doseCostructor;

        public RecapRichiesta(AuthenticationBean authenticationBean, RichiesteController richiesteController, DoseCostructor doseCostructor) {
            this.authenticationBean = authenticationBean;
            this.richiesteController = richiesteController;
            this.doseCostructor = doseCostructor;
            this.richiestaBean = new RichiestaBean();
            this.initialMessage = "Recap della richiesta:\n" + richiestaBean.toString() + "scgli cosa vuoi fare:\n" +
                    "invia -->  invia la richiesta\n" +
                    "indietro --> annulla creazione\n" +
                    "aggiungi --> aggiungi un altra dose\n";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "invia":
                    richiesteController.invia(authenticationBean.getCodice(), richiestaBean);
                case "aggiungi":
                    return stateMachine.goNext(new AggiungiState1(authenticationBean, richiesteController, doseCostructor, richiestaBean));
                case "indietro":
                    return stateMachine.goNext(new Opzioni(authenticationBean));
                default: return "comando non riconosciuto" + initialMessage;
            }
        }


    }

    private class AggiungiState1 extends AbstractState {
        private AuthenticationBean authenticationBean;
        private RichiesteController richiesteController;
        private RichiestaBean richiestaBean;
        private DoseCostructor doseCostructor;

        public AggiungiState1(AuthenticationBean authenticationBean, RichiesteController richiesteController, DoseCostructor doseCostructor, RichiestaBean richiestaBean) {
            this.authenticationBean = authenticationBean;
            this.richiesteController = richiesteController;
            this.richiestaBean = richiestaBean;
            this.doseCostructor = doseCostructor;
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            return "";
        }

        @Override
        public String comeBackAction(Receiver stateMachine){
            DoseBean dosebean = doseCostructor.getDose();
            if (dosebean.getNome() != null && dosebean.getCodice() != null) {
                return "ora immetti le informazioni finali\n "+ stateMachine.goNext(new AggiungiState2(authenticationBean, richiesteController, doseCostructor, richiestaBean)) ;
            } else {
                return stateMachine.goNext(new RecapRichiesta(authenticationBean, richiesteController, doseCostructor));
            }
        }
    }

    private class AggiungiState2 extends AbstractState {
        private AuthenticationBean authenticationBean;
        private RichiesteController richiesteController;
        private RichiestaBean richiestaBean;
        private DoseCostructor doseCostructor;

        public AggiungiState2(AuthenticationBean authenticationBean, RichiesteController richiesteController, DoseCostructor doseCostructor, RichiestaBean richiestaBean) {
            this.authenticationBean = authenticationBean;
            this.richiesteController = richiesteController;
            this.richiestaBean = richiestaBean;
            this.doseCostructor = doseCostructor;
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            return "";
        }

        @Override
        public String comeBackAction(Receiver stateMachine){
            if (doseCostructor.isComplete()) {
                richiestaBean.addDoseCostructor(doseCostructor);
                return "dose aggiunta con successo" + stateMachine.goNext(new RecapRichiesta(authenticationBean, richiesteController, doseCostructor));
            } else {
                return stateMachine.goNext(new RecapRichiesta(authenticationBean, richiesteController, doseCostructor));
            }
        }
    }
}
