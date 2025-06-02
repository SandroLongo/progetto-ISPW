package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.Dottore;
import it.uniroma2.progettoispw.model.domain.Paziente;
import it.uniroma2.progettoispw.model.domain.Utente;

import java.time.LocalDate;
import java.util.Date;

public interface UtenteDao {
    public Paziente getPaziente(String codice_fiscale) throws DaoException;
    public Dottore getDottore(String codice_fiscale) throws DaoException;
    public void addPaziente(String codice_fiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                            String pass) throws DaoException;
    public void addDottore(String codice_fiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                           String pass, int codice) throws DaoException;
    public Utente login(String codice_fiscale, String password, int is_dottore, int codice_dottore) throws DaoException;
    public void addDottoreAssociato(String codicePaziente,String codiceDottore) throws DaoException;


}
