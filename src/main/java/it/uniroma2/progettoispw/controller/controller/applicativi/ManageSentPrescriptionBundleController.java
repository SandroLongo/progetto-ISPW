package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.ListPrescriptionBundleBean;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ManageSentPrescriptionBundleController {
    DaoFacade daoFacade = new DaoFacade();

    public ListPrescriptionBundleBean getPendingPrescriptionBundles(int code) {
        User user = SessionManager.getInstance().getSession(code).getUtente();
        ListPrescriptionBundleBean listPrescriptionBundleBean;
        if (Objects.requireNonNull(user.isType()) == Role.PATIENT) {
            PendentBundles pendentBundles = ((Patient) user).getRichiestePendenti();
            listPrescriptionBundleBean = new ListPrescriptionBundleBean(pendentBundles);
            pendentBundles.attach(listPrescriptionBundleBean);
        } else {
            throw new UnsupportedOperation("operazione non supportata");
        }
        return listPrescriptionBundleBean;
    }

    public void acceptPrescriptionBundle(int code, int codiceRichiesta) {
        User user = SessionManager.getInstance().getSession(code).getUtente();
        if (Objects.requireNonNull(user.isType()) == Role.PATIENT) {
            SentPrescriptionBundle sentPrescriptionBundle = ((Patient) user).getRichiestePendenti().getBundleById(codiceRichiesta);
            List<List<LocalDate>> dates = daoFacade.addDosesByBundle(sentPrescriptionBundle);
            List<Prescription> prescriptions = sentPrescriptionBundle.getMedicinali();
            int i = 0;
            for (List<LocalDate> dateList : dates) {
                Prescription prescription = prescriptions.get(i);
                for (LocalDate date : dateList) {
                    SessionManager.getInstance().aggiornaSessioni(user.getTaxCode(), prescription.getDose(), date);
                }
                i++;
            }
            daoFacade.deleteBundle(codiceRichiesta);
            SessionManager.getInstance().deleteBundle(sentPrescriptionBundle);
        } else {
            throw new UnsupportedOperation("operazione non supportata");
        }
    }

    public void rejectPrescriptionBundle(int code, int codiceRichiesta) {
        User user = SessionManager.getInstance().getSession(code).getUtente();
        if (Objects.requireNonNull(user.isType()) == Role.PATIENT) {
            SentPrescriptionBundle sentPrescriptionBundle = ((Patient) user).getRichiestePendenti().getBundleById(codiceRichiesta);
            SessionManager.getInstance().deleteBundle(sentPrescriptionBundle);
            daoFacade.deleteBundle(codiceRichiesta);
        } else {
            throw new UnsupportedOperation("operazione non supportata");
        }
    }
}
