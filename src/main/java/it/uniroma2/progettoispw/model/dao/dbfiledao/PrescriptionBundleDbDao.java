package it.uniroma2.progettoispw.model.dao.dbfiledao;

import it.uniroma2.progettoispw.controller.bean.DoseBean;
import it.uniroma2.progettoispw.controller.bean.PrescriptionBean;
import it.uniroma2.progettoispw.controller.bean.PrescriptionBundleBean;
import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.PrescriptionBundleDao;
import it.uniroma2.progettoispw.model.domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionBundleDbDao extends DbDao implements PrescriptionBundleDao {

    private void addDosiInviataConfezione(ResultSet rs, SentPrescriptionBundle sentPrescriptionBundle) throws SQLException {
        try {
            while (rs.next()) {
                sentPrescriptionBundle.addDoseInviata(new Prescription(new MedicationDose(new MedicinalProduct(rs.getInt(1)), rs.getInt(5),
                        rs.getString(6), rs.getTime(7).toLocalTime(), rs.getString(8), sentPrescriptionBundle.getInviante()),
                        rs.getInt(3), rs.getDate(2).toLocalDate(), rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDosiInviataPrincipioAttivo(ResultSet rs, SentPrescriptionBundle sentPrescriptionBundle) throws SQLException {
        try {
            while (rs.next()) {
                sentPrescriptionBundle.addDoseInviata(new Prescription(new MedicationDose(new ActiveIngredient(rs.getString(1)), rs.getInt(5),
                        rs.getString(6), rs.getTime(7).toLocalTime(), rs.getString(8), sentPrescriptionBundle.getInviante()),
                        rs.getInt(3), rs.getDate(2).toLocalDate(), rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SentPrescriptionBundle> getPrescriptionBundlesByPatient(Patient patient) throws DaoException {
        List<SentPrescriptionBundle> richieste = new ArrayList<SentPrescriptionBundle>();
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call get_richieste(?)}");
            cs.setString(1, patient.getTaxCode());
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    richieste.add(new SentPrescriptionBundle(rs.getInt(1), rs.getDate(2).toLocalDate(), patient,
                            new Doctor(rs.getString(3))));
                }
                //da completare la creazione del DOCTOR inviante
                for (SentPrescriptionBundle sentPrescriptionBundle : richieste) {
                    addPrescriptions(sentPrescriptionBundle, cs);
                }

            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return richieste;
    }

    @Override
    public void deletePrescriptionBundle(int id) throws DaoException {
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call delete_richiesta(?)}");
            cs.setInt(1, id);
            cs.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public int addPrescriptionBundle(PrescriptionBundleBean sentPrescriptionBundle) throws DaoException {
        int id;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call add_richiesta(?,?,?,?)}");
            cs.setDate(1, Date.valueOf(sentPrescriptionBundle.getSubmissionDate()));
            cs.setString(2, sentPrescriptionBundle.getReceiver().getTaxCode());
            cs.setString(3, sentPrescriptionBundle.getSender().getTaxCode());
            cs.registerOutParameter(4, java.sql.Types.INTEGER);
            cs.execute();
            id = cs.getInt(4);
            CallableStatement cs1 = conn.prepareCall("{call add_invio_confezione(?,?,?,?,?,?,?,?,?)}");
            CallableStatement cs2 = conn.prepareCall("{call add_invio_pa(?,?,?,?,?,?,?,?,?)}");
            cs1.setInt(1, id);
            cs2.setInt(1, id);
            for (PrescriptionBean prescription : sentPrescriptionBundle.getPrescriptions()) {
                DoseBean medicationDose = prescription.getDose();
                cs1.setDate(3, Date.valueOf(prescription.getStartDate()));
                cs1.setInt(4, prescription.getRepetitionNumber());
                cs1.setInt(5, prescription.getDayRate());
                cs1.setInt(6, medicationDose.getQuantity());
                cs1.setString(7, medicationDose.getMeausurementUnit());
                cs1.setTime(8, Time.valueOf(medicationDose.getScheduledTime()));
                cs1.setString(9, medicationDose.getDescription());
                switch (medicationDose.getType()) {
                    case MEDICINALPRODUCT -> {
                        cs1.setInt(2, Integer.parseInt(medicationDose.getId()));
                        cs1.execute();
                    }
                    case ACRIVEINGREDIENT -> {
                        cs1.setString(2, medicationDose.getId());
                        cs1.execute();
                    }
                    default -> throw new DaoException("tipo confezione non valido");
                }
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return id;
    }

    @Override
    public SentPrescriptionBundle getPrescriptionBundleById(int id) throws DaoException {
        SentPrescriptionBundle sentPrescriptionBundle;
        try{
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call get_richiesta_by_id(?)}");
            cs.setInt(1, id);
            Boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()) {
                    sentPrescriptionBundle = new SentPrescriptionBundle(id, rs.getDate(2).toLocalDate(), new Patient(rs.getString(3)),
                            new Doctor(rs.getString(4)));
                    addPrescriptions(sentPrescriptionBundle, cs);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return sentPrescriptionBundle;
    }

    private void addPrescriptions(SentPrescriptionBundle sentPrescriptionBundle, CallableStatement cs) throws SQLException {
        Boolean status;
        ResultSet rs;
        status = cs.getMoreResults();
        if (status) {
            rs = cs.getResultSet();
            addDosiInviataConfezione(rs, sentPrescriptionBundle);
        }
        status = cs.getMoreResults();
        if (status) {
            rs = cs.getResultSet();
            addDosiInviataPrincipioAttivo(rs, sentPrescriptionBundle);
        }
    }
}
