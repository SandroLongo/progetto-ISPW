package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CalendarioTerapeutico {
    private Map<LocalDate, TerapiaGiornaliera> calendarioTerapeutico;

    public CalendarioTerapeutico() {
        calendarioTerapeutico = new HashMap<LocalDate, TerapiaGiornaliera>();
    }

    public void addTerapiaGiornaliera(TerapiaGiornaliera giornaliera) {
        calendarioTerapeutico.put(giornaliera.getData(), giornaliera);
    }

    public TerapiaGiornaliera getTerapiaGiornaliera(LocalDate date) {
        return calendarioTerapeutico.get(date);
    }

    public boolean esiste(LocalDate date) {
        return calendarioTerapeutico.containsKey(date);
    }

}
