package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.dao.dbfiledao.DbFileDaoFactory;
import it.uniroma2.progettoispw.model.dao.dbfiledao.MedicationDbDao;
import it.uniroma2.progettoispw.model.dao.memorydao.MemoryDaoFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;
    public abstract PrescriptionBundleDao getPrescriptionBundleDao();
    public abstract UserDao getUserDao();
    public abstract TherapyDao getTherapyDao();

    public MedicationDao getMedicationDao() {
        return new MedicationDbDao();
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
                        if (Objects.equals(option2, "DB")) {
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
                System.exit(1);
            }
        }
        return daoFactory;
    }
}
