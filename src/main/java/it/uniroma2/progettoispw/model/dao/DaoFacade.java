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
                    case CONFEZIONE:
                        DoseConfezione doseConfezione= (DoseConfezione) dose;
                        int codiceAic = Integer.parseInt(doseConfezione.getCodice());
                        //System.out.println(codiceAic + " in daofacade");
                        doseConfezione.setConfezione(medicinaliDao.getConfezioneByCodiceAic(codiceAic));
                        break;
                    case PRINCIPIOATTIVO:
                        DosePrincipioAttivo dosePrincipioAttivo = (DosePrincipioAttivo) dose;
                        String codiceAtc = dosePrincipioAttivo.getCodice();
                        //System.out.println(codiceAtc + " in daofacade");
                        dosePrincipioAttivo.setPrincipioAttivo(medicinaliDao.getPrincipioAttvoByCodiceAtc(codiceAtc));
                        break;
                    default: throw new DaoException("errore");
                }
                dose.setInviante(utenteDao.getDottore(dose.getInviante().getCodiceFiscale()));
            }
        }
        return terapiaGiornaliera;
    }

    public Paziente getPaziente(String codiceFiscale) throws DaoException{
        Paziente paziente = utenteDao.getPaziente(codiceFiscale);
        paziente.setRichiestePendenti(richiesteDao.getRichisteOfPaziente(paziente));
        return paziente;
    }

    public Dottore getDottore(String codiceFiscale) throws DaoException{
        return utenteDao.getDottore(codiceFiscale);
    }

    public void addPaziente(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                            String pass) throws DaoException{
        //System.out.println("in add paziente");
        utenteDao.addPaziente(codiceFiscale, nome, cognome, nascita, email, telefono, pass);
        //System.out.println("fatto  add paziente");
    }

    public int addDottore(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                          String pass) throws DaoException{
        return utenteDao.addDottore(codiceFiscale, nome, cognome, nascita, email, telefono, pass);
    }

    public Utente login(String codiceFiscale, String password, int isDottore, int codiceDottore) throws DaoException{
        Utente utente = utenteDao.login(codiceFiscale, password, isDottore, codiceDottore);
        if (Objects.requireNonNull(utente.isType()) == Ruolo.PAZIENTE) {
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
            if (dose.isType() == TipoDose.CONFEZIONE){
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
                    doseInviata.getDose().getUnitaMisura(), doseInviata.getDose().getOrario(),
                    doseInviata.getDose().getDescrizione(), doseInviata.getDose().getInviante());
            terapiaDao.addDoseConfezione(doseConfezione, data, codiceFiscale);
            aggiornaSessioni(sessions, doseConfezione, data);
            //System.out.println(doseInviata.getDose().getCodice());
        }
    }

    private void aggiornaSessioni(List<Session> sessions, Dose dose, LocalDate data){
        for (Session session : sessions) {
            Utente utente = session.getUtente();
            if (utente.isType() == Ruolo.PAZIENTE){
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
            DosePrincipioAttivo dosePrincipioAttivo = new DosePrincipioAttivo(new PrincipioAttivo(doseInviata.getDose().getCodice()), doseInviata.getDose().getQuantita(),
                    doseInviata.getDose().getUnitaMisura(), doseInviata.getDose().getOrario(),
                    doseInviata.getDose().getDescrizione(), doseInviata.getDose().getInviante());
            terapiaDao.addDosePrincipioAttivo(dosePrincipioAttivo, data, codiceFiscale);
            aggiornaSessioni(sessions, dosePrincipioAttivo, data);
        }
    }

    public List<Richiesta> getRichisteOfPaziente(Paziente paziente) throws DaoException{
        List<Richiesta> list = richiesteDao.getRichisteOfPaziente(paziente);
        for (Richiesta richiesta : list) {
            //System.out.println(richiesta.getInviante().getCodiceFiscale() + "nell'aggiunta ifnormazioni di daofacade");
            Dottore inviante = utenteDao.getDottore(richiesta.getInviante().getCodiceFiscale());
            richiesta.setInviante(inviante);
            for (DoseInviata doseInviata : richiesta.getMedicinali()) {
                Dose dose = doseInviata.getDose();
                TipoDose tipo = dose.isType();
                switch (tipo){
                    case CONFEZIONE:
                        //System.out.println("CONFEZIONE completata:" + dose.getCodice());
                        DoseConfezione doseConfezione = (DoseConfezione) dose;
                        doseConfezione.setConfezione(medicinaliDao.getConfezioneByCodiceAic(Integer.parseInt(doseConfezione.getCodice())));
                        doseConfezione.setInviante(inviante);
                        break;

                    case PRINCIPIOATTIVO:
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
    public void deleteRichiesta(int id) throws DaoException{
        richiesteDao.deleteRichiesta(id);
    }
    public void addRichiesta(RichiestaBean richiesta) throws DaoException{

    }

    public Confezione getConfezioneByCodiceAic(int codiceAic) throws DaoException{
        return medicinaliDao.getConfezioneByCodiceAic(codiceAic);
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
    public List<Confezione> getConfezioniByCodiceAtc(String codiceAtc) throws DaoException{
        return medicinaliDao.getConfezioniByCodiceAtc(codiceAtc);
    }
    public PrincipioAttivo getPrincipioAttvoByCodiceAtc(String codiceAtc) throws DaoException{
        return medicinaliDao.getPrincipioAttvoByCodiceAtc(codiceAtc);
    }

    public DoseInviata wrapDoseCostructor(DoseCostructor doseCostructor) throws DaoException{
        DoseInviata doseInviata;
        DoseBean doseBean = doseCostructor.getDose();
        switch (doseBean.getTipo()){
            case CONFEZIONE -> { Confezione confezione= medicinaliDao.getConfezioneByCodiceAic(Integer.parseInt(doseBean.getCodice()));
                DoseConfezione doseConfezione = new DoseConfezione(confezione, doseBean.getQuantita(), doseBean.getUnitaMisura(), doseBean.getOrario(),
                        doseBean.getDescrizione(), utenteDao.getDottore(doseBean.getInviante().getCodiceFiscale()));
                doseInviata = new DoseInviata(doseConfezione, doseCostructor.getNumRipetizioni(), doseCostructor.getInizio(), doseCostructor.getRateGiorni());}
            case PRINCIPIOATTIVO -> {PrincipioAttivo principioAttivo = medicinaliDao.getPrincipioAttvoByCodiceAtc(doseBean.getCodice());
                DosePrincipioAttivo dosePrincipioAttivo = new DosePrincipioAttivo(principioAttivo, doseBean.getQuantita(), doseBean.getUnitaMisura(), doseBean.getOrario(),
                        doseBean.getDescrizione(), utenteDao.getDottore(doseBean.getInviante().getCodiceFiscale()));
                doseInviata = new DoseInviata(dosePrincipioAttivo, doseCostructor.getNumRipetizioni(), doseCostructor.getInizio(), doseCostructor.getRateGiorni());}
            default -> throw new DaoException("tipo errato");
        }
        return doseInviata;
    }

    public Utente getInfoUtente(String codiceFiscale) throws DaoException{
        return utenteDao.getInfoUtente(codiceFiscale);
    }
 }
