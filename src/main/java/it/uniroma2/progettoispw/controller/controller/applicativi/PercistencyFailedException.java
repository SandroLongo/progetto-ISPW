package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.model.dao.DaoException;

public class PercistencyFailedException extends RuntimeException {
    private String daoError;
    public PercistencyFailedException(String message) {
        super(message);
    }
    public PercistencyFailedException(DaoException daoException) {
        super("errore nel trovare i dati, riprovare piu tardi", daoException);
        this.daoError = daoException.getMessage();
    }

    public String getDaoError() {
        if (daoError == null) {
            return "not specified";
        }
        return daoError;
    }
}
