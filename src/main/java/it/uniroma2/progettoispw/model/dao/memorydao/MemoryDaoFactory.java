package it.uniroma2.progettoispw.model.dao.memorydao;

import it.uniroma2.progettoispw.model.dao.DaoFactory;
import it.uniroma2.progettoispw.model.dao.RichiesteDao;
import it.uniroma2.progettoispw.model.dao.TerapiaDao;
import it.uniroma2.progettoispw.model.dao.UtenteDao;

public class MemoryDaoFactory extends DaoFactory {
    @Override
    public RichiesteDao getRichiesteDao() {
        return null;
    }

    @Override
    public UtenteDao getUtenteDao() {
        return null;
    }

    @Override
    public TerapiaDao getTerapiaDao() {
        return null;
    }
}
