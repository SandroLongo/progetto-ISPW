package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.*;

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

    public RichiestaBean(Richiesta richiesta) {
        this.invio = richiesta.getInvio();
        this.inviante = new InformazioniUtente(richiesta.getInviante());
        this.ricevente = new InformazioniUtente(richiesta.getRicevente());
        dosi = new ArrayList<>();
        replaceDosi(richiesta.getMedicinali());
    }

    public void replaceDosi(List<DoseInviata> lista) {
        for (DoseInviata doseInviata : lista) {
            this.addDoseCostructor(new DoseCostructor(doseInviata));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DoseCostructor doseCostructor : dosi) {
            sb.append(doseCostructor.toString() + "\n");
        }
        return sb.toString();
    }

    public String toStringBase(){
        return "Dottore: " + ricevente.getNome() + " " + ricevente.getCognome() + "data di invio: " + invio.toString();
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
        for (DoseCostructor doseCostructor : getDosi()) {
            doseCostructor.getDose().setInviante(inviante);
        }
        this.inviante = inviante;
    }
}
