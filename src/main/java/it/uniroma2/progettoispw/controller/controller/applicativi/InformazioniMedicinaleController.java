package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.ListNomiPABean;
import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.Confezione;
import it.uniroma2.progettoispw.model.domain.PrincipioAttivo;

import java.util.ArrayList;
import java.util.List;

public class InformazioniMedicinaleController implements Controller {
    private final DaoFacade daoFacade = new DaoFacade();


    public ListNomiPABean getPABynomeParziale(String nomeParziale) {
        return new ListNomiPABean(daoFacade.getNomiPrincipioAttivoByNomeParziale(nomeParziale));
    }

    public List<Confezione> getConfezioniByNomeParziale(String nomeParziale) {
        List<String> nomi = daoFacade.getNomiConfezioniByNomeParziale(nomeParziale);
        List<Confezione> confezioni = new ArrayList<Confezione>();
        for (String nome : nomi) {
            confezioni.addAll(daoFacade.getConfezioniByNome(nome));
        }
        return confezioni;
    }

    public Confezione getConfezioneByCodiceAic(int codiceAic) throws DaoException{
        return daoFacade.getConfezioneByCodiceAic(codiceAic);
    }
    public List<String> getNomiConfezioniByNomeParziale(String nome) throws DaoException{
        return daoFacade.getNomiConfezioniByNomeParziale(nome);
    }
    public List<Confezione> getConfezioniByNome(String nome) throws DaoException{
        return daoFacade.getConfezioniByNome(nome);
    }
    public List<String> getNomiPrincipioAttivoByNomeParziale(String nome) throws DaoException{
        return daoFacade.getNomiPrincipioAttivoByNomeParziale(nome);
    }
    public PrincipioAttivo getPrincipioAttvoByNome(String nome) throws DaoException{
        return daoFacade.getPrincipioAttvoByNome(nome);
    }
    public List<Confezione> getConfezioniByCodiceAtc(String codiceAtc) throws DaoException{
        return daoFacade.getConfezioniByCodiceAtc(codiceAtc);
    }
    public PrincipioAttivo getPrincipioAttvoByCodiceAtc(String codiceAtc) throws DaoException{
        return daoFacade.getPrincipioAttvoByCodiceAtc(codiceAtc);
    }
}
