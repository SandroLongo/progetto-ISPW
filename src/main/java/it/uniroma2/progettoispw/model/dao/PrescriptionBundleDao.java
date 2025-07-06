package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.Patient;
import it.uniroma2.progettoispw.model.domain.SentPrescriptionBundle;

import java.util.List;

public interface PrescriptionBundleDao {
    public List<SentPrescriptionBundle> getRichisteOfPaziente(Patient patient) throws DaoException;
    public void deleteRichiesta(int id) throws DaoException;
    public int addRichiesta(SentPrescriptionBundle sentPrescriptionBundle) throws DaoException;

}
