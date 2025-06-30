package it.uniroma2.progettoispw.model.domain;

import it.uniroma2.progettoispw.controller.bean.Subject;

import java.util.ArrayList;
import java.util.List;

public class RichiestePendenti extends Subject {
    private List<Richiesta> richieste;

    public RichiestePendenti() {
        richieste = new ArrayList<Richiesta>();
    }

    public RichiestePendenti(List<Richiesta> richieste) {
        this.richieste = richieste;
    }

    public List<Richiesta> getRichieste() {
        return richieste;
    }

    public void setRichieste(List<Richiesta> richieste) {
        this.richieste = richieste;
    }

    public void addRichiesta(Richiesta richiesta) {
        richieste.add(richiesta);
        notifica();
    }

    public Richiesta getRichiestaByid(int id) {
        for (Richiesta richiesta : richieste) {
            if (richiesta.getId() == id) {
                return richiesta;
            }
        }
        return null;
    }

    public boolean deleteRichiesta(int id) {
        for (Richiesta richiesta : richieste) {
            if (richiesta.getId() == id) {
                richieste.remove(richiesta);
                notifica();
                return true;
            }
        }
        return false;
    }


}
