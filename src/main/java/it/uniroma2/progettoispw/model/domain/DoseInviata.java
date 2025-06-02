package it.uniroma2.progettoispw.model.domain;

import java.util.Date;

public abstract class DoseInviata {
    private Dose dose;
    private Date inizio;
    private int num_ripetizioni;
    private int rate_giorni;

    public DoseInviata(Dose dose, int numGiorni, Date inizio) {
        this.dose = dose;
        this.num_ripetizioni = numGiorni;
        this.inizio = inizio;
    }
    public Dose getDoseMedicinale() {
        return dose;
    }
    public void setDoseMedicinale(Dose dose) {
        this.dose = dose;
    }
    public Date getInizio() {
        return inizio;
    }
    public void setInizio(Date inizio) {
        this.inizio = inizio;
    }
    public int getNumGiorni() {
        return num_ripetizioni;
    }
    public void setNumGiorni(int numGiorni) {
        this.num_ripetizioni = numGiorni;
    }
}
