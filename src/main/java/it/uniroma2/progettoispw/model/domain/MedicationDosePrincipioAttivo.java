package it.uniroma2.progettoispw.model.domain;

import java.time.LocalTime;

public class MedicationDosePrincipioAttivo extends MedicationDose {
    private ActiveIngridient activeIngridient;

    public MedicationDosePrincipioAttivo(ActiveIngridient activeIngridient, int quantita, String unitaMisura, LocalTime orario, String descrizione, User inviante){
        super(quantita, unitaMisura, orario, descrizione, inviante);
        this.activeIngridient = activeIngridient;
    }

    public MedicationDosePrincipioAttivo() {
        super();
    }

    public MedicationDosePrincipioAttivo(ActiveIngridient activeIngridient) {
        this.activeIngridient = activeIngridient;
    }

    public ActiveIngridient getPrincipioAttivo() {
        return activeIngridient;
    }
    public void setPrincipioAttivo(ActiveIngridient activeIngridient) {
        this.activeIngridient = activeIngridient;
    }

    @Override
    public String getCodice() {
        return activeIngridient.getCodiceAtc();
    }

    @Override
    public MediccationType isType() {
        return MediccationType.PRINCIPIOATTIVO;
    }

    @Override
    public String getNome() {
        return activeIngridient.getNome();
    }
}
