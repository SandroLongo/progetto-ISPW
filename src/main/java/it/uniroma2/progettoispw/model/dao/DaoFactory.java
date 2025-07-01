package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.dao.dbfiledao.DbFileDaoFactory;
import it.uniroma2.progettoispw.model.dao.dbfiledao.MedicinaliDbDao;
import it.uniroma2.progettoispw.model.dao.memorydao.MemoryDaoFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
            Properties props = new Properties();
            try (InputStream in = DaoFactory.class
                    .getClassLoader()
                    .getResourceAsStream("properties.properties")) {
                if (in == null) {
                    throw new RuntimeException("properties.properties non trovato nel classpath");
                }
                props.load(in);
            } catch (Exception e) {
                throw new RuntimeException("Errore nel caricamento delle proprietà", e);
            }
            String option;
            option = props.getProperty("DAOMODE");
        switch (option) {
                case "DB": daoFactory = new DbFileDaoFactory();
                break;
                case "Memory": daoFactory = new MemoryDaoFactory();
                break;
                default: throw new RuntimeException("DAOMODE unknown");
            }
        }
        return daoFactory;
    }
}
