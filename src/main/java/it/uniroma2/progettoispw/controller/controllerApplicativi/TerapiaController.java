package it.uniroma2.progettoispw.controller.controllerApplicativi;

import it.uniroma2.progettoispw.controller.bean.DoseBean;
import it.uniroma2.progettoispw.controller.bean.DoseCostructor;
import it.uniroma2.progettoispw.controller.bean.TerapiaGiornalieraBean;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class TerapiaController implements Controller{

    public TerapiaController() {
    }

    public void addDose(int code, DoseCostructor doseCostructor){
        Utente utente = SessionManager.getInstance().getSession(code).getUtente();
        DaoFacade daoFacade = new DaoFacade();
        switch (doseCostructor.getDose().getTipo()){
            case Confezione -> {}
            case PrincipioAttivo -> {}
            default -> {}
        }
    }


    public TerapiaGiornalieraBean getTerapiaGiornaliera(int code, LocalDate date) {
        DaoFacade daoFacade = new DaoFacade();
        Utente utente = SessionManager.getInstance().getSession(code).getUtente();
        TerapiaGiornaliera terapiaGiornaliera= daoFacade.getTerapiaGiornaliera(utente.getCodiceFiscale(), date);
        TerapiaGiornalieraBean terapiaGiornalieraBean = new TerapiaGiornalieraBean(terapiaGiornaliera);
        return terapiaGiornalieraBean;
    }

    public void deleteDose(int code, ){

    }


}
