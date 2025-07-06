package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.controller.controller.applicativi.Config;
import it.uniroma2.progettoispw.model.domain.Role;

import java.time.LocalDate;
import java.time.Period;

public abstract class UserRegistrationData {
    protected String taxCode;
    protected String name;
    protected String surname;
    protected String email;
    protected String phoneNumber;
    protected LocalDate birthDate;
    protected String password;

    protected UserRegistrationData() {

    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String cf)  throws FomatoInvalidoException{
        if (cf == null || cf.length() < Config.MIN_CF_LENGTH || cf.length() > Config.MAX_CF_LENGTH) {
            throw new FomatoInvalidoException("Codice fiscale non valido");
        }
        this.taxCode = cf.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws FomatoInvalidoException {
        if (name == null || name.length() < Config.MIN_NAME_LENGTH || name.length() > Config.MAX_NAME_LENGTH) {
            throw new FomatoInvalidoException("name non valido");
        }
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname)  throws FomatoInvalidoException {
        if (surname == null || surname.length() < Config.MIN_SURNAME_LENGTH || surname.length() > Config.MAX_SURNAME_LENGTH) {
            throw new FomatoInvalidoException("surname non valido");
        }
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email)  throws FomatoInvalidoException{
        if (email == null || surname.length() < Config.MIN_EMAIL_LENGTH || email.length() > Config.MAX_EMAIL_LENGTH) {
            throw new FomatoInvalidoException("email non valido");
        }
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)  throws FomatoInvalidoException{
        if (phoneNumber == null || phoneNumber.length() <= Config.MIN_PHONE_LENGTH || phoneNumber.length() > Config.MAX_PHONE_LENGTH) {
            throw new FomatoInvalidoException("phoneNumber non valido");
        }
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate)  throws FomatoInvalidoException {
        if (birthDate == null || Period.between(birthDate, LocalDate.now()).getYears() < Config.MIN_AGE) {
            throw new FomatoInvalidoException("devi avere " + Config.MIN_AGE + "per registrarti nell'applicazione");
        }
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pwd)  throws FomatoInvalidoException {
        if (pwd == null || pwd.length() > Config.MAX_PASSWORD_LENGTH || pwd.length() <= Config.MIN_PASSWORD_LENGTH) {
            throw  new FomatoInvalidoException("Password non valida");
        }
        this.password = pwd;
    }

    public boolean isComplete(){
        return taxCode != null && name != null && surname != null && email != null && phoneNumber != null && birthDate != null && password != null;
    }
    public abstract Role isType();

}
