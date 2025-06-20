package it.uniroma2.progettoispw.model.dao.memorydao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.RichiesteDao;
import it.uniroma2.progettoispw.model.domain.Paziente;
import it.uniroma2.progettoispw.model.domain.Richiesta;

import java.util.*;

public class RichiesteMemoryDao extends MemoryDao implements RichiesteDao {
    private static RichiesteMemoryDao instance;
    private Map<String, Map<Integer,  Richiesta>> richieste = new HashMap<>();

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
    public void deleteRichiesta(Richiesta richiesta) throws DaoException {
        for (Map<Integer, Richiesta> mappaRichieste : richieste.values()) {
            if (mappaRichieste.containsKey(richiesta.getId())) {
                mappaRichieste.remove(richiesta.getId());
            }
        }
    }

    @Override
    public void addRichiesta(Richiesta richiesta) throws DaoException {
        richieste.computeIfAbsent(richiesta.getRicevente().getCodiceFiscale(), k -> new HashMap<>())
                .put(richiesta.getId(), richiesta);
    }
}
