package it.uniroma2.progettoispw.model.dao.memorydao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.UserDao;
import it.uniroma2.progettoispw.model.domain.Doctor;
import it.uniroma2.progettoispw.model.domain.Patient;
import it.uniroma2.progettoispw.model.domain.User;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;

public class UserMemoryDao extends MemoryDao implements UserDao {
    private static UserMemoryDao instance;
    private Map<UserKey, Patient> pazienti = new HashMap<>();
    private Map<DoctorKey, Doctor> dottori = new HashMap<>();
    private Map<String, User> infoUtenti = new HashMap<>();
    private Random random = new SecureRandom();

    private UserMemoryDao() {
        super();
    }
    public static UserMemoryDao getInstance() {
        if (instance == null) {
            instance = new UserMemoryDao();
        }
        return instance;
    }

    @Override
    public Patient getPatient(String taxCode) throws DaoException {
        if (pazienti.containsKey(taxCode)) {
            return (Patient)infoUtenti.get(taxCode);
        } else {
            return null;
        }
    }

    @Override
    public Doctor getDoctor(String taxCode) throws DaoException {
        if (dottori.containsKey(taxCode)) {
            return (Doctor)infoUtenti.get(taxCode);
        }  else {
            return null;
        }
    }

    @Override
    public void addPatient(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono, String pass) throws DaoException {
        UserKey userKey = new UserKey(codiceFiscale, pass);
        System.out.println(codiceFiscale + pass);
        pazienti.put(userKey, new Patient(codiceFiscale, nome, cognome, nascita, email, telefono));
        infoUtenti.put(codiceFiscale, new Patient(codiceFiscale, nome, cognome, nascita, email, telefono));
    }

    @Override
    public int addDoctor(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono, String pass) throws DaoException {
        int codiceDottore = generaCodiceUnico(dottori);
        UserKey userKey = new UserKey(codiceFiscale, pass);
        DoctorKey doctorKey = new DoctorKey(codiceFiscale, pass, codiceDottore);
        pazienti.put(userKey, new Patient(codiceFiscale, nome, cognome, nascita, email, telefono));
        dottori.put(doctorKey, new Doctor(codiceFiscale, nome, cognome, nascita, email, telefono));
        infoUtenti.put(codiceFiscale, new Doctor(codiceFiscale, nome, cognome, nascita, email, telefono));
        return codiceDottore;
    }

    private int generaCodiceUnico(Map<DoctorKey, Doctor> mappa) {
        Set<Integer> codiciEsistenti = new HashSet<>();

        for (DoctorKey chiave : mappa.keySet()) {
            codiciEsistenti.add(chiave.getDoctorCode());
        }

        int nuovoCodice;
        do {
            nuovoCodice = random.nextInt(100_000); // codice da 0 a 99999
        } while (codiciEsistenti.contains(nuovoCodice));

        return nuovoCodice;
    }

    @Override
    public User login(String codiceFiscale, String password, int isDottore, int codiceDottore) throws DaoException {
        System.out.println(codiceFiscale + password);
        if (isDottore == 1) {
            DoctorKey chiave = new DoctorKey(codiceFiscale, password, codiceDottore);
            return new Doctor(dottori.get(chiave));
        } else {
            UserKey chiave = new UserKey(codiceFiscale, password);
            return new Patient(pazienti.get(chiave));
        }

    }


    @Override
    public User getUserInformation(String codiceFiscale) throws DaoException {
        return infoUtenti.get(codiceFiscale);
    }


    public static class UserKey {
        private final String taxCode;
        private final String password;

        public UserKey(String taxCode, String password) {
            this.taxCode = taxCode;
            this.password = password;
        }

        public String getTaxCode() {
            return taxCode;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserKey)) return false;
            UserKey that = (UserKey) o;
            return taxCode.equals(that.taxCode) &&
                    password.equals(that.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(taxCode, password);
        }
    }

    public static class DoctorKey {
        private final String taxCode;
        private final String password;
        private final int doctorCode;

        public DoctorKey(String taxCode, String password, int doctorCode) {
            this.taxCode = taxCode;
            this.password = password;
            this.doctorCode = doctorCode;
        }

        public String getTaxCode() {
            return taxCode;
        }

        public String getPassword() {
            return password;
        }

        public int getDoctorCode() {
            return doctorCode;
        }

        // ✅ Equals completo
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DoctorKey)) return false;
            DoctorKey that = (DoctorKey) o;
            return Objects.equals(taxCode, that.taxCode) &&
                    Objects.equals(password, that.password) &&
                    Objects.equals(doctorCode, that.doctorCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(taxCode, password, doctorCode);
        }
    }
}
