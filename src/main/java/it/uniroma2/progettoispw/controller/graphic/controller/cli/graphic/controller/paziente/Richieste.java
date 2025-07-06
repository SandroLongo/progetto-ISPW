package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.paziente;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.bean.ListPrescriptionBundleBean;
import it.uniroma2.progettoispw.controller.bean.SentPrescriptionBundleBean;
import it.uniroma2.progettoispw.controller.controller.applicativi.ManageSentPrescriptionBundleController;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.AbstractState;
import it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller.Receiver;

import java.util.List;

public class Richieste extends Receiver {
    private static final String OPZIONE_NON_VALIDA_ERROR = "opzione non valida\n";

    public Richieste(AuthenticationBean authenticationBean, Receiver receiver) {
        this.promptController = receiver.getPromptController();
        this.previousReceiver = receiver;
        this.currentState = new ShowRichieste(authenticationBean);
    }

    private class ShowRichieste extends AbstractState{
        private AuthenticationBean authenticationBean;
        private ManageSentPrescriptionBundleController manageSentPrescriptionBundleController;
        private ListPrescriptionBundleBean richiestePendenti;

        public ShowRichieste(AuthenticationBean authenticationBean) {
            this.authenticationBean = authenticationBean;
            this.manageSentPrescriptionBundleController = new ManageSentPrescriptionBundleController();
            richiestePendenti = manageSentPrescriptionBundleController.getPendingPrescriptionBundles(authenticationBean.getCodice());
            this.initialMessage = listaRichiesteToString(richiestePendenti.getList()) + """
                    scegli cosa fare:
                    scrivi un  numero(es 0) --> seleziona la sentPrescriptionBundle con quel numero
                    menu --> torna al menu
                    """;
        }

        private String listaRichiesteToString(List<SentPrescriptionBundleBean> lista){
            StringBuilder sb = new StringBuilder();
            int i = 0;
            if(lista.isEmpty()){
                return "non ci sono richieste al momento\n";
            }
            for (SentPrescriptionBundleBean mandata : lista){
                sb.append(i).append(" - ").append(mandata.toStringBase()).append("\n");
                i++;
            }
            return sb.toString();
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            int numero;
            int tipo;
            try {
                numero = Integer.parseInt(option);
                tipo = 1;
            } catch (NumberFormatException e) {
                numero = 0;
                tipo = 0;
            }
            switch (tipo) {
                case 1:
                    if (0 <= numero && numero < richiestePendenti.getList().size()){
                        SentPrescriptionBundleBean sentPrescriptionBundleBean = richiestePendenti.getList().get(numero);
                        return stateMachine.goNext(new ShowDettagliRichiesta(authenticationBean, manageSentPrescriptionBundleController, sentPrescriptionBundleBean));
                    } else {
                        return "numero non valido";
                    }
                case 0:
                    if (option.equals("menu")) {
                        return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
                    }
                    return OPZIONE_NON_VALIDA_ERROR;
                default:
                    return OPZIONE_NON_VALIDA_ERROR;
            }
        }
    }

    private class ShowDettagliRichiesta extends AbstractState{
        private AuthenticationBean authenticationBean;
        private ManageSentPrescriptionBundleController manageSentPrescriptionBundleController;
        private SentPrescriptionBundleBean mandata;

        public ShowDettagliRichiesta(AuthenticationBean authenticationBean, ManageSentPrescriptionBundleController manageSentPrescriptionBundleController, SentPrescriptionBundleBean mandata) {
            this.authenticationBean = authenticationBean;
            this.manageSentPrescriptionBundleController = manageSentPrescriptionBundleController;
            this.mandata = mandata;
            this.initialMessage = mandata.toString()  + "\n" + """
                    sceli cosa vuoi fare
                    accetta --> accetta la sentPrescriptionBundle
                    rifiuta --> rifiuta la sentPrescriptionBundle
                    indietro --> visualizza ancora tutte le richieste
                    """;
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "accetta":
                    manageSentPrescriptionBundleController.acceptPrescriptionBundle(authenticationBean.getCodice(), mandata.getId());
                    return stateMachine.goNext(new ShowRichieste(authenticationBean));
                case "rifiuta": return "";
                case "indietro": return stateMachine.goNext(new ShowRichieste(authenticationBean));
                default:
                    return OPZIONE_NON_VALIDA_ERROR;
            }
        }
    }
}
