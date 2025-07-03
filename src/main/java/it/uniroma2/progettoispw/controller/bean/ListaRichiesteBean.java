package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.controller.graphic.controller.Notificator;
import it.uniroma2.progettoispw.model.domain.Richiesta;
import it.uniroma2.progettoispw.model.domain.RichiestePendenti;

import java.util.ArrayList;
import java.util.List;

public class ListaRichiesteBean extends Observer {
    private List<RichiestaMandata> lista = new ArrayList<RichiestaMandata>();
    private RichiestePendenti pendenti;
    private List<Notificator> notificators = new ArrayList<>();

    public ListaRichiesteBean() {}

    public ListaRichiesteBean(RichiestePendenti pendenti) {
        replace(pendenti.getRichieste());
        this.pendenti = pendenti;
    }

    public void replace(List<Richiesta> richieste){
        for (Richiesta richiesta : richieste) {
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
