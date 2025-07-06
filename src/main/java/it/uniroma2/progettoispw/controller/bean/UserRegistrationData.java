package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.controller.controller.applicativi.Config;
import it.uniroma2.progettoispw.model.domain.Role;

import java.time.LocalDate;
import java.time.Period;

public abstract class UserRegistrationData {
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private LocalDate dataNascita;
    private String password;

    protected UserRegistrationData() {

    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String cf)  throws FomatoInvalidoException{
        if (cf == null || cf.length() < Config.MIN_CF_LENGTH || cf.length() > Config.MAX_CF_LENGTH) {
            throw new FomatoInvalidoException("Codice fiscale non valido");
        }
        this.codiceFiscale = cf.toUpperCase();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws FomatoInvalidoException {
        if (nome == null || nome.length() < Config.MIN_NAME_LENGTH || nome.length() > Config.MAX_NAME_LENGTH) {
            throw new FomatoInvalidoException("nome non valido");
        }
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome)  throws FomatoInvalidoException {
        if (cognome == null || cognome.length() < Config.MIN_SURNAME_LENGTH || cognome.length() > Config.MAX_SURNAME_LENGTH) {
            throw new FomatoInvalidoException("cognome non valido");
        }
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email)  throws FomatoInvalidoException{
        if (email == null || cognome.length() < Config.MIN_EMAIL_LENGTH || email.length() > Config.MAX_EMAIL_LENGTH) {
            throw new FomatoInvalidoException("email non valido");
        }
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono)  throws FomatoInvalidoException{
        if (telefono == null || telefono.length() <= Config.MIN_PHONE_LENGTH || telefono.length() > Config.MAX_PHONE_LENGTH) {
            throw new FomatoInvalidoException("telefono non valido");
        }
        this.telefono = telefono;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita)  throws FomatoInvalidoException {
        if (dataNascita == null || Period.between(dataNascita, LocalDate.now()).getYears() < Config.MIN_AGE) {
            throw new FomatoInvalidoException("devi avere " + Config.MIN_AGE + "per registrarti nell'applicazione");
        }
        this.dataNascita = dataNascita;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pwd)  throws FomatoInvalidoException {
        if (pwd == null || pwd.length() > Config.MAX_PASSWORD_LENGTH || pwd.length() <= Config.MIN_PASSWORD_LENGTH) {
            throw  new FomatoInvalidoException("Password non valida");
        }
        this.password = pwd;
    }

    public boolean isComplete(){
        return codiceFiscale != null && nome != null && cognome != null && email != null && telefono != null && dataNascita != null && password != null;
    }
    public abstract Role isType();

}
