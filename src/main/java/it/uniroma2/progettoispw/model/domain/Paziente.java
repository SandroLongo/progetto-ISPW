package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Paziente extends Utente {
    private List<Dottore> dottoriAccettati;
    private CalendarioTerapeutico calendario;
    private RichiestePendenti pendenti;

    public Paziente(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono, List<Dottore> dottoriAccettati) {
        super(codiceFiscale, nome, cognome, nascita, email, telefono);
        if (dottoriAccettati == null){
            this.dottoriAccettati = new ArrayList<>();
        } else {
            this.dottoriAccettati = new ArrayList<>(dottoriAccettati);
        }
        calendario = new CalendarioTerapeutico();
        this.pendenti = new RichiestePendenti();
    }

    public RichiestePendenti getRichiestePendenti() {
        return pendenti;
    }

    public void setRichiestePendenti(List<Richiesta> richiestePendenti) {
        pendenti.setRichieste(richiestePendenti);
    }

    public Paziente(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono) {
        super(codiceFiscale, nome, cognome, nascita, email, telefono);
    }

    public List<Dottore> getDottoriAccettati() {
        return dottoriAccettati;
    }
    public void setDottoriAccettati(List<Dottore> dottoriAccettati) {
        this.dottoriAccettati = dottoriAccettati;
    }
    public void addDottore(Dottore dottore) {
        this.dottoriAccettati.add(dottore);
    }

    public CalendarioTerapeutico getCalendario() {
        return calendario;
    }

    @Override
    public Ruolo isType() {
        return Ruolo.PAZIENTE;
    }
}
