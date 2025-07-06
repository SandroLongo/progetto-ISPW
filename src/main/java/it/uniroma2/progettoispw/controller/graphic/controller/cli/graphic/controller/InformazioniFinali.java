package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.PrescriptionBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InformazioniFinali extends Receiver {
    private static final String ERRORENUMERO = "devi inserire un numero\n";

     public InformazioniFinali(PrescriptionBean prescriptionBean, Receiver receiver) {
         this.promptController = receiver.getPromptController();
         this.previousReceiver = receiver;
         this.currentState = new InserisciQuantita(prescriptionBean);
     }

     private class InserisciQuantita extends AbstractState{
         private PrescriptionBean prescriptionBean;

         public InserisciQuantita(PrescriptionBean prescriptionBean) {
             this.prescriptionBean = prescriptionBean;
             this.initialMessage = "inserisci la quantita:";
         }


         @Override
         public String goNext(Receiver stateMachine, String command) {
             try {
                 int numero = Integer.parseInt(command);
                 prescriptionBean.getDose().setQuantita(numero);
                 return stateMachine.goNext(new InserisciUnitaMisura(prescriptionBean));
             } catch (NumberFormatException e) {
                 return ERRORENUMERO + initialMessage;
             }
         }

     }

    private class InserisciUnitaMisura extends AbstractState{
        private PrescriptionBean prescriptionBean;

        public InserisciUnitaMisura(PrescriptionBean prescriptionBean) {
            this.prescriptionBean = prescriptionBean;
            this.initialMessage = "inserisci l'unita di misura:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                prescriptionBean.getDose().setUnitaMisura(command);
                return stateMachine.goNext(new InserisciOrario(prescriptionBean));
            } catch (NumberFormatException e) {
                return ERRORENUMERO + initialMessage;
            }
        }

    }

    private class InserisciOrario extends AbstractState{
        private PrescriptionBean prescriptionBean;

        public InserisciOrario(PrescriptionBean prescriptionBean) {
            this.prescriptionBean = prescriptionBean;
            this.initialMessage = "inserisci l'orario(i.e. 15:10):";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                LocalTime orario = LocalTime.parse(command);
                prescriptionBean.getDose().setOrario(orario);
                return stateMachine.goNext(new InserisciDataIniziale(prescriptionBean));
            } catch (DateTimeParseException e) {
                return "devi inserire qualcosa come 15:10, 8:50 ecc..\n" + initialMessage;
            }
        }

    }

    private class InserisciDataIniziale extends AbstractState{
        private PrescriptionBean prescriptionBean;

        public InserisciDataIniziale(PrescriptionBean prescriptionBean) {
            this.prescriptionBean = prescriptionBean;
            this.initialMessage = "inserisci la dataIniziale(i.e. 31-1-2026):";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(command, formatter);
                prescriptionBean.setInizio(date);
                return stateMachine.goNext(new InserisciNumeroGiorni(prescriptionBean));
            } catch (DateTimeParseException e) {
                return "devi inserire una data come 31-1-2026(gg-mm-aaaa)\n" + initialMessage;
            }
        }

    }

    private class InserisciNumeroGiorni extends AbstractState{
        private PrescriptionBean prescriptionBean;

        public InserisciNumeroGiorni(PrescriptionBean prescriptionBean) {
            this.prescriptionBean = prescriptionBean;
            this.initialMessage = "inserisci il numero di volte:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                int numero = Integer.parseInt(command);
                prescriptionBean.setNumRipetizioni(numero);
                return stateMachine.goNext(new InserisciRate(prescriptionBean));
            } catch (NumberFormatException e) {
                return ERRORENUMERO + initialMessage;
            }
        }

    }

    private class InserisciRate extends AbstractState{
        private PrescriptionBean prescriptionBean;

        public InserisciRate(PrescriptionBean prescriptionBean) {
            this.prescriptionBean = prescriptionBean;
            this.initialMessage = "inserisci ogni quanti giorni:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                int numero = Integer.parseInt(command);
                prescriptionBean.setRateGiorni(numero);
                return stateMachine.goNext(new InserisciDescrizione(prescriptionBean));
            } catch (NumberFormatException e) {
                return ERRORENUMERO + initialMessage;
            }
        }

    }

    private class InserisciDescrizione extends AbstractState{
        private PrescriptionBean prescriptionBean;

        public InserisciDescrizione(PrescriptionBean prescriptionBean) {
            this.prescriptionBean = prescriptionBean;
            this.initialMessage = "inserisci una descrizione:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                prescriptionBean.getDose().setDescrizione(command);
                return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
            } catch (NumberFormatException e) {
                return ERRORENUMERO + initialMessage;
            }
        }

    }

}
