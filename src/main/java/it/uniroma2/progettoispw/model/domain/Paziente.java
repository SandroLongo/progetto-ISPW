package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Paziente extends Utente {
    private transient CalendarioTerapeutico calendario;
    private transient RichiestePendenti pendenti;

    public Paziente(Paziente paziente) {
        super(paziente);
        calendario = new CalendarioTerapeutico();
        pendenti = new RichiestePendenti();
    }

    public Paziente(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono) {
        super(codiceFiscale, nome, cognome, nascita, email, telefono);
        this.calendario = new CalendarioTerapeutico();
        this.pendenti = new RichiestePendenti();
    }

    public RichiestePendenti getRichiestePendenti() {
        return pendenti;
    }

    public void setRichiestePendenti(List<Richiesta> richiestePendenti) {
        pendenti.setRichieste(richiestePendenti);
    }


    public CalendarioTerapeutico getCalendario() {
        return calendario;
    }

    @Override
    public Ruolo isType() {
        return Ruolo.PAZIENTE;
    }

    @Override
    public void logout() {
        if (calendario != null){
            Map<LocalDate, TerapiaGiornaliera> map = calendario.getCalendario();
            for (TerapiaGiornaliera t : map.values()){
                t.detachAll();
            }
        }
        if (pendenti != null){
            pendenti.detachAll();
        }
    }
}
