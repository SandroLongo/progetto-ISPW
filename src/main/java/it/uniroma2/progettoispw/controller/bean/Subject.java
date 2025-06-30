package it.uniroma2.progettoispw.controller.bean;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    List<Observer> observers = new ArrayList<Observer>();

    public void attach(Observer o){
        observers.add(o);
    }
    public void detach(Observer o){
        observers.remove(o);
    }
    protected void notifica(){
        for(Observer o : observers){
            o.update();
        }
    }
}
