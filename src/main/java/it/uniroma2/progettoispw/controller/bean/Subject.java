package it.uniroma2.progettoispw.controller.bean;

public abstract class Subject {
    public abstract void attach(Observer o);
    public abstract void detach(Observer o);
    protected abstract void notifica();
}
