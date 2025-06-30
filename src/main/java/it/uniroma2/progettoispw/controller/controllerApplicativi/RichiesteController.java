package it.uniroma2.progettoispw.controller.controllerApplicativi;

import it.uniroma2.progettoispw.controller.bean.FinalStepBean;
import it.uniroma2.progettoispw.controller.bean.InformazioniUtente;
import it.uniroma2.progettoispw.controller.bean.RichiestaBean;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
import java.util.List;

public class RichiesteController implements Controller{
    DaoFacade daoFacade = new DaoFacade();

    public RichiesteController() {
    }



    public InformazioniUtente getInformazioniPaziente(int codice, String cf) {
        Utente utente = daoFacade.getInfoUtente(cf);
        InformazioniUtente informazioniUtente = new InformazioniUtente(utente.getCodiceFiscale(), utente.getNome(), utente.getCognome(),
                utente.getEmail(), utente.getTelefono(), utente.getData_nascita());
        return informazioniUtente;
    }


    public void invia(int codice, RichiestaBean richiestaBean) {
        Utente utente = SessionManager.getInstance().getSession(codice).getUtente();
        richiestaBean.setInviante(new InformazioniUtente(utente));
        richiestaBean.setInvio(LocalDate.now());
        switch (utente.isType()) {
            case Dottore -> {daoFacade.addRichiesta(richiestaBean);}
            default -> {throw new RuntimeException("operazione non supportata");}
        }
    }
}
