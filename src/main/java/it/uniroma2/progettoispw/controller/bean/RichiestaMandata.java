package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Richiesta;


public class RichiestaMandata extends RichiestaBean {
    private int idRichiesta;

    public RichiestaMandata(Richiesta richiesta) {
        super(richiesta);
        this.idRichiesta = richiesta.getId();
    }

    public int getIdRichiesta() {
        return idRichiesta;
    }

    public void setIdRichiesta(int idRichiesta) {
        this.idRichiesta = idRichiesta;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
