package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;

public class Doctor extends User {
    private static final long serialVersionUID = 1L;

    public Doctor(Doctor doctor){
        super(doctor);
    }

    public Doctor(String taxCode, String name, String surname, LocalDate birthDate, String email, String phoneNumber) {
        super(taxCode, name, surname, birthDate, email, phoneNumber);
    }

    public Doctor(String codiceFiscale){
        super(codiceFiscale);
    }

    @Override
    public Role isType() {
        return Role.DOCTOR;
    }

    @Override
    public void logout() {
        //non devo fare niente
    }
}
