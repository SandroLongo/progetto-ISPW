package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.controller.graphicController.Notificator;
import it.uniroma2.progettoispw.model.domain.Dose;
import it.uniroma2.progettoispw.model.domain.TerapiaGiornaliera;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TerapiaGiornalieraBean extends Observer {
    private LocalDate data;
    private TreeMap<LocalTime, List<DoseBean>> dosiPerOrario = new TreeMap<>();
    private TerapiaGiornaliera terapiaGiornaliera;
    private List<Notificator> notificators = new ArrayList<>();

    public TerapiaGiornalieraBean(TerapiaGiornaliera terapiaGiornaliera) {
        this.terapiaGiornaliera = terapiaGiornaliera;
        this.data = terapiaGiornaliera.getData();
        Map<LocalTime, List<Dose>> map = terapiaGiornaliera.getDosiPerOrario();
        replaceDosi(map);
    }

    private void replaceDosi(Map<LocalTime, List<Dose>> map){
        for (List<Dose> list: map.values()) {
            for (Dose dose: list) {
                DoseBean doseBean = new DoseBean(dose);
                this.aggiungiDose(doseBean);
            }
        }
    }

    public void aggiungiDose(DoseBean dose) {
        LocalTime orario = dose.getOrario();
        dosiPerOrario.computeIfAbsent(dose.getOrario(), orarioChiave -> new ArrayList<>()).add(dose);
    }

    public TreeMap<LocalTime, List<DoseBean>> getDosiPerOrario() {
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
        replaceDosi(terapiaGiornaliera.getDosiPerOrario());
        for (Notificator notificator: notificators) {
            notificator.notifica();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Terapia del: " + data.toString() + "\n");
        for (LocalTime orario: dosiPerOrario.keySet()) {
            for (DoseBean dose: dosiPerOrario.get(orario)) {
                sb.append(orario.toString() + " - " + dose.toString() +  "assunta: " + (dose.isAssunta() ? "si" : "no") + "\n");
            }
        }
        return sb.toString();

    }
}
