package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.FinalStepBean;
import it.uniroma2.progettoispw.controller.bean.PrescriptionBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InformazioniFinali extends Receiver {
    private static final String ERRORENUMERO = "devi inserire un numero\n";

     public InformazioniFinali(FinalStepBean finalStepBean, Receiver receiver) {
         this.promptController = receiver.getPromptController();
         this.previousReceiver = receiver;
         this.currentState = new InserisciQuantita(finalStepBean);
     }

     private class InserisciQuantita extends AbstractState{
         private FinalStepBean finalStepBean;

         public InserisciQuantita(FinalStepBean finalStepBean) {
             this.finalStepBean = finalStepBean;
             this.initialMessage = "inserisci la quantita:";
         }


         @Override
         public String goNext(Receiver stateMachine, String command) {
             try {
                 int numero = Integer.parseInt(command);
                 finalStepBean.setQuantity(numero);
                 return stateMachine.goNext(new InserisciUnitaMisura(finalStepBean));
             } catch (NumberFormatException e) {
                 return ERRORENUMERO + initialMessage;
             }
         }

     }

    private class InserisciUnitaMisura extends AbstractState{
        private FinalStepBean finalStepBean;

        public InserisciUnitaMisura(FinalStepBean finalStepBean) {
            this.finalStepBean = finalStepBean;
            this.initialMessage = "inserisci l'unita di misura:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                finalStepBean.setMeasurementUnit(command);
                return stateMachine.goNext(new InserisciOrario(finalStepBean));
            } catch (NumberFormatException e) {
                return ERRORENUMERO + initialMessage;
            }
        }

    }

    private class InserisciOrario extends AbstractState{
        private FinalStepBean finalStepBean;

        public InserisciOrario(FinalStepBean finalStepBean) {
            this.finalStepBean = finalStepBean;
            this.initialMessage = "inserisci l'orario(i.e. 15:10):";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                LocalTime orario = LocalTime.parse(command);
                finalStepBean.setScheduledTime(orario);
                return stateMachine.goNext(new InserisciDataIniziale(finalStepBean));
            } catch (DateTimeParseException e) {
                return "devi inserire qualcosa come 15:10, 8:50 ecc..\n" + initialMessage;
            }
        }

    }

    private class InserisciDataIniziale extends AbstractState{
        private FinalStepBean finalStepBean;

        public InserisciDataIniziale(FinalStepBean finalStepBean) {
            this.finalStepBean = finalStepBean;
            this.initialMessage = "inserisci la dataIniziale(i.e. 31-1-2026):";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(command, formatter);
                finalStepBean.setStartDate(date);
                return stateMachine.goNext(new InserisciNumeroGiorni(finalStepBean));
            } catch (DateTimeParseException e) {
                return "devi inserire una data come 31-1-2026(gg-mm-aaaa)\n" + initialMessage;
            }
        }

    }

    private class InserisciNumeroGiorni extends AbstractState{
        private FinalStepBean finalStepBean;

        public InserisciNumeroGiorni(FinalStepBean finalStepBean) {
            this.finalStepBean = finalStepBean;
            this.initialMessage = "inserisci il numero di volte:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                int numero = Integer.parseInt(command);
                finalStepBean.setRepetitionNumber(numero);
                return stateMachine.goNext(new InserisciRate(finalStepBean));
            } catch (NumberFormatException e) {
                return ERRORENUMERO + initialMessage;
            }
        }

    }

    private class InserisciRate extends AbstractState{
        private FinalStepBean finalStepBean;

        public InserisciRate(FinalStepBean finalStepBean) {
            this.finalStepBean = finalStepBean;
            this.initialMessage = "inserisci ogni quanti giorni:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                int numero = Integer.parseInt(command);
                finalStepBean.setDayRate(numero);
                return stateMachine.goNext(new InserisciDescrizione(finalStepBean));
            } catch (NumberFormatException e) {
                return ERRORENUMERO + initialMessage;
            }
        }

    }

    private class InserisciDescrizione extends AbstractState{
        private FinalStepBean finalStepBean;

        public InserisciDescrizione(FinalStepBean finalStepBean) {
            this.finalStepBean = finalStepBean;
            this.initialMessage = "inserisci una descrizione:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                finalStepBean.setDescription(command);
                return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
            } catch (NumberFormatException e) {
                return ERRORENUMERO + initialMessage;
            }
        }

    }

}
