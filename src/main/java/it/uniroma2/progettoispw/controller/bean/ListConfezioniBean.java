package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Confezione;

import java.util.ArrayList;
import java.util.List;

public class ListConfezioniBean {
    private List<ConfezioniInformationBean> listConfezioni;

    public ListConfezioniBean() {
        listConfezioni = new ArrayList<ConfezioniInformationBean>();
    }
    public ListConfezioniBean(List<ConfezioniInformationBean> listConfezioni) {
        this.listConfezioni = listConfezioni;
    }
    public List<ConfezioniInformationBean> getListConfezioni() {
        return listConfezioni;
    }
}
