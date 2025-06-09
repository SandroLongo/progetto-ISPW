package it.uniroma2.progettoispw.model.dao.memorydao;

import java.util.Objects;

public class ChiaveUtente {
    private final String codiceFiscale;
    private final String password;

    public ChiaveUtente(String codiceFiscale, String password) {
        this.codiceFiscale = codiceFiscale;
        this.password = password;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChiaveUtente)) return false;
        ChiaveUtente that = (ChiaveUtente) o;
        return codiceFiscale.equals(that.codiceFiscale) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceFiscale, password);
    }
}
