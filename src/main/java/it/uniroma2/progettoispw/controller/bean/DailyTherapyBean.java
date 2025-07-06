package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.controller.graphic.controller.Notificator;
import it.uniroma2.progettoispw.model.domain.MedicationDose;
import it.uniroma2.progettoispw.model.domain.DailyTherapy;

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
        this.data = dailyTherapy.getData();
        Map<LocalTime, List<MedicationDose>> map = dailyTherapy.getDosiPerOrario();
        replaceDosi(map);
    }

    private void replaceDosi(Map<LocalTime, List<MedicationDose>> map){
        for (List<MedicationDose> list: map.values()) {
            for (MedicationDose medicationDose : list) {
                DoseBean doseBean = new DoseBean(medicationDose);
                this.aggiungiDose(doseBean);
            }
        }
    }

    public void aggiungiDose(DoseBean dose) {
        dosiPerOrario.computeIfAbsent(dose.getOrario(), orarioChiave -> new ArrayList<>()).add(dose);
    }

    public SortedMap<LocalTime, List<DoseBean>> getDosiPerOrario() {
        return dosiPerOrario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void addNotificator(Notificator notificator) {
        this.notificators.add(notificator);
    }

    public void deleteNotificator(Notificator notificator) {
        this.notificators.remove(notificator);
    }

    @Override
    public void update() {
        replaceDosi(dailyTherapy.getDosiPerOrario());
        for (Notificator notificator: notificators) {
            notificator.notifica();
        }
    }

    @Override
    public void detach() {
        dailyTherapy = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Terapia del: ").append(data.toString()).append("\n");
        for (Map.Entry<LocalTime, List<DoseBean>> entry: dosiPerOrario.entrySet()) {
            for (DoseBean dose: entry.getValue()) {
                sb.append(entry.getKey().toString()).append(" - ").append(dose.toString()).append("assunta: ").append(dose.isAssunta() ? "si" : "no").append("\n");
            }
        }
        return sb.toString();

    }
}
