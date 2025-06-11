package it.uniroma2.progettoispw.controller.controllerApplicativi;

import it.uniroma2.progettoispw.model.bean.DottoreLogInData;
import it.uniroma2.progettoispw.model.bean.DottoreRegistrationData;
import it.uniroma2.progettoispw.model.bean.UtenteLogInData;
import it.uniroma2.progettoispw.model.bean.UtenteRegistrationData;
import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.Utente;

public class LogInController {
    private DaoFacade daoFacade = new DaoFacade();

    public Utente validate(UtenteLogInData utenteLogInData) {
        Utente utente;
        try {
            switch (utenteLogInData.isType()){
                case Paziente -> utente = daoFacade.login(utenteLogInData.getCodiceFiscale(), utenteLogInData.getPassword(), 0, 0);
                case Dottore -> { DottoreLogInData dottoreData = (DottoreLogInData) utenteLogInData;
                        utente = daoFacade.login(dottoreData.getCodiceFiscale(), dottoreData.getPassword(), 1,
                                dottoreData.getCodice());}
                default -> utente = null;
            }
        } catch (DaoException e) {
            utente = null;
        }
        return utente;
    }

    public boolean register(UtenteRegistrationData utenteRegistrationData) {
        try {
            switch (utenteRegistrationData.isType()) {
                case Paziente -> {daoFacade.addPaziente(utenteRegistrationData.getCodice_fiscale(), utenteRegistrationData.getNome(),
                        utenteRegistrationData.getCognome(), utenteRegistrationData.getData_nascita(), utenteRegistrationData.getEmail(),
                        utenteRegistrationData.getTelefono(), utenteRegistrationData.getPassword());
                            return true;}
                case Dottore -> {DottoreRegistrationData dottoreRegistrationData = (DottoreRegistrationData) utenteRegistrationData;
                    daoFacade.addDottore(dottoreRegistrationData.getCodice_fiscale(), dottoreRegistrationData.getNome(), dottoreRegistrationData.getCognome(),
                            dottoreRegistrationData.getData_nascita(), dottoreRegistrationData.getEmail(), dottoreRegistrationData.getTelefono(),
                            dottoreRegistrationData.getPassword(), dottoreRegistrationData.getCodice());
                            return true;}
                default -> throw new DaoException("Tipologia non valida");
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }
}
