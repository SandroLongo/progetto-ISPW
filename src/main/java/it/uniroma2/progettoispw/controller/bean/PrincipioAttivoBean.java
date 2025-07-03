package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.PrincipioAttivo;

public class PrincipioAttivoBean {
    private String codice_atc;
    private String nome;

    public PrincipioAttivoBean(PrincipioAttivo principioAttivo) {
        this.codice_atc = principioAttivo.getCodice_atc();
        this.nome = principioAttivo.getNome();
    }

    public String getCodice_atc() {
        return codice_atc;
    }
    public void setCodice_atc(String codice_atc) {
        this.codice_atc = codice_atc;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
