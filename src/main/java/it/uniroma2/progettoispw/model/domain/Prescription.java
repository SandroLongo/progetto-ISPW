package it.uniroma2.progettoispw.model.domain;

import it.uniroma2.progettoispw.controller.bean.DoseBean;
import it.uniroma2.progettoispw.controller.bean.InvalidFormatException;
import it.uniroma2.progettoispw.controller.bean.PrescriptionBean;

import java.time.LocalDate;

public class Prescription {
    private MedicationDose medicationDose;
    private LocalDate inizio;
    private int numRipetizioni;
    private int rateGiorni;

    public Prescription(MedicationDose medicationDose, int numGiorni, LocalDate inizio, int rateGiorni) {
        this.medicationDose = medicationDose;
        this.numRipetizioni = numGiorni;
        this.inizio = inizio;
        this.rateGiorni = rateGiorni;
    }

    public Prescription() {

    }

    public Prescription(PrescriptionBean prescriptionBean) {
        setInizio(prescriptionBean.getStartDate());
        setNumGiorni(prescriptionBean.getRepetitionNumber());
        setRateGiorni(prescriptionBean.getDayRate());
        DoseBean doseBean = prescriptionBean.getDose();
        switch (doseBean.getType()){
            case MEDICINALPRODUCT -> this.medicationDose = new MedicationDose(new MedicinalProduct(Integer.parseInt(doseBean.getId())),
                                                                            doseBean.getQuantity(), doseBean.getMeausurementUnit(),
                                                                        doseBean.getScheduledTime(), doseBean.getDescription(), new Patient(doseBean.getSender().getTaxCode()));
            case ACRIVEINGREDIENT -> new MedicationDose(new ActiveIngredient(doseBean.getId()),
                    doseBean.getQuantity(), doseBean.getMeausurementUnit(),
                    doseBean.getScheduledTime(), doseBean.getDescription(), new Patient(doseBean.getSender().getTaxCode()));
            default -> throw new InvalidFormatException("invalid medication type");
        }

    }

    public MedicationDose getDose() {
        return medicationDose;
    }
    public void setDose(MedicationDose medicationDose) {
        this.medicationDose = medicationDose;
    }
    public LocalDate getInizio() {
        return inizio;
    }
    public void setInizio(LocalDate inizio) {
        this.inizio = inizio;
    }
    public int getNumGiorni() {
        return numRipetizioni;
    }
    public void setNumGiorni(int numGiorni) {
        this.numRipetizioni = numGiorni;
    }
    public int getRateGiorni() {
        return rateGiorni;
    }
    public void setRateGiorni(int rateGiorni) {
        this.rateGiorni = rateGiorni;
    }

    public String getNomeDose(){
        return medicationDose.getName();
    }
    public MedicationType isType(){
        return medicationDose.isType();
    }
}
