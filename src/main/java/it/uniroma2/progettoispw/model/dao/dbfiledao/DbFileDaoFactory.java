package it.uniroma2.progettoispw.model.dao.dbfiledao;

import it.uniroma2.progettoispw.model.dao.*;
import it.uniroma2.progettoispw.model.dao.filedao.UtenteFileDao;

public class DbFileDaoFactory extends DaoFactory {
    private boolean utenteDaoIsDb;

    public DbFileDaoFactory(boolean utenteDaoIsDb) {
        this.utenteDaoIsDb = utenteDaoIsDb;
    }
    @Override
    public RichiesteDao getRichiesteDao() {
        return new RichiesteDbDao();
    }

    @Override
    public UtenteDao getUtenteDao() {
        if (utenteDaoIsDb) {
            return new UtenteDbDao();
        } else {
            return new UtenteFileDao();
        }
    }

    @Override
    public TerapiaDao getTerapiaDao() {
        return new TerapiaDbDao();
    }
}
