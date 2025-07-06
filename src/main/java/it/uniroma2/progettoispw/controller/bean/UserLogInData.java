package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.controller.controller.applicativi.Config;
import it.uniroma2.progettoispw.model.domain.Role;

public class UserLogInData {
    private String codiceFiscale;
    private String password;
    private Role role;
    private Integer codiceDottore;

    public UserLogInData(String codiceFiscale, String password, Role role, String codiceDottore) {
        this.codiceFiscale = codiceFiscale;
        this.password = password;
        this.role = role;
        try {
            this.codiceDottore = Integer.parseInt(codiceDottore);
        } catch (NumberFormatException e) {
            this.codiceDottore = null;
        }
    }

    public UserLogInData(String codice, String password, Role role) {
        this.codiceFiscale = codice;
        this.password = password;
        this.role = role;
    }

    public UserLogInData() {

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

    public Role getRuolo() {
        return role;
    }
    public void setRuolo(Role role) {
        this.role = role;
    }
    public int getCodiceDottore() {
        if (role != Role.DOTTORE){
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
        if (role != null && role == Role.DOTTORE && codiceDottore == null) {
            return false;

        }
        return codiceFiscale!=null && password!=null && role != null;
    }


}
