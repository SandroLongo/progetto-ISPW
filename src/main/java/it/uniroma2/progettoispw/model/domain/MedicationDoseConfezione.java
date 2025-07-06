package it.uniroma2.progettoispw.model.domain;

import java.time.LocalTime;

public class MedicationDoseConfezione extends MedicationDose {
    private MedicinalProduct medicinalProduct;

    public MedicationDoseConfezione(MedicinalProduct medicinalProduct, int quantita, String unitaMisura, LocalTime orario, String descrizione, User inviante) {
        super(quantita, unitaMisura, orario, descrizione, inviante);
        this.medicinalProduct = medicinalProduct;

    }

    public MedicationDoseConfezione() {

    }

    public MedicationDoseConfezione(MedicinalProduct medicinalProduct) {
        this.medicinalProduct = medicinalProduct;
    }

    public MedicinalProduct getConfezione() {
        return medicinalProduct;
    }
    public void setConfezione(MedicinalProduct medicinalProduct) {
        this.medicinalProduct = medicinalProduct;
    }


    @Override
    public String getCodice() {
        return String.valueOf(medicinalProduct.getCodiceAic());
    }

    @Override
    public MediccationType isType() {
        return MediccationType.CONFEZIONE;
    }

    @Override
    public String getNome() {
        return medicinalProduct.getDenominazione();
    }
}
