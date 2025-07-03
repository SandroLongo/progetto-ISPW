package it.uniroma2.progettoispw.model.dao.memorydao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.RichiesteDao;
import it.uniroma2.progettoispw.model.domain.Paziente;
import it.uniroma2.progettoispw.model.domain.Richiesta;

import java.util.*;

public class RichiesteMemoryDao extends MemoryDao implements RichiesteDao {
    private static RichiesteMemoryDao instance;
    private Map<String, Map<Integer,  Richiesta>> richieste = new HashMap<>();
    private int numRichieste;

    public RichiesteMemoryDao() {
        super();
    }

    public static RichiesteMemoryDao getInstance() {
        if (instance == null) {
            instance = new RichiesteMemoryDao();
        }
        return instance;
    }


    @Override
    public List<Richiesta> getRichisteOfPaziente(Paziente paziente) throws DaoException {
        return new ArrayList<>(richieste.getOrDefault(paziente.getCodiceFiscale(), Collections.emptyMap()).values());
    }

    @Override
    public void deleteRichiesta(int id) throws DaoException {
        for (Map<Integer, Richiesta> mappaRichieste : richieste.values()) {
            if (mappaRichieste.containsKey(id)) {
                mappaRichieste.remove(id);
            } else {
                throw new DaoException("Richiesta non esistente");
            }
        }
    }

    @Override
    public int addRichiesta(Richiesta richiesta) throws DaoException {
        numRichieste++;
        richieste.computeIfAbsent(richiesta.getRicevente().getCodiceFiscale(), k -> new HashMap<>())
                .put(numRichieste, richiesta);
        return numRichieste;
    }
}
