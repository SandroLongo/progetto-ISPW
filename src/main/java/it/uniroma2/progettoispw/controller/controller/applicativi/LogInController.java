package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.*;
import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.Session;
import it.uniroma2.progettoispw.model.domain.SessionManager;
import it.uniroma2.progettoispw.model.domain.User;

public class LogInController implements Controller{
    private final DaoFacade daoFacade = new DaoFacade();

    public AuthenticationBean logIn(UserLogInData userLogInData) throws InvalidFormatException, LogInFailedException {
        if (userLogInData.isComplete()) {
            User user;
            try {
                switch (userLogInData.getRole()){
                    case PATIENT -> user = daoFacade.login(userLogInData.getTaxCode(), userLogInData.getPassword(), 0, 0);
                    case DOCTOR -> { user = daoFacade.login(userLogInData.getTaxCode(), userLogInData.getPassword(), 1,
                                    userLogInData.getDoctorCode());}
                    default -> user = null;
                }
            } catch (DaoException e) {
                throw new LogInFailedException(e.getMessage());
            }
            if (user == null) {
                throw new LogInFailedException("login failed");
            }
            System.out.println(user.getTaxCode());
            Session session = SessionManager.getInstance().setSession(user);
            return new AuthenticationBean(session.getUtente().getName(), session.getUtente().getSurname(), session.getCode(), session.getUtente().isType());
        } else {
            throw new InvalidFormatException("le informazioni non sono state completate");
        }
    }


    public void registerPatient(PatientRegistrationData patientRegistrationData) throws InvalidFormatException {
        if (patientRegistrationData.isComplete()) {
            daoFacade.addPatient(patientRegistrationData.getTaxCode(), patientRegistrationData.getName(),
                            patientRegistrationData.getSurname(), patientRegistrationData.getBirthDate(), patientRegistrationData.getEmail(),
                            patientRegistrationData.getPhoneNumber(), patientRegistrationData.getPassword());
            System.out.println(patientRegistrationData.getTaxCode() + "in logincontroller");
        } else {
            throw new InvalidFormatException("i dati non sono stati compleatati");
        }

    }

    public int registerDoctor(DoctorRegistrationData doctorRegistrationData) throws InvalidFormatException {
        if (doctorRegistrationData.isComplete()) {
            return daoFacade.addDoctor(doctorRegistrationData.getTaxCode(), doctorRegistrationData.getName(),
                    doctorRegistrationData.getSurname(), doctorRegistrationData.getBirthDate(), doctorRegistrationData.getEmail(),
                    doctorRegistrationData.getPhoneNumber(), doctorRegistrationData.getPassword());
        } else {
            throw new InvalidFormatException("i dati non sono stati compleati");
        }
    }


    public void logOut(int codice){
        SessionManager.getInstance().logout(codice);
    }
}
