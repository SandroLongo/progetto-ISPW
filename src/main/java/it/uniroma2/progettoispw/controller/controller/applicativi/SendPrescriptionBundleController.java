package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.UserInformation;
import it.uniroma2.progettoispw.controller.bean.PrescriptionBundleBean;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
import java.util.Objects;

public class SendPrescriptionBundleController implements Controller{
    DaoFacade daoFacade = new DaoFacade();

    public UserInformation getPatientInformation(int code, String taxCode) {

        if (SessionManager.getInstance().exists(code)) {
            User user = daoFacade.getInfoUtente(taxCode);
            return new UserInformation(user.getTaxCode(), user.getName(), user.getSurname(),
                    user.getEmail(), user.getPhoneNumber(), user.getBirthDate());
        } else {
            throw new UnsupportedOperation("sessione di login non valida");
        }
    }


    public void send(int code, PrescriptionBundleBean prescriptionBundleBean) {
        User user = SessionManager.getInstance().getSession(code).getUtente();
        prescriptionBundleBean.setSender(new UserInformation(user));
        prescriptionBundleBean.setSubmissionDate(LocalDate.now());
        if (Objects.requireNonNull(user.isType()) == Role.DOCTOR) {
            prescriptionBundleBean.setSender(new UserInformation(user));
            SentPrescriptionBundle sentPrescriptionBundle = daoFacade.addRichiesta(prescriptionBundleBean);
            SessionManager.getInstance().addRichiesta(sentPrescriptionBundle);
        } else {
            throw new UnsupportedOperation("operazione non supportata");
        }
    }
}
