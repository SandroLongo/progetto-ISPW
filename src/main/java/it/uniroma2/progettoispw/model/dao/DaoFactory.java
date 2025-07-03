package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.dao.dbfiledao.DbFileDaoFactory;
import it.uniroma2.progettoispw.model.dao.dbfiledao.MedicinaliDbDao;
import it.uniroma2.progettoispw.model.dao.memorydao.MemoryDaoFactory;

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
                props.load(in);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            String option;
            option = props.getProperty("DAOMODE");
        switch (option) {
                case "DB": daoFactory = new DbFileDaoFactory();
                break;
                case "Memory": daoFactory = new MemoryDaoFactory();
                break;
                default: System.exit(1);
            }
        }
        return daoFactory;
    }
}
