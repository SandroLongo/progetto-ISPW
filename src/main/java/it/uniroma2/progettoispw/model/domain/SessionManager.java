package it.uniroma2.progettoispw.model.domain;

public class SessionManager {
    private static SessionManager instance;
    private Utente currentUtente;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public Utente getCurrentUtente() {
        return currentUtente;
    }
    public void setCurrentUtente(Utente currentUtente) {
        this.currentUtente = currentUtente;
    }


}
