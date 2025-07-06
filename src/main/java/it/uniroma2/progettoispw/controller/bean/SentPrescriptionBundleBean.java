package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.SentPrescriptionBundle;


public class SentPrescriptionBundleBean extends PrescriptionBundleBean {
    private int idRichiesta;

    public SentPrescriptionBundleBean(SentPrescriptionBundle sentPrescriptionBundle) {
        super(sentPrescriptionBundle);
        this.idRichiesta = sentPrescriptionBundle.getId();
    }

    public int getIdRichiesta() {
        return idRichiesta;
    }

    public void setIdRichiesta(int idRichiesta) {
        this.idRichiesta = idRichiesta;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
