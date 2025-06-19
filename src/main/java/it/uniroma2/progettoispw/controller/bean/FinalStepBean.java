package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Dottore;

import java.time.LocalDate;
import java.time.LocalTime;

public class FinalStepBean {
    private LocalDate inizio;
    private int num_ripetizioni;
    private int rate_giorni;
    private int quantita;
    private String unita_misura;
    private LocalTime orario;
    private String descrizione_medica;

    public FinalStepBean(LocalDate inizio, int num_ripetizioni, int rate_giorni, int quantita, String unita_misura, LocalTime orario, String descrizione_medica) {
        this.inizio = inizio;
        this.num_ripetizioni = num_ripetizioni;
        this.rate_giorni = rate_giorni;
        this.quantita = quantita;
        this.unita_misura = unita_misura;
        this.orario = orario;
        this.descrizione_medica = descrizione_medica;
    }

    public FinalStepBean() {}

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

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getUnita_misura() {
        return unita_misura;
    }

    public void setUnita_misura(String unita_misura) {
        this.unita_misura = unita_misura;
    }

    public LocalTime getOrario() {
        return orario;
    }

    public void setOrario(LocalTime orario) {
        this.orario = orario;
    }

    public String getDescrizione_medica() {
        return descrizione_medica;
    }

    public void setDescrizione_medica(String descrizione_medica) {
        this.descrizione_medica = descrizione_medica;
    }
}
