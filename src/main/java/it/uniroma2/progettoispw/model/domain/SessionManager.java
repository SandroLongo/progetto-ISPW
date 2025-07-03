package it.uniroma2.progettoispw.model.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SessionManager {
    private static SessionManager instance;
    private Map<Integer, Session> sessions = new HashMap<Integer, Session>();
    private int numSessions;

    private SessionManager() {
        numSessions = 0;
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public List<Session> getOpenSessionsByCF(String cf) {
        return sessions.values().stream()
                .filter(session -> session.getUtente() != null &&
                        cf.equals(session.getUtente().getCodiceFiscale()))
                .toList();
    }

    public Session getSession(int code) {
        return sessions.get(code);
    }

    public Session setSession(Utente currentUtente) {
        numSessions = numSessions+1;
        Session session = new Session(currentUtente, numSessions);
        sessions.put(numSessions, session);
        return session;
    }

    public void deleteRichiesta(Richiesta richiesta) {
        List<Session> sessions = getOpenSessionsByCF(richiesta.getRicevente().getCodiceFiscale());
        for (Session s : sessions){
            Utente utente = s.getUtente();
            if (Objects.requireNonNull(utente.isType()) == Ruolo.Paziente) {
                RichiestePendenti richiestePendenti = ((Paziente) utente).getRichiestePendenti();
                richiestePendenti.deleteRichiesta(richiesta.getId());
            }
        }
    }

    public void addRichiesta(Richiesta richiesta) {
        List<Session> sessions = SessionManager.getInstance().getOpenSessionsByCF(richiesta.getRicevente().getCodiceFiscale());
        for (Session session : sessions){
            Utente utente = session.getUtente();
            if (Objects.requireNonNull(utente.isType()) == Ruolo.Paziente) {
                RichiestePendenti richiestePendenti = ((Paziente) utente).getRichiestePendenti();
                richiestePendenti.addRichiesta(richiesta);
            }
        }
    }

    public void logout(int codice){
        sessions.remove(codice);
    }

    public boolean esiste(int codice){
        return sessions.containsKey(codice);
    }


}
