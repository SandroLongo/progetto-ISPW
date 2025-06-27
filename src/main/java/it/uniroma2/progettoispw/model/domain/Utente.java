package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public abstract class Utente {
    private String codice_fiscale;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private LocalDate data_nascita;
    private List<Richiesta> richieste;
    private CalendarioTerapeutico calendarioTerapeutico;


    public Utente(String codice_fiscale, String nome, String cognome, LocalDate data_nascita, String email, String telefono) {
        this.codice_fiscale = codice_fiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.data_nascita = data_nascita;
        this.email = email;
        this.telefono = telefono;

    }

    public Utente(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }

    public abstract Ruolo isType();

    public String getCodiceFiscale() {
        return codice_fiscale;
    }
    public void setCodiceFiscale(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
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
    public LocalDate getData_nascita() {
        return data_nascita;
    }
    public void setData_nascita(LocalDate data_nascita) {
        this.data_nascita = data_nascita;
    }
}
