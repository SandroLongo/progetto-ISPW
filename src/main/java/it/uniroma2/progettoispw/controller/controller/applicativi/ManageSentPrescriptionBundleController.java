package it.uniroma2.progettoispw.controller.controller.applicativi;

import it.uniroma2.progettoispw.controller.bean.ListPrescriptionBundleBean;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.*;

import java.util.Objects;

public class ManageSentPrescriptionBundleController {
    DaoFacade daoFacade = new DaoFacade();

    public ListPrescriptionBundleBean getRichieste(int codice) {
        User user = SessionManager.getInstance().getSession(codice).getUtente();
        ListPrescriptionBundleBean listPrescriptionBundleBean;
        if (Objects.requireNonNull(user.isType()) == Role.PAZIENTE) {
            PendentBundle pendentBundle = ((Patient) user).getRichiestePendenti();
            listPrescriptionBundleBean = new ListPrescriptionBundleBean(pendentBundle);
            pendentBundle.attach(listPrescriptionBundleBean);
        } else {
            throw new UnsupportedOperation("operazione non supportata");
        }
        return listPrescriptionBundleBean;
    }

    public void accettaRichiesta(int codice, int codiceRichiesta) {
        User user = SessionManager.getInstance().getSession(codice).getUtente();
        if (Objects.requireNonNull(user.isType()) == Role.PAZIENTE) {
            SentPrescriptionBundle sentPrescriptionBundle = ((Patient) user).getRichiestePendenti().getRichiestaByid(codiceRichiesta);
            daoFacade.addTerapiaByRichiesta(sentPrescriptionBundle);
            ((Patient) user).getRichiestePendenti().deleteRichiesta(codiceRichiesta);
            daoFacade.deleteRichiesta(codiceRichiesta);
            SessionManager.getInstance().deleteRichiesta(sentPrescriptionBundle);
        } else {
            throw new UnsupportedOperation("operazione non supportata");
        }
    }

    public void rifiutaRichiesta(int codice, int codiceRichiesta) {
        User user = SessionManager.getInstance().getSession(codice).getUtente();
        if (Objects.requireNonNull(user.isType()) == Role.PAZIENTE) {
            SentPrescriptionBundle sentPrescriptionBundle = ((Patient) user).getRichiestePendenti().getRichiestaByid(codiceRichiesta);
            SessionManager.getInstance().deleteRichiesta(sentPrescriptionBundle);
        } else {
            throw new UnsupportedOperation("operazione non supportata");
        }
    }
}
