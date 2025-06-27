package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Dose;
import it.uniroma2.progettoispw.model.domain.DoseInviata;
import it.uniroma2.progettoispw.model.domain.Dottore;
import it.uniroma2.progettoispw.model.domain.Paziente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RichiestaBean {
    List<DoseCostructor> dosi;
    private LocalDate invio;
    private InformazioniUtente ricevente;
    private InformazioniUtente inviante;

    public RichiestaBean() {
        dosi = new ArrayList<>();
    }

    public void replaceDosi(List<DoseInviata> lista) {
        for (DoseInviata doseInviata : lista) {
            this.addDoseCostructor(new DoseCostructor(doseInviata));
        }
    }

    public List<DoseCostructor> getDosi() {
        return dosi;
    }

    public void addDoseCostructor(DoseCostructor doseCostructor) {
        dosi.add(doseCostructor);
    }

    public LocalDate getInvio() {
        return invio;
    }

    public void setInvio(LocalDate invio) {
        this.invio = invio;
    }

    public InformazioniUtente getRicevente() {
        return ricevente;
    }

    public void setRicevente(InformazioniUtente ricevente) {
        this.ricevente = ricevente;
    }

    public InformazioniUtente getInviante() {
        return inviante;
    }

    public void setInviante(InformazioniUtente inviante) {
        this.inviante = inviante;
    }
}
