package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Role;


public class PatientRegistrationData extends UserRegistrationData {

    public PatientRegistrationData() {
        super();
    }

    @Override
    public Role isType() {
        return Role.PATIENT;
    }

}
