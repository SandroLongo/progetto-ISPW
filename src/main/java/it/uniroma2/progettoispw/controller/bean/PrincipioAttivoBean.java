package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.ActiveIngridient;

public class PrincipioAttivoBean {
    private String codiceAtc;
    private String nome;

    public PrincipioAttivoBean(ActiveIngridient activeIngridient) {
        this.codiceAtc = activeIngridient.getId();
        this.nome = activeIngridient.getName();
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
