package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.controller.controller.applicativi.Config;

import java.time.LocalDate;
import java.time.LocalTime;

public class FinalStepBean {
    private LocalDate startDate;
    private int repetitionNumber;
    private int dayRate;
    private int quantity;
    private String measurementUnit;
    private LocalTime scheduledTime;
    private String description;

    public FinalStepBean(LocalDate startDate, int repetitionNumber, int dayRate, int quantity, String measurementUnit, LocalTime scheduledTime, String description) {
        this.startDate = startDate;
        this.repetitionNumber = repetitionNumber;
        this.dayRate = dayRate;
        this.quantity = quantity;
        this.measurementUnit = measurementUnit;
        this.scheduledTime = scheduledTime;
        this.description = description;
    }

    public FinalStepBean() {}

    public boolean isComplete() {
        return startDate != null && repetitionNumber > 0 && dayRate > 0 && quantity > 0 && description != null && measurementUnit != null && scheduledTime != null;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) throws InvalidFormatException {
        if (startDate == null) {
            throw new InvalidFormatException("startDate is null");
        }
        this.startDate = startDate;
    }

    public int getRepetitionNumber() {
        return repetitionNumber;
    }

    public void setRepetitionNumber(int repetitionNumber) throws InvalidFormatException {
        if (repetitionNumber < 0 || repetitionNumber > Config.MAX_REPETITION_ALLOWED) {
            throw new InvalidFormatException("repetitionNumber is out of range");
        }
        this.repetitionNumber = repetitionNumber;
    }

    public int getDayRate() {
        return dayRate;
    }

    public void setDayRate(int dayRate) throws InvalidFormatException {
        if (dayRate < 0 || dayRate > Config.MAX_RATE_ALLOWED) {
            throw new InvalidFormatException("dayRate is out of range");
        }
        this.dayRate = dayRate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) throws InvalidFormatException {
        if (quantity < 0 || quantity > Config.MAX_QUANTITY_ALLOWED) {
            throw new InvalidFormatException("quantity is out of range");
        }
        this.quantity = quantity;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) throws InvalidFormatException {
        if (measurementUnit == null || measurementUnit.isEmpty() || measurementUnit.length() > Config.MAX_MEAUSUREMENT_UNIT_LENGHT) {
            throw new InvalidFormatException("measurementUnit is out of range");
        }
        this.measurementUnit = measurementUnit;
    }

    public LocalTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalTime scheduledTime) throws InvalidFormatException {
        if (scheduledTime == null) {
            throw new InvalidFormatException("scheduledTime is null");
        }
        this.scheduledTime = scheduledTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isEmpty() || description.length() > Config.MAX_DESCRIPTION_LENGTH) {
            throw new InvalidFormatException("description empty or too long");
        }
        this.description = description;
    }
}
