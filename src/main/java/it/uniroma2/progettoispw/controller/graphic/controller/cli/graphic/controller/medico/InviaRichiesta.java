package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.medico;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.controller.applicativi.SendPrescriptionBundleController;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.AbstractState;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.InformazioniFinali;
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
            this.initialMessage = """
                    scegli cosa vuoi fare:
                    menu -->  torna al menu
                    inizia --> inizia l'invio della sentPrescriptionBundle
                    """;
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
        private SendPrescriptionBundleController sendPrescriptionBundleController = new SendPrescriptionBundleController();

        public GetCfPaziente(AuthenticationBean authenticationBean) {
            this.authenticationBean = authenticationBean;
            this.initialMessage = "inserisci il codice fiscale del paziente a cui vuoi mandare la sentPrescriptionBundle\n";
        }
        @Override
        public String goNext(Receiver stateMachine, String command) {
            InformazioniUtente informazioniUtente = sendPrescriptionBundleController.getInformazioniPaziente(authenticationBean.getCodice(), command);
            if (informazioniUtente == null) {
                return "paziente non trovato\n" + stateMachine.goNext(new Opzioni(authenticationBean));
            } else {
                return stateMachine.goNext(new MostraInformazionePaziente(authenticationBean, sendPrescriptionBundleController, informazioniUtente));
            }
        }
    }

    private class MostraInformazionePaziente extends AbstractState {
        private AuthenticationBean authenticationBean;
        private SendPrescriptionBundleController sendPrescriptionBundleController;
        private InformazioniUtente informazioniUtente;
        public MostraInformazionePaziente(AuthenticationBean authenticationBean, SendPrescriptionBundleController sendPrescriptionBundleController, InformazioniUtente informazioniUtente) {
            this.authenticationBean = authenticationBean;
            this.sendPrescriptionBundleController = sendPrescriptionBundleController;
            this.informazioniUtente = informazioniUtente;
            this.initialMessage =this.informazioniUtente.toString() + "Verifica le informazioni e:\n" +
                    "conferma --> procedi alla costruzione della sentPrescriptionBundle\n" +
                    "indietro --> inserisci un altro codice fiscale\n";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "conferma":
                    PrescriptionBean prescriptionBean = new PrescriptionBean();
                    DoseBean doseBean = prescriptionBean.getDose();
                    PrescriptionBundleBean prescriptionBundleBean = new PrescriptionBundleBean();
                    prescriptionBundleBean.setRicevente(informazioniUtente);
                    stateMachine.goNext(new AggiungiState1(authenticationBean, sendPrescriptionBundleController, prescriptionBean, prescriptionBundleBean));
                    return stateMachine.getPromptController().setReceiver(new SelezionaMedicinale(doseBean, stateMachine));
                case "indietro": return stateMachine.goNext(new GetCfPaziente(authenticationBean));
                default: return "comando non riconosciuto";
            }
        }
    }

    private class RecapRichiesta extends AbstractState {
        private AuthenticationBean authenticationBean;
        private SendPrescriptionBundleController sendPrescriptionBundleController;
        private PrescriptionBundleBean prescriptionBundleBean;
        private PrescriptionBean prescriptionBean;

        public RecapRichiesta(AuthenticationBean authenticationBean, SendPrescriptionBundleController sendPrescriptionBundleController, PrescriptionBean prescriptionBean, PrescriptionBundleBean prescriptionBundleBean) {
            this.authenticationBean = authenticationBean;
            this.sendPrescriptionBundleController = sendPrescriptionBundleController;
            this.prescriptionBean = prescriptionBean;
            this.prescriptionBundleBean = prescriptionBundleBean;
            this.initialMessage = "Recap della sentPrescriptionBundle:\n" + prescriptionBundleBean.toString() + "scegli cosa vuoi fare:\n" +
                    "invia -->  invia la sentPrescriptionBundle\n" +
                    "indietro --> annulla creazione\n" +
                    "aggiungi --> aggiungi un altra dose\n";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "invia":
                    sendPrescriptionBundleController.invia(authenticationBean.getCodice(), prescriptionBundleBean);
                    return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
                case "aggiungi":
                    return stateMachine.goNext(new AggiungiState1(authenticationBean, sendPrescriptionBundleController, prescriptionBean, prescriptionBundleBean));
                case "indietro":
                    return stateMachine.goNext(new Opzioni(authenticationBean));
                default: return "comando non riconosciuto" + initialMessage;
            }
        }


    }

    private class AggiungiState1 extends AbstractState {
        private AuthenticationBean authenticationBean;
        private SendPrescriptionBundleController sendPrescriptionBundleController;
        private PrescriptionBundleBean prescriptionBundleBean;
        private PrescriptionBean prescriptionBean;

        public AggiungiState1(AuthenticationBean authenticationBean, SendPrescriptionBundleController sendPrescriptionBundleController, PrescriptionBean prescriptionBean, PrescriptionBundleBean prescriptionBundleBean) {
            this.authenticationBean = authenticationBean;
            this.sendPrescriptionBundleController = sendPrescriptionBundleController;
            this.prescriptionBundleBean = prescriptionBundleBean;
            this.prescriptionBean = prescriptionBean;
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            return "";
        }

        @Override
        public String comeBackAction(Receiver stateMachine){
            DoseBean dosebean = prescriptionBean.getDose();
            if (dosebean.getNome() != null && dosebean.getCodice() != null) {
                return stateMachine.goNext(new AggiungiState2(authenticationBean, sendPrescriptionBundleController, prescriptionBean, prescriptionBundleBean)) +
                        stateMachine.getPromptController().setReceiver(new InformazioniFinali(prescriptionBean, stateMachine));
            } else {
                return "aggiunta abortita" + stateMachine.goNext(new RecapRichiesta(authenticationBean, sendPrescriptionBundleController, prescriptionBean, prescriptionBundleBean));
            }
        }
    }

    private class AggiungiState2 extends AbstractState {
        private AuthenticationBean authenticationBean;
        private SendPrescriptionBundleController sendPrescriptionBundleController;
        private PrescriptionBundleBean prescriptionBundleBean;
        private PrescriptionBean prescriptionBean;

        public AggiungiState2(AuthenticationBean authenticationBean, SendPrescriptionBundleController sendPrescriptionBundleController, PrescriptionBean prescriptionBean, PrescriptionBundleBean prescriptionBundleBean) {
            this.authenticationBean = authenticationBean;
            this.sendPrescriptionBundleController = sendPrescriptionBundleController;
            this.prescriptionBundleBean = prescriptionBundleBean;
            this.prescriptionBean = prescriptionBean;
            this.initialMessage = "ora immetti le informazioni finali\n ";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            return "";
        }

        @Override
        public String comeBackAction(Receiver stateMachine){
            if (prescriptionBean.isComplete()) {
                prescriptionBundleBean.addDoseCostructor(prescriptionBean);
                return "dose aggiunta con successo" + stateMachine.goNext(new RecapRichiesta(authenticationBean, sendPrescriptionBundleController, prescriptionBean, prescriptionBundleBean));
            } else {
                return "aggiunta abortita" + stateMachine.goNext(new RecapRichiesta(authenticationBean, sendPrescriptionBundleController, prescriptionBean, prescriptionBundleBean));
            }
        }
    }
}
