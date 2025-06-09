package it.uniroma2.progettoispw.model.dao.memorydao;

import java.util.Objects;

public class ChiaveDottore {
    private final String codiceFiscale;
    private final String password;
    private final int codiceDottore;

    public ChiaveDottore(String codiceFiscale, String password, int codiceDottore) {
        this.codiceFiscale = codiceFiscale;
        this.password = password;
        this.codiceDottore = codiceDottore;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getPassword() {
        return password;
    }

    public int getCodiceDottore() {
        return codiceDottore;
    }

    // ✅ Equals completo
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChiaveDottore)) return false;
        ChiaveDottore that = (ChiaveDottore) o;
        return Objects.equals(codiceFiscale, that.codiceFiscale) &&
                Objects.equals(password, that.password) &&
                Objects.equals(codiceDottore, that.codiceDottore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceFiscale, password, codiceDottore);
    }
}
