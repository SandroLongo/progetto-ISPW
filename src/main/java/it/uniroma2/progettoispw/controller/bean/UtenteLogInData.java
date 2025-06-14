package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Ruolo;

public class UtenteLogInData {
    private String codiceFiscale;
    private String password;
    private Ruolo ruolo;
    private Integer codiceDottore;

    public UtenteLogInData(String codiceFiscale, String password, Ruolo ruolo, String codiceDottore) {
        this.codiceFiscale = codiceFiscale;
        this.password = password;
        this.ruolo = ruolo;
        try {
            this.codiceDottore = Integer.parseInt(codiceDottore);
        } catch (NumberFormatException e) {
            this.codiceDottore = null;
        }
    }

    public UtenteLogInData(String codice, String password, Ruolo ruolo) {
        this.codiceFiscale = codice;
        this.password = password;
        this.ruolo = ruolo;
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

    public Ruolo getRuolo() {
        return ruolo;
    }
    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }
    public int getCodiceDottore() {
        return codiceDottore;
    }
    public void setCodiceDottore(String codiceDottore) {
        try {
            this.codiceDottore = Integer.parseInt(codiceDottore);
        } catch (NumberFormatException e) {
            this.codiceDottore = null;
        }
    }

}
