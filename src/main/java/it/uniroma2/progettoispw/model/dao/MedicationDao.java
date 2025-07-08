package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.MedicinalProduct;
import it.uniroma2.progettoispw.model.domain.ActiveIngredient;

import java.util.List;

public interface MedicationDao {
    public MedicinalProduct getMedicinalProductByID(String id) throws DaoException;
    public List<String> getMPNameByPartialName(String name) throws DaoException;
    public List<MedicinalProduct> getMPbyName(String name) throws DaoException;
    public List<String> getAINamesByPartialName(String name) throws DaoException;
    public ActiveIngredient getAIByName(String name) throws DaoException;
    public List<MedicinalProduct> getMPByAIID(String id) throws DaoException;
    public ActiveIngredient getAIByID(String id) throws DaoException;

}
