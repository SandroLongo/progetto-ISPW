package it.uniroma2.progettoispw.model.dao.dbfiledao;

import it.uniroma2.progettoispw.model.dao.*;
import it.uniroma2.progettoispw.model.dao.filedao.UserFileDao;

public class DbFileDaoFactory extends DaoFactory {
    private boolean utenteDaoIsDb;

    public DbFileDaoFactory(boolean utenteDaoIsDb) {
        this.utenteDaoIsDb = utenteDaoIsDb;
    }
    @Override
    public PrescriptionBundleDao getRichiesteDao() {
        return new PrescriptionBundleDbDao();
    }

    @Override
    public UserDao getUtenteDao() {
        if (utenteDaoIsDb) {
            return new UserDbDao();
        } else {
            return new UserFileDao();
        }
    }

    @Override
    public TherapyDao getTerapiaDao() {
        return new TherapyDbDao();
    }
}
