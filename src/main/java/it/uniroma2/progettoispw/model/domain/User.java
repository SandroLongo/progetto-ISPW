package it.uniroma2.progettoispw.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class User implements Serializable {
    private String taxCode;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;

    protected User(User user){
        this.taxCode = user.getTaxCode();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.birthDate = user.getBirthDate();
    }


    protected User(String taxCode, String name, String surname, LocalDate birthDate, String email, String phoneNumber) {
        this.taxCode = taxCode;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;

    }

    protected User(String taxCode) {
        this.taxCode = taxCode;
    }

    public abstract Role isType();

    public String getTaxCode() {
        return taxCode;
    }
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public abstract void logout();
}
