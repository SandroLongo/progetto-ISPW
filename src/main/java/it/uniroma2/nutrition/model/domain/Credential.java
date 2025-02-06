package it.uniroma2.nutrition.model.domain;

public class Credential {
    String username;
    String password;

    public Credential(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return username + " " + password;
    }
}
