package it.uniroma2.progettoispw.model.domain;

import it.uniroma2.progettoispw.controller.bean.Subject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class TerapiaGiornaliera extends Subject {
    private LocalDate data;
    private TreeMap<LocalTime, List<Dose>> dosiPerOrario = new TreeMap<>();

    public TerapiaGiornaliera(LocalDate data) {
        this.data = data;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public SortedMap<LocalTime, List<Dose>> getDosiPerOrario() {
        return dosiPerOrario;
    }
    public void addDose(Dose nuovaDose) {
        //forse da lanciare un eccezione se null
        if (nuovaDose == null){
            return;
        }
        dosiPerOrario.computeIfAbsent(nuovaDose.getOrario(), orarioChiave -> new ArrayList<>()).add(nuovaDose);
        notifica();
    }

    public void removeDose(Dose dose) {
        if (dose != null){
            dosiPerOrario.get(dose.getOrario()).remove(dose);
        }
    }

    public void setDosiPerOrario(TreeMap<LocalTime, List<Dose>> dosiPerOrario) {
        this.dosiPerOrario = dosiPerOrario;
    }

}
