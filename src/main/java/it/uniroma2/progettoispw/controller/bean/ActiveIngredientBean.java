package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.ActiveIngredient;

public class ActiveIngredientBean {
    private String id;
    private String name;

    public ActiveIngredientBean(ActiveIngredient activeIngredient) {
        this.id = activeIngredient.getId();
        this.name = activeIngredient.getName();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
