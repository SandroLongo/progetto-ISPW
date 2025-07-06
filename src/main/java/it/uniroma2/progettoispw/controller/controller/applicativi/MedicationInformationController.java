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


    public ListActiveIngridientName getActiveIngridientsByPartialName(String partialName) {
        return new ListActiveIngridientName(daoFacade.getNomiPrincipioAttivoByNomeParziale(partialName));
    }

    public List<MedicinalProduct> getMedicinalProductsByPartialName(String partialName) {
        List<String> names = daoFacade.getNomiConfezioniByNomeParziale(partialName);
        List<MedicinalProduct> medicinalProducts = new ArrayList<MedicinalProduct>();
        for (String nome : names) {
            medicinalProducts.addAll(daoFacade.getConfezioniByNome(nome));
        }
        return medicinalProducts;
    }

    public MedicinalProduct getMedicinalProductById(int id) throws DaoException{
        return daoFacade.getConfezioneByCodiceAic(id);
    }
    public List<String> getMedicinalProductNameByPartialName(String partialName) throws DaoException{
        return daoFacade.getNomiConfezioniByNomeParziale(partialName);
    }
    public List<MedicinalProduct> getMedicinalProductByName(String name) throws DaoException{
        return daoFacade.getConfezioniByNome(name);
    }
    public List<String> getActiveIngridientsNameByPartialName(String partialName) throws DaoException{
        return daoFacade.getNomiPrincipioAttivoByNomeParziale(partialName);
    }
    public ActiveIngredient getActiveIngridientByName(String name) throws DaoException{
        return daoFacade.getPrincipioAttvoByNome(name);
    }
    public List<MedicinalProduct> getMedicinalProductByActiveIngridient(String ActiveIngridientId) throws DaoException{
        return daoFacade.getConfezioniByCodiceAtc(ActiveIngridientId);
    }
    public ActiveIngredient getActiveIngridientById(String id) throws DaoException{
        return daoFacade.getPrincipioAttvoByCodiceAtc(id);
    }
}
