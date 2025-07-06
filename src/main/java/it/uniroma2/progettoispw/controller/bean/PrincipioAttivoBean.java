package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.ActiveIngredient;

public class PrincipioAttivoBean {
    private String codiceAtc;
    private String nome;

    public PrincipioAttivoBean(ActiveIngredient activeIngredient) {
        this.codiceAtc = activeIngredient.getId();
        this.nome = activeIngredient.getName();
    }

    public String getCodiceAtc() {
        return codiceAtc;
    }
    public String getNome() {
        return nome;
    }
}
