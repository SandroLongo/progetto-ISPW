package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.Confezione;
import it.uniroma2.progettoispw.model.domain.PrincipioAttivo;

import java.util.List;

public interface MedicinaliDao {
    public Confezione getConfezioneByCodiceAic(int codice_aic) throws DaoException;
    public List<String> getNomiConfezioniByNomeParziale(String nome) throws DaoException;
    public List<Confezione> getConfezioniByNome(String nome) throws DaoException;
    public List<String> getNomiPrincipioAttivoByNomeParziale(String nome) throws DaoException;
    public PrincipioAttivo getPrincipioAttvoByNome(String nome) throws DaoException;
    public List<Confezione> getConfezioniByCodiceAtc(String codice_atc) throws DaoException;
    public PrincipioAttivo getPrincipioAttvoByCodiceAtc(String codice_atc) throws DaoException;

}
