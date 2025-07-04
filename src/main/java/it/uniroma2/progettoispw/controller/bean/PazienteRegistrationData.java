package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Ruolo;


public class PazienteRegistrationData extends UtenteRegistrationData{

    public PazienteRegistrationData() {
        super();
    }

    @Override
    public Ruolo isType() {
        return Ruolo.PAZIENTE;
    }
}
