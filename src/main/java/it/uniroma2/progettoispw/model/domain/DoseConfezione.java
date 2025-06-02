package it.uniroma2.progettoispw.model.domain;

import java.time.LocalTime;

public class DoseConfezione extends Dose {
    private Confezione confezione;

    public DoseConfezione(Confezione confezione, int quantita, String unita_misura, LocalTime orario, String descrizione, Dottore inviante) {
        super(quantita, unita_misura, orario, descrizione, inviante);
        this.confezione = confezione;

    }
    public Confezione getConfezione() {
        return confezione;
    }
    public void setConfezione(Confezione confezione) {
        this.confezione = confezione;
    }


}
