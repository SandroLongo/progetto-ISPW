package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.Doctor;
import it.uniroma2.progettoispw.model.domain.Patient;
import it.uniroma2.progettoispw.model.domain.User;

import java.time.LocalDate;

public interface UserDao {
    Patient getPaziente(String taxCode) throws DaoException;
    Doctor getDottore(String taxCode) throws DaoException;
    void addPaziente(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                     String pass) throws DaoException;
    int addDottore(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                   String pass) throws DaoException;
    User login(String codiceFiscale, String password, int isDottore, int codiceDottore) throws DaoException;
    User getInfoUtente(String codiceFiscale) throws DaoException;


}
