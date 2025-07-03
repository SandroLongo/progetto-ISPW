package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;

public class Dottore extends Utente{

    public Dottore(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono) {
        super(codiceFiscale, nome, cognome, nascita, email, telefono);
    }

    public Dottore(String codiceFiscale){
        super(codiceFiscale);
    }

    @Override
    public Ruolo isType() {
        return Ruolo.DOTTORE;
    }
}
