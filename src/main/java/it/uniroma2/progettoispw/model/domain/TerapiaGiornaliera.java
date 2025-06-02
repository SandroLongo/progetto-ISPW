package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class TerapiaGiornaliera {
    private LocalDate data;
    TreeMap<LocalTime, List<Dose>> dosiPerOrario = new TreeMap<>();

    public TerapiaGiornaliera(LocalDate data) {
        this.data = data;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public TreeMap<LocalTime, List<Dose>> getDosiPerOrario() {
        return dosiPerOrario;
    }
    public void addDose(Dose nuovaDose) {
        //forse da lanciare un eccezione se null
        if (nuovaDose == null){
            return;
        }
        dosiPerOrario.computeIfAbsent(nuovaDose.getOrario(), orarioChiave -> new ArrayList<>()).add(nuovaDose);
    }



}
