package it.uniroma2.progettoispw.model.dao.memorydao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.TerapiaDao;
import it.uniroma2.progettoispw.model.domain.Dose;
import it.uniroma2.progettoispw.model.domain.DoseConfezione;
import it.uniroma2.progettoispw.model.domain.DosePrincipioAttivo;
import it.uniroma2.progettoispw.model.domain.TerapiaGiornaliera;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class TerapiaMemoryDao extends MemoryDao implements TerapiaDao {
    private static TerapiaMemoryDao instance;
    private TreeMap<String, TreeMap<LocalDate, TreeMap<LocalTime, List<Dose<?>>>>> terapie = new TreeMap<String, TreeMap<LocalDate, TreeMap<LocalTime, List<Dose<?>>>>>();

    private TerapiaMemoryDao() {
        super();
    }

    public static TerapiaMemoryDao getInstance() {
        if (instance == null) {
            instance = new TerapiaMemoryDao();
        }
        return instance;
    }
    @Override
    public TerapiaGiornaliera getTerapiaGiornaliera(String codiceFiscale, LocalDate data) throws DaoException {
        TerapiaGiornaliera terapiaGiornaliera= new TerapiaGiornaliera(data);
        if (terapie.containsKey(codiceFiscale)) {
            if (terapie.get(codiceFiscale).containsKey(data)) {
                terapiaGiornaliera.setDosiPerOrario(terapie.get(codiceFiscale).get(data));
            }
        }
        return terapiaGiornaliera;
    }

    @Override
    public void addDoseConfezione(DoseConfezione doseConfezione, LocalDate giorno, String codiceFiscale) throws DaoException {
        LocalTime orario = doseConfezione.getOrario();


        TreeMap<LocalDate, TreeMap<LocalTime, List<Dose<?>>>> mappaDate =
                terapie.computeIfAbsent(codiceFiscale, k -> new TreeMap<>());


        TreeMap<LocalTime, List<Dose<?>>> mappaOrari =
                mappaDate.computeIfAbsent(giorno, d -> new TreeMap<>());


        List<Dose<?>> listaDosi =
                mappaOrari.computeIfAbsent(orario, t -> new ArrayList<>());

        listaDosi.add(doseConfezione);
    }

    @Override
    public void addDosePrincipioAttivo(DosePrincipioAttivo principioAttivo, LocalDate giorno, String codiceFiscale) throws DaoException {
        LocalTime orario = principioAttivo.getOrario();


        TreeMap<LocalDate, TreeMap<LocalTime, List<Dose<?>>>> mappaDate =
                terapie.computeIfAbsent(codiceFiscale, k -> new TreeMap<>());


        TreeMap<LocalTime, List<Dose<?>>> mappaOrari =
                mappaDate.computeIfAbsent(giorno, d -> new TreeMap<>());


        List<Dose<?>> listaDosi =
                mappaOrari.computeIfAbsent(orario, t -> new ArrayList<>());

        listaDosi.add(principioAttivo);
    }

}
