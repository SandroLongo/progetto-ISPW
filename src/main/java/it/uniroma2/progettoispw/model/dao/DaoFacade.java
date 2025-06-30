package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.controller.bean.DoseBean;
import it.uniroma2.progettoispw.controller.bean.DoseCostructor;
import it.uniroma2.progettoispw.controller.bean.RichiestaBean;
import it.uniroma2.progettoispw.model.dao.dbfiledao.MedicinaliDbDao;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class DaoFacade {
    MedicinaliDao medicinaliDao;
    RichiesteDao richiesteDao;
    TerapiaDao terapiaDao;
    UtenteDao utenteDao;

    public DaoFacade() {
        medicinaliDao = new MedicinaliDbDao();
        richiesteDao = DaoFactory.getIstance().getRichiesteDao();
        terapiaDao = DaoFactory.getIstance().getTerapiaDao();
        utenteDao = DaoFactory.getIstance().getUtenteDao();
    }

    public TerapiaGiornaliera getTerapiaGiornaliera(String codiceFiscale, LocalDate data) throws DaoException{
        TerapiaGiornaliera terapiaGiornaliera = terapiaDao.getTerapiaGiornaliera(codiceFiscale, data);
        for (List<Dose> dosiOrario: terapiaGiornaliera.getDosiPerOrario().values()){
            for (Dose dose : dosiOrario){
                TipoDose tipo = dose.isType();
                switch (tipo){
                    case Confezione:
                        DoseConfezione doseConfezione= (DoseConfezione) dose;
                        int codiceAic = Integer.parseInt(doseConfezione.getCodice());
                        System.out.println(codiceAic + " in daofacade");
                        doseConfezione.setConfezione(medicinaliDao.getConfezioneByCodiceAic(codiceAic));
                        break;
                    case PrincipioAttivo:
                        DosePrincipioAttivo dosePrincipioAttivo = (DosePrincipioAttivo) dose;
                        String codiceAtc = dosePrincipioAttivo.getCodice();
                        System.out.println(codiceAtc + " in daofacade");
                        dosePrincipioAttivo.setPrincipioAttivo(medicinaliDao.getPrincipioAttvoByCodiceAtc(codiceAtc));
                        break;
                    default: throw new RuntimeException("errore");
                }
                dose.setInviante(utenteDao.getDottore(dose.getInviante().getCodiceFiscale()));
            }
        }
        return terapiaGiornaliera;
    }

    public Paziente getPaziente(String codice_fiscale) throws DaoException{
        Paziente paziente = utenteDao.getPaziente(codice_fiscale);
        paziente.setRichiestePendenti(richiesteDao.getRichisteOfPaziente(paziente));
        return paziente;
    }

    public Dottore getDottore(String codice_fiscale) throws DaoException{
        return utenteDao.getDottore(codice_fiscale);
    }

    public void addPaziente(String codice_fiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                            String pass) throws DaoException{
        System.out.println("in add paziente");
        utenteDao.addPaziente(codice_fiscale, nome, cognome, nascita, email, telefono, pass);
        System.out.println("fatto  add paziente");
    }

    public int addDottore(String codice_fiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                           String pass) throws DaoException{
        return utenteDao.addDottore(codice_fiscale, nome, cognome, nascita, email, telefono, pass);
    }

    public Utente login(String codice_fiscale, String password, int is_dottore, int codice_dottore) throws DaoException{
        Utente utente = utenteDao.login(codice_fiscale, password, is_dottore, codice_dottore);
        if (Objects.requireNonNull(utente.isType()) == Ruolo.Paziente) {
            ((Paziente)utente).setRichiestePendenti(getRichisteOfPaziente((Paziente) utente));
        }
        return utente;
    }
    public void addDottoreAssociato(String codicePaziente,String codiceDottore) throws DaoException{
        utenteDao.addDottoreAssociato(codicePaziente, codiceDottore);
    }

    public void addTerapiaByRichiesta(Richiesta richiesta) throws DaoException {
        Paziente ricevente = richiesta.getRicevente();
        List<DoseInviata> dosiInviate= richiesta.getMedicinali();
        for (DoseInviata dose: dosiInviate) {
            if (dose.isType() == TipoDose.Confezione){
                buildDoseConfezione(dose, ricevente.getCodiceFiscale());
            } else {
                buildDosePrincipioAttivo(dose, ricevente.getCodiceFiscale());
            }
        }
    }

    public void buildDoseConfezione(DoseInviata doseInviata, String codiceFiscale) throws DaoException {
        LocalDate data = doseInviata.getInizio();
        List<Session> sessions = SessionManager.getInstance().getOpenSessionsByCF(codiceFiscale);
        for (int i = 0; i < doseInviata.getNumGiorni(); i++ ) {
            data = data.plusDays(doseInviata.getRateGiorni());
            DoseConfezione doseConfezione= new DoseConfezione(new Confezione(Integer.parseInt(doseInviata.getDose().getCodice())), doseInviata.getDose().getQuantita(),
                    doseInviata.getDose().getUnita_misura(), doseInviata.getDose().getOrario(),
                    doseInviata.getDose().getDescrizione(), doseInviata.getDose().getInviante());
            terapiaDao.addDoseConfezione(doseConfezione, data, codiceFiscale);
            aggiornaSessioni(sessions, doseConfezione, data, codiceFiscale);
            //System.out.println(doseInviata.getDose().getCodice());
        }
    }

    private void aggiornaSessioni(List<Session> sessions, Dose dose, LocalDate data, String codiceFiscale){
        for (Session session : sessions) {
            Utente utente = session.getUtente();
            if (utente.isType() == Ruolo.Paziente){
                CalendarioTerapeutico calendarioTerapeutico = ((Paziente)utente).getCalendario();
                if (calendarioTerapeutico.esiste(data)){
                    calendarioTerapeutico.getTerapiaGiornaliera(data).addDose(dose);
                }
            }
        }


    }

    public void buildDosePrincipioAttivo(DoseInviata doseInviata, String codiceFiscale) throws DaoException {
        LocalDate data = doseInviata.getInizio();
        List<Session> sessions = SessionManager.getInstance().getOpenSessionsByCF(codiceFiscale);
        for (int i = 0; i < doseInviata.getNumGiorni(); i++ ) {
            data = data.plusDays(doseInviata.getRateGiorni());
            DosePrincipioAttivo dosePrincipioAttivo = new DosePrincipioAttivo(new PrincipioAttivo((String)doseInviata.getDose().getCodice()), doseInviata.getDose().getQuantita(),
                    doseInviata.getDose().getUnita_misura(), doseInviata.getDose().getOrario(),
                    doseInviata.getDose().getDescrizione(), doseInviata.getDose().getInviante());
            terapiaDao.addDosePrincipioAttivo(dosePrincipioAttivo, data, codiceFiscale);
            aggiornaSessioni(sessions, dosePrincipioAttivo, data, codiceFiscale);
        }
    }

    public List<Richiesta> getRichisteOfPaziente(Paziente paziente) throws DaoException{
        List<Richiesta> list = richiesteDao.getRichisteOfPaziente(paziente);
        for (Richiesta richiesta : list) {
            System.out.println(richiesta.getInviante().getCodiceFiscale() + "nell'aggiunta ifnormazioni di daofacade");
            Dottore inviante = utenteDao.getDottore(richiesta.getInviante().getCodiceFiscale());
            richiesta.setInviante(inviante);
            for (DoseInviata doseInviata : richiesta.getMedicinali()) {
                Dose dose = doseInviata.getDose();
                TipoDose tipo = dose.isType();
                switch (tipo){
                    case Confezione:
                        System.out.println("Confezione completata:" + dose.getCodice());
                        DoseConfezione doseConfezione = (DoseConfezione) dose;
                        doseConfezione.setConfezione(medicinaliDao.getConfezioneByCodiceAic(Integer.parseInt(doseConfezione.getCodice())));
                        doseConfezione.setInviante(inviante);
                        break;

                    case PrincipioAttivo:
                        DosePrincipioAttivo dosePrincipioAttivo = (DosePrincipioAttivo) dose;
                        dosePrincipioAttivo.setPrincipioAttivo(medicinaliDao.getPrincipioAttvoByCodiceAtc(dosePrincipioAttivo.getCodice()));
                        dosePrincipioAttivo.setInviante(inviante);
                        break;

                    default:
                        break;

                }
            }
        }
        return list;
    }
    public void deleteRichiesta(Richiesta richiesta) throws DaoException{
        richiesteDao.deleteRichiesta(richiesta);
        List<Session> sessions = SessionManager.getInstance().getOpenSessionsByCF(richiesta.getRicevente().getCodiceFiscale());
        for (Session session : sessions){
            Utente utente = session.getUtente();
            switch (utente.isType()){
                case Paziente -> {RichiestePendenti richiestePendenti = ((Paziente)utente).getRichiestePendenti();
                    richiestePendenti.deleteRichiesta(richiesta.getId());}
                default -> {}
            }
        }
    }
    public void addRichiesta(RichiestaBean richiesta) throws DaoException{
        Richiesta nuovaRichiesta = new Richiesta();
        nuovaRichiesta.setInvio(richiesta.getInvio());
        nuovaRichiesta.setRicevente(utenteDao.getPaziente(richiesta.getRicevente().getCodice_fiscale())); //da rivedere classe InformazioniUtente
        nuovaRichiesta.setInviante(utenteDao.getDottore(richiesta.getInviante().getCodice_fiscale()));
        for (DoseCostructor dose: richiesta.getDosi()){
            nuovaRichiesta.addDoseInviata(wrapDoseCostructor(dose));
        }
        richiesteDao.addRichiesta(nuovaRichiesta);
        List<Session> sessions = SessionManager.getInstance().getOpenSessionsByCF(richiesta.getRicevente().getCodice_fiscale());
        for (Session session : sessions){
            Utente utente = session.getUtente();
            switch (utente.isType()){
                case Paziente -> {RichiestePendenti richiestePendenti = ((Paziente)utente).getRichiestePendenti();
                    richiestePendenti.addRichiesta(nuovaRichiesta);}
                default -> {}
            }
        }
    }

    public Confezione getConfezioneByCodiceAic(int codice_aic) throws DaoException{
        return medicinaliDao.getConfezioneByCodiceAic(codice_aic);
    }

    public List<String> getNomiConfezioniByNomeParziale(String nome) throws DaoException{
        return medicinaliDao.getNomiConfezioniByNomeParziale(nome);
    }
    public List<Confezione> getConfezioniByNome(String nome) throws DaoException{
        return medicinaliDao.getConfezioniByNome(nome);
    }
    public List<String> getNomiPrincipioAttivoByNomeParziale(String nome) throws DaoException{
        return medicinaliDao.getNomiPrincipioAttivoByNomeParziale(nome);
    }
    public PrincipioAttivo getPrincipioAttvoByNome(String nome) throws DaoException{
        return medicinaliDao.getPrincipioAttvoByNome(nome);
    }
    public List<Confezione> getConfezioniByCodiceAtc(String codice_atc) throws DaoException{
        return medicinaliDao.getConfezioniByCodiceAtc(codice_atc);
    }
    public PrincipioAttivo getPrincipioAttvoByCodiceAtc(String codice_atc) throws DaoException{
        return medicinaliDao.getPrincipioAttvoByCodiceAtc(codice_atc);
    }

    public DoseInviata wrapDoseCostructor(DoseCostructor doseCostructor) throws DaoException{
        DoseInviata doseInviata;
        DoseBean doseBean = doseCostructor.getDose();
        switch (doseBean.getTipo()){
            case Confezione -> { Confezione confezione= medicinaliDao.getConfezioneByCodiceAic(Integer.parseInt(doseBean.getCodice()));
                DoseConfezione doseConfezione = new DoseConfezione(confezione, doseBean.getQuantita(), doseBean.getUnita_misura(), doseBean.getOrario(),
                        doseBean.getDescrizione_medica(), utenteDao.getDottore(doseBean.getInviante().getCodice_fiscale()));
                doseInviata = new DoseInviata(doseConfezione, doseCostructor.getNum_ripetizioni(), doseCostructor.getInizio(), doseCostructor.getRate_giorni());}
            case PrincipioAttivo -> {PrincipioAttivo principioAttivo = medicinaliDao.getPrincipioAttvoByCodiceAtc(doseBean.getCodice());
                DosePrincipioAttivo dosePrincipioAttivo = new DosePrincipioAttivo(principioAttivo, doseBean.getQuantita(), doseBean.getUnita_misura(), doseBean.getOrario(),
                        doseBean.getDescrizione_medica(), utenteDao.getDottore(doseBean.getInviante().getCodice_fiscale()));
                doseInviata = new DoseInviata(dosePrincipioAttivo, doseCostructor.getNum_ripetizioni(), doseCostructor.getInizio(), doseCostructor.getRate_giorni());}
            default -> {throw new DaoException("tipo errato");}
        }
        return doseInviata;
    }

    public Utente getInfoUtente(String codiceFiscale) throws DaoException{
        return utenteDao.getInfoUtente(codiceFiscale);
    }
 }
