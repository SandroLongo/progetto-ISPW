package it.uniroma2.progettoispw.controller.graphicController.cliGraphicController;

import it.uniroma2.progettoispw.controller.bean.DoseBean;
import it.uniroma2.progettoispw.controller.controllerApplicativi.InformazioniMedicinaleController;
import it.uniroma2.progettoispw.model.domain.Confezione;
import it.uniroma2.progettoispw.model.domain.PrincipioAttivo;
import it.uniroma2.progettoispw.model.domain.TipoDose;

import java.util.List;

public class SelezionaMedicinale extends Receiver{

    public SelezionaMedicinale(DoseBean doseBean, Receiver prevoisReceiver) {
            this.previousReceiver = prevoisReceiver;
            this.promptController = prevoisReceiver.getPromptController();
            this.currentState = new InitialState(doseBean);
    }

    private class InitialState extends AbstractState {
        private DoseBean doseBean;

        public InitialState(DoseBean doseBean) {
            this.doseBean = doseBean;
            this.initialMessage = "cosa vuoi cercare: confezione, principioAttivo\n";
        }
        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case "confezione": return stateMachine.goNext(new CercaConfezione(doseBean));
                case "principioattivo": return stateMachine.goNext(new CercaPrincipio(doseBean));
                default: return "opzione non valida";
            }
        }
    }

    private class CercaConfezione extends AbstractState {
        private DoseBean doseBean;
        public CercaConfezione(DoseBean doseBean) {
            this.doseBean = doseBean;
            this.initialMessage = "confezione";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            InformazioniMedicinaleController informazioniMedicinaleController = new InformazioniMedicinaleController();
            List<Confezione> lista = informazioniMedicinaleController.getConfezioniByNomeParziale(command);
            if (lista.isEmpty()) {
                return "non ci sono risultati per " + command;
            } else {
                return "risultati per " + command + stateMachine.goNext(new VisualizzaConfezioni(doseBean, lista));
            }
        }
    }

    private class VisualizzaConfezioni extends AbstractState {
        private DoseBean doseBean;
        private List<Confezione> listaConfezioni;
        public VisualizzaConfezioni(DoseBean doseBean, List<Confezione> listaConfezioni) {
            this.doseBean = doseBean;
            this.listaConfezioni = listaConfezioni;
            this.initialMessage = listaToString(listaConfezioni) + "scrivi il numero della confezione scelta\n";
        }

        private String listaToString(List<Confezione> listaConfezioni) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (Confezione confezione : listaConfezioni) {
                sb.append(i + " - " + confezione.toString() + "\n");
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
                    if (0 < numero && numero < listaConfezioni.size()){
                        Confezione confezione = listaConfezioni.get(numero);
                        this.doseBean.setNome(confezione.getDenominazione());
                        this.doseBean.setTipo(TipoDose.Confezione);
                        this.doseBean.setCodice(String.valueOf(confezione.getCodiceAic()));
                        return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
                    } else {
                        return "numero non valido";
                    }
                case 0:
                    switch (option) {
                        case "principio" : return stateMachine.goNext(new CercaPrincipio(doseBean));
                        //case "indietro": return;
                        case "confezione": return stateMachine.goNext(new CercaConfezione(doseBean));
                        default: return "opzione non valida";
                    }
                default:
                    return "opzione non valida";
            }
        }

    }

    private class CercaPrincipio extends AbstractState {
        private DoseBean doseBean;
        public CercaPrincipio(DoseBean doseBean) {
            this.doseBean = doseBean;
            this.initialMessage = "inserisci il nome del principio";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            InformazioniMedicinaleController informazioniMedicinaleController = new InformazioniMedicinaleController();
            List<String> risultati = informazioniMedicinaleController.getNomiPrincipioAttivoByNomeParziale(command);
            if (risultati.isEmpty()) {
                return "non ci sono risultati per " + command;
            } else {
                return stateMachine.goNext(new VisualizzaPrincipio(doseBean, risultati));
            }
        }
    }

    private class VisualizzaPrincipio extends AbstractState {
        private DoseBean doseBean;
        private List<String> risultatiPrincipio;
        public VisualizzaPrincipio(DoseBean doseBean, List<String> risultatiPrincipio) {
            this.doseBean = doseBean;
            this.risultatiPrincipio = risultatiPrincipio;
            this.initialMessage = listaToString(risultatiPrincipio) + "selezione il numero del principio";
        }

        private String listaToString(List<String> listaPrincipi) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (String principio : listaPrincipi) {
                sb.append(i + " - " + principio + "\n");
                i++;
            }
            return sb.toString();
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            int numero;
            int tipo;
            InformazioniMedicinaleController informazioniMedicinaleController = new InformazioniMedicinaleController();
            try {
                numero = Integer.parseInt(option);
                tipo = 1;
            } catch (NumberFormatException e) {
                numero = 0;
                tipo = 0;
            }
            String[] options = command.split(" ");
            switch (tipo) {
                case 1:
                    if (0 < numero && numero < risultatiPrincipio.size()){
                        PrincipioAttivo selezione = informazioniMedicinaleController.getPrincipioAttvoByNome(risultatiPrincipio.get(numero));
                        this.doseBean.setNome(selezione.getNome());
                        this.doseBean.setTipo(TipoDose.PrincipioAttivo);
                        this.doseBean.setCodice(String.valueOf(selezione.getCodiceAtc()));
                        return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
                    } else {
                        return "numero non valido";
                    }
                case 0:
                    switch (options[0]) {
                        case "principio" :
                            return stateMachine.goNext(new CercaPrincipio(doseBean));
                        //case "indietro": return;
                        case "confezione":
                            return stateMachine.goNext(new CercaConfezione(doseBean));
                        case "cerca":
                            try {
                                numero = Integer.parseInt(option);
                                tipo = 1;
                            } catch (NumberFormatException e) {
                                numero = 0;
                                tipo = 0;
                            }
                            if (tipo == 1 && 0 < numero && numero < risultatiPrincipio.size()){
                                PrincipioAttivo selezione = informazioniMedicinaleController.getPrincipioAttvoByNome(risultatiPrincipio.get(numero));
                                List<Confezione> listaConfezioni = informazioniMedicinaleController.getConfezioniByCodiceAtc(selezione.getCodiceAtc());
                                return stateMachine.goNext(new VisualizzaConfezioni(doseBean, listaConfezioni));
                            } else {
                                return "numero non valido nel comando cerca";
                            }
                        default:
                            return "opzione non valida";
                    }
                default:
                    return "opzione non valida";
            }
        }
    }
}
