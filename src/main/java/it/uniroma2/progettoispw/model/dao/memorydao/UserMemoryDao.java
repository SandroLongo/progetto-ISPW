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
    private Map<ChiaveUtente, Patient> pazienti = new HashMap<>();
    private Map<ChiaveDottore, Doctor> dottori = new HashMap<>();
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
    public Patient getPaziente(String taxCode) throws DaoException {
        if (pazienti.containsKey(taxCode)) {
            return (Patient)infoUtenti.get(taxCode);
        } else {
            return null;
        }
    }

    @Override
    public Doctor getDottore(String taxCode) throws DaoException {
        if (dottori.containsKey(taxCode)) {
            return (Doctor)infoUtenti.get(taxCode);
        }  else {
            return null;
        }
    }

    @Override
    public void addPaziente(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono, String pass) throws DaoException {
        ChiaveUtente chiaveUtente = new ChiaveUtente(codiceFiscale, pass);
        System.out.println(codiceFiscale + pass);
        pazienti.put(chiaveUtente, new Patient(codiceFiscale, nome, cognome, nascita, email, telefono));
        infoUtenti.put(codiceFiscale, new Patient(codiceFiscale, nome, cognome, nascita, email, telefono));
    }

    @Override
    public int addDottore(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono, String pass) throws DaoException {
        int codiceDottore = generaCodiceUnico(dottori);
        ChiaveUtente chiaveUtente = new ChiaveUtente(codiceFiscale, pass);
        ChiaveDottore chiaveDottore = new ChiaveDottore(codiceFiscale, pass, codiceDottore);
        pazienti.put(chiaveUtente, new Patient(codiceFiscale, nome, cognome, nascita, email, telefono));
        dottori.put(chiaveDottore, new Doctor(codiceFiscale, nome, cognome, nascita, email, telefono));
        infoUtenti.put(codiceFiscale, new Doctor(codiceFiscale, nome, cognome, nascita, email, telefono));
        return codiceDottore;
    }

    private int generaCodiceUnico(Map<ChiaveDottore, Doctor> mappa) {
        Set<Integer> codiciEsistenti = new HashSet<>();

        for (ChiaveDottore chiave : mappa.keySet()) {
            codiciEsistenti.add(chiave.getCodiceDottore());
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
            ChiaveDottore chiave = new ChiaveDottore(codiceFiscale, password, codiceDottore);
            return new Doctor(dottori.get(chiave));
        } else {
            ChiaveUtente chiave = new ChiaveUtente(codiceFiscale, password);
            return new Patient(pazienti.get(chiave));
        }

    }


    @Override
    public User getInfoUtente(String codiceFiscale) throws DaoException {
        return infoUtenti.get(codiceFiscale);
    }


    public static class ChiaveUtente {
        private final String codiceFiscale;
        private final String password;

        public ChiaveUtente(String codiceFiscale, String password) {
            this.codiceFiscale = codiceFiscale;
            this.password = password;
        }

        public String getCodiceFiscale() {
            return codiceFiscale;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ChiaveUtente)) return false;
            ChiaveUtente that = (ChiaveUtente) o;
            return codiceFiscale.equals(that.codiceFiscale) &&
                    password.equals(that.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(codiceFiscale, password);
        }
    }

    public static class ChiaveDottore {
        private final String codiceFiscale;
        private final String password;
        private final int codiceDottore;

        public ChiaveDottore(String codiceFiscale, String password, int codiceDottore) {
            this.codiceFiscale = codiceFiscale;
            this.password = password;
            this.codiceDottore = codiceDottore;
        }

        public String getCodiceFiscale() {
            return codiceFiscale;
        }

        public String getPassword() {
            return password;
        }

        public int getCodiceDottore() {
            return codiceDottore;
        }

        // ✅ Equals completo
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ChiaveDottore)) return false;
            ChiaveDottore that = (ChiaveDottore) o;
            return Objects.equals(codiceFiscale, that.codiceFiscale) &&
                    Objects.equals(password, that.password) &&
                    Objects.equals(codiceDottore, that.codiceDottore);
        }

        @Override
        public int hashCode() {
            return Objects.hash(codiceFiscale, password, codiceDottore);
        }
    }
}
