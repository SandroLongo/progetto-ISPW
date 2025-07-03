package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.Dottore;
import it.uniroma2.progettoispw.model.domain.Paziente;
import it.uniroma2.progettoispw.model.domain.Utente;

import java.time.LocalDate;

public interface UtenteDao {
    Paziente getPaziente(String codiceFiscale) throws DaoException;
    Dottore getDottore(String codiceFiscale) throws DaoException;
    void addPaziente(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                     String pass) throws DaoException;
    int addDottore(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                   String pass) throws DaoException;
    Utente login(String codiceFiscale, String password, int isDottore, int codiceDottore) throws DaoException;
    void addDottoreAssociato(String codicePaziente,String codiceDottore) throws DaoException;
    Utente getInfoUtente(String codiceFiscale) throws DaoException;


}
