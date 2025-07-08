package it.uniroma2.progettoispw.controller.bean;

import it.uniroma2.progettoispw.model.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionBundleBean {
    List<PrescriptionBean> prescriptions;
    private LocalDate submissionDate;
    private UserInformation receiver;
    private UserInformation sender;

    public PrescriptionBundleBean() {
        prescriptions = new ArrayList<>();
    }

    public PrescriptionBundleBean(SentPrescriptionBundle sentPrescriptionBundle) {
        this.submissionDate = sentPrescriptionBundle.getInvio();
        this.sender = new UserInformation(sentPrescriptionBundle.getInviante());
        this.receiver = new UserInformation(sentPrescriptionBundle.getRicevente());
        prescriptions = new ArrayList<>();
        replaceDosi(sentPrescriptionBundle.getMedicinali());
    }

    public void replaceDosi(List<it.uniroma2.progettoispw.model.domain.Prescription> lista) {
        for (it.uniroma2.progettoispw.model.domain.Prescription prescription : lista) {
            this.addPrescription(new PrescriptionBean(prescription));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (PrescriptionBean prescriptionBean : prescriptions) {
            sb.append(prescriptionBean.toString()).append("\n");
        }
        return sb.toString();
    }

    public String toStringBase(){
        return "DOCTOR: " + receiver.getName() + " " + receiver.getSurname() + "data di submissionDate: " + submissionDate.toString();
    }

    public List<PrescriptionBean> getPrescriptions() {
        return prescriptions;
    }

    public void addPrescription(PrescriptionBean prescriptionBean) {
        if (!prescriptionBean.isCompleate()) {
            throw new InvalidFormatException("prescriptionBean is not complete");
        }
        prescriptions.add(prescriptionBean);
    }

    public void removePrescription(PrescriptionBean prescriptionBean){
        prescriptions.remove(prescriptionBean);
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) throws InvalidFormatException {
        if (submissionDate == null) {
            throw new InvalidFormatException("submissionDate cannot be null");
        }
        this.submissionDate = submissionDate;
    }

    public UserInformation getReceiver() {
        return receiver;
    }

    public void setReceiver(UserInformation receiver) throws InvalidFormatException {
        if (!receiver.isCompleate()){
            throw new InvalidFormatException("Receiver not compleate");
        }
        this.receiver = receiver;
    }

    public UserInformation getSender() {
        return sender;
    }

    public void setSender(UserInformation sender)  throws InvalidFormatException {
        if (!sender.isCompleate()){
            throw new InvalidFormatException("Sender not compleate");
        }
        for (PrescriptionBean prescriptionBean : getPrescriptions()) {
            prescriptionBean.getDose().setSender(sender);
        }
        this.sender = sender;
    }
}
