package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;
import java.util.Date;

public class Dottore extends Utente{

    public Dottore(String codice_fiscale, String nome, String cognome, LocalDate nascita, String email, String telefono) {
        super(codice_fiscale, nome, cognome, nascita, email, telefono);
    }

    public Dottore(String codiceFiscale){
        super(codiceFiscale);
    }

    @Override
    public Ruolo isType() {
        return Ruolo.Dottore;
    }
}
