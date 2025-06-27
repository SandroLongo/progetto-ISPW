package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.controller.bean.RichiestaBean;
import it.uniroma2.progettoispw.model.domain.Paziente;
import it.uniroma2.progettoispw.model.domain.Richiesta;

import java.util.List;

public interface RichiesteDao {
    public List<Richiesta> getRichisteOfPaziente(Paziente paziente) throws DaoException;
    public void deleteRichiesta(Richiesta richiesta) throws DaoException;
    public void addRichiesta(RichiestaBean richiesta) throws DaoException;

}
