package it.uniroma2.progettoispw.model.domain;

import java.time.LocalTime;

public class DosePrincipioAttivo extends Dose<String>{
    private PrincipioAttivo principioAttivo;

    public DosePrincipioAttivo(PrincipioAttivo principioAttivo, int quantita, String unita_misura, LocalTime orario, String descrizione, Dottore inviante){
        super(quantita, unita_misura, orario, descrizione, inviante);
        this.principioAttivo = principioAttivo;
    }

    public DosePrincipioAttivo() {
        super();
    }

    public PrincipioAttivo getPrincipioAttivo() {
        return principioAttivo;
    }
    public void setPrincipioAttivo(PrincipioAttivo principioAttivo) {
        this.principioAttivo = principioAttivo;
    }

    @Override
    public String getCodice() {
        return principioAttivo.getCodice_atc();
    }

    @Override
    public TipoDose isType() {
        return TipoDose.PrincipioAttivo;
    }
}
