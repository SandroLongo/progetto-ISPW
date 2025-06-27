package it.uniroma2.progettoispw.controller.bean;

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

    public TerapiaGiornalieraBean(TerapiaGiornaliera terapiaGiornaliera) {
        this.data = terapiaGiornaliera.getData();
        Map<LocalTime, List<Dose>> map = terapiaGiornaliera.getDosiPerOrario();

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

    @Override
    public void update() {
        replaceDosi(terapiaGiornaliera.getDosiPerOrario());
    }
}
