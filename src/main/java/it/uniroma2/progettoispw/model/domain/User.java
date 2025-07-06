package it.uniroma2.progettoispw.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class User implements Serializable {
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private LocalDate dataNascita;

    protected User(User user){
        this.codiceFiscale = user.getCodiceFiscale();
        this.nome = user.getNome();
        this.cognome = user.getCognome();
        this.email = user.getEmail();
        this.telefono = user.getTelefono();
        this.dataNascita = user.getDataNascita();
    }


    protected User(String codiceFiscale, String nome, String cognome, LocalDate dataNascita, String email, String telefono) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.email = email;
        this.telefono = telefono;

    }

    protected User(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public abstract Role isType();

    public String getCodiceFiscale() {
        return codiceFiscale;
    }
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public LocalDate getDataNascita() {
        return dataNascita;
    }
    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public abstract void logout();
}
