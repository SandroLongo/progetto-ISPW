package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CalendarioTerapeutico {
    private Map<LocalDate, TerapiaGiornaliera> calendario;

    public CalendarioTerapeutico() {
        calendario = new HashMap<LocalDate, TerapiaGiornaliera>();
    }

    public Map<LocalDate, TerapiaGiornaliera> getCalendario() {
        return calendario;
    }

    public void addTerapiaGiornaliera(TerapiaGiornaliera giornaliera) {
        calendario.put(giornaliera.getData(), giornaliera);
    }

    public TerapiaGiornaliera getTerapiaGiornaliera(LocalDate date) {
        return calendario.get(date);
    }

    public boolean esiste(LocalDate date) {
        return calendario.containsKey(date);
    }

}
