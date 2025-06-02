package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;

public interface TerapiaDao {
    public TerapiaGiornaliera getTerapiaGiornaliera(String codiceFiscale, LocalDate data) throws DaoException;
    public void addTerapiaByRichiesta(Richiesta richiesta) throws DaoException;
    public void addDoseConfezione(DoseConfezione doseConfezione) throws DaoException;
    public void addDosePincipioAttivo(PrincipioAttivo principioAttivo) throws DaoException;

}
