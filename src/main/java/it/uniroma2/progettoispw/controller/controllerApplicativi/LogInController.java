package it.uniroma2.progettoispw.controller.controllerApplicativi;

import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.bean.DottoreRegistrationData;
import it.uniroma2.progettoispw.controller.bean.UtenteLogInData;
import it.uniroma2.progettoispw.controller.bean.UtenteRegistrationData;
import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.Ruolo;
import it.uniroma2.progettoispw.model.domain.Session;
import it.uniroma2.progettoispw.model.domain.SessionManager;
import it.uniroma2.progettoispw.model.domain.Utente;

import java.time.LocalDate;
import java.time.Period;

public class LogInController implements Controller{
    private final DaoFacade daoFacade = new DaoFacade();

    public AuthenticationBean logIn(UtenteLogInData utenteLogInData) throws FomatoInvalidoException, DaoException {
        Utente utente;

        verifyLogInData(utenteLogInData);
        try {
            switch (utenteLogInData.getRuolo()){
                case Paziente -> utente = daoFacade.login(utenteLogInData.getCodiceFiscale(), utenteLogInData.getPassword(), 0, 0);
                case Dottore -> { utente = daoFacade.login(utenteLogInData.getCodiceFiscale(), utenteLogInData.getPassword(), 1,
                                utenteLogInData.getCodiceDottore());}
                default -> utente = null;
            }
        } catch (DaoException e) {
            utente = null;
        }
        Session session = SessionManager.getInstance().setSession(utente);
        return new AuthenticationBean(session.getUtente().getNome(), session.getUtente().getCognome(), session.getCodice(), session.getUtente().isType());
    }

    private void verifyLogInData(UtenteLogInData utenteLogInData) throws FomatoInvalidoException {
        String cf = utenteLogInData.getCodiceFiscale();
        String pwd = utenteLogInData.getPassword();

        verifyCFandPass(cf, pwd);
        if (utenteLogInData.getRuolo() == Ruolo.Dottore) {
            if (utenteLogInData.getCodiceDottore() >= Config.MAX_DOCTOR_CODE_LENGTH
                    || utenteLogInData.getCodiceDottore() <= Config.MAX_DOCTOR_CODE_LENGTH){
                throw new FomatoInvalidoException("codice dottore non valido");
            }
        }
    }

    public void register(UtenteRegistrationData utenteRegistrationData) throws FomatoInvalidoException, DaoException {

        verifyRegistrationData(utenteRegistrationData);
        try {
            switch (utenteRegistrationData.isType()) {
                case Paziente -> {daoFacade.addPaziente(utenteRegistrationData.getCodice_fiscale(), utenteRegistrationData.getNome(),
                        utenteRegistrationData.getCognome(), utenteRegistrationData.getData_nascita(), utenteRegistrationData.getEmail(),
                        utenteRegistrationData.getTelefono(), utenteRegistrationData.getPassword());
                        System.out.println(utenteRegistrationData.getCodice_fiscale() + "in logincontroller");}
                case Dottore -> {DottoreRegistrationData dottoreRegistrationData = (DottoreRegistrationData) utenteRegistrationData;
                    daoFacade.addDottore(dottoreRegistrationData.getCodice_fiscale(), dottoreRegistrationData.getNome(), dottoreRegistrationData.getCognome(),
                            dottoreRegistrationData.getData_nascita(), dottoreRegistrationData.getEmail(), dottoreRegistrationData.getTelefono(),
                            dottoreRegistrationData.getPassword(), dottoreRegistrationData.getCodice());}
                default -> throw new DaoException("Tipologia non valida");
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }

    private void verifyRegistrationData(UtenteRegistrationData utenteRegistrationData) throws FomatoInvalidoException {
        String cf = utenteRegistrationData.getCodice_fiscale();
        String pwd = utenteRegistrationData.getPassword();
        String nome = utenteRegistrationData.getNome();
        String cognome = utenteRegistrationData.getCognome();
        String email = utenteRegistrationData.getEmail();
        String telefono = utenteRegistrationData.getTelefono();
        LocalDate data_nascita = utenteRegistrationData.getData_nascita();

        verifyCFandPass(cf, pwd);
        if (nome == null || nome.length() < Config.MIN_NAME_LENGTH || nome.length() > Config.MAX_NAME_LENGTH) {
            throw new FomatoInvalidoException("nome non valido");
        }
        if (cognome == null || cognome.length() < Config.MIN_SURNAME_LENGTH || cognome.length() > Config.MAX_SURNAME_LENGTH) {
            throw new FomatoInvalidoException("cognome non valido");
        }
        if (email == null || cognome.length() < Config.MIN_EMAIL_LENGTH || email.length() > Config.MAX_EMAIL_LENGTH) {
            throw new FomatoInvalidoException("email non valido");
        }
        if (telefono == null || telefono.length() <= Config.MIN_PHONE_LENGTH || telefono.length() > Config.MAX_PHONE_LENGTH) {
            throw new FomatoInvalidoException("telefono non valido");
        }
        if (data_nascita == null || Period.between(data_nascita, LocalDate.now()).getYears() < Config.MIN_AGE) {
            throw new FomatoInvalidoException("devi avere " + String.valueOf(Config.MIN_AGE)+ "per registrarti nell'applicazione");
        }
    }

    private void verifyCFandPass(String cf, String pwd) {
        if (cf == null || cf.length() < Config.MIN_CF_LENGTH || cf.length() > Config.MAX_CF_LENGTH) {
            throw new FomatoInvalidoException("Codice fiscale non valido");
        }
        if (pwd == null || pwd.length() > Config.MAX_PASSWORD_LENGTH || pwd.length() <= Config.MIN_PASSWORD_LENGTH) {
            throw new FomatoInvalidoException("Password non valida");
        }
    }
}
