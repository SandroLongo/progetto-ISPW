package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Confezione;

public class ConfezioneBean {
    private int codiceAic;
    private String denominazione;
    private String descrizione;
    private String forma;
    private String paAssociati;
    private long quantita;
    private String unitaMisura;

    public ConfezioneBean(Confezione confezione) {
        this.codiceAic = confezione.getCodiceAic();
        this.denominazione = confezione.getDenominazione();
        this.descrizione = confezione.getDescrizione();
        this.forma = confezione.getForma();
        this.paAssociati = confezione.getPaAssociati();
        this.quantita = confezione.getQuantita();
        this.unitaMisura = confezione.getUnitaMisura();
    }

    public int getCodiceAic() {
        return codiceAic;
    }
    public void setCodiceAic(int codiceAic) {
        this.codiceAic = codiceAic;
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
    public String getPaAssociati() {
        return paAssociati;
    }
    public void setPaAssociati(String paAssociati) {
        this.paAssociati = paAssociati;
    }
    public long getQuantita() {
        return quantita;
    }
    public void setQuantita(long quantita) {
        this.quantita = quantita;
    }
    public String getUnitaMisura() {
        return unitaMisura;
    }
    public void setUnitaMisura(String unitaMisura) {
        this.unitaMisura = unitaMisura;
    }
}
