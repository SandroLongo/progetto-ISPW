package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.controller.graphic.controller.Notificator;
import it.uniroma2.progettoispw.model.domain.Richiesta;
import it.uniroma2.progettoispw.model.domain.RichiestePendenti;

import java.util.ArrayList;
import java.util.List;

public class ListaRichiesteBean extends Observer {
    private List<RichiestaMandata> lista = new ArrayList<RichiestaMandata>();
    private RichiestePendenti pendenti;
    private Notificator notificator;

    public ListaRichiesteBean() {}

    public ListaRichiesteBean(RichiestePendenti pendenti) {
        replace(pendenti.getRichieste());
        this.pendenti = pendenti;
    }

    public void replace(List<Richiesta> richieste){
        for (Richiesta richiesta : richieste) {
            System.out.println("aggiunto in listarichiestebean "+ richiesta.getId());
            RichiestaMandata richiestaMandata = new RichiestaMandata(richiesta);
            lista.add(richiestaMandata);
        }
    }

    public List<RichiestaMandata> getLista() {
        return lista;
    }

    public void add(RichiestaMandata bean) {
        lista.add(bean);
    }

    @Override
    public void update() {
        replace(pendenti.getRichieste());
        notificator.notifica();
    }

    public Notificator getNotificator() {
        return notificator;
    }

    public void setNotificator(Notificator notificator) {
        this.notificator = notificator;
    }
}
