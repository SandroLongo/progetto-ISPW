package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Ruolo;


public class DottoreRegistrationData extends UtenteRegistrationData{


    public DottoreRegistrationData() {
        super();
    }

    @Override
    public Ruolo isType() {
        return Ruolo.DOTTORE;
    }

}
