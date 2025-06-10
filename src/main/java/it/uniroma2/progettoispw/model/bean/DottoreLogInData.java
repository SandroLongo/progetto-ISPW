package it.uniroma2.progettoispw.model.bean;

import it.uniroma2.progettoispw.model.domain.Ruolo;

public class DottoreLogInData extends UtenteLogInData{
    private int codice;

    public DottoreLogInData(String codiceFiscale, String password, int codice) {
        super(codiceFiscale, password);
        this.codice = codice;
    }
    public int getCodice() {
        return codice;
    }
    public void setCodice(int codice) {
        this.codice = codice;
    }

    @Override
    public Ruolo isType() {
        return Ruolo.Dottore;
    }
}
