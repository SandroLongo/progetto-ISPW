package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.DoseBean;
import it.uniroma2.progettoispw.controller.bean.InvalidFormatException;
import it.uniroma2.progettoispw.controller.bean.PrescriptionBean;
import it.uniroma2.progettoispw.controller.bean.DailyTherapyBean;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
import java.util.List;

public class TherapyController implements Controller{
    private DaoFacade daoFacade = new DaoFacade();


    public void addDose(int code, PrescriptionBean prescriptionBean){
        User user = SessionManager.getInstance().getSession(code).getUtente();
        Medication medication;
        DoseBean doseBean = prescriptionBean.getDose();
        switch (doseBean.getType()){
            case MEDICINALPRODUCT ->
                medication = daoFacade.getMedicinalProductByID(doseBean.getId());

            case ACRIVEINGREDIENT ->
                medication = daoFacade.getActiveIngridientByID(doseBean.getId());

            default -> throw new InvalidFormatException("invalid dose type");
        }
        MedicationDose medDose = new MedicationDose(medication, doseBean.getQuantity(), doseBean.getMeausurementUnit(), doseBean.getScheduledTime(),
                doseBean.getDescription(), user);
        if (medication == null){
            throw new UnsupportedOperation("the medication specified is not existent");
        }
        Prescription prescription = new Prescription(medDose, prescriptionBean.getRepetitionNumber(), prescriptionBean.getStartDate(),
                                                        prescriptionBean.getDayRate());
        prescription.getDose().setSender(user);
        String taxCode = user.getTaxCode();
        List<LocalDate> dates = daoFacade.buildMedicationDose(prescription, user.getTaxCode());
        SessionManager sessionManager = SessionManager.getInstance();
        for (LocalDate date : dates) {
            sessionManager.aggiornaSessioni(taxCode, medDose, date);
        }
    }


    public DailyTherapyBean getDailyTherapy(int code, LocalDate date) {
        Session session = SessionManager.getInstance().getSession(code);
        Patient patient = (Patient)session.getUtente();
        DailyTherapy dailyTherapy = null;
        TherapyCalendar therapyCalendar = patient.getCalendar();
        if (therapyCalendar.exists(date)) {
            dailyTherapy = therapyCalendar.getDailyTherapy(date);
        } else {
            dailyTherapy = daoFacade.getDailyTherapy(patient.getTaxCode(), date);
            patient.getCalendar().addDailyTherapy(dailyTherapy);
        }
        DailyTherapyBean dailyTherapyBean = new DailyTherapyBean(dailyTherapy);
        dailyTherapy.attach(dailyTherapyBean);
        return dailyTherapyBean;
    }


}
