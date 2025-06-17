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
            String option;
            System.out.println("risorse/properties.properties");
            try (InputStream inputstream = new FileInputStream("risorse/properties.properties")){
                Properties DBproperties = new Properties();
                DBproperties.load(inputstream);
                option = DBproperties.getProperty("DAOMODE");
            }  catch (IOException e) {
                throw new RuntimeException(e);
            }
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
