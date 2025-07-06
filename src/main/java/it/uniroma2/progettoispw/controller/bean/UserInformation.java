package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.User;

import java.time.LocalDate;

public class UserInformation {
    private String taxCode;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;

    public UserInformation(String taxCode, String name, String surname, String email, String phoneNumber, LocalDate birthDate) {
        this.taxCode = taxCode;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public UserInformation(User user) {
        name = user.getName();
        surname = user.getSurname();
        email = user.getEmail();
        phoneNumber = user.getPhoneNumber();
        birthDate = user.getBirthDate();
        taxCode = user.getTaxCode();
    }

    public String getTaxCode() {
        return taxCode;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
        return "taxCode: " + taxCode + " name: " + name + " surname: " + surname + " email: " + email + " phoneNumber: " + phoneNumber + " birthDate: " + birthDate;
    }

    public boolean isCompleate() {
        return taxCode != null && name != null && surname != null && email != null && phoneNumber != null && birthDate != null;
    }
}
