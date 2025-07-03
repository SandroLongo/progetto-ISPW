package it.uniroma2.progettoispw.model.domain;

public class Session {
    private Utente utente;
    private int codice;

    public Session(Utente utente, int codice) {
        this.utente = utente;
        this.codice = codice;
    }
    public Utente getUtente() {
        return utente;
    }

    public int getCodice() {
        return codice;
    }

    public void logout() {
        utente.logout();
    }
}
