package it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.paziente;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.bean.ListaRichiesteBean;
import it.uniroma2.progettoispw.controller.bean.RichiestaMandata;
import it.uniroma2.progettoispw.controller.controllerApplicativi.ManageRequestController;
import it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.AbstractState;
import it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.Receiver;

import java.util.List;

public class Richieste extends Receiver {
    private AuthenticationBean authenticationBean;
    public Richieste(AuthenticationBean authenticationBean, Receiver receiver) {
        this.promptController = receiver.getPromptController();
        this.authenticationBean = authenticationBean;
        this.previousReceiver = receiver;
        this.currentState = new ShowRichieste(authenticationBean);
    }

    private class ShowRichieste extends AbstractState{
        private AuthenticationBean authenticationBean;
        private ManageRequestController manageRequestController;
        private ListaRichiesteBean richiestePendenti;

        public ShowRichieste(AuthenticationBean authenticationBean) {
            this.authenticationBean = authenticationBean;
            this.manageRequestController = new ManageRequestController();
            richiestePendenti = manageRequestController.getRichieste(authenticationBean.getCodice());
            this.initialMessage = listaRichiesteToString(richiestePendenti.getLista());
        }

        private String listaRichiesteToString(List<RichiestaMandata> lista){
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (RichiestaMandata mandata : lista){
                sb.append(i + " - " + mandata.toStringBase() + "\n");
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
                    if (0 < numero && numero < richiestePendenti.getLista().size()){
                        RichiestaMandata richiestaMandata = richiestePendenti.getLista().get(numero);
                        return stateMachine.goNext(new ShowDettagliRichiesta(authenticationBean, manageRequestController, richiestaMandata));
                    } else {
                        return "numero non valido";
                    }
                case 0:
                    switch (option) {
                        case "menu" : return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
                        default: return "opzione non valida";
                    }
                default:
                    return "opzione non valida";
            }
        }
    }

    private class ShowDettagliRichiesta extends AbstractState{
        private AuthenticationBean authenticationBean;
        private ManageRequestController manageRequestController;
        private RichiestaMandata mandata;

        public ShowDettagliRichiesta(AuthenticationBean authenticationBean, ManageRequestController manageRequestController, RichiestaMandata mandata) {
            this.authenticationBean = authenticationBean;
            this.manageRequestController = manageRequestController;
            this.mandata = mandata;
            this.initialMessage = mandata.toString();
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "accetta":
                    manageRequestController.accettaRichiesta(authenticationBean.getCodice(), mandata.getIdRichiesta());
                case "rifiuta": return "";
                case "indietro": return stateMachine.goNext(new ShowRichieste(authenticationBean));
                default:
                    return "opzione non valida";
            }
        }
    }
}
