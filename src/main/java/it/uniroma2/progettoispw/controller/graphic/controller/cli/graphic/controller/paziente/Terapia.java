package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.paziente;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.bean.DoseBean;
import it.uniroma2.progettoispw.controller.bean.DoseCostructor;
import it.uniroma2.progettoispw.controller.bean.TerapiaGiornalieraBean;
import it.uniroma2.progettoispw.controller.controller.applicativi.TerapiaController;
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
        private TerapiaGiornalieraBean terapiaGiornalieraBean;
        private TerapiaController terapiaController;

        public ShowTerapiaState(AuthenticationBean authenticationBean) {
            this(authenticationBean, LocalDate.now());
        }

        public ShowTerapiaState(AuthenticationBean authenticationBean, LocalDate currentDate) {
            this.authenticationBean = authenticationBean;
            this.currentDate = currentDate;
            this.terapiaController = new TerapiaController();
            this.terapiaGiornalieraBean = terapiaController.getTerapiaGiornaliera(authenticationBean.getCodice(), currentDate);
            this.initialMessage = terapiaGiornalieraBean.toString() + "scegli cosa fare\n" +
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
                    this.terapiaGiornalieraBean = terapiaController.getTerapiaGiornaliera(authenticationBean.getCodice(), currentDate);
                    return stateMachine.goNext(new ShowTerapiaState(authenticationBean, currentDate));
                case "indietro": this.currentDate = currentDate.minusDays(1);
                    this.terapiaGiornalieraBean = terapiaController.getTerapiaGiornaliera(authenticationBean.getCodice(), currentDate);
                    return stateMachine.goNext(new ShowTerapiaState(authenticationBean, currentDate));
                case "aggiungi": DoseCostructor doseCostructor = new DoseCostructor();
                    return stateMachine.goNext(new AggiungiStateFase1(authenticationBean, terapiaController, doseCostructor)) +
                            stateMachine.getPromptController().setReceiver(new SelezionaMedicinale(doseCostructor.getDose(), stateMachine));
                case "menu": return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
                default: return "selezione invalida";
            }
        }

    }

    private class AggiungiStateFase1 extends AbstractState{
        private AuthenticationBean authenticationBean;
        private DoseCostructor doseCostructor;
        private TerapiaController terapiaController;

        public AggiungiStateFase1(AuthenticationBean authenticationBean, TerapiaController terapiaController, DoseCostructor doseCostructor) {
            this.doseCostructor = doseCostructor;
            this.authenticationBean = authenticationBean;
            this.terapiaController = terapiaController;
            this.initialMessage = "";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            return "";
        }

        @Override
        public String comeBackAction(Receiver stateMachine){
            DoseBean dosebean = doseCostructor.getDose();
            if (dosebean.getNome() != null && dosebean.getCodice() != null) {
                return stateMachine.goNext(new AggiungiStateFase2(authenticationBean, terapiaController, doseCostructor)) + stateMachine.getPromptController().setReceiver(new InformazioniFinali(doseCostructor, stateMachine)) ;
            } else {
                return stateMachine.goNext(new ShowTerapiaState(authenticationBean));
            }
        }
    }

    private class AggiungiStateFase2 extends AbstractState{
        private AuthenticationBean authenticationBean;
        private DoseCostructor doseCostructor;
        private TerapiaController terapiaController;

        public AggiungiStateFase2(AuthenticationBean authenticationBean, TerapiaController terapiaController, DoseCostructor doseCostructor) {
            this.doseCostructor = doseCostructor;
            this.authenticationBean = authenticationBean;
            this.terapiaController = terapiaController;
            this.initialMessage = "ora immetti le informazioni finali\n ";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            return "";
        }

        @Override
        public String comeBackAction(Receiver stateMachine){
            DoseBean dosebean = doseCostructor.getDose();
            if (dosebean.isCompleate()) {
                terapiaController.addDose(authenticationBean.getCodice(), doseCostructor);
                return "dose aggiunta con successo\n" + stateMachine.goNext(new ShowTerapiaState(authenticationBean));
            } else {
                return "aggiunta della dose fallita\n" + stateMachine.goNext(new ShowTerapiaState(authenticationBean));
            }
        }
    }
}
