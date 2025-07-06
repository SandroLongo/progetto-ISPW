package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.Doctor;
import it.uniroma2.progettoispw.model.domain.Patient;
import it.uniroma2.progettoispw.model.domain.User;

import java.time.LocalDate;

public interface UserDao {
    Patient getPatient(String taxCode) throws DaoException;
    Doctor getDoctor(String taxCode) throws DaoException;
    void addPatient(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                    String pass) throws DaoException;
    int addDoctor(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                  String pass) throws DaoException;
    User login(String codiceFiscale, String password, int isDottore, int codiceDottore) throws DaoException;
    User getUserInformation(String codiceFiscale) throws DaoException;


}
