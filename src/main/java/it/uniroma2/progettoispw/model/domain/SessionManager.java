package it.uniroma2.progettoispw.model.domain;

import java.util.Map;

public class SessionManager {
    private static SessionManager instance;
    private Map<Integer, Session> sessions;
    private int numSessions;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
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


}
