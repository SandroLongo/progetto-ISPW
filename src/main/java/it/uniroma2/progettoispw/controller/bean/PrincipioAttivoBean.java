package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.PrincipioAttivo;

public class PrincipioAttivoBean {
    private String codiceAtc;
    private String nome;

    public PrincipioAttivoBean(PrincipioAttivo principioAttivo) {
        this.codiceAtc = principioAttivo.getCodice_atc();
        this.nome = principioAttivo.getNome();
    }

    public String getCodiceAtc() {
        return codiceAtc;
    }
    public void setCodiceAtc(String codiceAtc) {
        this.codiceAtc = codiceAtc;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
