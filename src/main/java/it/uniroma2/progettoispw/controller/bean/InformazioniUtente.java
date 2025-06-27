package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Dottore;
import it.uniroma2.progettoispw.model.domain.Utente;

import java.time.LocalDate;

public class InformazioniUtente {
    private String codice_fiscale;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private LocalDate data_nascita;

    public InformazioniUtente(String codice_fiscale, String nome, String cognome, String email, String telefono, LocalDate data_nascita) {
        this.codice_fiscale = codice_fiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
        this.data_nascita = data_nascita;
    }

    public InformazioniUtente(Utente inviante) {
        nome = inviante.getNome();
        cognome = inviante.getCognome();
        email = inviante.getEmail();
        telefono = inviante.getTelefono();
        data_nascita = inviante.getData_nascita();
        codice_fiscale = inviante.getCodiceFiscale();
    }

    public String getCodice_fiscale() {
        return codice_fiscale;
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

    public LocalDate getData_nascita() {
        return data_nascita;
    }
}
