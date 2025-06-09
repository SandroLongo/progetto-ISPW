package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.dao.dbfiledao.DbFileDaoFactory;
import it.uniroma2.progettoispw.model.dao.dbfiledao.MedicinaliDbDao;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;
    public abstract RichiesteDao getRichiesteDao();
    public abstract UtenteDao getUtenteDao();
    public abstract TerapiaDao getTerapiaDao();

    public MedicinaliDao getMedicinaliDao() {
        return new MedicinaliDbDao();
    }

    public static DaoFactory getIstance() {
        if (daoFactory == null) {
            daoFactory = new DbFileDaoFactory();
        }
        return daoFactory;
    }
}
