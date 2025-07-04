package it.uniroma2.progettoispw.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Utente implements Serializable {
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private LocalDate dataNascita;

    protected Utente(Utente utente){
        this.codiceFiscale = utente.getCodiceFiscale();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.email = utente.getEmail();
        this.telefono = utente.getTelefono();
        this.dataNascita = utente.getDataNascita();
    }


    protected Utente(String codiceFiscale, String nome, String cognome, LocalDate dataNascita, String email, String telefono) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.email = email;
        this.telefono = telefono;

    }

    protected Utente(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public abstract Ruolo isType();

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
