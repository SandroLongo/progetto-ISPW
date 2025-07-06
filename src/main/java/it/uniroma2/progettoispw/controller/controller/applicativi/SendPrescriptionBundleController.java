package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.PrescriptionBean;
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
            prescriptionBundleBean.setInviante(new InformazioniUtente(user));
            SentPrescriptionBundle sentPrescriptionBundle = daoFacade.addRichiesta(prescriptionBundleBean);
            SessionManager.getInstance().addRichiesta(sentPrescriptionBundle);
        } else {
            throw new UnsupportedOperation("operazione non supportata");
        }
    }
}
