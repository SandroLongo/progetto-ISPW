package it.uniroma2.progettoispw.model.dao.dbfiledao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.TherapyDao;
import it.uniroma2.progettoispw.model.domain.*;

import java.sql.*;
import java.time.LocalDate;

public class TherapyDbDao extends DbDao implements TherapyDao {

    private void addDosiConfezione(ResultSet rs, DailyTherapy terapia) throws SQLException {
        try {
            while (rs.next()) {
                terapia.addDose(new MedicationDose(new MedicinalProduct(String.valueOf(rs.getInt(6))), rs.getInt(1),
                        rs.getString(2), rs.getTime(3).toLocalTime(), rs.getString(4), new Doctor(rs.getString(5))));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void addDosiPrincipioAttivo(ResultSet rs, DailyTherapy terapia) throws SQLException {
        try {
            while (rs.next()) {
                terapia.addDose(new MedicationDose(new ActiveIngredient(rs.getString(6)), rs.getInt(1),
                        rs.getString(2), rs.getTime(3).toLocalTime(), rs.getString(4), new Doctor(rs.getString(5))));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public DailyTherapy getDailyTherapy(String taxCode, LocalDate date) throws DaoException {
        DailyTherapy terapia = new DailyTherapy(date);
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call get_terapia_giornaliera(?,?)}");
            cs.setString(1, taxCode);
            cs.setDate(2, java.sql.Date.valueOf(date));
            boolean status = cs.execute();
            // DosiConfezioni
            if (status) {
                ResultSet rs = cs.getResultSet();
                addDosiConfezione(rs, terapia);
            }
            cs.getMoreResults();
            if (status) {
                ResultSet rs = cs.getResultSet();
                addDosiPrincipioAttivo(rs, terapia);
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return terapia;
    }

    @Override
    public void addMedicationDose(MedicationDose medicationDose, LocalDate date, String taxCode) throws DaoException {
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = createCSandSetMedicationInformation(medicationDose.getMedication());
            cs.setString(1, taxCode);
            cs.setInt(3, medicationDose.getQuantity());
            cs.setString(4, medicationDose.getMeasurementUnit());
            cs.setDate(5, java.sql.Date.valueOf(date));
            cs.setTime(6, Time.valueOf(medicationDose.getScheduledTime()));
            cs.setString(7, medicationDose.getDescription());
            cs.setString(8, medicationDose.getSender().getTaxCode());
            cs.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private CallableStatement createCSandSetMedicationInformation(Medication medication) throws SQLException,DaoException {
        Connection conn = ConnectionFactory.getConnection();
        CallableStatement cs;
        switch (medication.getType()){
            case MEDICINALPRODUCT -> {
                cs = conn.prepareCall("{call add_dose_confezione(?,?,?,?,?,?,?,?)}");
                cs.setInt(2, Integer.parseInt(medication.getId()));}
            case ACRIVEINGREDIENT -> {
                cs = conn.prepareCall("{call add_dose_pa(?,?,?,?,?,?,?,?)}");
                cs.setString(1, medication.getId());}
            default -> {throw new DaoException("invalid medication type");
            }
        }
        return cs;
    }

}
