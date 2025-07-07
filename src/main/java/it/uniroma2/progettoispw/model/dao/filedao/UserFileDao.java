package it.uniroma2.progettoispw.model.dao.filedao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.DaoFactory;
import it.uniroma2.progettoispw.model.dao.UserDao;
import it.uniroma2.progettoispw.model.dao.dbfiledao.FileDao;
import it.uniroma2.progettoispw.model.domain.Doctor;
import it.uniroma2.progettoispw.model.domain.Patient;
import it.uniroma2.progettoispw.model.domain.User;

import java.io.*;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;

public class UserFileDao extends FileDao implements UserDao {
    private String utentiPath;
    private String dottoriPath;
    private Random random = new SecureRandom();

    public UserFileDao() {
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
    public Patient getPatient(String taxCode) throws DaoException {
        Optional<UtenteRegistrato> pazienteOpt= caricaUtenti().stream().filter(u -> u.getPaziente().getTaxCode().equals(taxCode)).findFirst();
        if (pazienteOpt.isPresent()){
            return new Patient(pazienteOpt.get().getPaziente());
        } else {
            throw new DaoException("patient non trovato");
        }
    }

    @Override
    public Doctor getDoctor(String taxCode) throws DaoException {
        Optional<DottoreRegistrato> dottoreOpt = caricaDottori().stream().filter(u -> u.getDottore().getTaxCode().equals(taxCode)).findFirst();
        if (dottoreOpt.isPresent()){
            return new Doctor(dottoreOpt.get().getDottore());
        } else {
            throw new DaoException("patient non trovato");
        }
    }

    @Override
    public void addPatient(String taxCode, String name, String surname, LocalDate birthDate, String email, String phoneNumber, String pass) throws DaoException {
        List<UtenteRegistrato> utenti = caricaUtenti();
        if (utenti.stream().anyMatch(u -> u.getPaziente().getTaxCode().equals(taxCode))){
            throw new DaoException("patient gia registrato");
        }
        utenti.add(new UtenteRegistrato(new Patient(taxCode, name, surname, birthDate, email, phoneNumber), pass));
        salvaUtenti(utenti);
    }

    @Override
    public int addDoctor(String taxCode, String name, String surname, LocalDate birthDate, String email, String phoneNumber, String pass) throws DaoException {
        List<DottoreRegistrato> dottori = caricaDottori();
        if (dottori.stream().anyMatch(u -> u.getDottore().getTaxCode().equals(taxCode))){
            throw new DaoException("patient gia registrato");
        }
        int codice = generaCodiceUnico(dottori);
        dottori.add(new DottoreRegistrato(new Doctor(taxCode, name, surname, birthDate,email, phoneNumber), pass, codice));
        List<UtenteRegistrato> utenti = caricaUtenti();
        if (utenti.stream().anyMatch(u -> u.getPaziente().getTaxCode().equals(taxCode))){
            throw new DaoException("patient gia registrato");
        }
        salvaUtenti(utenti);
        salvaDottori(dottori);
        return codice;
    }

    @Override
    public User login(String taxCode, String password, int isDoctor, int doctorCode) throws DaoException {
        if (isDoctor == 1){
            List<DottoreRegistrato> dottori = caricaDottori();
            Optional<DottoreRegistrato> dottoreRegistrato = dottori.stream().filter(u -> u.getDottore().getTaxCode().equals(taxCode) && u.getPassword().equals(password)).findFirst();
            if (dottoreRegistrato.isPresent()){
                return new Doctor(dottoreRegistrato.get().getDottore());
            } else {
                throw new DaoException("doctor non trovato");
            }
        } else {
            List<UtenteRegistrato> pazienti = caricaUtenti();
            Optional<UtenteRegistrato> utenteRegistrato = pazienti.stream().filter(u -> u.getPaziente().getTaxCode().equals(taxCode) && u.getPassword().equals(password)).findFirst();
            if (utenteRegistrato.isPresent()){
                return new Patient(utenteRegistrato.get().getPaziente());
            } else {
                throw new DaoException("patient non trovato");
            }
        }
    }

    @Override
    public User getUserInformation(String taxCode) throws DaoException {
        List<UtenteRegistrato> utenti = caricaUtenti();
        Optional<UtenteRegistrato> utenteRegistrato = utenti.stream().filter(u -> u.getPaziente().getTaxCode().equals(taxCode)).findFirst();
        if (utenteRegistrato.isPresent()){
            return new Patient(utenteRegistrato.get().getPaziente());
        } else {
            throw new DaoException("patient non trovato");
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
        private Patient patient;
        private String password;

        public UtenteRegistrato(Patient patient, String password) {
            this.patient = patient;
            this.password = password;
        }

        public Patient getPaziente() {
            return patient;
        }

        public void setPaziente(Patient patient) {
            this.patient = patient;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    private static class DottoreRegistrato implements Serializable {
        private Doctor doctor;
        private String password;
        private int codice;

        public DottoreRegistrato(Doctor doctor, String password, int codice) {
            this.doctor = doctor;
            this.password = password;
            this.codice = codice;
        }

        public Doctor getDottore() {
            return doctor;
        }

        public void setDottore(Doctor doctor) {
            this.doctor = doctor;
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
