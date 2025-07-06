package it.uniroma2.progettoispw.model.domain;

import it.uniroma2.progettoispw.controller.bean.Subject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class DailyTherapy extends Subject {
    private LocalDate date;
    private SortedMap<LocalTime, List<MedicationDose>> dosesByTime = new TreeMap<>();

    public DailyTherapy(LocalDate date) {
        this.date = date;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public SortedMap<LocalTime, List<MedicationDose>> getDosesByTime() {
        return dosesByTime;
    }
    public void addDose(MedicationDose newMedicationDose) {
        if (newMedicationDose == null){
            return;
        }
        dosesByTime.computeIfAbsent(newMedicationDose.getScheduledTime(), timeKey -> new ArrayList<>()).add(newMedicationDose);
        notifica();
    }

    public void removeDose(MedicationDose medicationDose) {
        if (medicationDose != null){
            dosesByTime.get(medicationDose.getScheduledTime()).remove(medicationDose);
        }
    }

    public void setDosesByTime(SortedMap<LocalTime, List<MedicationDose>> dosesByTime) {
        this.dosesByTime = dosesByTime;
    }

}
