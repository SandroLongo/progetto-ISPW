package it.uniroma2.progettoispw.controller.controllerApplicativi;

import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.Dose;
import it.uniroma2.progettoispw.model.domain.SessionManager;
import it.uniroma2.progettoispw.model.domain.TerapiaGiornaliera;
import it.uniroma2.progettoispw.model.domain.Utente;

import java.time.LocalDate;

public class TerapiaController {
    Utente utente;
    private TerapiaGiornaliera terapiaGiornaliera;
    private final DaoFacade daoFacade= new DaoFacade();

    public TerapiaController() {
        utente = SessionManager.getInstance().getCurrentUtente();
    }

    public TerapiaGiornaliera switchTo(LocalDate date) {
        terapiaGiornaliera = daoFacade.getTerapiaGiornaliera(utente.getCodiceFiscale(), date);
        return terapiaGiornaliera;
    }

    public void deleteDose(Dose<?> dose){
        terapiaGiornaliera.removeDose(dose);
    }

}
