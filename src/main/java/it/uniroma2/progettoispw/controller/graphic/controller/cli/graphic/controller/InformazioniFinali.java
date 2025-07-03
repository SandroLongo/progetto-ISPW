package it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller;

import it.uniroma2.progettoispw.controller.bean.DoseCostructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InformazioniFinali extends Receiver {

     public InformazioniFinali(DoseCostructor doseCostructor, Receiver receiver) {
         this.promptController = receiver.getPromptController();
         this.previousReceiver = receiver;
         this.currentState = new InserisciQuantita(doseCostructor);
     }

     private class InserisciQuantita extends AbstractState{
         private DoseCostructor doseCostructor;

         public InserisciQuantita(DoseCostructor doseCostructor) {
             this.doseCostructor = doseCostructor;
             this.initialMessage = "inserisci la quantita:";
         }


         @Override
         public String goNext(Receiver stateMachine, String command) {
             try {
                 int numero = Integer.parseInt(command);
                 doseCostructor.getDose().setQuantita(numero);
                 return stateMachine.goNext(new InserisciUnitaMisura(doseCostructor));
             } catch (NumberFormatException e) {
                 return "devi inserire un numero\n" + initialMessage;
             }
         }

     }

    private class InserisciUnitaMisura extends AbstractState{
        private DoseCostructor doseCostructor;

        public InserisciUnitaMisura(DoseCostructor doseCostructor) {
            this.doseCostructor = doseCostructor;
            this.initialMessage = "inserisci l'unita di misura:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                doseCostructor.getDose().setUnitaMisura(command);
                return stateMachine.goNext(new InserisciOrario(doseCostructor));
            } catch (NumberFormatException e) {
                return "devi inserire un numero\n" + initialMessage;
            }
        }

    }

    private class InserisciOrario extends AbstractState{
        private DoseCostructor doseCostructor;

        public InserisciOrario(DoseCostructor doseCostructor) {
            this.doseCostructor = doseCostructor;
            this.initialMessage = "inserisci l'orario(i.e. 15:10):";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                LocalTime orario = LocalTime.parse(command);
                doseCostructor.getDose().setOrario(orario);
                return stateMachine.goNext(new InserisciDataIniziale(doseCostructor));
            } catch (DateTimeParseException e) {
                return "devi inserire qualcosa come 15:10, 8:50 ecc..\n" + initialMessage;
            }
        }

    }

    private class InserisciDataIniziale extends AbstractState{
        private DoseCostructor doseCostructor;

        public InserisciDataIniziale(DoseCostructor doseCostructor) {
            this.doseCostructor = doseCostructor;
            this.initialMessage = "inserisci la dataIniziale(i.e. 31-1-2026):";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(command, formatter);
                doseCostructor.setInizio(date);
                return stateMachine.goNext(new InserisciNumeroGiorni(doseCostructor));
            } catch (DateTimeParseException e) {
                return "devi inserire una data come 31-1-2026(gg-mm-aaaa)\n" + initialMessage;
            }
        }

    }

    private class InserisciNumeroGiorni extends AbstractState{
        private DoseCostructor doseCostructor;

        public InserisciNumeroGiorni(DoseCostructor doseCostructor) {
            this.doseCostructor = doseCostructor;
            this.initialMessage = "inserisci il numero di volte:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                int numero = Integer.parseInt(command);
                doseCostructor.setNumRipetizioni(numero);
                return stateMachine.goNext(new InserisciRate(doseCostructor));
            } catch (NumberFormatException e) {
                return "devi inserire un numero\n" + initialMessage;
            }
        }

    }

    private class InserisciRate extends AbstractState{
        private DoseCostructor doseCostructor;

        public InserisciRate(DoseCostructor doseCostructor) {
            this.doseCostructor = doseCostructor;
            this.initialMessage = "inserisci ogni quanti giorni:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                int numero = Integer.parseInt(command);
                doseCostructor.setRateGiorni(numero);
                return stateMachine.goNext(new InserisciDescrizione(doseCostructor));
            } catch (NumberFormatException e) {
                return "devi inserire un numero\n" + initialMessage;
            }
        }

    }

    private class InserisciDescrizione extends AbstractState{
        private DoseCostructor doseCostructor;

        public InserisciDescrizione(DoseCostructor doseCostructor) {
            this.doseCostructor = doseCostructor;
            this.initialMessage = "inserisci una descrizione:";
        }


        @Override
        public String goNext(Receiver stateMachine, String command) {
            try {
                doseCostructor.getDose().setDescrizione(command);
                return stateMachine.getPromptController().setReceiver(stateMachine.getPreviousReceiver());
            } catch (NumberFormatException e) {
                return "devi inserire un numero\n" + initialMessage;
            }
        }

    }

}
