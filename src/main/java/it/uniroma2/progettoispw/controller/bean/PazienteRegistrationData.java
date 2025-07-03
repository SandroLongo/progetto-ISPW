package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Ruolo;

import java.time.LocalDate;

public class PazienteRegistrationData extends UtenteRegistrationData{
    public PazienteRegistrationData(String codiceFiscale, String nome, String cognome, String email, String telefono, LocalDate dataNascita, String password) {
        super(codiceFiscale, nome, cognome, email, telefono, dataNascita, password);
    }

    @Override
    public Ruolo isType() {
        return Ruolo.PAZIENTE;
    }
}
