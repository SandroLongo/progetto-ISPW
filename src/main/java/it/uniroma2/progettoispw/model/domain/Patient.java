package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Patient extends User {
    private transient TherapyCalendar calendar;
    private transient PendentBundles pending;

    public Patient(Patient patient) {
        super(patient);
        calendar = new TherapyCalendar();
        pending = new PendentBundles();
    }

    public Patient(String taxCode){
        super(taxCode);
    }

    public Patient(String taxCode, String name, String surname, LocalDate birthDate, String email, String phoneNumber) {
        super(taxCode, name, surname, birthDate, email, phoneNumber);
        this.calendar = new TherapyCalendar();
        this.pending = new PendentBundles();
    }

    public PendentBundles getRichiestePendenti() {
        return pending;
    }

    public void setRichiestePendenti(List<SentPrescriptionBundle> richiestePendenti) {
        pending.setPending(richiestePendenti);
    }


    public TherapyCalendar getCalendar() {
        return calendar;
    }

    @Override
    public Role isType() {
        return Role.PATIENT;
    }

    @Override
    public void logout() {
        if (calendar != null){
            Map<LocalDate, DailyTherapy> map = calendar.getCalendar();
            for (DailyTherapy t : map.values()){
                t.detachAll();
            }
        }
        if (pending != null){
            pending.detachAll();
        }
    }
}
