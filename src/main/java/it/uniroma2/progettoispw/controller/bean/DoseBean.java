package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.MedicationDose;
import it.uniroma2.progettoispw.model.domain.MediccationType;

import java.time.LocalTime;

public class DoseBean {
    private String codice;
    private int quantita;
    private String unitaMisura;
    private LocalTime orario;
    private String descrizione;
    private boolean assunta;
    private InformazioniUtente inviante;
    private String nome;
    private MediccationType tipo;

    public DoseBean(MediccationType tipo) {
        this.tipo = tipo;
    }

    public DoseBean(MedicationDose medicationDose) {
        this.codice = medicationDose.getCodice();
        this.unitaMisura = medicationDose.getUnitaMisura();
        this.orario = medicationDose.getOrario();
        this.quantita = medicationDose.getQuantita();
        this.assunta = medicationDose.isAssunta();
        this.descrizione = medicationDose.getDescrizione();
        this.nome = medicationDose.getNome();
        this.tipo = medicationDose.isType();
        this.inviante = new InformazioniUtente(medicationDose.getInviante());
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

    public boolean isAssunta() {
        return assunta;
    }

    public void setAssunta(boolean assunta) {
        this.assunta = assunta;
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

    public MediccationType getTipo() {
        return tipo;
    }

    public void setTipo(MediccationType tipo) {
        this.tipo = tipo;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }
}
