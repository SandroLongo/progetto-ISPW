package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;

public interface TherapyDao {
    public DailyTherapy getTerapiaGiornaliera(String taxCode, LocalDate date) throws DaoException;
    public void addMedicationDose(MedicationDose doseConfezione, LocalDate date, String taxCode) throws DaoException;
}
