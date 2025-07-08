package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.MedicationType;

public class MedicationBean {
    private String id;
    private String name;
    private MedicationType type;

    public MedicationBean(MedicinalProductBean product) {
        this.type = MedicationType.MEDICINALPRODUCT;
        id = product.getId();
        name = product.getName();
    }

    public MedicationBean(ActiveIngredientBean activeIngredient) {
        this.type = MedicationType.ACRIVEINGREDIENT;
        id = activeIngredient.getId();
        name = activeIngredient.getName();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MedicationType getType() {
        return type;
    }

}
