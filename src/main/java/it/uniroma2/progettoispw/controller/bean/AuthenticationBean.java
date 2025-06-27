package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Ruolo;

public class AuthenticationBean {
    private String nome;
    private String cognome;
    private int codice;
    private Ruolo ruolo;

    public AuthenticationBean(String nome, String cognome, int codice, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.codice = codice;
        this.ruolo = ruolo;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public int getCodice() {
        return codice;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }
}
