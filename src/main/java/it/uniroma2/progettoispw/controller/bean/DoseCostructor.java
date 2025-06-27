package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Dose;
import it.uniroma2.progettoispw.model.domain.DoseInviata;

import java.time.LocalDate;

public class DoseCostructor {
    private DoseBean dose;
    private LocalDate inizio;
    private int num_ripetizioni;
    private int rate_giorni;

    public DoseCostructor() {}

    public DoseCostructor(DoseInviata doseInviata) {
        this.dose = new DoseBean(doseInviata.getDose());
        this.inizio = doseInviata.getInizio();
        this.num_ripetizioni = doseInviata.getNumGiorni();
        this.rate_giorni = doseInviata.getRateGiorni();
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

    public int getNum_ripetizioni() {
        return num_ripetizioni;
    }

    public void setNum_ripetizioni(int num_ripetizioni) {
        this.num_ripetizioni = num_ripetizioni;
    }

    public int getRate_giorni() {
        return rate_giorni;
    }

    public void setRate_giorni(int rate_giorni) {
        this.rate_giorni = rate_giorni;
    }
}
