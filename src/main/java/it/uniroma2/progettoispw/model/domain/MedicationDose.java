package it.uniroma2.progettoispw.model.domain;

import java.time.LocalTime;

public abstract class MedicationDose {
    private int quantita;
    private String unitaMisura;
    private LocalTime orario;
    private String descrizioneMedica;
    private boolean assunta;
    private User inviante;

    protected MedicationDose(int quantita, String unitaMisura, LocalTime orario, String descrizione, User inviante) {
        this.quantita = quantita;
        this.unitaMisura = unitaMisura;
        this.orario = orario;
        this.descrizioneMedica = descrizione;
        this.inviante = inviante;
        this.assunta = false;
    }

    protected MedicationDose() {

    }

    public abstract String getCodice();
    public abstract MediccationType isType();
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
    public User getInviante() {
        return inviante;
    }
    public void setInviante(User inviante) {
        this.inviante = inviante;
    }
}
