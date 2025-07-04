package it.uniroma2.progettoispw.model.domain;

import java.time.LocalTime;

public abstract class Dose {
    private int quantita;
    private String unitaMisura;
    private LocalTime orario;
    private String descrizioneMedica;
    private boolean assunta;
    private Utente inviante;

    protected Dose(int quantita, String unitaMisura, LocalTime orario, String descrizione, Utente inviante) {
        this.quantita = quantita;
        this.unitaMisura = unitaMisura;
        this.orario = orario;
        this.descrizioneMedica = descrizione;
        this.inviante = inviante;
        this.assunta = false;
    }

    protected Dose() {

    }

    public abstract String getCodice();
    public abstract TipoDose isType();
    public abstract String getNome();

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
    public String getDescrizione() {
        return descrizioneMedica;
    }
    public void setDescrizione(String descrizione) {
        this.descrizioneMedica = descrizione;
    }
    public boolean isAssunta() {
        return assunta;
    }
    public void setAssunta(boolean assunta) {
        this.assunta = assunta;
    }
    public Utente getInviante() {
        return inviante;
    }
    public void setInviante(Utente inviante) {
        this.inviante = inviante;
    }
}
