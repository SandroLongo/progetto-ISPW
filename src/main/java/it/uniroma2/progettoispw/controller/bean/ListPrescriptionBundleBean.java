package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.controller.graphic.controller.Notificator;
import it.uniroma2.progettoispw.model.domain.Observer;
import it.uniroma2.progettoispw.model.domain.SentPrescriptionBundle;
import it.uniroma2.progettoispw.model.domain.PendentBundles;

import java.util.ArrayList;
import java.util.List;

public class ListPrescriptionBundleBean extends Observer {
    private List<SentPrescriptionBundleBean> list = new ArrayList<SentPrescriptionBundleBean>();
    private PendentBundles pending;
    private List<Notificator> notificators = new ArrayList<>();

    public ListPrescriptionBundleBean() {}

    public ListPrescriptionBundleBean(PendentBundles pending) {
        replace(pending.getPending());
        this.pending = pending;
    }

    public void replace(List<SentPrescriptionBundle> richieste){
        list.clear();
        for (SentPrescriptionBundle sentPrescriptionBundle : richieste) {
            SentPrescriptionBundleBean sentPrescriptionBundleBean = new SentPrescriptionBundleBean(sentPrescriptionBundle);
            list.add(sentPrescriptionBundleBean);
        }
    }

    public List<SentPrescriptionBundleBean> getList() {
        return list;
    }

    @Override
    public void update() {
        replace(pending.getPending());
        for (Notificator notificator : notificators) {
            notificator.notifyChanges();
        }
    }

    @Override
    public void detach() {
        pending = null;
    }

    public void addNotificator(Notificator notificator) {
        notificators.add(notificator);
    }
}
