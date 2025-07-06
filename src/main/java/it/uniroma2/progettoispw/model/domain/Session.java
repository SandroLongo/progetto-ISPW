package it.uniroma2.progettoispw.model.domain;

public class Session {
    private User user;
    private int code;

    public Session(User user, int code) {
        this.user = user;
        this.code = code;
    }
    public User getUtente() {
        return user;
    }

    public int getCode() {
        return code;
    }

    public void logout() {
        user.logout();
    }
}
