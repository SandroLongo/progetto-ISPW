package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.dao.dbfiledao.MedicinaliDbDao;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
import java.util.List;

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
        for (List<Dose<?>> dosiOrario: terapiaGiornaliera.getDosiPerOrario().values()){
            for (Dose<?> dose : dosiOrario){
                TipoDose tipo = dose.isType();
                switch (tipo){
                    case Confezione:
                        DoseConfezione doseConfezione= (DoseConfezione) dose;
                        int codiceAic = doseConfezione.getCodice();
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
        return utenteDao.getPaziente(codice_fiscale);
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

    public void addDottore(String codice_fiscale, String nome, String cognome, LocalDate nascita, String email, String telefono,
                           String pass, int codice) throws DaoException{
        utenteDao.addDottore(codice_fiscale, nome, cognome, nascita, email, telefono, pass, codice);
    }

    public Utente login(String codice_fiscale, String password, int is_dottore, int codice_dottore) throws DaoException{
        return utenteDao.login(codice_fiscale, password, is_dottore, codice_dottore);
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
        for (int i = 0; i < doseInviata.getNumGiorni(); i++ ) {
            data = data.plusDays(doseInviata.getRateGiorni());
            terapiaDao.addDoseConfezione(new DoseConfezione(new Confezione((int)doseInviata.getDose().getCodice()), doseInviata.getDose().getQuantita(),
                    doseInviata.getDose().getUnita_misura(), doseInviata.getDose().getOrario(),
                    doseInviata.getDose().getDescrizione(), doseInviata.getDose().getInviante()), data, codiceFiscale);
            //System.out.println(doseInviata.getDose().getCodice());
        }
    }

    public void buildDosePrincipioAttivo(DoseInviata doseInviata, String codiceFiscale) throws DaoException {
        LocalDate data = doseInviata.getInizio();
        for (int i = 0; i < doseInviata.getNumGiorni(); i++ ) {
            data = data.plusDays(doseInviata.getRateGiorni());
            terapiaDao.addDosePrincipioAttivo(new DosePrincipioAttivo(new PrincipioAttivo((String)doseInviata.getDose().getCodice()), doseInviata.getDose().getQuantita(),
                    doseInviata.getDose().getUnita_misura(), doseInviata.getDose().getOrario(),
                    doseInviata.getDose().getDescrizione(), doseInviata.getDose().getInviante()), data, codiceFiscale);
        }
    }

    public List<Richiesta> getRichisteOfPaziente(Paziente paziente) throws DaoException{
        List<Richiesta> list = richiesteDao.getRichisteOfPaziente(paziente);
        for (Richiesta richiesta : list) {
            Dottore inviante = utenteDao.getDottore(richiesta.getInviante().getCodiceFiscale());
            richiesta.setInviante(inviante);
            for (DoseInviata doseInviata : richiesta.getMedicinali()) {
                Dose<?> dose = doseInviata.getDose();
                TipoDose tipo = dose.isType();
                switch (tipo){
                    case Confezione:
                        DoseConfezione doseConfezione = (DoseConfezione) dose;
                        doseConfezione.setConfezione(medicinaliDao.getConfezioneByCodiceAic(doseConfezione.getCodice()));
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
    }
    public void addRichiesta(Richiesta richiesta) throws DaoException{
        richiesteDao.addRichiesta(richiesta);
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
 }
