package it.uniroma2.progettoispw.model.domain;

import it.uniroma2.progettoispw.controller.bean.Subject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class DailyTherapy extends Subject {
    private LocalDate data;
    private SortedMap<LocalTime, List<MedicationDose>> dosiPerOrario = new TreeMap<>();

    public DailyTherapy(LocalDate data) {
        this.data = data;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public SortedMap<LocalTime, List<MedicationDose>> getDosiPerOrario() {
        return dosiPerOrario;
    }
    public void addDose(MedicationDose nuovaMedicationDose) {
        //forse da lanciare un eccezione se null
        if (nuovaMedicationDose == null){
            return;
        }
        dosiPerOrario.computeIfAbsent(nuovaMedicationDose.getOrario(), orarioChiave -> new ArrayList<>()).add(nuovaMedicationDose);
        notifica();
    }

    public void removeDose(MedicationDose medicationDose) {
        if (medicationDose != null){
            dosiPerOrario.get(medicationDose.getOrario()).remove(medicationDose);
        }
    }

    public void setDosiPerOrario(SortedMap<LocalTime, List<MedicationDose>> dosiPerOrario) {
        this.dosiPerOrario = dosiPerOrario;
    }

}
