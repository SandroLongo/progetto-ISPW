package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Dose;
import it.uniroma2.progettoispw.model.domain.DoseInviata;
import it.uniroma2.progettoispw.model.domain.Richiesta;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class RichiestaMandata extends RichiestaBean {
    private int idRichiesta;

    public RichiestaMandata(Richiesta richiesta) {
        super();
        super.setInvio(richiesta.getInvio());
        super.setInviante(new InformazioniUtente(richiesta.getInviante()));
        super.setRicevente(new InformazioniUtente(richiesta.getRicevente()));
        List<DoseInviata> lista = richiesta.getMedicinali();
        super.replaceDosi(lista);
    }

    public int getIdRichiesta() {
        return idRichiesta;
    }

    public void setIdRichiesta(int idRichiesta) {
        this.idRichiesta = idRichiesta;
    }
}
