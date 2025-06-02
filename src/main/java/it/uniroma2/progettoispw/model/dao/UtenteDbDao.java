package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.Dottore;
import it.uniroma2.progettoispw.model.domain.Paziente;
import it.uniroma2.progettoispw.model.domain.Utente;

import java.util.Date;

public class UtenteDbDao extends DbDao implements UtenteDao {
    @Override
    public Paziente getPaziente(String codice_fiscale, String password) throws DaoException {
        return null;
    }

    @Override
    public Dottore getDottore(String codice_fiscale, String password, int codice) throws DaoException {
        return null;
    }

    @Override
    public void addPaziente(String codice_fiscale, String nome, String cognome, Date nascita, String email, String telefono, String pass) throws DaoException {

    }

    @Override
    public void addDottore(String codice_fiscale, String nome, String cognome, Date nascita, String email, String telefono, String pass, int codice) throws DaoException {

    }

    @Override
    public Utente login(String codice_fiscale, String password, int is_dottore, int codice_dottore) throws DaoException {
        return null;
    }
}
