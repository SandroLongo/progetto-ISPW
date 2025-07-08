package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.controller.bean.InvalidFormatException;
import it.uniroma2.progettoispw.controller.bean.PrescriptionBundleBean;
import it.uniroma2.progettoispw.model.dao.dbfiledao.MedicationDbDao;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaoFacade {
    MedicationDao medicationDao;
    PrescriptionBundleDao prescriptionBundleDao;
    TherapyDao therapyDao;
    UserDao userDao;

    public DaoFacade() {
        medicationDao = new MedicationDbDao();
        prescriptionBundleDao = DaoFactory.getIstance().getPrescriptionBundleDao();
        therapyDao = DaoFactory.getIstance().getTherapyDao();
        userDao = DaoFactory.getIstance().getUserDao();
    }

    public DailyTherapy getDailyTherapy(String codiceFiscale, LocalDate data) throws DaoException{
        DailyTherapy dailyTherapy = therapyDao.getDailyTherapy(codiceFiscale, data);
        for (List<MedicationDose> dosiOrario: dailyTherapy.getDosesByTime().values()){
            for (MedicationDose medicationDose : dosiOrario){
                Medication medication = medicationDose.getMedication();
                MedicationType tipo = medication.getType();
                switch (tipo){
                    case MEDICINALPRODUCT:
                        medicationDose.setMedication(medicationDao.getMedicinalProductByID(medication.getId()));
                        break;
                    case ACRIVEINGREDIENT:
                        medicationDose.setMedication(medicationDao.getAIByID(medication.getId()));
                        break;
                    default: throw new DaoException("invalid medication type");
                }
                medicationDose.setSender(userDao.getUserInformation(medicationDose.getSender().getTaxCode()));
            }
        }
        return dailyTherapy;
    }

    public Patient getPatient(String codiceFiscale) throws DaoException{
        Patient patient = userDao.getPatient(codiceFiscale);
        patient.setRichiestePendenti(prescriptionBundleDao.getBundles(patient));
        return patient;
    }

    public Doctor getDoctor(String codiceFiscale) throws DaoException{
        return userDao.getDoctor(codiceFiscale);
    }

    public void addPatient(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                           String pass) throws DaoException{
        System.out.println("IN ADD PATIENT");
        userDao.addPatient(codiceFiscale, nome, cognome, nascita, email, telefono, pass);
    }

    public int addDoctor(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                         String pass) throws DaoException{
        return userDao.addDoctor(codiceFiscale, nome, cognome, nascita, email, telefono, pass);
    }

    public User login(String codiceFiscale, String password, int isDottore, int codiceDottore) throws DaoException{
        User user = userDao.login(codiceFiscale, password, isDottore, codiceDottore);
        if (user == null){
            throw new DaoException("credenziali errate");
        }
        if (user.isType() == Role.PATIENT) {
            ((Patient) user).setRichiestePendenti(getBundlesOfPatient((Patient) user));
        }
        return user;
    }

    public List<List<LocalDate>> addDosesByBundle(SentPrescriptionBundle sentPrescriptionBundle) throws DaoException {
        Patient ricevente = sentPrescriptionBundle.getRicevente();
        List<Prescription> dosiInviate= sentPrescriptionBundle.getMedicinali();
        List<List<LocalDate>> dates = new ArrayList<>();
        for (Prescription dose: dosiInviate) {
            dates.add(new ArrayList<>(buildMedicationDose(dose, ricevente.getTaxCode())));
        }
        return dates;
    }

    public List<LocalDate> buildMedicationDose(Prescription prescription, String codiceFiscale) throws DaoException {
        List<LocalDate> dates = new ArrayList<LocalDate>();
        LocalDate data = prescription.getInizio();
        for (int i = 0; i < prescription.getNumGiorni(); i++ ) {
            MedicationDose dose = prescription.getDose();
            therapyDao.addMedicationDose(dose, data, codiceFiscale);
            dates.add(data);
            data = data.plusDays(prescription.getRateGiorni());
        }
        return dates;
    }

    public List<SentPrescriptionBundle> getBundlesOfPatient(Patient patient) throws DaoException{
        List<SentPrescriptionBundle> list = prescriptionBundleDao.getBundles(patient);
        for (SentPrescriptionBundle sentPrescriptionBundle : list) {
            compleateBundle(sentPrescriptionBundle);
        }
        return list;
    }
    public void deleteBundle(int id) throws DaoException{
        prescriptionBundleDao.deleteBundle(id);
    }

    public MedicinalProduct getMedicinalProductByID(String id) throws DaoException, InvalidFormatException {
        return medicationDao.getMedicinalProductByID(id);
    }

    public List<String> getMedicinalProductsNamesByPartialName(String nome) throws DaoException{
        return medicationDao.getMPNameByPartialName(nome);
    }
    public List<MedicinalProduct> getMedicinalProductsByName(String nome) throws DaoException{
        return medicationDao.getMPbyName(nome);
    }
    public List<String> getActiveIngredientNamesByPartialNames(String nome) throws DaoException{
        return medicationDao.getAINamesByPartialName(nome);
    }
    public ActiveIngredient getActiveIgredientByName(String nome) throws DaoException{
        return medicationDao.getAIByName(nome);
    }
    public List<MedicinalProduct> getMedicinalProductByActiveIngredientID(String id) throws DaoException{
        return medicationDao.getMPByAIID(id);
    }
    public ActiveIngredient getActiveIngridientByID(String id) throws DaoException{
        return medicationDao.getAIByID(id);
    }

    public SentPrescriptionBundle addBundle(PrescriptionBundleBean prescriptionBean) throws DaoException{
        int id = prescriptionBundleDao.addBundle(prescriptionBean);
        System.out.println("inviata richiesta con id:" + id);
        SentPrescriptionBundle sentPrescriptionBundle = prescriptionBundleDao.getBundleById(id);
        return sentPrescriptionBundle;
    }

    private void compleateBundle(SentPrescriptionBundle sentPrescriptionBundle) {
        Doctor inviante = userDao.getDoctor(sentPrescriptionBundle.getInviante().getTaxCode());
        sentPrescriptionBundle.setInviante(inviante);
        for (Prescription prescription : sentPrescriptionBundle.getMedicinali()) {
            MedicationDose medicationDose = prescription.getDose();
            MedicationType tipo = medicationDose.isType();
            switch (tipo){
                case MEDICINALPRODUCT:
                    medicationDose.setMedication(medicationDao.getMedicinalProductByID(medicationDose.getMedication().getId()));
                    medicationDose.setSender(inviante);
                    break;

                case ACRIVEINGREDIENT:
                    medicationDose.setMedication(medicationDao.getAIByID(medicationDose.getMedication().getId()));
                    medicationDose.setSender(inviante);
                    break;

                default:
                    break;

            }
        }
    }

    public User getUserInfo(String codiceFiscale) throws DaoException{
        return userDao.getUserInformation(codiceFiscale);
    }
 }
