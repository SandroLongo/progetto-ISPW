package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.DoseBean;
import it.uniroma2.progettoispw.controller.bean.PrescriptionBean;
import it.uniroma2.progettoispw.controller.bean.InformazioniUtente;
import it.uniroma2.progettoispw.controller.bean.DailyTherapyBean;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class TherapyController implements Controller{
    private DaoFacade daoFacade = new DaoFacade();


    public void addDose(int code, PrescriptionBean prescriptionBean){
        User user = SessionManager.getInstance().getSession(code).getUtente();
        Medication medication;
        DoseBean doseBean = prescriptionBean.getDose();
        switch (doseBean.getTipo()){
            case CONFEZIONE -> {
                medication = daoFacade.getConfezioneByCodiceAic(Integer.parseInt(doseBean.getCodice()));
            }
            case PRINCIPIOATTIVO -> {
                medication = daoFacade.getPrincipioAttvoByCodiceAtc(doseBean.getCodice());
            }
            default -> throw new IllegalArgumentException("invalid dose type");
        }
        if (medication == null){
            throw new UnsupportedOperation("the medication specified is not existent");
        }
        Prescription prescription = new Prescription(new MedicationDose(medication, doseBean.getQuantita(), doseBean.getUnitaMisura(), doseBean.getOrario(),
                                                        doseBean.getDescrizione(), user), prescriptionBean.getNumRipetizioni(), prescriptionBean.getInizio(),
                                                        prescriptionBean.getRateGiorni());
        prescription.getDose().setSender(user);
        MedicationDose medDose = new MedicationDose();
        String taxCode = user.getCodiceFiscale();
        List<LocalDate> dates = daoFacade.buildMedicationDose(prescription, user.getCodiceFiscale());
        SessionManager sessionManager = SessionManager.getInstance();
        for (LocalDate date : dates) {
            sessionManager.aggiornaSessioni(taxCode, medDose, date);
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
