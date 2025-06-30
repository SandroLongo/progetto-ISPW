package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Ruolo;

import java.time.LocalDate;

public class DottoreRegistrationData extends UtenteRegistrationData{

    public DottoreRegistrationData(String codice_fiscale, String nome, String cognome, String email, String telefono, LocalDate data_nascita, String password) {
        super(codice_fiscale, nome, cognome, email, telefono, data_nascita, password);
    }

    @Override
    public Ruolo isType() {
        return Ruolo.Dottore;
    }

}
