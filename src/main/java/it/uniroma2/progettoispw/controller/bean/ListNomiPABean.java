package it.uniroma2.progettoispw.controller.bean;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ListNomiPABean {
    private List<String> nomiPa = new ArrayList<String>();

    public ListNomiPABean() {}

    public ListNomiPABean(List<String> nomiPa) {
        this.nomiPa = nomiPa;
    }

    public List<String> getNomiPa() {
        return nomiPa;
    }
}
