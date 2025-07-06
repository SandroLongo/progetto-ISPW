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

    public List<Session> getOpenSessionsByCF(String cf) {
        return sessions.values().stream()
                .filter(session -> session.getUtente() != null &&
                        cf.equals(session.getUtente().getCodiceFiscale()))
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
        List<Session> openSessionsByCF = getOpenSessionsByCF(sentPrescriptionBundle.getRicevente().getCodiceFiscale());
        for (Session s : openSessionsByCF){
            User user = s.getUtente();
            if (Objects.requireNonNull(user.isType()) == Role.PAZIENTE) {
                PendentBundle pendentBundle = ((Patient) user).getRichiestePendenti();
                pendentBundle.deleteRichiesta(sentPrescriptionBundle.getId());
            }
        }
    }

    public void addRichiesta(SentPrescriptionBundle sentPrescriptionBundle) {
        List<Session> attive = SessionManager.getInstance().getOpenSessionsByCF(sentPrescriptionBundle.getRicevente().getCodiceFiscale());
        for (Session session : attive){
            User user = session.getUtente();
            if (Objects.requireNonNull(user.isType()) == Role.PAZIENTE) {
                PendentBundle pendentBundle = ((Patient) user).getRichiestePendenti();
                pendentBundle.addRichiesta(sentPrescriptionBundle);
            }
        }
    }

    public void logout(int codice){
        Session s = sessions.remove(codice);
        System.out.println("chiusa sessione" + numSessions);
        s.logout();
    }

    public void aggiornaSessioni(String taxCode,MedicationDose medicationDose, LocalDate data){
        List<Session> openSessions = getOpenSessionsByCF(taxCode);
        for (Session session : openSessions) {
            User user = session.getUtente();
            if (user.isType() == Role.PAZIENTE){
                TherapyCalendar therapyCalendar = ((Patient) user).getCalendario();
                if (therapyCalendar.esiste(data)){
                    therapyCalendar.getDailyTherapy(data).addDose(medicationDose);
                }
            }
        }


    }

    public boolean esiste(int codice){
        return sessions.containsKey(codice);
    }


}
