package it.uniroma2.progettoispw.model.dao.dbfiledao;

import it.uniroma2.progettoispw.model.dao.*;

public class DbFileDaoFactory extends DaoFactory {
    @Override
    public RichiesteDao getRichiesteDao() {
        return new RichiesteDbDao();
    }

    @Override
    public UtenteDao getUtenteDao() {
        return new UtenteDbDao();
    }

    @Override
    public TerapiaDao getTerapiaDao() {
        return new TerapiaDbDao();
    }
}
