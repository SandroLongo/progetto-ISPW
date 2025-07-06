package it.uniroma2.progettoispw.model.domain;

public class Session {
    private User user;
    private int codice;

    public Session(User user, int codice) {
        this.user = user;
        this.codice = codice;
    }
    public User getUtente() {
        return user;
    }

    public int getCodice() {
        return codice;
    }

    public void logout() {
        user.logout();
    }
}
