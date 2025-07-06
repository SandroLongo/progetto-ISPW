package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.SentPrescriptionBundle;


public class SentPrescriptionBundleBean extends PrescriptionBundleBean {
    private int id;

    public SentPrescriptionBundleBean(SentPrescriptionBundle sentPrescriptionBundle) {
        super(sentPrescriptionBundle);
        this.id = sentPrescriptionBundle.getId();
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
