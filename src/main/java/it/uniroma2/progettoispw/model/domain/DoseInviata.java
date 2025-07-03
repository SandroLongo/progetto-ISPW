package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;

public class DoseInviata {
    private Dose dose;
    private LocalDate inizio;
    private int numRipetizioni;
    private int rateGiorni;

    public DoseInviata(Dose dose, int numGiorni, LocalDate inizio, int rateGiorni) {
        this.dose = dose;
        this.numRipetizioni = numGiorni;
        this.inizio = inizio;
        this.rateGiorni = rateGiorni;
    }

    public DoseInviata() {

    }

    public Dose getDose() {
        return dose;
    }
    public void setDose(Dose dose) {
        this.dose = dose;
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
        return dose.getNome();
    }
    public TipoDose isType(){
        return dose.isType();
    }
}
