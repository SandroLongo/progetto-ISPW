package it.uniroma2.progettoispw.model.dao.memorydao;

import it.uniroma2.progettoispw.controller.bean.PrescriptionBundleBean;
import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.PrescriptionBundleDao;
import it.uniroma2.progettoispw.model.domain.Patient;
import it.uniroma2.progettoispw.model.domain.SentPrescriptionBundle;

import java.util.*;

public class PrescriptionBundleMemoryDao extends MemoryDao implements PrescriptionBundleDao {
    private static PrescriptionBundleMemoryDao instance;
    private Map<String, Map<Integer, SentPrescriptionBundle>> prescriptionBundleByUser = new HashMap<>();
    private Map<Integer, SentPrescriptionBundle> prescriptionBundleById = new HashMap<>();
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
    public List<SentPrescriptionBundle> getPrescriptionBundlesByPatient(Patient patient) throws DaoException {
        return new ArrayList<>(prescriptionBundleByUser.getOrDefault(patient.getTaxCode(), Collections.emptyMap()).values());
    }

    @Override
    public void deletePrescriptionBundle(int id) throws DaoException {
        for (Map<Integer, SentPrescriptionBundle> mappaRichieste : prescriptionBundleByUser.values()) {
            if (mappaRichieste.containsKey(id)) {
                mappaRichieste.remove(id);
            } else {
                throw new DaoException("SentPrescriptionBundle non esistente");
            }
        }
    }

    @Override
    public int addPrescriptionBundle(PrescriptionBundleBean prescriptionBundle) throws DaoException {
        numRichieste++;
        SentPrescriptionBundle sentPrescriptionBundle = new SentPrescriptionBundle(prescriptionBundle);
        sentPrescriptionBundle.setId(numRichieste);
        prescriptionBundleById.put(numRichieste, sentPrescriptionBundle);
        prescriptionBundleByUser.computeIfAbsent(sentPrescriptionBundle.getRicevente().getTaxCode(), k -> new HashMap<>())
                .put(numRichieste, sentPrescriptionBundle);
        return numRichieste;
    }

    @Override
    public SentPrescriptionBundle getPrescriptionBundleById(int id) throws DaoException {
        return prescriptionBundleById.get(id);
    }
}
