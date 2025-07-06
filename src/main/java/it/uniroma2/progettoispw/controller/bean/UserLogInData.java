package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.controller.controller.applicativi.Config;
import it.uniroma2.progettoispw.model.domain.Role;

public class UserLogInData {
    private String taxCode;
    private String password;
    private Role role;
    private Integer doctorCode;

    public UserLogInData(String taxCode, String password, Role role, String doctorCode) {
        this.taxCode = taxCode;
        this.password = password;
        this.role = role;
        try {
            this.doctorCode = Integer.parseInt(doctorCode);
        } catch (NumberFormatException e) {
            this.doctorCode = null;
        }
    }

    public UserLogInData(String codice, String password, Role role) {
        this.taxCode = codice;
        this.password = password;
        this.role = role;
    }

    public UserLogInData() {

    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String cf) {
        if (cf == null || cf.length() < Config.MIN_CF_LENGTH || cf.length() > Config.MAX_CF_LENGTH) {
            throw new FomatoInvalidoException("Codice fiscale non valido");
        }
        this.taxCode = cf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pwd) {
        if (pwd == null || pwd.length() > Config.MAX_PASSWORD_LENGTH || pwd.length() <= Config.MIN_PASSWORD_LENGTH) {
            throw  new FomatoInvalidoException("Password non valida");
        }
        this.password = pwd;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public int getDoctorCode() {
        if (role != Role.DOCTOR){
            throw new FomatoInvalidoException("il codice puo essere impostato solo per i dottori");
        }
        return doctorCode;
    }
    public void setDoctorCode(String doctorCode) {
        try {
            this.doctorCode = Integer.parseInt(doctorCode);
        } catch (NumberFormatException e) {
            throw new FomatoInvalidoException("il codice deve essere un numero");
        }
    }

    public boolean isComplete(){
        if (role != null && role == Role.DOCTOR && doctorCode == null) {
            return false;

        }
        return taxCode !=null && password!=null && role != null;
    }


}
