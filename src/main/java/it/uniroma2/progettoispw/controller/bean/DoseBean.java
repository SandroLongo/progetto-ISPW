package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.controller.controller.applicativi.Config;
import it.uniroma2.progettoispw.model.domain.MedicationDose;
import it.uniroma2.progettoispw.model.domain.MedicationType;

import java.time.LocalTime;

public class DoseBean {
    private String id;
    private int quantity;
    private String meausurementUnit;
    private LocalTime scheduledTime;
    private String description;
    private UserInformation sender;
    private String name;
    private MedicationType type;

    public DoseBean(MedicationType type) {
        this.type = type;
    }

    public DoseBean(MedicationDose medicationDose) {
        this.id = medicationDose.getId();
        this.meausurementUnit = medicationDose.getMeasurementUnit();
        this.scheduledTime = medicationDose.getScheduledTime();
        this.quantity = medicationDose.getQuantity();
        this.description = medicationDose.getDescription();
        this.name = medicationDose.getName();
        this.type = medicationDose.isType();
        this.sender = new UserInformation(medicationDose.getSender());
    }

    public DoseBean() {
    }

    @Override
    public String toString() {
        return "type: " + type + ", name: " + name +", quantity: " + quantity + " " + meausurementUnit + ", description: " + description;
    }

    public boolean isCompleate(){
        return id != null && quantity != 0 && meausurementUnit != null && scheduledTime != null && description != null && name != null && type != null;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) throws IllegalArgumentException {
        if (quantity <= 0 || quantity > Config.MAX_QUANTITY_ALLOWED) {
            throw new IllegalArgumentException("quantity too large or negative");
        }
        this.quantity = quantity;
    }

    public String getMeausurementUnit(){
        if (meausurementUnit == null || meausurementUnit.isEmpty() || meausurementUnit.length() > Config.MAX_MEAUSUREMENT_UNIT_LENGHT) {
            throw new IllegalArgumentException("meaurementUnit empty or too long");
        }
        return meausurementUnit;
    }

    public void setMeausurementUnit(String meausurementUnit) throws IllegalArgumentException{
        if (meausurementUnit == null || meausurementUnit.isEmpty() || meausurementUnit.length() > 50) {
            throw new IllegalArgumentException("meaurementUnit empty or too long");
        }
        this.meausurementUnit = meausurementUnit;
    }

    public LocalTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalTime scheduledTime) throws IllegalArgumentException{
        if (scheduledTime == null) {
            throw new IllegalArgumentException("scheduledTime empty");
        }
        this.scheduledTime = scheduledTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalArgumentException{
        if (description == null || description.isEmpty() || description.length() > Config.MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("description empty or too long");
        }
        this.description = description;
    }

    public UserInformation getSender() {
        return sender;
    }

    public void setSender(UserInformation sender) throws IllegalArgumentException{
        if (sender == null || !sender.isCompleate()) {
            throw new IllegalArgumentException("sender empty");
        }
        this.sender = sender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException{
        if (name == null || name.isEmpty() || name.length() > Config.MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("name empty or too long");
        }
        this.name = name;
    }

    public MedicationType getType() {
        return type;
    }

    public void setType(MedicationType type) throws IllegalArgumentException{
        if (type == null) {
            throw new IllegalArgumentException("type empty");
        }
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws IllegalArgumentException{
        if (type == null){
            throw new IllegalArgumentException("type still empty");
        }
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("id empty");
        }
        switch (type){
            case ACRIVEINGREDIENT -> {
                if (id.length() > Config.MAX_ACTIVE_INGRIDIENT_ID_LENGHT) {
                    throw new IllegalArgumentException("id too long");
                }
            }
            case MEDICINALPRODUCT -> {
                if (id.length() > Config.MAX_MODICINAL_PRODUCT_ID_LENGHT) {
                    throw new IllegalArgumentException("id too long");
                } else {
                    try {
                        Integer.parseInt(id);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("id not a number");
                    }
                }
            }
            default -> throw new IllegalArgumentException("type non valid");
        }
        this.id = id;
    }
}
