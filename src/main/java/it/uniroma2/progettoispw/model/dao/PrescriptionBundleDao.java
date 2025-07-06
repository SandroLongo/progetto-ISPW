package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.controller.bean.PrescriptionBundleBean;
import it.uniroma2.progettoispw.model.domain.Patient;
import it.uniroma2.progettoispw.model.domain.SentPrescriptionBundle;

import java.util.List;

public interface PrescriptionBundleDao {
    public List<SentPrescriptionBundle> getRichisteOfPaziente(Patient patient) throws DaoException;
    public void deleteRichiesta(int id) throws DaoException;
    public int addRichiesta(PrescriptionBundleBean sentPrescriptionBundle) throws DaoException;

    public SentPrescriptionBundle getPrescriptionBundleById(int id) throws DaoException;
}
