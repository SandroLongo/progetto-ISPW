package it.uniroma2.progettoispw.model.domain;

import java.time.LocalTime;

public class DoseConfezione extends Dose{
    private Confezione confezione;

    public DoseConfezione(Confezione confezione, int quantita, String unita_misura, LocalTime orario, String descrizione, Dottore inviante) {
        super(quantita, unita_misura, orario, descrizione, inviante);
        this.confezione = confezione;

    }

    public DoseConfezione() {

    }

    public DoseConfezione(Confezione confezione) {
        this.confezione = confezione;
    }

    public Confezione getConfezione() {
        return confezione;
    }
    public void setConfezione(Confezione confezione) {
        this.confezione = confezione;
    }


    @Override
    public String getCodice() {
        return String.valueOf(confezione.getCodice_aic());
    }

    @Override
    public TipoDose isType() {
        return TipoDose.Confezione;
    }

    @Override
    public String getNome() {
        return confezione.getDenominazione();
    }
}
