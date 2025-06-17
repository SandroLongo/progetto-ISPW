package it.uniroma2.progettoispw.controller.bean;

public class ConfezioniInformationBean {
    private int codice_aic;
    private int cod_farmaco;
    private String denominazione;
    private String descrizione;
    private String forma;
    private String codice_atc;
    private String pa_associati;
    private long quantita;
    private String unita_misura;

    public ConfezioniInformationBean(int codice_aic, int cod_farmaco, String denominazione, String descrizione, String forma, String codice_atc, String pa_associati, long quantita, String unita_misura) {
        this.codice_aic = codice_aic;
        this.cod_farmaco = cod_farmaco;
        this.denominazione = denominazione;
        this.descrizione = descrizione;
        this.forma = forma;
        this.codice_atc = codice_atc;
        this.pa_associati = pa_associati;
        this.quantita = quantita;
        this.unita_misura = unita_misura;
    }

    public int getCodice_aic() {
        return codice_aic;
    }

    public void setCodice_aic(int codice_aic) {
        this.codice_aic = codice_aic;
    }

    public int getCod_farmaco() {
        return cod_farmaco;
    }

    public void setCod_farmaco(int cod_farmaco) {
        this.cod_farmaco = cod_farmaco;
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

    public String getCodice_atc() {
        return codice_atc;
    }

    public void setCodice_atc(String codice_atc) {
        this.codice_atc = codice_atc;
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
