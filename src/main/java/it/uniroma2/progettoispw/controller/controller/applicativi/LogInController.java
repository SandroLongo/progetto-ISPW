package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.Session;
import it.uniroma2.progettoispw.model.domain.SessionManager;
import it.uniroma2.progettoispw.model.domain.User;

public class LogInController implements Controller{
    private final DaoFacade daoFacade = new DaoFacade();

    public AuthenticationBean logIn(UserLogInData userLogInData) throws FomatoInvalidoException, DaoException {
        if (userLogInData.isComplete()) {
            User user;
            try {
                switch (userLogInData.getRuolo()){
                    case PAZIENTE -> user = daoFacade.login(userLogInData.getCodiceFiscale(), userLogInData.getPassword(), 0, 0);
                    case DOTTORE -> { user = daoFacade.login(userLogInData.getCodiceFiscale(), userLogInData.getPassword(), 1,
                                    userLogInData.getCodiceDottore());}
                    default -> user = null;
                }
            } catch (DaoException e) {
                e.printStackTrace();
                throw new LogInFailedException(e.getMessage());
            }
            System.out.println(user.getCodiceFiscale());
            Session session = SessionManager.getInstance().setSession(user);
            return new AuthenticationBean(session.getUtente().getNome(), session.getUtente().getCognome(), session.getCodice(), session.getUtente().isType());
        } else {
            throw new FomatoInvalidoException("le informazioni non sono state completate");
        }
    }


    public void registerPaziente(PatientRegistrationData utenteRegistrationData) throws FomatoInvalidoException {
        if (utenteRegistrationData.isComplete()) {
            daoFacade.addPaziente(utenteRegistrationData.getCodiceFiscale(), utenteRegistrationData.getNome(),
                            utenteRegistrationData.getCognome(), utenteRegistrationData.getDataNascita(), utenteRegistrationData.getEmail(),
                            utenteRegistrationData.getTelefono(), utenteRegistrationData.getPassword());
            System.out.println(utenteRegistrationData.getCodiceFiscale() + "in logincontroller");
        } else {
            throw new FomatoInvalidoException("i dati non sono stati compleatati");
        }

    }

    public int registerDottore(DoctorRegistrationData utenteRegistrationData) throws FomatoInvalidoException{
        if (utenteRegistrationData.isComplete()) {
            return daoFacade.addDottore(utenteRegistrationData.getCodiceFiscale(), utenteRegistrationData.getNome(),
                    utenteRegistrationData.getCognome(), utenteRegistrationData.getDataNascita(), utenteRegistrationData.getEmail(),
                    utenteRegistrationData.getTelefono(), utenteRegistrationData.getPassword());
        } else {
            throw new FomatoInvalidoException("i dati non sono stati compleati");
        }
    }


    public void logOut(int codice){
        SessionManager.getInstance().logout(codice);
    }
}
