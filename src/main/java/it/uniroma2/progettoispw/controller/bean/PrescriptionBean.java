package it.uniroma2.progettoispw.controller.bean;

import java.time.LocalDate;

public class PrescriptionBean {
    private DoseBean dose;
    private LocalDate inizio;
    private int numRipetizioni;
    private int rateGiorni;

    public PrescriptionBean() {
        dose = new DoseBean();
    }

    public PrescriptionBean(it.uniroma2.progettoispw.model.domain.Prescription prescription) {
        this.dose = new DoseBean(prescription.getDose());
        this.inizio = prescription.getInizio();
        this.numRipetizioni = prescription.getNumGiorni();
        this.rateGiorni = prescription.getRateGiorni();
    }

    public boolean isComplete(){
        return dose.isCompleate() && inizio != null && numRipetizioni > 0 && rateGiorni > 0;
    }

    @Override
    public String toString() {
        return dose.toString() + "inizio: " + inizio.toString() + "per:  " + numRipetizioni + "giorni, ogni: " + rateGiorni + "giorni";
    }

    public DoseBean getDose() {
        return dose;
    }

    public void setDose(DoseBean dose) {
        this.dose = dose;
    }

    public LocalDate getInizio() {
        return inizio;
    }

    public void setInizio(LocalDate inizio) {
        this.inizio = inizio;
    }

    public int getNumRipetizioni() {
        return numRipetizioni;
    }

    public void setNumRipetizioni(int numRipetizioni) {
        this.numRipetizioni = numRipetizioni;
    }

    public int getRateGiorni() {
        return rateGiorni;
    }

    public void setRateGiorni(int rateGiorni) {
        this.rateGiorni = rateGiorni;
    }
}
