package it.uniroma2.progettoispw.model.bean;

import it.uniroma2.progettoispw.model.domain.Ruolo;

public abstract class UtenteLogInData {
    private String codiceFiscale;
    private String password;
    public UtenteLogInData(String codiceFiscale, String password) {
        this.codiceFiscale = codiceFiscale;
        this.password = password;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract Ruolo isType();
}
