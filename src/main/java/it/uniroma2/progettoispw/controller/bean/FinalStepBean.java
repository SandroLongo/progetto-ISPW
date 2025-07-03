package it.uniroma2.progettoispw.controller.bean;

import java.time.LocalDate;
import java.time.LocalTime;

public class FinalStepBean {
    private LocalDate inizio;
    private int numRipetizioni;
    private int rateGiorni;
    private int quantita;
    private String unitaMisura;
    private LocalTime orario;
    private String descrizioneMedica;

    public FinalStepBean(LocalDate inizio, int numRipetizioni, int rate_giorni, int quantita, String unitaMisura, LocalTime orario, String descrizioneMedica) {
        this.inizio = inizio;
        this.numRipetizioni = numRipetizioni;
        this.rateGiorni = rate_giorni;
        this.quantita = quantita;
        this.unitaMisura = unitaMisura;
        this.orario = orario;
        this.descrizioneMedica = descrizioneMedica;
    }

    public FinalStepBean() {}

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

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getUnitaMisura() {
        return unitaMisura;
    }

    public void setUnitaMisura(String unitaMisura) {
        this.unitaMisura = unitaMisura;
    }

    public LocalTime getOrario() {
        return orario;
    }

    public void setOrario(LocalTime orario) {
        this.orario = orario;
    }

    public String getDescrizioneMedica() {
        return descrizioneMedica;
    }

    public void setDescrizioneMedica(String descrizioneMedica) {
        this.descrizioneMedica = descrizioneMedica;
    }
}
