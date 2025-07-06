package it.uniroma2.progettoispw.model.domain;

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
        return medicationDose.getNome();
    }
    public MediccationType isType(){
        return medicationDose.isType();
    }
}
