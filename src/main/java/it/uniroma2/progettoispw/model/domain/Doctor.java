package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;

public class Doctor extends User {

    public Doctor(Doctor doctor){
        super(doctor);
    }

    public Doctor(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono) {
        super(codiceFiscale, nome, cognome, nascita, email, telefono);
    }

    public Doctor(String codiceFiscale){
        super(codiceFiscale);
    }

    @Override
    public Role isType() {
        return Role.DOTTORE;
    }

    @Override
    public void logout() {
        //non devo fare niente
    }
}
