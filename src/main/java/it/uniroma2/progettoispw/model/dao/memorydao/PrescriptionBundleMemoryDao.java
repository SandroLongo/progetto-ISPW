package it.uniroma2.progettoispw.model.dao.memorydao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.PrescriptionBundleDao;
import it.uniroma2.progettoispw.model.domain.Patient;
import it.uniroma2.progettoispw.model.domain.SentPrescriptionBundle;

import java.util.*;

public class PrescriptionBundleMemoryDao extends MemoryDao implements PrescriptionBundleDao {
    private static PrescriptionBundleMemoryDao instance;
    private Map<String, Map<Integer, SentPrescriptionBundle>> richieste = new HashMap<>();
    private int numRichieste;

    public PrescriptionBundleMemoryDao() {
        super();
    }

    public static PrescriptionBundleMemoryDao getInstance() {
        if (instance == null) {
            instance = new PrescriptionBundleMemoryDao();
        }
        return instance;
    }


    @Override
    public List<SentPrescriptionBundle> getRichisteOfPaziente(Patient patient) throws DaoException {
        return new ArrayList<>(richieste.getOrDefault(patient.getCodiceFiscale(), Collections.emptyMap()).values());
    }

    @Override
    public void deleteRichiesta(int id) throws DaoException {
        for (Map<Integer, SentPrescriptionBundle> mappaRichieste : richieste.values()) {
            if (mappaRichieste.containsKey(id)) {
                mappaRichieste.remove(id);
            } else {
                throw new DaoException("SentPrescriptionBundle non esistente");
            }
        }
    }

    @Override
    public int addRichiesta(SentPrescriptionBundle sentPrescriptionBundle) throws DaoException {
        numRichieste++;
        richieste.computeIfAbsent(sentPrescriptionBundle.getRicevente().getCodiceFiscale(), k -> new HashMap<>())
                .put(numRichieste, sentPrescriptionBundle);
        return numRichieste;
    }
}
