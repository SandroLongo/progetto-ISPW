package it.uniroma2.progettoispw.model.dao.memorydao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.TherapyDao;
import it.uniroma2.progettoispw.model.domain.MedicationDose;
import it.uniroma2.progettoispw.model.domain.DailyTherapy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class TherapyMemoryDao extends MemoryDao implements TherapyDao {
    private static TherapyMemoryDao instance;
    private TreeMap<String, TreeMap<LocalDate, TreeMap<LocalTime, List<MedicationDose>>>> terapie = new TreeMap<String, TreeMap<LocalDate, TreeMap<LocalTime, List<MedicationDose>>>>();

    private TherapyMemoryDao() {
        super();
    }

    public static TherapyMemoryDao getInstance() {
        if (instance == null) {
            instance = new TherapyMemoryDao();
        }
        return instance;
    }
    @Override
    public DailyTherapy getTerapiaGiornaliera(String taxCode, LocalDate date) throws DaoException {
        DailyTherapy dailyTherapy = new DailyTherapy(date);
        if (terapie.containsKey(taxCode) && terapie.get(taxCode).containsKey(date)) {
            dailyTherapy.setDosiPerOrario(terapie.get(taxCode).get(date));
        }
        return dailyTherapy;
    }

    @Override
    public void addMedicationDose(MedicationDose doseConfezione, LocalDate date, String taxCode) throws DaoException {
        LocalTime orario = doseConfezione.getScheduledTime();


        TreeMap<LocalDate, TreeMap<LocalTime, List<MedicationDose>>> mappaDate =
                terapie.computeIfAbsent(taxCode, k -> new TreeMap<>());


        TreeMap<LocalTime, List<MedicationDose>> mappaOrari =
                mappaDate.computeIfAbsent(date, d -> new TreeMap<>());


        List<MedicationDose> listaDosi =
                mappaOrari.computeIfAbsent(orario, t -> new ArrayList<>());

        listaDosi.add(doseConfezione);
    }

}
