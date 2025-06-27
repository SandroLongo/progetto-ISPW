package it.uniroma2.progettoispw.model.dao.memorydao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.UtenteDao;
import it.uniroma2.progettoispw.model.domain.Dottore;
import it.uniroma2.progettoispw.model.domain.Paziente;
import it.uniroma2.progettoispw.model.domain.Utente;

import java.time.LocalDate;
import java.util.*;

public class UtenteMemoryDao extends MemoryDao implements UtenteDao {
    private static UtenteMemoryDao instance;
    private Map<ChiaveUtente, Paziente> pazienti = new HashMap<>();
    private Map<ChiaveDottore, Dottore> dottori = new HashMap<>();
    private Map<String, Utente> infoUtenti = new HashMap<>();
    private TreeMap<String, List<String>> pazienteDottore = new TreeMap<>();

    private UtenteMemoryDao() {
        super();
    }
    public static UtenteMemoryDao getInstance() {
        if (instance == null) {
            instance = new UtenteMemoryDao();
        }
        return instance;
    }

    @Override
    public Paziente getPaziente(String codice_fiscale) throws DaoException {
        if (pazienti.containsKey(codice_fiscale)) {
            return (Paziente) infoUtenti.get(codice_fiscale);
        } else {
            return null;
        }
    }

    @Override
    public Dottore getDottore(String codice_fiscale) throws DaoException {
        if (dottori.containsKey(codice_fiscale)) {
            return (Dottore) infoUtenti.get(codice_fiscale);
        }  else {
            return null;
        }
    }

    @Override
    public void addPaziente(String codice_fiscale, String nome, String cognome, LocalDate nascita, String email, String telefono, String pass) throws DaoException {
        ChiaveUtente chiaveUtente = new ChiaveUtente(codice_fiscale, pass);
        System.out.println(codice_fiscale);
        System.out.println(pass);
        pazienti.put(chiaveUtente, new Paziente(codice_fiscale, nome, cognome, nascita, email, telefono, new ArrayList<>()));
        infoUtenti.put(codice_fiscale, new Paziente(codice_fiscale, nome, cognome, nascita, email, telefono));
    }

    @Override
    public void addDottore(String codice_fiscale, String nome, String cognome, LocalDate nascita, String email, String telefono, String pass, int codice) throws DaoException {
        ChiaveUtente chiaveUtente = new ChiaveUtente(codice_fiscale, pass);
        ChiaveDottore chiaveDottore = new ChiaveDottore(codice_fiscale, pass, codice);
        pazienti.put(chiaveUtente, new Paziente(codice_fiscale, nome, cognome, nascita, email, telefono, new ArrayList<>()));
        dottori.put(chiaveDottore, new Dottore(codice_fiscale, nome, cognome, nascita, email, telefono));
        infoUtenti.put(codice_fiscale, new Dottore(codice_fiscale, nome, cognome, nascita, email, telefono));
    }

    @Override
    public Utente login(String codice_fiscale, String password, int is_dottore, int codice_dottore) throws DaoException {
        if (is_dottore == 1) {
            ChiaveDottore chiave = new ChiaveDottore(codice_fiscale, password, codice_dottore);
            return dottori.get(chiave);
        } else {
            ChiaveUtente chiave = new ChiaveUtente(codice_fiscale, password);
            return pazienti.get(chiave);
        }

    }

    @Override
    public void addDottoreAssociato(String codicePaziente, String codiceDottore) throws DaoException {
        if (pazienteDottore.containsKey(codicePaziente)) {
            pazienteDottore.get(codicePaziente).add(codiceDottore);
        }
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
