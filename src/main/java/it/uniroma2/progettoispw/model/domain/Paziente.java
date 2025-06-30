package it.uniroma2.progettoispw.model.domain;

import it.uniroma2.progettoispw.controller.bean.Subject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Paziente extends Utente {
    private List<Dottore> dottori_accettati;
    private CalendarioTerapeutico calendario;
    private RichiestePendenti pendenti;

    public Paziente(String codice_fiscale, String nome, String cognome, LocalDate nascita, String email, String telefono, List<Dottore> dottori_accettati) {
        super(codice_fiscale, nome, cognome, nascita, email, telefono);
        if (dottori_accettati == null){
            this.dottori_accettati = new ArrayList<>();
        } else {
            this.dottori_accettati = new ArrayList<>(dottori_accettati);
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

    public List<Dottore> getDottori_accettati() {
        return dottori_accettati;
    }
    public void setDottori_accettati(List<Dottore> dottori_accettati) {
        this.dottori_accettati = dottori_accettati;
    }
    public void addDottore(Dottore dottore) {
        this.dottori_accettati.add(dottore);
    }

    public CalendarioTerapeutico getCalendario() {
        return calendario;
    }

    @Override
    public Ruolo isType() {
        return Ruolo.Paziente;
    }
}
