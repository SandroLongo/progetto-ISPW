package it.uniroma2.progettoispw.model.dao;

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
        prescriptionBundleDao = DaoFactory.getIstance().getRichiesteDao();
        therapyDao = DaoFactory.getIstance().getTerapiaDao();
        userDao = DaoFactory.getIstance().getUtenteDao();
    }

    public DailyTherapy getTerapiaGiornaliera(String codiceFiscale, LocalDate data) throws DaoException{
        DailyTherapy dailyTherapy = therapyDao.getDailyTherapy(codiceFiscale, data);
        for (List<MedicationDose> dosiOrario: dailyTherapy.getDosesByTime().values()){
            for (MedicationDose medicationDose : dosiOrario){
                Medication medication = medicationDose.getMedication();
                MedicationType tipo = medication.getType();
                switch (tipo){
                    case MEDICINALPRODUCT:
                        int codiceAic = Integer.parseInt(medication.getId());
                        medicationDose.setMedication(medicationDao.getConfezioneByCodiceAic(codiceAic));
                        break;
                    case ACRIVEINGREDIENT:
                        String codiceAtc = medication.getId();
                        medicationDose.setMedication(medicationDao.getPrincipioAttvoByCodiceAtc(codiceAtc));
                        break;
                    default: throw new DaoException("invalid medication type");
                }
                medicationDose.setSender(userDao.getUserInformation(medicationDose.getSender().getTaxCode()));
            }
        }
        return dailyTherapy;
    }

    public Patient getPaziente(String codiceFiscale) throws DaoException{
        Patient patient = userDao.getPatient(codiceFiscale);
        patient.setRichiestePendenti(prescriptionBundleDao.getPrescriptionBundlesByPatient(patient));
        return patient;
    }

    public Doctor getDottore(String codiceFiscale) throws DaoException{
        return userDao.getDoctor(codiceFiscale);
    }

    public void addPaziente(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                            String pass) throws DaoException{
        System.out.println("IN ADD PATIENT");
        userDao.addPatient(codiceFiscale, nome, cognome, nascita, email, telefono, pass);
    }

    public int addDottore(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                          String pass) throws DaoException{
        return userDao.addDoctor(codiceFiscale, nome, cognome, nascita, email, telefono, pass);
    }

    public User login(String codiceFiscale, String password, int isDottore, int codiceDottore) throws DaoException{
        User user = userDao.login(codiceFiscale, password, isDottore, codiceDottore);
        if (user == null){
            throw new DaoException("credenziali errate");
        }
        if (user.isType() == Role.PATIENT) {
            ((Patient) user).setRichiestePendenti(getRichisteOfPaziente((Patient) user));
        }
        return user;
    }

    public void addTerapiaByRichiesta(SentPrescriptionBundle sentPrescriptionBundle) throws DaoException {
        Patient ricevente = sentPrescriptionBundle.getRicevente();
        List<Prescription> dosiInviate= sentPrescriptionBundle.getMedicinali();
        for (it.uniroma2.progettoispw.model.domain.Prescription dose: dosiInviate) {
            buildMedicationDose(dose, ricevente.getTaxCode());
        }
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

    public List<SentPrescriptionBundle> getRichisteOfPaziente(Patient patient) throws DaoException{
        List<SentPrescriptionBundle> list = prescriptionBundleDao.getPrescriptionBundlesByPatient(patient);
        for (SentPrescriptionBundle sentPrescriptionBundle : list) {
            Doctor inviante = userDao.getDoctor(sentPrescriptionBundle.getInviante().getTaxCode());
            sentPrescriptionBundle.setInviante(inviante);
            for (it.uniroma2.progettoispw.model.domain.Prescription prescription : sentPrescriptionBundle.getMedicinali()) {
                MedicationDose medicationDose = prescription.getDose();
                MedicationType tipo = medicationDose.isType();
                switch (tipo){
                    case MEDICINALPRODUCT:
                        medicationDose.setMedication(medicationDao.getConfezioneByCodiceAic(Integer.parseInt(medicationDose.getMedication().getId())));
                        medicationDose.setSender(inviante);
                        break;

                    case ACRIVEINGREDIENT:
                        medicationDose.setMedication(medicationDao.getPrincipioAttvoByCodiceAtc(medicationDose.getMedication().getId()));
                        medicationDose.setSender(inviante);
                        break;

                    default:
                        break;

                }
            }
        }
        return list;
    }
    public void deleteRichiesta(int id) throws DaoException{
        prescriptionBundleDao.deletePrescriptionBundle(id);
    }

    public MedicinalProduct getConfezioneByCodiceAic(int codiceAic) throws DaoException{
        return medicationDao.getConfezioneByCodiceAic(codiceAic);
    }

    public List<String> getNomiConfezioniByNomeParziale(String nome) throws DaoException{
        return medicationDao.getNomiConfezioniByNomeParziale(nome);
    }
    public List<MedicinalProduct> getConfezioniByNome(String nome) throws DaoException{
        return medicationDao.getConfezioniByNome(nome);
    }
    public List<String> getNomiPrincipioAttivoByNomeParziale(String nome) throws DaoException{
        return medicationDao.getMedicinalProductNameByPartialName(nome);
    }
    public ActiveIngredient getPrincipioAttvoByNome(String nome) throws DaoException{
        return medicationDao.getPrincipioAttvoByNome(nome);
    }
    public List<MedicinalProduct> getConfezioniByCodiceAtc(String codiceAtc) throws DaoException{
        return medicationDao.getConfezioniByCodiceAtc(codiceAtc);
    }
    public ActiveIngredient getPrincipioAttvoByCodiceAtc(String codiceAtc) throws DaoException{
        return medicationDao.getPrincipioAttvoByCodiceAtc(codiceAtc);
    }

    public SentPrescriptionBundle addRichiesta(PrescriptionBundleBean prescriptionBean) throws DaoException{
        int id = prescriptionBundleDao.addPrescriptionBundle(prescriptionBean);
        System.out.println("inviata richiesta con id:" + id);
        return prescriptionBundleDao.getPrescriptionBundleById(id);
    }

    public User getInfoUtente(String codiceFiscale) throws DaoException{
        return userDao.getUserInformation(codiceFiscale);
    }
 }
