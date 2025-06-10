package it.uniroma2.progettoispw.model.bean;

import it.uniroma2.progettoispw.model.domain.Ruolo;

import java.time.LocalDate;

public class PazienteRegistrationData extends UtenteRegistrationData{
    public PazienteRegistrationData(String codice_fiscale, String nome, String cognome, String email, String telefono, LocalDate data_nascita, String password) {
        super(codice_fiscale, nome, cognome, email, telefono, data_nascita, password);
    }

    @Override
    public Ruolo isType() {
        return Ruolo.Paziente;
    }
}
