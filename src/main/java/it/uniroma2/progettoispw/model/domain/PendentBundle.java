package it.uniroma2.progettoispw.model.domain;

import it.uniroma2.progettoispw.controller.bean.Subject;

import java.util.ArrayList;
import java.util.List;

public class PendentBundle extends Subject {
    private List<SentPrescriptionBundle> richieste;

    public PendentBundle() {
        richieste = new ArrayList<SentPrescriptionBundle>();
    }

    public PendentBundle(List<SentPrescriptionBundle> richieste) {
        this.richieste = richieste;
    }

    public List<SentPrescriptionBundle> getRichieste() {
        return richieste;
    }

    public void setRichieste(List<SentPrescriptionBundle> richieste) {
        this.richieste = richieste;
    }

    public void addRichiesta(SentPrescriptionBundle sentPrescriptionBundle) {
        richieste.add(sentPrescriptionBundle);
        notifica();
    }

    public SentPrescriptionBundle getRichiestaByid(int id) {
        for (SentPrescriptionBundle sentPrescriptionBundle : richieste) {
            if (sentPrescriptionBundle.getId() == id) {
                return sentPrescriptionBundle;
            }
        }
        return null;
    }

    public boolean deleteRichiesta(int id) {
        for (SentPrescriptionBundle sentPrescriptionBundle : richieste) {
            if (sentPrescriptionBundle.getId() == id) {
                richieste.remove(sentPrescriptionBundle);
                notifica();
                return true;
            }
        }
        return false;
    }


}
