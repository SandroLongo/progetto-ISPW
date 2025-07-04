package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Dose;
import it.uniroma2.progettoispw.model.domain.TipoDose;

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
    private TipoDose tipo;

    public DoseBean(TipoDose tipo) {
        this.tipo = tipo;
    }

    public DoseBean(Dose dose) {
        this.codice = dose.getCodice();
        this.unitaMisura = dose.getUnitaMisura();
        this.orario = dose.getOrario();
        this.quantita = dose.getQuantita();
        this.assunta = dose.isAssunta();
        this.descrizione = dose.getDescrizione();
        this.nome = dose.getNome();
        this.tipo = dose.isType();
        this.inviante = new InformazioniUtente(dose.getInviante());
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

    public TipoDose getTipo() {
        return tipo;
    }

    public void setTipo(TipoDose tipo) {
        this.tipo = tipo;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }
}
