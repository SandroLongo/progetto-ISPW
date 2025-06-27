package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;
import java.util.Date;

public class DoseInviata {
    private Dose dose;
    private LocalDate inizio;
    private int num_ripetizioni;
    private int rate_giorni;

    public DoseInviata(Dose dose, int numGiorni, LocalDate inizio, int rateGiorni) {
        this.dose = dose;
        this.num_ripetizioni = numGiorni;
        this.inizio = inizio;
        this.rate_giorni = rateGiorni;
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
        return num_ripetizioni;
    }
    public void setNumGiorni(int numGiorni) {
        this.num_ripetizioni = numGiorni;
    }
    public int getRateGiorni() {
        return rate_giorni;
    }
    public void setRateGiorni(int rateGiorni) {
        this.rate_giorni = rateGiorni;
    }

    public String getNomeDose(){
        return dose.getNome();
    }
    public TipoDose isType(){
        return dose.isType();
    };
}
