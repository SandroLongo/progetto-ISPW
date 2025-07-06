package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.MedicationDose;
import it.uniroma2.progettoispw.model.domain.MedicationType;

import java.time.LocalTime;

public class DoseBean {
    private String codice;
    private int quantita;
    private String unitaMisura;
    private LocalTime orario;
    private String descrizione;
    private InformazioniUtente inviante;
    private String nome;
    private MedicationType tipo;

    public DoseBean(MedicationType tipo) {
        this.tipo = tipo;
    }

    public DoseBean(MedicationDose medicationDose) {
        this.codice = medicationDose.getId();
        this.unitaMisura = medicationDose.getMeasurementUnit();
        this.orario = medicationDose.getScheduledTime();
        this.quantita = medicationDose.getQuantity();
        this.descrizione = medicationDose.getDescription();
        this.nome = medicationDose.getName();
        this.tipo = medicationDose.isType();
        this.inviante = new InformazioniUtente(medicationDose.getSender());
    }

    public DoseBean() {
    }

    @Override
    public String toString() {
        return "tipo: " + tipo + ", nome: " + nome +", quantita: " + quantita + " " + unitaMisura + ", descrizione: " + descrizione;
    }

    public boolean isCompleate(){
        return codice != null && quantita != 0 && unitaMisura != null && orario != null && descrizione != null && nome != null && tipo != null;
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public InformazioniUtente getInviante() {
        return inviante;
    }

    public void setInviante(InformazioniUtente inviante) {
        this.inviante = inviante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public MedicationType getTipo() {
        return tipo;
    }

    public void setTipo(MedicationType tipo) {
        this.tipo = tipo;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }
}
