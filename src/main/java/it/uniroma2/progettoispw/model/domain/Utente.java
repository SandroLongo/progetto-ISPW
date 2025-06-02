package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;
import java.util.Date;

public abstract class Utente {
    private String codice_fiscale;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private LocalDate data_nascita;


    public Utente(String codice_fiscale, String nome, String cognome, LocalDate data_nascita, String email, String telefono) {
        this.codice_fiscale = codice_fiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.data_nascita = data_nascita;
        this.email = email;
        this.telefono = telefono;

    }

    public abstract Ruolo isType();
}
