package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.MedicinalProduct;
import it.uniroma2.progettoispw.model.domain.ActiveIngredient;

import java.util.List;

public interface MedicationDao {
    public MedicinalProduct getConfezioneByCodiceAic(int codiceAic) throws DaoException;
    public List<String> getNomiConfezioniByNomeParziale(String nome) throws DaoException;
    public List<MedicinalProduct> getConfezioniByNome(String nome) throws DaoException;
    public List<String> getMedicinalProductNameByPartialName(String nome) throws DaoException;
    public ActiveIngredient getPrincipioAttvoByNome(String nome) throws DaoException;
    public List<MedicinalProduct> getConfezioniByCodiceAtc(String codiceAtc) throws DaoException;
    public ActiveIngredient getPrincipioAttvoByCodiceAtc(String codiceAtc) throws DaoException;

}
