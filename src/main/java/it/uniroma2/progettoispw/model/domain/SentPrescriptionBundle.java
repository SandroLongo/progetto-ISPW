package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SentPrescriptionBundle {
    private int id;
    private List<Prescription> medicinali;
    private LocalDate invio;
    private Patient ricevente;
    private Doctor inviante;

    public SentPrescriptionBundle(int id, LocalDate invio, Patient ricevente, Doctor inviante) {
        this.id = id;
        this.invio = invio;
        this.ricevente = ricevente;
        this.inviante = inviante;
        medicinali = new ArrayList<>();
    }

    public SentPrescriptionBundle() {
        medicinali = new ArrayList<>();
    }

    public List<Prescription> getMedicinali() {
        return medicinali;
    }
    public void setMedicinali(List<Prescription> medicinali) {
        this.medicinali = medicinali;
    }
    public LocalDate getInvio() {
        return invio;
    }
    public void setInvio(LocalDate invio) {
        this.invio = invio;
    }
    public Patient getRicevente() {
        return ricevente;
    }
    public void setRicevente(Patient ricevente) {
        this.ricevente = ricevente;
    }
    public Doctor getInviante() {
        return inviante;
    }
    public void setInviante(Doctor inviante) {
        this.inviante = inviante;
    }
    public void addDoseInviata(Prescription medicinale) {
        medicinali.add(medicinale);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


}
