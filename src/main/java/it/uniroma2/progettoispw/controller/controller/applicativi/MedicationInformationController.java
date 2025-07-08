package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.ActiveIngredientBean;
import it.uniroma2.progettoispw.controller.bean.InvalidFormatException;
import it.uniroma2.progettoispw.controller.bean.MedicinalProductBean;
import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.MedicinalProduct;
import it.uniroma2.progettoispw.model.domain.ActiveIngredient;

import java.util.ArrayList;
import java.util.List;

public class MedicationInformationController implements Controller {
    private final DaoFacade daoFacade = new DaoFacade();

    public List<MedicinalProductBean> getMedicinalProductsByPartialName(String partialName) throws PercistencyFailedException {
        List<String> names = null;
        try {
            names = daoFacade.getMedicinalProductsNamesByPartialName(partialName);
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        }
        List<MedicinalProduct> medicinalProducts = new ArrayList<MedicinalProduct>();
        for (String nome : names) {
            try {
                medicinalProducts.addAll(daoFacade.getMedicinalProductsByName(nome));
            } catch (DaoException e) {
                throw new PercistencyFailedException(e);
            }
        }
        return getMedicinalProductBeans(medicinalProducts);
    }

    public MedicinalProductBean getMedicinalProductById(String id) throws PercistencyFailedException{
        try {
            MedicinalProduct medicinalProduct= daoFacade.getMedicinalProductByID(id);
            if (medicinalProduct != null) {
                return new MedicinalProductBean(daoFacade.getMedicinalProductByID(id));
            } else {
                return null;
            }
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("deve essere un numero");
        }
    }
    public List<String> getMedicinalProductNameByPartialName(String partialName) throws PercistencyFailedException{
        try {
            return daoFacade.getMedicinalProductsNamesByPartialName(partialName);
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        }
    }
    public List<MedicinalProductBean> getMedicinalProductByName(String name) throws PercistencyFailedException{
        try {
            List<MedicinalProduct> medicinalProducts=daoFacade.getMedicinalProductsByName(name);
            return getMedicinalProductBeans(medicinalProducts);
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        }
    }
    public List<String> getActiveIngridientsNameByPartialName(String partialName) throws PercistencyFailedException{
        try {
            return daoFacade.getActiveIngredientNamesByPartialNames(partialName);
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        }
    }
    public ActiveIngredientBean getActiveIngridientByName(String name) throws PercistencyFailedException{
        try {
            ActiveIngredient activeIngredient = daoFacade.getActiveIgredientByName(name);
            if (activeIngredient != null) {
                return new ActiveIngredientBean(activeIngredient);
            } else {
                return null;
            }
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        }
    }
    public List<MedicinalProductBean> getMedicinalProductByActiveIngridient(String activeIngridientId) throws PercistencyFailedException {
        try {
            List<MedicinalProduct> medicinalProducts= daoFacade.getMedicinalProductByActiveIngredientID(activeIngridientId);
            return getMedicinalProductBeans(medicinalProducts);
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        }
    }

    private List<MedicinalProductBean> getMedicinalProductBeans(List<MedicinalProduct> medicinalProducts) {
        if (medicinalProducts != null && !medicinalProducts.isEmpty()) {
            List<MedicinalProductBean> results = new ArrayList<>();
            for (MedicinalProduct medicinalProduct : medicinalProducts) {
                results.add(new MedicinalProductBean(medicinalProduct));
            }
            return results;
        } else {
            return null;
        }
    }

    public ActiveIngredientBean getActiveIngridientById(String id) throws PercistencyFailedException{
        try {
            ActiveIngredient activeIngredient = daoFacade.getActiveIngridientByID(id);
            if (activeIngredient != null) {
                return new ActiveIngredientBean(activeIngredient);
            } else {
                return null;
            }
        } catch (DaoException e) {
            throw new PercistencyFailedException(e);
        }
    }
}
