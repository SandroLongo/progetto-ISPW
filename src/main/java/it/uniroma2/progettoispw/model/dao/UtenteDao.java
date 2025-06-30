package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.Dottore;
import it.uniroma2.progettoispw.model.domain.Paziente;
import it.uniroma2.progettoispw.model.domain.Utente;

import java.time.LocalDate;

public interface UtenteDao {
    Paziente getPaziente(String codice_fiscale) throws DaoException;
    Dottore getDottore(String codice_fiscale) throws DaoException;
    void addPaziente(String codice_fiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                            String pass) throws DaoException;
    int addDottore(String codice_fiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                           String pass) throws DaoException;
    Utente login(String codice_fiscale, String password, int is_dottore, int codice_dottore) throws DaoException;
    void addDottoreAssociato(String codicePaziente,String codiceDottore) throws DaoException;
    Utente getInfoUtente(String codice_fiscale) throws DaoException;


}
