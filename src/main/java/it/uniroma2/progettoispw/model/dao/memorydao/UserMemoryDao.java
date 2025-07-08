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
    private Map<UserKey, Patient> patients = new HashMap<>();
    private Map<DoctorKey, Doctor> doctors = new HashMap<>();
    private Map<String, User> userinfo = new HashMap<>();
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
        if (patients.containsKey(taxCode)) {
            return (Patient) userinfo.get(taxCode);
        } else {
            return null;
        }
    }

    @Override
    public Doctor getDoctor(String taxCode) throws DaoException {
        if (doctors.containsKey(taxCode)) {
            return (Doctor) userinfo.get(taxCode);
        }  else {
            return null;
        }
    }

    @Override
    public void addPatient(String taxCode, String name, String surname, LocalDate birthDate, String email, String phoneNumber, String pass) throws DaoException {
        UserKey userKey = new UserKey(taxCode, pass);
        System.out.println(taxCode + pass);
        patients.put(userKey, new Patient(taxCode, name, surname, birthDate, email, phoneNumber));
        userinfo.put(taxCode, new Patient(taxCode, name, surname, birthDate, email, phoneNumber));
    }

    @Override
    public int addDoctor(String taxCode, String name, String surname, LocalDate birthDate, String email, String phoneNumber, String pass) throws DaoException {
        int codiceDottore = generaCodiceUnico(doctors);
        UserKey userKey = new UserKey(taxCode, pass);
        DoctorKey doctorKey = new DoctorKey(taxCode, pass, codiceDottore);
        patients.put(userKey, new Patient(taxCode, name, surname, birthDate, email, phoneNumber));
        doctors.put(doctorKey, new Doctor(taxCode, name, surname, birthDate, email, phoneNumber));
        userinfo.put(taxCode, new Doctor(taxCode, name, surname, birthDate, email, phoneNumber));
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
    public User login(String taxCode, String password, int isDoctor, int doctorCode) throws DaoException {
        System.out.println(taxCode + password);
        if (isDoctor == 1) {
            DoctorKey chiave = new DoctorKey(taxCode, password, doctorCode);
            Doctor doctor = doctors.get(chiave);
            if (doctor == null) {
                return null;
            }
            return doctor;
        } else {
            UserKey chiave = new UserKey(taxCode, password);
            Patient patient = patients.get(chiave);
            if (patient == null) {
                return null;
            }
            return patient;
        }

    }


    @Override
    public User getUserInformation(String taxCode) throws DaoException {
        return userinfo.get(taxCode);
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
