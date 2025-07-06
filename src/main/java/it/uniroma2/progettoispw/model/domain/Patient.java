package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Patient extends User {
    private transient TherapyCalendar calendario;
    private transient PendentBundle pendenti;

    public Patient(Patient patient) {
        super(patient);
        calendario = new TherapyCalendar();
        pendenti = new PendentBundle();
    }

    public Patient(String taxCode){
        super(taxCode);
    }

    public Patient(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono) {
        super(codiceFiscale, nome, cognome, nascita, email, telefono);
        this.calendario = new TherapyCalendar();
        this.pendenti = new PendentBundle();
    }

    public PendentBundle getRichiestePendenti() {
        return pendenti;
    }

    public void setRichiestePendenti(List<SentPrescriptionBundle> richiestePendenti) {
        pendenti.setRichieste(richiestePendenti);
    }


    public TherapyCalendar getCalendario() {
        return calendario;
    }

    @Override
    public Role isType() {
        return Role.PAZIENTE;
    }

    @Override
    public void logout() {
        if (calendario != null){
            Map<LocalDate, DailyTherapy> map = calendario.getCalendar();
            for (DailyTherapy t : map.values()){
                t.detachAll();
            }
        }
        if (pendenti != null){
            pendenti.detachAll();
        }
    }
}
