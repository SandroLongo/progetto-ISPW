package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.paziente;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.controller.controller.applicativi.TherapyController;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.AbstractState;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.InsertFinalInformation;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.Receiver;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.SelectMedication;

import java.time.LocalDate;

public class ManageTherapy extends Receiver {
    private AuthenticationBean authenticationBean;

    public ManageTherapy(AuthenticationBean authenticationBean, Receiver previousReceiver) {
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
            this.dailyTherapyBean = therapyController.getDailyTherapy(authenticationBean.getCodice(), currentDate);
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
                    this.dailyTherapyBean = therapyController.getDailyTherapy(authenticationBean.getCodice(), currentDate);
                    return stateMachine.goNext(new ShowTerapiaState(authenticationBean, currentDate));
                case "indietro": this.currentDate = currentDate.minusDays(1);
                    this.dailyTherapyBean = therapyController.getDailyTherapy(authenticationBean.getCodice(), currentDate);
                    return stateMachine.goNext(new ShowTerapiaState(authenticationBean, currentDate));
                case "aggiungi": PrescriptionBean prescriptionBean = new PrescriptionBean();
                    return stateMachine.goNext(new AggiungiStateFase1(authenticationBean, therapyController, prescriptionBean)) +
                            stateMachine.getPromptController().setReceiver(new SelectMedication(prescriptionBean.getDose(), stateMachine));
                case "menu": return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
                default: return "selezione invalida";
            }
        }

    }

    private class AggiungiStateFase1 extends AbstractState{
        private AuthenticationBean authenticationBean;
        private PrescriptionBean prescriptionBean;
        private TherapyController therapyController;

        public AggiungiStateFase1(AuthenticationBean authenticationBean, TherapyController therapyController, PrescriptionBean prescriptionBean) {
            this.prescriptionBean = prescriptionBean;
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
            DoseBean dosebean = prescriptionBean.getDose();
            if (dosebean.getName() != null && dosebean.getId() != null) {
                FinalStepBean finalStepBean = new FinalStepBean();
                return stateMachine.goNext(new AggiungiStateFase2(authenticationBean, therapyController, prescriptionBean, finalStepBean)) + stateMachine.getPromptController().setReceiver(new InsertFinalInformation(finalStepBean, stateMachine)) ;
            } else {
                return stateMachine.goNext(new ShowTerapiaState(authenticationBean));
            }
        }
    }

    private class AggiungiStateFase2 extends AbstractState{
        private AuthenticationBean authenticationBean;
        private PrescriptionBean prescriptionBean;
        private TherapyController therapyController;
        private FinalStepBean finalStepBean;

        public AggiungiStateFase2(AuthenticationBean authenticationBean, TherapyController therapyController, PrescriptionBean prescriptionBean, FinalStepBean finalStepBean) {
            this.finalStepBean = finalStepBean;
            this.prescriptionBean = prescriptionBean;
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
            DoseBean dosebean = prescriptionBean.getDose();
            if (finalStepBean.isComplete()) {
                prescriptionBean.setLastInformation(finalStepBean);
                therapyController.addDose(authenticationBean.getCodice(), prescriptionBean);
                return "dose aggiunta con successo\n" + stateMachine.goNext(new ShowTerapiaState(authenticationBean));
            } else {
                return "aggiunta della dose fallita\n" + stateMachine.goNext(new ShowTerapiaState(authenticationBean));
            }
        }
    }
}
