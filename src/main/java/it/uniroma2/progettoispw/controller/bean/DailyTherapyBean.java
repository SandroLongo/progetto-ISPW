package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.controller.graphic.controller.Notificator;
import it.uniroma2.progettoispw.model.domain.MedicationDose;
import it.uniroma2.progettoispw.model.domain.DailyTherapy;
import it.uniroma2.progettoispw.model.domain.Observer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class DailyTherapyBean extends Observer {
    private LocalDate data;
    private TreeMap<LocalTime, List<DoseBean>> dosiPerOrario = new TreeMap<>();
    private DailyTherapy dailyTherapy;
    private List<Notificator> notificators = new ArrayList<>();

    public DailyTherapyBean(DailyTherapy dailyTherapy) {
        this.dailyTherapy = dailyTherapy;
        this.data = dailyTherapy.getDate();
        Map<LocalTime, List<MedicationDose>> map = dailyTherapy.getDosesByTime();
        replaceDosi(map);
    }

    private void replaceDosi(Map<LocalTime, List<MedicationDose>> map){
        dosiPerOrario.clear();
        for (List<MedicationDose> list: map.values()) {
            for (MedicationDose medicationDose : list) {
                DoseBean doseBean = new DoseBean(medicationDose);
                this.aggiungiDose(doseBean);
            }
        }
    }

    private void aggiungiDose(DoseBean dose) {
        dosiPerOrario.computeIfAbsent(dose.getScheduledTime(), orarioChiave -> new ArrayList<>()).add(dose);
    }

    public SortedMap<LocalTime, List<DoseBean>> getDosiPerOrario() {
        return dosiPerOrario;
    }

    public LocalDate getData() {
        return data;
    }

    public void addNotificator(Notificator notificator) {
        this.notificators.add(notificator);
    }

    public void deleteNotificator(Notificator notificator) {
        this.notificators.remove(notificator);
    }

    @Override
    public void update() {
        replaceDosi(dailyTherapy.getDosesByTime());
        for (Notificator notificator: notificators) {
            notificator.notifyChanges();
        }
    }

    @Override
    public void detach() {
        dailyTherapy = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ManageTherapy del: ").append(data.toString()).append("\n");
        for (Map.Entry<LocalTime, List<DoseBean>> entry: dosiPerOrario.entrySet()) {
            for (DoseBean dose: entry.getValue()) {
                sb.append(entry.getKey().toString()).append(" - ").append(dose.toString()).append("\n");
            }
        }
        return sb.toString();

    }
}
