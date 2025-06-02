package it.uniroma2.progettoispw.model.domain;

import java.time.LocalTime;

public abstract class Dose<P> {
    private int quantita;
    private String unita_misura;
    private LocalTime orario;
    private String descrizione_medica;
    private boolean assunta;
    private Dottore inviante;

    public Dose(int quantita, String unita_misura, LocalTime orario, String descrizione, Dottore inviante) {
        this.quantita = quantita;
        this.unita_misura = unita_misura;
        this.orario = orario;
        this.descrizione_medica = descrizione;
        this.inviante = inviante;
        this.assunta = false;
    }

    public abstract P getCodice();
    public abstract TipoDose isType();

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
    public String getDescrizione() {
        return descrizione_medica;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione_medica = descrizione;
    }
    public boolean isAssunta() {
        return assunta;
    }
    public void setAssunta(boolean assunta) {
        this.assunta = assunta;
    }
    public Dottore getInviante() {
        return inviante;
    }
    public void setInviante(Dottore inviante) {
        this.inviante = inviante;
    }
}
