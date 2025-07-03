package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Confezione;

public class ConfezioneBean {
    private int codice_aic;
    private String denominazione;
    private String descrizione;
    private String forma;
    private String pa_associati;
    private long quantita;
    private String unita_misura;

    public ConfezioneBean(Confezione confezione) {
        this.codice_aic = confezione.getCodice_aic();
        this.denominazione = confezione.getDenominazione();
        this.descrizione = confezione.getDescrizione();
        this.forma = confezione.getForma();
        this.pa_associati = confezione.getPa_associati();
        this.quantita = confezione.getQuantita();
        this.unita_misura = confezione.getUnita_misura();
    }

    public int getCodice_aic() {
        return codice_aic;
    }
    public void setCodice_aic(int codice_aic) {
        this.codice_aic = codice_aic;
    }
    public String getDenominazione() {
        return denominazione;
    }
    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getForma() {
        return forma;
    }
    public void setForma(String forma) {
        this.forma = forma;
    }
    public String getPa_associati() {
        return pa_associati;
    }
    public void setPa_associati(String pa_associati) {
        this.pa_associati = pa_associati;
    }
    public long getQuantita() {
        return quantita;
    }
    public void setQuantita(long quantita) {
        this.quantita = quantita;
    }
    public String getUnita_misura() {
        return unita_misura;
    }
    public void setUnita_misura(String unita_misura) {
        this.unita_misura = unita_misura;
    }
}
