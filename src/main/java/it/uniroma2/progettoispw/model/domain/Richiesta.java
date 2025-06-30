package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Richiesta {
    private int id;
    private List<DoseInviata> medicinali;
    private LocalDate invio;
    private Paziente ricevente;
    private Dottore inviante;

    public Richiesta(int id, LocalDate invio, Paziente ricevente, Dottore inviante) {
        this.id = id;
        this.invio = invio;
        this.ricevente = ricevente;
        this.inviante = inviante;
        medicinali = new ArrayList<>();
    }

    public Richiesta() {
        medicinali = new ArrayList<>();
    }

    public List<DoseInviata> getMedicinali() {
        return medicinali;
    }
    public void setMedicinali(List<DoseInviata> medicinali) {
        this.medicinali = medicinali;
    }
    public LocalDate getInvio() {
        return invio;
    }
    public void setInvio(LocalDate invio) {
        this.invio = invio;
    }
    public Paziente getRicevente() {
        return ricevente;
    }
    public void setRicevente(Paziente ricevente) {
        this.ricevente = ricevente;
    }
    public Dottore getInviante() {
        return inviante;
    }
    public void setInviante(Dottore inviante) {
        this.inviante = inviante;
    }
    public void addDoseInviata(DoseInviata medicinale) {
        medicinali.add(medicinale);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


}
