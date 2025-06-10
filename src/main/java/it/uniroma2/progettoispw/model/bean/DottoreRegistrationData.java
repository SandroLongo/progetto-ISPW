package it.uniroma2.progettoispw.model.bean;

import it.uniroma2.progettoispw.model.domain.Ruolo;

import java.time.LocalDate;

public class DottoreRegistrationData extends UtenteRegistrationData{
    private int codice;

    public DottoreRegistrationData(String codice_fiscale, String nome, String cognome, String email, String telefono, LocalDate data_nascita, int codice, String password) {
        super(codice_fiscale, nome, cognome, email, telefono, data_nascita, password);
        this.codice = codice;
    }

    @Override
    public Ruolo isType() {
        return Ruolo.Dottore;
    }

    public int getCodice() {
        return codice;
    }
}
