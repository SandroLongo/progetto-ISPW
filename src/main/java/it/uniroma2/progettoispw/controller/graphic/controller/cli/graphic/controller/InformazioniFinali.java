package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.Prescription;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InformazioniFinali extends Receiver {
    private static final String ERRORENUMERO = "devi inserire un numero\n";

     public InformazioniFinali(Prescription prescription, Receiver receiver) {
         this.promptController = receiver.getPromptController();
         this.previousReceiver = receiver;
         this.currentState = new InserisciQuantita(prescription);
     }

     private class InserisciQuantita extends AbstractState{
         private Prescription prescription;

         public InserisciQuantita(Prescription prescription) {
             this.prescription = prescription;
             this.initialMessage = "inserisci la quantita:";
         }


         @Override
         public String goNext(Receiver stateMachine, String command) {
             try {
                 int numero = Integer.parseInt(command);
                 prescription.getDose().setQuantita(numero);
                 return stateMachine.goNext(new InserisciUnitaMisura(prescription));
             } catch (NumberFormatException e) {
                 return ERRORENUMERO + initialMessage;
             }
         }

     }

    private class InserisciUnitaMisura extends AbstractState{
        private Prescription prescription;

        public InserisciUnitaMisura(Prescription prescription) {
            this.prescription = prescription;
            this.initialMessage = "inserisci l'unita di misura:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                prescription.getDose().setUnitaMisura(command);
                return stateMachine.goNext(new InserisciOrario(prescription));
            } catch (NumberFormatException e) {
                return ERRORENUMERO + initialMessage;
            }
        }

    }

    private class InserisciOrario extends AbstractState{
        private Prescription prescription;

        public InserisciOrario(Prescription prescription) {
            this.prescription = prescription;
            this.initialMessage = "inserisci l'orario(i.e. 15:10):";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                LocalTime orario = LocalTime.parse(command);
                prescription.getDose().setOrario(orario);
                return stateMachine.goNext(new InserisciDataIniziale(prescription));
            } catch (DateTimeParseException e) {
                return "devi inserire qualcosa come 15:10, 8:50 ecc..\n" + initialMessage;
            }
        }

    }

    private class InserisciDataIniziale extends AbstractState{
        private Prescription prescription;

        public InserisciDataIniziale(Prescription prescription) {
            this.prescription = prescription;
            this.initialMessage = "inserisci la dataIniziale(i.e. 31-1-2026):";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(command, formatter);
                prescription.setInizio(date);
                return stateMachine.goNext(new InserisciNumeroGiorni(prescription));
            } catch (DateTimeParseException e) {
                return "devi inserire una data come 31-1-2026(gg-mm-aaaa)\n" + initialMessage;
            }
        }

    }

    private class InserisciNumeroGiorni extends AbstractState{
        private Prescription prescription;

        public InserisciNumeroGiorni(Prescription prescription) {
            this.prescription = prescription;
            this.initialMessage = "inserisci il numero di volte:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                int numero = Integer.parseInt(command);
                prescription.setNumRipetizioni(numero);
                return stateMachine.goNext(new InserisciRate(prescription));
            } catch (NumberFormatException e) {
                return ERRORENUMERO + initialMessage;
            }
        }

    }

    private class InserisciRate extends AbstractState{
        private Prescription prescription;

        public InserisciRate(Prescription prescription) {
            this.prescription = prescription;
            this.initialMessage = "inserisci ogni quanti giorni:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                int numero = Integer.parseInt(command);
                prescription.setRateGiorni(numero);
                return stateMachine.goNext(new InserisciDescrizione(prescription));
            } catch (NumberFormatException e) {
                return ERRORENUMERO + initialMessage;
            }
        }

    }

    private class InserisciDescrizione extends AbstractState{
        private Prescription prescription;

        public InserisciDescrizione(Prescription prescription) {
            this.prescription = prescription;
            this.initialMessage = "inserisci una descrizione:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                prescription.getDose().setDescrizione(command);
                return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
            } catch (NumberFormatException e) {
                return ERRORENUMERO + initialMessage;
            }
        }

    }

}
