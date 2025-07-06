package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Role;

public class AuthenticationBean {
    private String nome;
    private String cognome;
    private int codice;
    private Role role;

    public AuthenticationBean(String nome, String cognome, int codice, Role role) {
        this.nome = nome;
        this.cognome = cognome;
        this.codice = codice;
        this.role = role;
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

    public Role getRuolo() {
        return role;
    }
}
