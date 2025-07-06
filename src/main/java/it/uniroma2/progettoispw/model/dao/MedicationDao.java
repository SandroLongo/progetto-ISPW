package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.MedicinalProduct;
import it.uniroma2.progettoispw.model.domain.ActiveIngridient;

import java.util.List;

public interface MedicationDao {
    public MedicinalProduct getConfezioneByCodiceAic(int codiceAic) throws DaoException;
    public List<String> getNomiConfezioniByNomeParziale(String nome) throws DaoException;
    public List<MedicinalProduct> getConfezioniByNome(String nome) throws DaoException;
    public List<String> getNomiPrincipioAttivoByNomeParziale(String nome) throws DaoException;
    public ActiveIngridient getPrincipioAttvoByNome(String nome) throws DaoException;
    public List<MedicinalProduct> getConfezioniByCodiceAtc(String codiceAtc) throws DaoException;
    public ActiveIngridient getPrincipioAttvoByCodiceAtc(String codiceAtc) throws DaoException;

}
