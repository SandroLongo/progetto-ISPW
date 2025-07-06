package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;

public interface TherapyDao {
    public DailyTherapy getTerapiaGiornaliera(String codiceFiscale, LocalDate data) throws DaoException;
    public void addDoseConfezione(MedicationDoseConfezione doseConfezione, LocalDate giorno, String codiceFiscale) throws DaoException;
    public void addDosePrincipioAttivo(MedicationDosePrincipioAttivo principioAttivo, LocalDate giorno, String codiceFiscale) throws DaoException;

}
