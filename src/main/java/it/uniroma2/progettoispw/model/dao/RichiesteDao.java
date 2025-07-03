package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.Paziente;
import it.uniroma2.progettoispw.model.domain.Richiesta;

import java.util.List;

public interface RichiesteDao {
    public List<Richiesta> getRichisteOfPaziente(Paziente paziente) throws DaoException;
    public void deleteRichiesta(int id) throws DaoException;
    public int addRichiesta(Richiesta richiesta) throws DaoException;

}
