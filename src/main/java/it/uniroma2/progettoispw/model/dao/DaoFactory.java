package it.uniroma2.progettoispw.model.dao;

public interface DaoFactory {
    public RichiesteDao getRichiesteDao();
    public MedicinaliDao getConfezioniDao();
    public UtenteDao getUtenteDao();
    public TerapiaDao getTerapiaDao();
}
