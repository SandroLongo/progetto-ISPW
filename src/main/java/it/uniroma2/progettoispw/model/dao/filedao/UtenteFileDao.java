package it.uniroma2.progettoispw.model.dao.filedao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.DaoFactory;
import it.uniroma2.progettoispw.model.dao.UtenteDao;
import it.uniroma2.progettoispw.model.dao.dbfiledao.FileDao;
import it.uniroma2.progettoispw.model.domain.Dottore;
import it.uniroma2.progettoispw.model.domain.Paziente;
import it.uniroma2.progettoispw.model.domain.Utente;

import java.io.*;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;

public class UtenteFileDao extends FileDao implements UtenteDao {
    private String utentiPath;
    private String dottoriPath;
    private Random random = new SecureRandom();

    public UtenteFileDao() {
        try (InputStream input = DaoFactory.class.getClassLoader().getResourceAsStream("properties.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            utentiPath = properties.getProperty("utentiPath");
            System.out.println(utentiPath);
            dottoriPath = properties.getProperty("dottoriPath");
        } catch (IOException e) {
            throw new DaoException("path non trovati");
        }
    }

        private List<UtenteRegistrato> caricaUtenti() throws DaoException {
        File file = new File(utentiPath);
        if (!file.exists()){
            throw new DaoException("file non trovato");
        }
        if (file.length() == 0){
            return new ArrayList<UtenteRegistrato>();
        }
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<UtenteRegistrato>) ois.readObject();
        } catch (IOException e) {
            if (e instanceof FileNotFoundException){
                throw new DaoException("file non trovato");
            } else if (e instanceof EOFException){
                throw new DaoException("file finito");
            } else if (e instanceof StreamCorruptedException){
                throw new DaoException("file corrotto");
            } else {
                throw new DaoException("file");
            }
        } catch (ClassNotFoundException e) {
            throw new DaoException("class not found");
        }
    }

    private void salvaUtenti(List<UtenteRegistrato> lista) throws DaoException {
        File file = new File(utentiPath);
        if (!file.exists()){
            throw new DaoException("file non trovato");
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private List<DottoreRegistrato> caricaDottori() throws DaoException {
        File file = new File(dottoriPath);
        if (!file.exists()){
            throw new DaoException("file non trovato");
        }
        if (file.length() == 0){
            return new ArrayList<DottoreRegistrato>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<DottoreRegistrato>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private void salvaDottori(List<DottoreRegistrato> lista) throws DaoException {
        File file = new File(dottoriPath);
        if (!file.exists()){
            throw new DaoException("file non trovato");
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
    }


    @Override
    public Paziente getPaziente(String codiceFiscale) throws DaoException {
        Optional<UtenteRegistrato> pazienteOpt= caricaUtenti().stream().filter(u -> u.getPaziente().getCodiceFiscale().equals(codiceFiscale)).findFirst();
        if (pazienteOpt.isPresent()){
            return new Paziente(pazienteOpt.get().getPaziente());
        } else {
            throw new DaoException("paziente non trovato");
        }
    }

    @Override
    public Dottore getDottore(String codiceFiscale) throws DaoException {
        Optional<DottoreRegistrato> dottoreOpt = caricaDottori().stream().filter(u -> u.getDottore().getCodiceFiscale().equals(codiceFiscale)).findFirst();
        if (dottoreOpt.isPresent()){
            return new Dottore(dottoreOpt.get().getDottore());
        } else {
            throw new DaoException("paziente non trovato");
        }
    }

    @Override
    public void addPaziente(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono, String pass) throws DaoException {
        List<UtenteRegistrato> utenti = caricaUtenti();
        if (utenti.stream().anyMatch(u -> u.getPaziente().getCodiceFiscale().equals(codiceFiscale))){
            throw new DaoException("paziente gia registrato");
        }
        utenti.add(new UtenteRegistrato(new Paziente(codiceFiscale, nome, cognome, nascita, email, telefono), pass));
        salvaUtenti(utenti);
    }

    @Override
    public int addDottore(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono, String pass) throws DaoException {
        List<DottoreRegistrato> dottori = caricaDottori();
        if (dottori.stream().anyMatch(u -> u.getDottore().getCodiceFiscale().equals(codiceFiscale))){
            throw new DaoException("paziente gia registrato");
        }
        int codice = generaCodiceUnico(dottori);
        dottori.add(new DottoreRegistrato(new Dottore(codiceFiscale, nome, cognome, nascita,email, telefono), pass, codice));
        salvaDottori(dottori);
        return codice;
    }

    @Override
    public Utente login(String codiceFiscale, String password, int isDottore, int codiceDottore) throws DaoException {
        if (isDottore == 1){
            List<DottoreRegistrato> dottori = caricaDottori();
            Optional<DottoreRegistrato> dottoreRegistrato = dottori.stream().filter(u -> u.getDottore().getCodiceFiscale().equals(codiceFiscale) && u.getPassword().equals(password)).findFirst();
            if (dottoreRegistrato.isPresent()){
                return new Dottore(dottoreRegistrato.get().getDottore());
            } else {
                throw new DaoException("dottore non trovato");
            }
        } else {
            List<UtenteRegistrato> pazienti = caricaUtenti();
            Optional<UtenteRegistrato> utenteRegistrato = pazienti.stream().filter(u -> u.getPaziente().getCodiceFiscale().equals(codiceFiscale) && u.getPassword().equals(password)).findFirst();
            if (utenteRegistrato.isPresent()){
                return new Paziente(utenteRegistrato.get().getPaziente());
            } else {
                throw new DaoException("paziente non trovato");
            }
        }
    }

    @Override
    public Utente getInfoUtente(String codiceFiscale) throws DaoException {
        List<UtenteRegistrato> utenti = caricaUtenti();
        Optional<UtenteRegistrato> utenteRegistrato = utenti.stream().filter(u -> u.getPaziente().getCodiceFiscale().equals(codiceFiscale)).findFirst();
        if (utenteRegistrato.isPresent()){
            return new Paziente(utenteRegistrato.get().getPaziente());
        } else {
            throw new DaoException("paziente non trovato");
        }
    }

    private int generaCodiceUnico(List<DottoreRegistrato> dottori) {
        Set<Integer> codiciEsistenti = new HashSet<>();

        for (DottoreRegistrato u : dottori) {
            codiciEsistenti.add(u.getCodice());
        }

        int nuovoCodice;
        do {
            nuovoCodice = random.nextInt(100_000); // codice da 0 a 99999
        } while (codiciEsistenti.contains(nuovoCodice));

        return nuovoCodice;
    }

    private static class UtenteRegistrato implements Serializable {
        private Paziente paziente;
        private String password;

        public UtenteRegistrato(Paziente paziente, String password) {
            this.paziente = paziente;
            this.password = password;
        }

        public Paziente getPaziente() {
            return paziente;
        }

        public void setPaziente(Paziente paziente) {
            this.paziente = paziente;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    private static class DottoreRegistrato implements Serializable {
        private Dottore dottore;
        private String password;
        private int codice;

        public DottoreRegistrato(Dottore dottore, String password, int codice) {
            this.dottore = dottore;
            this.password = password;
            this.codice = codice;
        }

        public Dottore getDottore() {
            return dottore;
        }

        public void setDottore(Dottore dottore) {
            this.dottore = dottore;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getCodice() {
            return codice;
        }

        public void setCodice(int codice) {
            this.codice = codice;
        }
    }


}
