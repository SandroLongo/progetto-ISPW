package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionBundleBean {
    List<Prescription> dosi;
    private LocalDate invio;
    private InformazioniUtente ricevente;
    private InformazioniUtente inviante;

    public PrescriptionBundleBean() {
        dosi = new ArrayList<>();
    }

    public PrescriptionBundleBean(SentPrescriptionBundle sentPrescriptionBundle) {
        this.invio = sentPrescriptionBundle.getInvio();
        this.inviante = new InformazioniUtente(sentPrescriptionBundle.getInviante());
        this.ricevente = new InformazioniUtente(sentPrescriptionBundle.getRicevente());
        dosi = new ArrayList<>();
        replaceDosi(sentPrescriptionBundle.getMedicinali());
    }

    public void replaceDosi(List<it.uniroma2.progettoispw.model.domain.Prescription> lista) {
        for (it.uniroma2.progettoispw.model.domain.Prescription prescription : lista) {
            this.addDoseCostructor(new Prescription(prescription));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Prescription prescription : dosi) {
            sb.append(prescription.toString()).append("\n");
        }
        return sb.toString();
    }

    public String toStringBase(){
        return "DOTTORE: " + ricevente.getNome() + " " + ricevente.getCognome() + "data di invio: " + invio.toString();
    }

    public List<Prescription> getDosi() {
        return dosi;
    }

    public void addDoseCostructor(Prescription prescription) {
        dosi.add(prescription);
    }

    public void deleteDoseCostructor(Prescription prescription){
        dosi.remove(prescription);
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
        for (Prescription prescription : getDosi()) {
            prescription.getDose().setInviante(inviante);
        }
        this.inviante = inviante;
    }
}
