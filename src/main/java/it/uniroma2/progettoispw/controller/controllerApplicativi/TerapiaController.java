package it.uniroma2.progettoispw.controller.controllerApplicativi;

import it.uniroma2.progettoispw.controller.bean.FinalStepBean;
import it.uniroma2.progettoispw.model.dao.DaoFacade;
import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;

public class TerapiaController {
    Utente utente;
    private TerapiaGiornaliera terapiaGiornaliera;
    private DoseInviata doseInviata;
    private final DaoFacade daoFacade= new DaoFacade();

    public TerapiaController() {
        utente = SessionManager.getInstance().getCurrentUtente();
    }

    public void setConfezione(Confezione confezione) {
        this.doseInviata.setDose(new DoseConfezione());
        ((DoseConfezione)this.doseInviata.getDose()).setConfezione(confezione);
    }

    public void setPrincipio(PrincipioAttivo principio) {
        this.doseInviata.setDose(new DosePrincipioAttivo());
        ((DosePrincipioAttivo)this.doseInviata.getDose()).setPrincipioAttivo(principio);
    }

    public void createNewDoseInviata(){
        doseInviata = new DoseInviata();
    }
    public void setFinalInformation(FinalStepBean finalStep) {
        this.doseInviata.setInizio(finalStep.getInizio());
        this.doseInviata.setNumGiorni(finalStep.getNum_ripetizioni());
        this.doseInviata.setRateGiorni(finalStep.getRate_giorni());
        this.doseInviata.getDose().setDescrizione(finalStep.getDescrizione_medica());
        this.doseInviata.getDose().setOrario(finalStep.getOrario());
        this.doseInviata.getDose().setQuantita(finalStep.getQuantita());
        this.doseInviata.getDose().setUnita_misura(finalStep.getUnita_misura());
        this.doseInviata.getDose().setAssunta(false);
        this.doseInviata.getDose().setInviante(new Dottore(utente.getCodiceFiscale()));
    }

    public void setOther() {}
    public TerapiaGiornaliera switchTo(LocalDate date) {
        terapiaGiornaliera = daoFacade.getTerapiaGiornaliera(utente.getCodiceFiscale(), date);
        return terapiaGiornaliera;
    }

    public void deleteDose(Dose<?> dose){
        terapiaGiornaliera.removeDose(dose);
    }

    public void loadDoseInviata() {
        switch (doseInviata.getDose().isType()){
            case Confezione -> daoFacade.buildDoseConfezione(doseInviata, utente.getCodiceFiscale());
            case PrincipioAttivo -> daoFacade.buildDosePrincipioAttivo(doseInviata, utente.getCodiceFiscale());
            default -> throw new IllegalArgumentException("Dose inviata non valida");
        }
    }
}
