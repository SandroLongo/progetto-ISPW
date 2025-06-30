package it.uniroma2.progettoispw.model.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());
    }

    public Session getSession(int code) {
        return sessions.get(code);
    }

    public Session setSession(Utente currentUtente) {
        numSessions = numSessions+1;
        Session session = new Session(currentUtente, numSessions);
        System.out.println(numSessions);
        sessions.put(numSessions, session);
        return session;
    }


}
