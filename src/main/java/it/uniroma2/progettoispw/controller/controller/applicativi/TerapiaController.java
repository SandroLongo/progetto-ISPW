package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.DoseCostructor;
import it.uniroma2.progettoispw.controller.bean.InformazioniUtente;
import it.uniroma2.progettoispw.controller.bean.TerapiaGiornalieraBean;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
import java.util.Objects;

public class TerapiaController implements Controller{
    private DaoFacade daoFacade = new DaoFacade();

    public TerapiaController() {
    }

    public void addDose(int code, DoseCostructor doseCostructor){
        Utente utente = SessionManager.getInstance().getSession(code).getUtente();
        doseCostructor.getDose().setInviante(new InformazioniUtente(utente));
        if (Objects.requireNonNull(doseCostructor.getDose().getTipo()) == TipoDose.Confezione) {
            daoFacade.buildDoseConfezione(daoFacade.wrapDoseCostructor(doseCostructor), utente.getCodiceFiscale());
        } else {
            //quindi e un principioAttivo
            daoFacade.buildDosePrincipioAttivo(daoFacade.wrapDoseCostructor(doseCostructor), utente.getCodiceFiscale());
        }
    }


    public TerapiaGiornalieraBean getTerapiaGiornaliera(int code, LocalDate date) {
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


}
