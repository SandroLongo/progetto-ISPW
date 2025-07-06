package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.Prescription;
import it.uniroma2.progettoispw.controller.bean.InformazioniUtente;
import it.uniroma2.progettoispw.controller.bean.PrescriptionBundleBean;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.dao.DaoFactory;
import it.uniroma2.progettoispw.model.dao.PrescriptionBundleDao;
import it.uniroma2.progettoispw.model.dao.UserDao;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
import java.util.Objects;

public class SendPrescriptionBundleController implements Controller{
    DaoFacade daoFacade = new DaoFacade();

    public InformazioniUtente getInformazioniPaziente(int codice, String cf) {

        if (SessionManager.getInstance().esiste(codice)) {
            User user = daoFacade.getInfoUtente(cf);
            return new InformazioniUtente(user.getCodiceFiscale(), user.getNome(), user.getCognome(),
                    user.getEmail(), user.getTelefono(), user.getDataNascita());
        } else {
            throw new UnsupportedOperation("sessione di login non valida");
        }
    }


    public void invia(int codice, PrescriptionBundleBean prescriptionBundleBean) {
        User user = SessionManager.getInstance().getSession(codice).getUtente();
        prescriptionBundleBean.setInviante(new InformazioniUtente(user));
        prescriptionBundleBean.setInvio(LocalDate.now());
        if (Objects.requireNonNull(user.isType()) == Role.DOTTORE) {
            SentPrescriptionBundle nuovaSentPrescriptionBundle = new SentPrescriptionBundle();
            nuovaSentPrescriptionBundle.setInvio(prescriptionBundleBean.getInvio());
            UserDao userDao = DaoFactory.getIstance().getUtenteDao();
            nuovaSentPrescriptionBundle.setRicevente(userDao.getPaziente(prescriptionBundleBean.getRicevente().getCodiceFiscale())); //da rivedere classe InformazioniUtente
            nuovaSentPrescriptionBundle.setInviante(userDao.getDottore(prescriptionBundleBean.getInviante().getCodiceFiscale()));
            for (Prescription dose : prescriptionBundleBean.getDosi()) {
                nuovaSentPrescriptionBundle.addDoseInviata(daoFacade.wrapDoseCostructor(dose));
            }
            PrescriptionBundleDao prescriptionBundleDao = DaoFactory.getIstance().getRichiesteDao();
            int id = prescriptionBundleDao.addRichiesta(nuovaSentPrescriptionBundle);
            nuovaSentPrescriptionBundle.setId(id);
            SessionManager.getInstance().addRichiesta(nuovaSentPrescriptionBundle);
        } else {
            throw new UnsupportedOperation("operazione non supportata");
        }
    }
}
