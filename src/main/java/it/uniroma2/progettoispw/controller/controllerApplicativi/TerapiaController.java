package it.uniroma2.progettoispw.controller.controllerApplicativi;

import it.uniroma2.progettoispw.controller.bean.DoseBean;
import it.uniroma2.progettoispw.controller.bean.DoseCostructor;
import it.uniroma2.progettoispw.controller.bean.InformazioniUtente;
import it.uniroma2.progettoispw.controller.bean.TerapiaGiornalieraBean;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class TerapiaController implements Controller{
    private DaoFacade daoFacade = new DaoFacade();

    public TerapiaController() {
    }

    public void addDose(int code, DoseCostructor doseCostructor){
        Utente utente = SessionManager.getInstance().getSession(code).getUtente();
        doseCostructor.getDose().setInviante(new InformazioniUtente(utente));
        switch (doseCostructor.getDose().getTipo()){
            case Confezione -> {daoFacade.buildDoseConfezione(daoFacade.wrapDoseCostructor(doseCostructor), utente.getCodiceFiscale());}
            case PrincipioAttivo -> {daoFacade.buildDosePrincipioAttivo(daoFacade.wrapDoseCostructor(doseCostructor), utente.getCodiceFiscale());}
            default -> {throw new RuntimeException("Dose non valido");}
        }
    }


    public TerapiaGiornalieraBean getTerapiaGiornaliera(int code, LocalDate date) {
        DaoFacade daoFacade = new DaoFacade();
        Session session = SessionManager.getInstance().getSession(code);
        Paziente paziente = (Paziente)session.getUtente();
        TerapiaGiornaliera terapiaGiornaliera= null;
        CalendarioTerapeutico calendarioTerapeutico = paziente.getCalendario();
        if (calendarioTerapeutico.esiste(date)) {
            terapiaGiornaliera = calendarioTerapeutico.getTerapiaGiornaliera(date);
        } else {
            terapiaGiornaliera = daoFacade.getTerapiaGiornaliera(paziente.getCodiceFiscale(), date);
            paziente.getCalendario().addTerapiaGiornaliera(terapiaGiornaliera);
        }
        TerapiaGiornalieraBean terapiaGiornalieraBean = new TerapiaGiornalieraBean(terapiaGiornaliera);
        terapiaGiornaliera.attach(terapiaGiornalieraBean);
        return terapiaGiornalieraBean;
    }

    //public void deleteDose(int code,){

    //}


}
