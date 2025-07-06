package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.ListNomiPABean;
import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.MedicinalProduct;
import it.uniroma2.progettoispw.model.domain.ActiveIngridient;

import java.util.ArrayList;
import java.util.List;

public class MedicationInformationController implements Controller {
    private final DaoFacade daoFacade = new DaoFacade();


    public ListNomiPABean getPABynomeParziale(String nomeParziale) {
        return new ListNomiPABean(daoFacade.getNomiPrincipioAttivoByNomeParziale(nomeParziale));
    }

    public List<MedicinalProduct> getConfezioniByNomeParziale(String nomeParziale) {
        List<String> nomi = daoFacade.getNomiConfezioniByNomeParziale(nomeParziale);
        List<MedicinalProduct> confezioni = new ArrayList<MedicinalProduct>();
        for (String nome : nomi) {
            confezioni.addAll(daoFacade.getConfezioniByNome(nome));
        }
        return confezioni;
    }

    public MedicinalProduct getConfezioneByCodiceAic(int codiceAic) throws DaoException{
        return daoFacade.getConfezioneByCodiceAic(codiceAic);
    }
    public List<String> getNomiConfezioniByNomeParziale(String nome) throws DaoException{
        return daoFacade.getNomiConfezioniByNomeParziale(nome);
    }
    public List<MedicinalProduct> getConfezioniByNome(String nome) throws DaoException{
        return daoFacade.getConfezioniByNome(nome);
    }
    public List<String> getNomiPrincipioAttivoByNomeParziale(String nome) throws DaoException{
        return daoFacade.getNomiPrincipioAttivoByNomeParziale(nome);
    }
    public ActiveIngridient getPrincipioAttvoByNome(String nome) throws DaoException{
        return daoFacade.getPrincipioAttvoByNome(nome);
    }
    public List<MedicinalProduct> getConfezioniByCodiceAtc(String codiceAtc) throws DaoException{
        return daoFacade.getConfezioniByCodiceAtc(codiceAtc);
    }
    public ActiveIngridient getPrincipioAttvoByCodiceAtc(String codiceAtc) throws DaoException{
        return daoFacade.getPrincipioAttvoByCodiceAtc(codiceAtc);
    }
}
