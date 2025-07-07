package it.uniroma2.progettoispw.model.domain;

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
    protected void notifiyChanges(){
        for(Observer o : observers){
            o.update();
        }
    }

    public void detachAll(){
        for(Observer o : observers){
            o.detach();
        }
        observers.clear();
    }
}
