package it.uniroma2.progettoispw.model.domain;

import java.time.LocalTime;

public class MedicationDose {
    private Medication medication;
    private int quantity;
    private String measurementUnit;
    private LocalTime scheduledTime;
    private String description;
    private User sender;

    public MedicationDose() {

    }

    public MedicationDose(Medication medication, int quantity, String meausurementUnit, LocalTime scheduledTime, String description, User sender) {
        this.medication = medication;
        this.quantity = quantity;
        this.measurementUnit = meausurementUnit;
        this.scheduledTime = scheduledTime;
        this.description = description;
        this.sender = sender;
    }

    public Medication getMedication() {
        return medication;
    }

    public String getId(){
        return medication.getId();
    }
    public MedicationType isType(){
        return medication.getType();
    }
    public String getName(){
        return medication.getName();
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public LocalTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
