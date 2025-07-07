package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.controller.bean.PrescriptionBundleBean;
import it.uniroma2.progettoispw.model.domain.Patient;
import it.uniroma2.progettoispw.model.domain.SentPrescriptionBundle;

import java.util.List;

public interface PrescriptionBundleDao {
    public List<SentPrescriptionBundle> getBundles(Patient patient) throws DaoException;
    public void deleteBundle(int id) throws DaoException;
    public int addBundle(PrescriptionBundleBean prescriptionBundle) throws DaoException;
    public SentPrescriptionBundle getBundleById(int id) throws DaoException;
}
