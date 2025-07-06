package it.uniroma2.progettoispw.controller.bean;

import java.util.ArrayList;
import java.util.List;

public class ListActiveIngridientName {
    private List<String> nomiPa = new ArrayList<String>();

    public ListActiveIngridientName() {}

    public ListActiveIngridientName(List<String> nomiPa) {
        this.nomiPa = nomiPa;
    }

    public List<String> getNomiPa() {
        return nomiPa;
    }
}
