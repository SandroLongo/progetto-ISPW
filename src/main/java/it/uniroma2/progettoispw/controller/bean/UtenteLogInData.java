package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.controller.controller.applicativi.Config;
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

    public UtenteLogInData() {

    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String cf) {
        if (cf == null || cf.length() < Config.MIN_CF_LENGTH || cf.length() > Config.MAX_CF_LENGTH) {
            throw new FomatoInvalidoException("Codice fiscale non valido");
        }
        this.codiceFiscale = cf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pwd) {
        if (pwd == null || pwd.length() > Config.MAX_PASSWORD_LENGTH || pwd.length() <= Config.MIN_PASSWORD_LENGTH) {
            throw  new FomatoInvalidoException("Password non valida");
        }
        this.password = pwd;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }
    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }
    public int getCodiceDottore() {
        if (ruolo != Ruolo.DOTTORE){
            throw new FomatoInvalidoException("il codice puo essere impostato solo per i dottori");
        }
        return codiceDottore;
    }
    public void setCodiceDottore(String codiceDottore) {
        try {
            this.codiceDottore = Integer.parseInt(codiceDottore);
        } catch (NumberFormatException e) {
            throw new FomatoInvalidoException("il codice deve essere un numero");
        }
    }

    public boolean isComplete(){
        if (ruolo != null && ruolo == Ruolo.DOTTORE && codiceDottore == null) {
            return false;

        }
        return codiceFiscale!=null && password!=null && ruolo != null;
    }


}
