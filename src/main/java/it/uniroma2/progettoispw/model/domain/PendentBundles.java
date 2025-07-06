package it.uniroma2.progettoispw.model.domain;

import it.uniroma2.progettoispw.controller.bean.Subject;

import java.util.ArrayList;
import java.util.List;

public class PendentBundles extends Subject {
    private List<SentPrescriptionBundle> pending;

    public PendentBundles() {
        pending = new ArrayList<SentPrescriptionBundle>();
    }

    public PendentBundles(List<SentPrescriptionBundle> pending) {
        this.pending = pending;
    }

    public List<SentPrescriptionBundle> getPending() {
        return pending;
    }

    public void setPending(List<SentPrescriptionBundle> pending) {
        this.pending = pending;
    }

    public void addBundle(SentPrescriptionBundle sentPrescriptionBundle) {
        pending.add(sentPrescriptionBundle);
        notifica();
    }

    public SentPrescriptionBundle getBundlesById(int id) {
        for (SentPrescriptionBundle sentPrescriptionBundle : pending) {
            if (sentPrescriptionBundle.getId() == id) {
                return sentPrescriptionBundle;
            }
        }
        return null;
    }

    public boolean deleteRichiesta(int id) {
        for (SentPrescriptionBundle sentPrescriptionBundle : pending) {
            if (sentPrescriptionBundle.getId() == id) {
                pending.remove(sentPrescriptionBundle);
                notifica();
                return true;
            }
        }
        return false;
    }


}
