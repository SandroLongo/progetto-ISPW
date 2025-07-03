package it.uniroma2.progettoispw.model.domain;

import java.time.LocalTime;

public class DosePrincipioAttivo extends Dose{
    private PrincipioAttivo principioAttivo;

    public DosePrincipioAttivo(PrincipioAttivo principioAttivo, int quantita, String unitaMisura, LocalTime orario, String descrizione, Utente inviante){
        super(quantita, unitaMisura, orario, descrizione, inviante);
        this.principioAttivo = principioAttivo;
    }

    public DosePrincipioAttivo() {
        super();
    }

    public DosePrincipioAttivo(PrincipioAttivo principioAttivo) {
        this.principioAttivo = principioAttivo;
    }

    public PrincipioAttivo getPrincipioAttivo() {
        return principioAttivo;
    }
    public void setPrincipioAttivo(PrincipioAttivo principioAttivo) {
        this.principioAttivo = principioAttivo;
    }

    @Override
    public String getCodice() {
        return principioAttivo.getCodiceAtc();
    }

    @Override
    public TipoDose isType() {
        return TipoDose.PRINCIPIOATTIVO;
    }

    @Override
    public String getNome() {
        return principioAttivo.getNome();
    }
}
