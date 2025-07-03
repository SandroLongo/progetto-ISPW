package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.ListaRichiesteBean;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.*;

public class ManageRequestController {
    DaoFacade daoFacade = new DaoFacade();

    public ManageRequestController() {
    }

    public ListaRichiesteBean getRichieste(int codice) {
        Utente utente = SessionManager.getInstance().getSession(codice).getUtente();
        ListaRichiesteBean listaRichiesteBean;
        switch (utente.isType()) {
            case Paziente -> {
                RichiestePendenti richiestePendenti = ((Paziente)utente).getRichiestePendenti();
            listaRichiesteBean = new ListaRichiesteBean(richiestePendenti);
            richiestePendenti.attach(listaRichiesteBean);
            }
            default -> {throw new RuntimeException("operazione non supportata");}
        }
        return listaRichiesteBean;
    }

    public void accettaRichiesta(int codice, int codiceRichiesta) {
        Utente utente = SessionManager.getInstance().getSession(codice).getUtente();
        switch (utente.isType()) {
            case Paziente -> {Richiesta richiesta = ((Paziente)utente).getRichiestePendenti().getRichiestaByid(codiceRichiesta);
                daoFacade.addTerapiaByRichiesta(richiesta);
                ((Paziente)utente).getRichiestePendenti().deleteRichiesta(codiceRichiesta);
                daoFacade.deleteRichiesta(codiceRichiesta);
                SessionManager.getInstance().deleteRichiesta(richiesta);}
            default -> {throw new RuntimeException("operazione non supportata");}
        }
    }

    public void rifiutaRichiesta(int codice, int codiceRichiesta) {
        Utente utente = SessionManager.getInstance().getSession(codice).getUtente();
        switch (utente.isType()) {
            case Paziente -> {Richiesta richiesta = ((Paziente)utente).getRichiestePendenti().getRichiestaByid(codiceRichiesta);
                                SessionManager.getInstance().deleteRichiesta(richiesta);}
            default -> {throw new RuntimeException("operazione non supportata");}
        }
    }
}
