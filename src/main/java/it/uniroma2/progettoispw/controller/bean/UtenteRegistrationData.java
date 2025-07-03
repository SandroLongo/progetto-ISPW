package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Ruolo;

import java.time.LocalDate;

public abstract class UtenteRegistrationData {
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private LocalDate dataNascita;
    private String password;

    protected UtenteRegistrationData(String codiceFiscale, String nome, String cognome, String email, String telefono, LocalDate dataNascita, String password) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
        this.dataNascita = dataNascita;
        this.password = password;
    }

    protected UtenteRegistrationData() {

    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract Ruolo isType();

}
