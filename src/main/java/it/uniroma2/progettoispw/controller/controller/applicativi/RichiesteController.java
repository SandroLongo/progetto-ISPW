package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.DoseCostructor;
import it.uniroma2.progettoispw.controller.bean.InformazioniUtente;
import it.uniroma2.progettoispw.controller.bean.RichiestaBean;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.dao.DaoFactory;
import it.uniroma2.progettoispw.model.dao.RichiesteDao;
import it.uniroma2.progettoispw.model.dao.UtenteDao;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;

public class RichiesteController implements Controller{
    DaoFacade daoFacade = new DaoFacade();

    public RichiesteController() {
    }



    public InformazioniUtente getInformazioniPaziente(int codice, String cf) {
        Utente utente = daoFacade.getInfoUtente(cf);
        InformazioniUtente informazioniUtente = new InformazioniUtente(utente.getCodiceFiscale(), utente.getNome(), utente.getCognome(),
                utente.getEmail(), utente.getTelefono(), utente.getDataNascita());
        return informazioniUtente;
    }


    public void invia(int codice, RichiestaBean richiestaBean) {
        Utente utente = SessionManager.getInstance().getSession(codice).getUtente();
        richiestaBean.setInviante(new InformazioniUtente(utente));
        richiestaBean.setInvio(LocalDate.now());
        switch (utente.isType()) {
            case Dottore -> {Richiesta nuovaRichiesta = new Richiesta();
                nuovaRichiesta.setInvio(richiestaBean.getInvio());
                UtenteDao utenteDao = DaoFactory.getIstance().getUtenteDao();
                nuovaRichiesta.setRicevente(utenteDao.getPaziente(richiestaBean.getRicevente().getCodiceFiscale())); //da rivedere classe InformazioniUtente
                nuovaRichiesta.setInviante(utenteDao.getDottore(richiestaBean.getInviante().getCodiceFiscale()));
                for (DoseCostructor dose: richiestaBean.getDosi()){
                    nuovaRichiesta.addDoseInviata(daoFacade.wrapDoseCostructor(dose));
                }
                RichiesteDao richiesteDao = DaoFactory.getIstance().getRichiesteDao();
                int id = richiesteDao.addRichiesta(nuovaRichiesta);
                nuovaRichiesta.setId(id);
                SessionManager.getInstance().addRichiesta(nuovaRichiesta);}
                default -> {throw new RuntimeException("operazione non supportata");}
            }
    }
}
