package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.Prescription;
import it.uniroma2.progettoispw.controller.bean.InformazioniUtente;
import it.uniroma2.progettoispw.controller.bean.DailyTherapyBean;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
import java.util.Objects;

public class TherapyController implements Controller{
    private DaoFacade daoFacade = new DaoFacade();


    public void addDose(int code, Prescription prescription){
        User user = SessionManager.getInstance().getSession(code).getUtente();
        prescription.getDose().setInviante(new InformazioniUtente(user));
        if (Objects.requireNonNull(prescription.getDose().getTipo()) == MediccationType.CONFEZIONE) {
            daoFacade.buildDoseConfezione(daoFacade.wrapDoseCostructor(prescription), user.getCodiceFiscale());
        } else {
            //quindi e un principioAttivo
            daoFacade.buildDosePrincipioAttivo(daoFacade.wrapDoseCostructor(prescription), user.getCodiceFiscale());
        }
    }


    public DailyTherapyBean getTerapiaGiornaliera(int code, LocalDate date) {
        Session session = SessionManager.getInstance().getSession(code);
        Patient patient = (Patient)session.getUtente();
        DailyTherapy dailyTherapy = null;
        TherapyCalendar therapyCalendar = patient.getCalendario();
        if (therapyCalendar.esiste(date)) {
            dailyTherapy = therapyCalendar.getDailyTherapy(date);
        } else {
            dailyTherapy = daoFacade.getTerapiaGiornaliera(patient.getCodiceFiscale(), date);
            patient.getCalendario().addDailyTherapy(dailyTherapy);
        }
        DailyTherapyBean dailyTherapyBean = new DailyTherapyBean(dailyTherapy);
        dailyTherapy.attach(dailyTherapyBean);
        return dailyTherapyBean;
    }


}
