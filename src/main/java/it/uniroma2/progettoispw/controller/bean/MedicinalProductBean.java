package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.MedicinalProduct;

public class MedicinalProductBean {
    private String id;
    private String name;
    private String description;
    private String form;
    private String activeIngridientId;
    private String activeIngridientName;
    private long quantity;
    private String meausurementUnit;

    public MedicinalProductBean(MedicinalProduct medicinalProduct) {
        this.id = medicinalProduct.getId();
        this.name = medicinalProduct.getName();
        this.description = medicinalProduct.getDescrizione();
        this.form = medicinalProduct.getForma();
        this.activeIngridientId = medicinalProduct.getCodiceAtc();
        this.quantity = medicinalProduct.getQuantita();
        this.meausurementUnit = medicinalProduct.getUnitaMisura();
        this.activeIngridientName = medicinalProduct.getPaAssociati();
    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getForm() {
        return form;
    }

    public String getActiveIngridientId() {
        return activeIngridientId;
    }

    public String getActiveIngridientName() {
        return activeIngridientName;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getUnitaMisura() {
        return meausurementUnit;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("confezione : " + "Nome: ").append(name).append(", Descrizione: ").append(description).append(", Forma:").append(form).append(",pa associati: ").append(activeIngridientName).append(",codice aic: ").append(id).append(", codice Atc: ").append(activeIngridientId);
        return builder.toString();
    }
}
