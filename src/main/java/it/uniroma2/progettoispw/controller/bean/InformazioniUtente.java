package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.User;

import java.time.LocalDate;

public class InformazioniUtente {
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private LocalDate dataNascita;

    public InformazioniUtente(String codiceFiscale, String nome, String cognome, String email, String telefono, LocalDate dataNascita) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
        this.dataNascita = dataNascita;
    }

    public InformazioniUtente(User inviante) {
        nome = inviante.getNome();
        cognome = inviante.getCognome();
        email = inviante.getEmail();
        telefono = inviante.getTelefono();
        dataNascita = inviante.getDataNascita();
        codiceFiscale = inviante.getCodiceFiscale();
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    @Override
    public String toString() {
        return "codiceFiscale: " + codiceFiscale + " nome: " + nome + " cognome: " + cognome + " email: " + email + " telefono: " + telefono + " dataNascita: " + dataNascita;
    }
}
