package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.ActiveIngredientBean;
import it.uniroma2.progettoispw.controller.bean.DoseBean;
import it.uniroma2.progettoispw.controller.bean.MedicinalProductBean;
import it.uniroma2.progettoispw.controller.controller.applicativi.MedicationInformationController;
import it.uniroma2.progettoispw.model.domain.MedicinalProduct;
import it.uniroma2.progettoispw.model.domain.ActiveIngredient;
import it.uniroma2.progettoispw.model.domain.MedicationType;

import java.util.List;

public class SelectMedication extends Receiver{
    private static final String COMANDO_CONFEZIONE = "medicinalProduct";
    private static final String OPZIONE_NON_VALIDA_ERROR = "opzione non valida\n";

    public SelectMedication(DoseBean doseBean, Receiver prevoisReceiver) {
            this.previousReceiver = prevoisReceiver;
            this.promptController = prevoisReceiver.getPromptController();
            this.currentState = new InitialState(doseBean);
    }

    private class InitialState extends AbstractState {
        private DoseBean doseBean;

        public InitialState(DoseBean doseBean) {
            this.doseBean = doseBean;
            this.initialMessage = "cosa vuoi cercare: medicinalProduct, principioAttivo\n";
        }
        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            switch (option) {
                case COMANDO_CONFEZIONE: return stateMachine.goNext(new CercaConfezione(doseBean));
                case "principioattivo": return stateMachine.goNext(new CercaPrincipio(doseBean));
                default: return OPZIONE_NON_VALIDA_ERROR;
            }
        }
    }

    private class CercaConfezione extends AbstractState {
        private DoseBean doseBean;
        public CercaConfezione(DoseBean doseBean) {
            this.doseBean = doseBean;
            this.initialMessage = """
                    inserisci il name della medicinalProduct(anche incompleto):
                    """;
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            MedicationInformationController medicationInformationController = new MedicationInformationController();
            List<MedicinalProductBean> lista = medicationInformationController.getMedicinalProductsByPartialName(command);
            if (lista.isEmpty()) {
                return "non ci sono risultati per " + command;
            } else {
                return "risultati per " + command + stateMachine.goNext(new VisualizzaConfezioni(doseBean, lista));
            }
        }
    }

    private class VisualizzaConfezioni extends AbstractState {
        private DoseBean doseBean;
        private List<MedicinalProductBean> listaConfezioni;
        public VisualizzaConfezioni(DoseBean doseBean, List<MedicinalProductBean> listaConfezioni) {
            this.doseBean = doseBean;
            this.listaConfezioni = listaConfezioni;
            this.initialMessage = listaToString(listaConfezioni) + "scrivi il numero della medicinalProduct scelta\n";
        }

        private String listaToString(List<MedicinalProductBean> listaConfezioni) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (MedicinalProductBean medicinalProduct : listaConfezioni) {
                sb.append(i).append(" - ").append(medicinalProduct.toString()).append("\n");
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
                        MedicinalProductBean medicinalProduct = listaConfezioni.get(numero);
                        this.doseBean.setName(medicinalProduct.getName());
                        this.doseBean.setType(MedicationType.MEDICINALPRODUCT);
                        this.doseBean.setId(String.valueOf(medicinalProduct.getId()));
                        return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
                    } else {
                        return "numero non valido";
                    }
                case 0:
                    switch (option) {
                        case "principio" : return stateMachine.goNext(new CercaPrincipio(doseBean));
                        case COMANDO_CONFEZIONE: return stateMachine.goNext(new CercaConfezione(doseBean));
                        default: return "opzione non valida";
                    }
                default:
                    return OPZIONE_NON_VALIDA_ERROR;
            }
        }

    }

    private class CercaPrincipio extends AbstractState {
        private DoseBean doseBean;
        public CercaPrincipio(DoseBean doseBean) {
            this.doseBean = doseBean;
            this.initialMessage = "inserisci il name del principio";
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            MedicationInformationController medicationInformationController = new MedicationInformationController();
            List<String> risultati = medicationInformationController.getActiveIngridientsNameByPartialName(command);
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
                sb.append(i).append(" - ").append(principio).append("\n");
                i++;
            }
            return sb.toString();
        }

        @Override
        public String goNext(Receiver stateMachine, String command) {
            String option = command.toLowerCase();
            MedicationInformationController medicationInformationController = new MedicationInformationController();
            int tipo = isNumero(option) ? 1 : 0;
            switch (tipo) {
                case 1:
                    return processaNumero(option, medicationInformationController, stateMachine);
                case 0:
                    return processaComando(option, medicationInformationController, stateMachine);
                default:
                    return OPZIONE_NON_VALIDA_ERROR;
            }
        }

        private String processaComando(String option, MedicationInformationController medicationInformationController, Receiver stateMachine) {
            String[] options = option.split(" ");
            int tipo;
            int numero;
            switch (options[0]) {
                case "principio" :
                    return stateMachine.goNext(new CercaPrincipio(doseBean));
                case COMANDO_CONFEZIONE:
                    return stateMachine.goNext(new CercaConfezione(doseBean));
                case "cerca":
                    tipo = isNumero(options[1]) ? 1 : 0;
                    if (tipo == 1){
                        numero = Integer.parseInt(options[1]);
                        if (0 < numero && numero < risultatiPrincipio.size()){
                            ActiveIngredientBean selezione = medicationInformationController.getActiveIngridientByName(risultatiPrincipio.get(numero));
                            List<MedicinalProductBean> listaConfezioni = medicationInformationController.getMedicinalProductByActiveIngridient(selezione.getId());
                            return stateMachine.goNext(new VisualizzaConfezioni(doseBean, listaConfezioni));
                        } else {
                            return "il numero è troppo grande o troppo piccolo";
                        }
                    } else {
                        return "numero non valido nel comando cerca";
                    }
                default:
                    return OPZIONE_NON_VALIDA_ERROR;
            }
        }

        private boolean isNumero(String numero) {
            try {
                Integer.parseInt(numero);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        private String processaNumero(String option, MedicationInformationController medicationInformationController, Receiver stateMachine) {
            int numero = Integer.parseInt(option);
            if (0 < numero && numero < risultatiPrincipio.size()){
                ActiveIngredientBean selezione = medicationInformationController.getActiveIngridientByName(risultatiPrincipio.get(numero));
                this.doseBean.setName(selezione.getName());
                this.doseBean.setType(MedicationType.ACRIVEINGREDIENT);
                this.doseBean.setId(String.valueOf(selezione.getId()));
                return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
            } else {
                return "numero non valido";
            }
        }
    }

}
