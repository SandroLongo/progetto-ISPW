package it.uniroma2.progettoispw.model.domain;

public class Confezione {
    private int codice_aic;
    private int cod_farmaco;
    private int cod_confezione;
    private String denominazione;
    private String descrizione;
    private int codice_ditta;
    private String ragione_sociale;
    private String stato_amministrativo;
    private String tipo_procedura;
    private String forma;
    private String codice_atc;
    private String pa_associati;
    private long quantita;
    private String unita_misura;
    private String link;

    public Confezione(int codice_aic, int cod_farmaco, int cod_confezione, String denominazione, String descrizione, int codice_ditta, String ragione_sociale,
                      String stato_amministrativo, String tipo_procedura, String forma, String codice_atc, String pa_associati,
                      long quantita, String unita_misura, String link) {
        this.codice_aic = codice_aic;
        this.cod_farmaco = cod_farmaco;
        this.cod_confezione = cod_confezione;
        this.denominazione = denominazione;
        this.descrizione = descrizione;
        this.codice_ditta = codice_ditta;
        this.ragione_sociale = ragione_sociale;
        this.stato_amministrativo = stato_amministrativo;
        this.tipo_procedura = tipo_procedura;
        this.forma = forma;
        this.codice_atc = codice_atc;
        this.pa_associati = pa_associati;
        this.quantita = quantita;
        this.unita_misura = unita_misura;
        this.link = link;

    }

    public Confezione(int codice_aic) {
        this.codice_aic = codice_aic;
    };

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
    public int getCod_confezione() {
        return cod_confezione;
    }
    public void setCod_confezione(int cod_confezione) {
        this.cod_confezione = cod_confezione;
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
    public int getCodice_ditta() {
        return codice_ditta;
    }
    public void setCodice_ditta(int codice_ditta) {
        this.codice_ditta = codice_ditta;
    }
    public String getRagione_sociale() {
        return ragione_sociale;
    }
    public void setRagione_sociale(String ragione_sociale) {
        this.ragione_sociale = ragione_sociale;
    }
    public String getStato_amministrativo() {
        return stato_amministrativo;
    }
    public void setStato_amministrativo(String stato_amministrativo) {
        this.stato_amministrativo = stato_amministrativo;
    }
    public String getTipo_procedura() {
        return tipo_procedura;
    }
    public void setTipo_procedura(String tipo_procedura) {
        this.tipo_procedura = tipo_procedura;
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
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String createLink(){
        return null;
    }

}
