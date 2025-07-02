package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Dose;
import it.uniroma2.progettoispw.model.domain.Dottore;
import it.uniroma2.progettoispw.model.domain.TipoDose;

import java.time.LocalTime;

public class DoseBean {
    private String codice;
    private int quantita;
    private String unita_misura;
    private LocalTime orario;
    private String descrizione_medica;
    private boolean assunta;
    private InformazioniUtente inviante;
    private String nome;
    private TipoDose tipo;

    public DoseBean(TipoDose tipo) {
        this.tipo = tipo;
    }

    public DoseBean(Dose dose) {
        this.codice = dose.getCodice();
        this.unita_misura = dose.getUnita_misura();
        this.orario = dose.getOrario();
        this.quantita = dose.getQuantita();
        this.assunta = dose.isAssunta();
        this.descrizione_medica = dose.getDescrizione();
        this.nome = dose.getNome();
        this.tipo = dose.isType();
        this.inviante = new InformazioniUtente(dose.getInviante());
    }

    public DoseBean() {
    }

    @Override
    public String toString() {
        return "tipo: " + tipo + ", nome: " + nome +", quantita: " + quantita + " " + unita_misura;
    }

    public boolean isCompleate(){
        return codice != null && quantita != 0 && unita_misura != null && orario != null && descrizione_medica != null && inviante != null
                && nome != null && tipo != null;
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
