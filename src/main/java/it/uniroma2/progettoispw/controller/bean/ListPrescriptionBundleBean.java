package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.controller.graphic.controller.Notificator;
import it.uniroma2.progettoispw.model.domain.SentPrescriptionBundle;
import it.uniroma2.progettoispw.model.domain.PendentBundle;

import java.util.ArrayList;
import java.util.List;

public class ListPrescriptionBundleBean extends Observer {
    private List<SentPrescriptionBundleBean> lista = new ArrayList<SentPrescriptionBundleBean>();
    private PendentBundle pendenti;
    private List<Notificator> notificators = new ArrayList<>();

    public ListPrescriptionBundleBean() {}

    public ListPrescriptionBundleBean(PendentBundle pendenti) {
        replace(pendenti.getRichieste());
        this.pendenti = pendenti;
    }

    public void replace(List<SentPrescriptionBundle> richieste){
        for (SentPrescriptionBundle sentPrescriptionBundle : richieste) {
            SentPrescriptionBundleBean sentPrescriptionBundleBean = new SentPrescriptionBundleBean(sentPrescriptionBundle);
            lista.add(sentPrescriptionBundleBean);
        }
    }

    public List<SentPrescriptionBundleBean> getLista() {
        return lista;
    }

    public void add(SentPrescriptionBundleBean bean) {
        lista.add(bean);
    }

    @Override
    public void update() {
        replace(pendenti.getRichieste());
        for (Notificator notificator : notificators) {
            notificator.notifica();
        }
    }

    @Override
    public void detach() {
        pendenti = null;
    }

    public void addNotificator(Notificator notificator) {
        notificators.add(notificator);
    }
}
