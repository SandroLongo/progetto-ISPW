package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.ListActiveIngridientName;
import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.MedicinalProduct;
import it.uniroma2.progettoispw.model.domain.ActiveIngredient;

import java.util.ArrayList;
import java.util.List;

public class MedicationInformationController implements Controller {
    private final DaoFacade daoFacade = new DaoFacade();

    public List<MedicinalProduct> getMedicinalProductsByPartialName(String partialName) throws PercistencyFailedException {
        List<String> names = null;
        try {
            names = daoFacade.getNomiConfezioniByNomeParziale(partialName);
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        }
        List<MedicinalProduct> medicinalProducts = new ArrayList<MedicinalProduct>();
        for (String nome : names) {
            try {
                medicinalProducts.addAll(daoFacade.getConfezioniByNome(nome));
            } catch (DaoException e) {
                throw new PercistencyFailedException(e);
            }
        }
        return medicinalProducts;
    }

    public MedicinalProduct getMedicinalProductById(int id) throws PercistencyFailedException{
        try {
            return daoFacade.getConfezioneByCodiceAic(id);
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        }
    }
    public List<String> getMedicinalProductNameByPartialName(String partialName) throws PercistencyFailedException{
        try {
            return daoFacade.getNomiConfezioniByNomeParziale(partialName);
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        }
    }
    public List<MedicinalProduct> getMedicinalProductByName(String name) throws PercistencyFailedException{
        try {
            return daoFacade.getConfezioniByNome(name);
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        }
    }
    public List<String> getActiveIngridientsNameByPartialName(String partialName) throws PercistencyFailedException{
        try {
            return daoFacade.getNomiPrincipioAttivoByNomeParziale(partialName);
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        }
    }
    public ActiveIngredient getActiveIngridientByName(String name) throws PercistencyFailedException{
        try {
            return daoFacade.getPrincipioAttvoByNome(name);
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        }
    }
    public List<MedicinalProduct> getMedicinalProductByActiveIngridient(String ActiveIngridientId) throws PercistencyFailedException {
        try {
            return daoFacade.getConfezioniByCodiceAtc(ActiveIngridientId);
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        }
    }
    public ActiveIngredient getActiveIngridientById(String id) throws PercistencyFailedException{
        try {
            return daoFacade.getPrincipioAttvoByCodiceAtc(id);
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        }
    }
}
