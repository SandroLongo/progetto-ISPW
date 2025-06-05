package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;

public interface TerapiaDao {
    public TerapiaGiornaliera getTerapiaGiornaliera(String codiceFiscale, LocalDate data) throws DaoException;
    public void addDoseConfezione(DoseConfezione doseConfezione, LocalDate giorno, String codiceFiscale) throws DaoException;
    public void addDosePrincipioAttivo(DosePrincipioAttivo principioAttivo, LocalDate giorno, String codiceFiscale) throws DaoException;

}
