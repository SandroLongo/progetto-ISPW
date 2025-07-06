package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.controller.bean.PrescriptionBundleBean;
import it.uniroma2.progettoispw.model.domain.Patient;
import it.uniroma2.progettoispw.model.domain.SentPrescriptionBundle;

import java.util.List;

public interface PrescriptionBundleDao {
    public List<SentPrescriptionBundle> getPrescriptionBundlesByPatient(Patient patient) throws DaoException;
    public void deletePrescriptionBundle(int id) throws DaoException;
    public int addPrescriptionBundle(PrescriptionBundleBean sentPrescriptionBundle) throws DaoException;
    public SentPrescriptionBundle getPrescriptionBundleById(int id) throws DaoException;
}
