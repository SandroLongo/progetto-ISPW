package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Prescription;

import java.time.LocalDate;

public class PrescriptionBean {
    private DoseBean dose;
    private LocalDate startDate;
    private int repetitionNumber;
    private int dayRate;

    public PrescriptionBean() {
    }

    public PrescriptionBean(Prescription prescription) {
        this.dose = new DoseBean(prescription.getDose());
        this.startDate = prescription.getInizio();
        this.repetitionNumber = prescription.getNumGiorni();
        this.dayRate = prescription.getRateGiorni();
    }

    public boolean isComplete(){
        return dose.isCompleate() && startDate != null && repetitionNumber > 0 && dayRate > 0;
    }

    @Override
    public String toString() {
        return dose.toString() + "startDate: " + startDate.toString() + "per:  " + repetitionNumber + "giorni, ogni: " + dayRate + "giorni";
    }

    public DoseBean getDose() {
        return dose;
    }

    public void setDose(DoseBean dose) throws IllegalArgumentException{
        if (dose == null) {}
        this.dose = dose;
    }

    public void setLastInformation(FinalStepBean lastInformation)throws IllegalArgumentException{
        if (!lastInformation.isComplete()) {
            throw new IllegalArgumentException("information incompleate");
        }
        this.startDate = lastInformation.getStartDate();
        this.repetitionNumber = lastInformation.getRepetitionNumber();
        this.dayRate = lastInformation.getDayRate();
        this.dose.setDescription(lastInformation.getDescription());
        this.dose.setQuantity(lastInformation.getQuantity());
        this.dose.setMeausurementUnit(lastInformation.getMeasurementUnit());
        this.dose.setScheduledTime(lastInformation.getScheduledTime());
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getRepetitionNumber() {
        return repetitionNumber;
    }

    public int getDayRate() {
        return dayRate;
    }
}
