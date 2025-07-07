package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SessionManager {
    private static SessionManager instance;
    private Map<Integer, Session> sessions = new HashMap<Integer, Session>();
    private int numSessions;

    private SessionManager() {
        numSessions = 0;
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public List<Session> getOpenSessionsByCF(String taxCode) {
        return sessions.values().stream()
                .filter(session -> session.getUtente() != null &&
                        taxCode.equals(session.getUtente().getTaxCode()))
                .toList();
    }

    public Session getSession(int code) {
        return sessions.get(code);
    }

    public Session setSession(User currentUser) {
        numSessions = numSessions+1;
        Session session = new Session(currentUser, numSessions);
        sessions.put(numSessions, session);
        System.out.println("aperta sessione" + numSessions);
        return session;
    }

    public void deleteRichiesta(SentPrescriptionBundle sentPrescriptionBundle) {
        List<Session> openSessionsByCF = getOpenSessionsByCF(sentPrescriptionBundle.getRicevente().getTaxCode());
        for (Session s : openSessionsByCF){
            User user = s.getUtente();
            if (Objects.requireNonNull(user.isType()) == Role.PATIENT) {
                PendentBundles pendentBundles = ((Patient) user).getRichiestePendenti();
                pendentBundles.deleteBundle(sentPrescriptionBundle.getId());
            }
        }
    }

    public void addRichiesta(SentPrescriptionBundle sentPrescriptionBundle) {
        List<Session> attive = SessionManager.getInstance().getOpenSessionsByCF(sentPrescriptionBundle.getRicevente().getTaxCode());
        for (Session session : attive){
            User user = session.getUtente();
            if (Objects.requireNonNull(user.isType()) == Role.PATIENT) {
                PendentBundles pendentBundles = ((Patient) user).getRichiestePendenti();
                pendentBundles.addBundle(sentPrescriptionBundle);
            }
        }
    }

    public void logout(int code){
        Session s = sessions.remove(code);
        System.out.println("chiusa sessione" + numSessions);
        s.logout();
    }

    public void aggiornaSessioni(String taxCode,MedicationDose medicationDose, LocalDate date){
        List<Session> openSessions = getOpenSessionsByCF(taxCode);
        for (Session session : openSessions) {
            User user = session.getUtente();
            if (user.isType() == Role.PATIENT){
                TherapyCalendar therapyCalendar = ((Patient) user).getCalendar();
                if (therapyCalendar.exists(date)){
                    System.out.println("aggiunto");
                    therapyCalendar.getDailyTherapy(date).addDose(medicationDose);
                }
            }
        }


    }

    public boolean exists(int code){
        return sessions.containsKey(code);
    }


}
