package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.controller.bean.DoseBean;
import it.uniroma2.progettoispw.controller.bean.Prescription;
import it.uniroma2.progettoispw.model.dao.dbfiledao.MedicationDbDao;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
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
        DailyTherapy dailyTherapy = therapyDao.getTerapiaGiornaliera(codiceFiscale, data);
        for (List<MedicationDose> dosiOrario: dailyTherapy.getDosiPerOrario().values()){
            for (MedicationDose medicationDose : dosiOrario){
                MediccationType tipo = medicationDose.isType();
                switch (tipo){
                    case CONFEZIONE:
                        MedicationDoseConfezione doseConfezione= (MedicationDoseConfezione) medicationDose;
                        int codiceAic = Integer.parseInt(doseConfezione.getCodice());
                        doseConfezione.setConfezione(medicationDao.getConfezioneByCodiceAic(codiceAic));
                        break;
                    case PRINCIPIOATTIVO:
                        MedicationDosePrincipioAttivo dosePrincipioAttivo = (MedicationDosePrincipioAttivo) medicationDose;
                        String codiceAtc = dosePrincipioAttivo.getCodice();
                        dosePrincipioAttivo.setPrincipioAttivo(medicationDao.getPrincipioAttvoByCodiceAtc(codiceAtc));
                        break;
                    default: throw new DaoException("errore");
                }
                medicationDose.setInviante(userDao.getInfoUtente(medicationDose.getInviante().getCodiceFiscale()));
            }
        }
        return dailyTherapy;
    }

    public Patient getPaziente(String codiceFiscale) throws DaoException{
        Patient patient = userDao.getPaziente(codiceFiscale);
        patient.setRichiestePendenti(prescriptionBundleDao.getRichisteOfPaziente(patient));
        return patient;
    }

    public Doctor getDottore(String codiceFiscale) throws DaoException{
        return userDao.getDottore(codiceFiscale);
    }

    public void addPaziente(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                            String pass) throws DaoException{
        System.out.println("IN ADD PAZIENTE");
        userDao.addPaziente(codiceFiscale, nome, cognome, nascita, email, telefono, pass);
    }

    public int addDottore(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                          String pass) throws DaoException{
        return userDao.addDottore(codiceFiscale, nome, cognome, nascita, email, telefono, pass);
    }

    public User login(String codiceFiscale, String password, int isDottore, int codiceDottore) throws DaoException{
        User user = userDao.login(codiceFiscale, password, isDottore, codiceDottore);
        if (user == null){
            throw new DaoException("credenziali errate");
        }
        if (user.isType() == Role.PAZIENTE) {
            ((Patient) user).setRichiestePendenti(getRichisteOfPaziente((Patient) user));
        }
        return user;
    }

    public void addTerapiaByRichiesta(SentPrescriptionBundle sentPrescriptionBundle) throws DaoException {
        Patient ricevente = sentPrescriptionBundle.getRicevente();
        List<it.uniroma2.progettoispw.model.domain.Prescription> dosiInviate= sentPrescriptionBundle.getMedicinali();
        for (it.uniroma2.progettoispw.model.domain.Prescription dose: dosiInviate) {
            if (dose.isType() == MediccationType.CONFEZIONE){
                buildDoseConfezione(dose, ricevente.getCodiceFiscale());
            } else {
                buildDosePrincipioAttivo(dose, ricevente.getCodiceFiscale());
            }
        }
    }

    public void buildDoseConfezione(it.uniroma2.progettoispw.model.domain.Prescription prescription, String codiceFiscale) throws DaoException {
        LocalDate data = prescription.getInizio();
        List<Session> sessions = SessionManager.getInstance().getOpenSessionsByCF(codiceFiscale);
        for (int i = 0; i < prescription.getNumGiorni(); i++ ) {
            data = data.plusDays(prescription.getRateGiorni());
            MedicationDoseConfezione doseConfezione= new MedicationDoseConfezione(new MedicinalProduct(Integer.parseInt(prescription.getDose().getCodice())), prescription.getDose().getQuantita(),
                    prescription.getDose().getUnitaMisura(), prescription.getDose().getOrario(),
                    prescription.getDose().getDescrizione(), prescription.getDose().getInviante());
            therapyDao.addDoseConfezione(doseConfezione, data, codiceFiscale);
            aggiornaSessioni(sessions, doseConfezione, data);
        }
    }

    private void aggiornaSessioni(List<Session> sessions, MedicationDose medicationDose, LocalDate data){
        for (Session session : sessions) {
            User user = session.getUtente();
            if (user.isType() == Role.PAZIENTE){
                TherapyCalendar therapyCalendar = ((Patient) user).getCalendario();
                if (therapyCalendar.esiste(data)){
                    therapyCalendar.getDailyTherapy(data).addDose(medicationDose);
                }
            }
        }


    }

    public void buildDosePrincipioAttivo(it.uniroma2.progettoispw.model.domain.Prescription prescription, String codiceFiscale) throws DaoException {
        LocalDate data = prescription.getInizio();
        List<Session> sessions = SessionManager.getInstance().getOpenSessionsByCF(codiceFiscale);
        for (int i = 0; i < prescription.getNumGiorni(); i++ ) {
            data = data.plusDays(prescription.getRateGiorni());
            MedicationDosePrincipioAttivo dosePrincipioAttivo = new MedicationDosePrincipioAttivo(new ActiveIngridient(prescription.getDose().getCodice()), prescription.getDose().getQuantita(),
                    prescription.getDose().getUnitaMisura(), prescription.getDose().getOrario(),
                    prescription.getDose().getDescrizione(), prescription.getDose().getInviante());
            therapyDao.addDosePrincipioAttivo(dosePrincipioAttivo, data, codiceFiscale);
            aggiornaSessioni(sessions, dosePrincipioAttivo, data);
        }
    }

    public List<SentPrescriptionBundle> getRichisteOfPaziente(Patient patient) throws DaoException{
        List<SentPrescriptionBundle> list = prescriptionBundleDao.getRichisteOfPaziente(patient);
        for (SentPrescriptionBundle sentPrescriptionBundle : list) {
            Doctor inviante = userDao.getDottore(sentPrescriptionBundle.getInviante().getCodiceFiscale());
            sentPrescriptionBundle.setInviante(inviante);
            for (it.uniroma2.progettoispw.model.domain.Prescription prescription : sentPrescriptionBundle.getMedicinali()) {
                MedicationDose medicationDose = prescription.getDose();
                MediccationType tipo = medicationDose.isType();
                switch (tipo){
                    case CONFEZIONE:
                        MedicationDoseConfezione doseConfezione = (MedicationDoseConfezione) medicationDose;
                        doseConfezione.setConfezione(medicationDao.getConfezioneByCodiceAic(Integer.parseInt(doseConfezione.getCodice())));
                        doseConfezione.setInviante(inviante);
                        break;

                    case PRINCIPIOATTIVO:
                        MedicationDosePrincipioAttivo dosePrincipioAttivo = (MedicationDosePrincipioAttivo) medicationDose;
                        dosePrincipioAttivo.setPrincipioAttivo(medicationDao.getPrincipioAttvoByCodiceAtc(dosePrincipioAttivo.getCodice()));
                        dosePrincipioAttivo.setInviante(inviante);
                        break;

                    default:
                        break;

                }
            }
        }
        return list;
    }
    public void deleteRichiesta(int id) throws DaoException{
        prescriptionBundleDao.deleteRichiesta(id);
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
        return medicationDao.getNomiPrincipioAttivoByNomeParziale(nome);
    }
    public ActiveIngridient getPrincipioAttvoByNome(String nome) throws DaoException{
        return medicationDao.getPrincipioAttvoByNome(nome);
    }
    public List<MedicinalProduct> getConfezioniByCodiceAtc(String codiceAtc) throws DaoException{
        return medicationDao.getConfezioniByCodiceAtc(codiceAtc);
    }
    public ActiveIngridient getPrincipioAttvoByCodiceAtc(String codiceAtc) throws DaoException{
        return medicationDao.getPrincipioAttvoByCodiceAtc(codiceAtc);
    }

    public it.uniroma2.progettoispw.model.domain.Prescription wrapDoseCostructor(Prescription doseCostructor) throws DaoException{
        it.uniroma2.progettoispw.model.domain.Prescription prescription;
        DoseBean doseBean = doseCostructor.getDose();
        switch (doseBean.getTipo()){
            case CONFEZIONE -> { MedicinalProduct medicinalProduct = medicationDao.getConfezioneByCodiceAic(Integer.parseInt(doseBean.getCodice()));
                MedicationDoseConfezione doseConfezione = new MedicationDoseConfezione(medicinalProduct, doseBean.getQuantita(), doseBean.getUnitaMisura(), doseBean.getOrario(),
                        doseBean.getDescrizione(), userDao.getInfoUtente(doseBean.getInviante().getCodiceFiscale()));
                prescription = new it.uniroma2.progettoispw.model.domain.Prescription(doseConfezione, doseCostructor.getNumRipetizioni(), doseCostructor.getInizio(), doseCostructor.getRateGiorni());}
            case PRINCIPIOATTIVO -> {
                ActiveIngridient activeIngridient = medicationDao.getPrincipioAttvoByCodiceAtc(doseBean.getCodice());
                MedicationDosePrincipioAttivo dosePrincipioAttivo = new MedicationDosePrincipioAttivo(activeIngridient, doseBean.getQuantita(), doseBean.getUnitaMisura(), doseBean.getOrario(),
                        doseBean.getDescrizione(), userDao.getInfoUtente(doseBean.getInviante().getCodiceFiscale()));
                prescription = new it.uniroma2.progettoispw.model.domain.Prescription(dosePrincipioAttivo, doseCostructor.getNumRipetizioni(), doseCostructor.getInizio(), doseCostructor.getRateGiorni());}
            default -> throw new DaoException("tipo errato");
        }
        return prescription;
    }

    public User getInfoUtente(String codiceFiscale) throws DaoException{
        return userDao.getInfoUtente(codiceFiscale);
    }
 }
