package it.uniroma2.progettoispw.model.bean;

import it.uniroma2.progettoispw.model.domain.Ruolo;

public class PazienteLogInData extends UtenteLogInData{
    public PazienteLogInData(String codiceFiscale, String password) {
        super(codiceFiscale, password);
    }

    @Override
    public Ruolo isType() {
        return Ruolo.Paziente;
    }
}
