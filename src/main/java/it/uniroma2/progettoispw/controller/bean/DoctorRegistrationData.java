package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.Role;


public class DoctorRegistrationData extends UserRegistrationData {


    public DoctorRegistrationData() {
        super();
    }

    @Override
    public Role isType() {
        return Role.DOTTORE;
    }

}
