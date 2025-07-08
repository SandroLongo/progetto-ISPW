package it.uniroma2.progettoispw.model.dao.memorydao;

import it.uniroma2.progettoispw.model.dao.DaoFactory;
import it.uniroma2.progettoispw.model.dao.PrescriptionBundleDao;
import it.uniroma2.progettoispw.model.dao.TherapyDao;
import it.uniroma2.progettoispw.model.dao.UserDao;

public class MemoryDaoFactory extends DaoFactory {
    @Override
    public PrescriptionBundleDao getPrescriptionBundleDao() {
        return PrescriptionBundleMemoryDao.getInstance();
    }

    @Override
    public UserDao getUserDao() {
        return UserMemoryDao.getInstance();
    }

    @Override
    public TherapyDao getTherapyDao() {
        return TherapyMemoryDao.getInstance();
    }
}
