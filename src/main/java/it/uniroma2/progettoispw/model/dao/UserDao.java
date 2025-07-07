package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.Doctor;
import it.uniroma2.progettoispw.model.domain.Patient;
import it.uniroma2.progettoispw.model.domain.User;

import java.time.LocalDate;

public interface UserDao {
    Patient getPatient(String taxCode) throws DaoException;
    Doctor getDoctor(String taxCode) throws DaoException;
    void addPatient(String taxCode, String name, String surname, LocalDate birthDate, String email, String phoneNumber,
                    String pass) throws DaoException;
    int addDoctor(String taxCode, String name, String surname, LocalDate birthDate, String email, String phoneNumber,
                  String pass) throws DaoException;
    User login(String taxCode, String password, int isDoctor, int doctorCode) throws DaoException;
    User getUserInformation(String taxCode) throws DaoException;


}
