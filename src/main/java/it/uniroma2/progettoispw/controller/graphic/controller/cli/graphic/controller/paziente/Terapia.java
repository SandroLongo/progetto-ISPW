package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.paziente;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.bean.DoseBean;
import it.uniroma2.progettoispw.controller.bean.Prescription;
import it.uniroma2.progettoispw.controller.bean.DailyTherapyBean;
import it.uniroma2.progettoispw.controller.controller.applicativi.TherapyController;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.AbstractState;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.InformazioniFinali;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.Receiver;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.SelezionaMedicinale;

import java.time.LocalDate;

public class Terapia extends Receiver {
    private AuthenticationBean authenticationBean;

    public Terapia(AuthenticationBean authenticationBean, Receiver previousReceiver) {
        this.authenticationBean = authenticationBean;
        this.promptController = previousReceiver.getPromptController();
        this.currentState = new ShowTerapiaState(authenticationBean);
        this.previousReceiver = previousReceiver;
    }

    private class ShowTerapiaState extends AbstractState{
        private LocalDate currentDate;
        private AuthenticationBean authenticationBean;
        private DailyTherapyBean dailyTherapyBean;
        private TherapyController therapyController;

        public ShowTerapiaState(AuthenticationBean authenticationBean) {
            this(authenticationBean, LocalDate.now());
        }

        public ShowTerapiaState(AuthenticationBean authenticationBean, LocalDate currentDate) {
            this.authenticationBean = authenticationBean;
            this.currentDate = currentDate;
            this.therapyController = new TherapyController();
            this.dailyTherapyBean = therapyController.getTerapiaGiornaliera(authenticationBean.getCodice(), currentDate);
            this.initialMessage = dailyTherapyBean.toString() + "scegli cosa fare\n" +
                    "avanti --> giorno successivo\n" +
                    "indietro --> giorno precedente\n" +
                    "aggiungi --> procedi all'aggiunta di un medicinale\n" +
                    "menu --> torna al menu\n";

        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            String opzione = command.trim();

            switch (opzione) {
                case "avanti": this.currentDate = currentDate.plusDays(1);
                    this.dailyTherapyBean = therapyController.getTerapiaGiornaliera(authenticationBean.getCodice(), currentDate);
                    return stateMachine.goNext(new ShowTerapiaState(authenticationBean, currentDate));
                case "indietro": this.currentDate = currentDate.minusDays(1);
                    this.dailyTherapyBean = therapyController.getTerapiaGiornaliera(authenticationBean.getCodice(), currentDate);
                    return stateMachine.goNext(new ShowTerapiaState(authenticationBean, currentDate));
                case "aggiungi": Prescription prescription = new Prescription();
                    return stateMachine.goNext(new AggiungiStateFase1(authenticationBean, therapyController, prescription)) +
                            stateMachine.getPromptController().setReceiver(new SelezionaMedicinale(prescription.getDose(), stateMachine));
                case "menu": return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
                default: return "selezione invalida";
            }
        }

    }

    private class AggiungiStateFase1 extends AbstractState{
        private AuthenticationBean authenticationBean;
        private Prescription prescription;
        private TherapyController therapyController;

        public AggiungiStateFase1(AuthenticationBean authenticationBean, TherapyController therapyController, Prescription prescription) {
            this.prescription = prescription;
            this.authenticationBean = authenticationBean;
            this.therapyController = therapyController;
            this.initialMessage = "";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            return "";
        }

        @Override
        public String comeBackAction(Receiver stateMachine){
            DoseBean dosebean = prescription.getDose();
            if (dosebean.getNome() != null && dosebean.getCodice() != null) {
                return stateMachine.goNext(new AggiungiStateFase2(authenticationBean, therapyController, prescription)) + stateMachine.getPromptController().setReceiver(new InformazioniFinali(prescription, stateMachine)) ;
            } else {
                return stateMachine.goNext(new ShowTerapiaState(authenticationBean));
            }
        }
    }

    private class AggiungiStateFase2 extends AbstractState{
        private AuthenticationBean authenticationBean;
        private Prescription prescription;
        private TherapyController therapyController;

        public AggiungiStateFase2(AuthenticationBean authenticationBean, TherapyController therapyController, Prescription prescription) {
            this.prescription = prescription;
            this.authenticationBean = authenticationBean;
            this.therapyController = therapyController;
            this.initialMessage = "ora immetti le informazioni finali\n ";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            return "";
        }

        @Override
        public String comeBackAction(Receiver stateMachine){
            DoseBean dosebean = prescription.getDose();
            if (dosebean.isCompleate()) {
                therapyController.addDose(authenticationBean.getCodice(), prescription);
                return "dose aggiunta con successo\n" + stateMachine.goNext(new ShowTerapiaState(authenticationBean));
            } else {
                return "aggiunta della dose fallita\n" + stateMachine.goNext(new ShowTerapiaState(authenticationBean));
            }
        }
    }
}
