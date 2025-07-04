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
            try (InputStream input = DaoFactory.class.getClassLoader().getResourceAsStream("properties.properties")) {
                Properties properties = new Properties();
                properties.load(input);
                String option = properties.getProperty("DAOMODE");
                switch (option) {
                    case "DB":
                        String option2 = properties.getProperty("UTENTE_DAOMODE");
                        if (option2 == "DB") {
                            daoFactory = new DbFileDaoFactory(true);
                        } else {
                            daoFactory = new DbFileDaoFactory(false);
                        }
                        break;
                        case "MEMORY":
                        daoFactory = new MemoryDaoFactory();
                        break;
                    default:
                        System.exit(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return daoFactory;
    }
}
