package it.uniroma2.progettoispw.model.dao;

public abstract class DaoFactory {
    public abstract RichiesteDao getRichiesteDao();
    public abstract UtenteDao getUtenteDao();
    public abstract TerapiaDao getTerapiaDao();

    public static DaoFactory getDaoFactory() {
        return null;
    }
}
