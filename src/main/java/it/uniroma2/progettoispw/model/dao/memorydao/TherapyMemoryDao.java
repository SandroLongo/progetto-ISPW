package it.uniroma2.progettoispw.model.dao.memorydao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.TherapyDao;
import it.uniroma2.progettoispw.model.domain.MedicationDose;
import it.uniroma2.progettoispw.model.domain.MedicationDoseConfezione;
import it.uniroma2.progettoispw.model.domain.MedicationDosePrincipioAttivo;
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
    public DailyTherapy getTerapiaGiornaliera(String codiceFiscale, LocalDate data) throws DaoException {
        DailyTherapy dailyTherapy = new DailyTherapy(data);
        if (terapie.containsKey(codiceFiscale) && terapie.get(codiceFiscale).containsKey(data)) {
            dailyTherapy.setDosiPerOrario(terapie.get(codiceFiscale).get(data));
        }
        return dailyTherapy;
    }

    @Override
    public void addDoseConfezione(MedicationDoseConfezione doseConfezione, LocalDate giorno, String codiceFiscale) throws DaoException {
        LocalTime orario = doseConfezione.getOrario();


        TreeMap<LocalDate, TreeMap<LocalTime, List<MedicationDose>>> mappaDate =
                terapie.computeIfAbsent(codiceFiscale, k -> new TreeMap<>());


        TreeMap<LocalTime, List<MedicationDose>> mappaOrari =
                mappaDate.computeIfAbsent(giorno, d -> new TreeMap<>());


        List<MedicationDose> listaDosi =
                mappaOrari.computeIfAbsent(orario, t -> new ArrayList<>());

        listaDosi.add(doseConfezione);
    }

    @Override
    public void addDosePrincipioAttivo(MedicationDosePrincipioAttivo principioAttivo, LocalDate giorno, String codiceFiscale) throws DaoException {
        LocalTime orario = principioAttivo.getOrario();


        TreeMap<LocalDate, TreeMap<LocalTime, List<MedicationDose>>> mappaDate =
                terapie.computeIfAbsent(codiceFiscale, k -> new TreeMap<>());


        TreeMap<LocalTime, List<MedicationDose>> mappaOrari =
                mappaDate.computeIfAbsent(giorno, d -> new TreeMap<>());


        List<MedicationDose> listaDosi =
                mappaOrari.computeIfAbsent(orario, t -> new ArrayList<>());

        listaDosi.add(principioAttivo);
    }

}
