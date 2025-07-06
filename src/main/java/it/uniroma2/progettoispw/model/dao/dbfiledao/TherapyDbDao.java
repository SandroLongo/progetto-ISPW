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
                terapia.addDose(new MedicationDoseConfezione(new MedicinalProduct(rs.getInt(6)), rs.getInt(1),
                        rs.getString(2), rs.getTime(3).toLocalTime(), rs.getString(4), new Doctor(rs.getString(5))));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void addDosiPrincipioAttivo(ResultSet rs, DailyTherapy terapia) throws SQLException {
        try {
            while (rs.next()) {
                terapia.addDose(new MedicationDosePrincipioAttivo(new ActiveIngridient(rs.getString(6)), rs.getInt(1),
                        rs.getString(2), rs.getTime(3).toLocalTime(), rs.getString(4), new Doctor(rs.getString(5))));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public DailyTherapy getTerapiaGiornaliera(String codiceFiscale, LocalDate data) throws DaoException {
        DailyTherapy terapia = new DailyTherapy(data);
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call get_terapia_giornaliera(?,?)}");
            cs.setString(1, codiceFiscale);
            cs.setDate(2, java.sql.Date.valueOf(data));
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
    public void addDoseConfezione(MedicationDoseConfezione doseConfezione, LocalDate giorno, String codiceFiscale) throws DaoException {
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call add_dose_confezione(?,?,?,?,?,?,?,?)}");
            cs.setString(1, codiceFiscale);
            cs.setInt(2, Integer.parseInt(doseConfezione.getCodice()));
            cs.setInt(3, doseConfezione.getQuantita());
            cs.setString(4, doseConfezione.getUnitaMisura());
            cs.setDate(5, java.sql.Date.valueOf(giorno));
            cs.setTime(6, Time.valueOf(doseConfezione.getOrario()));
            cs.setString(7, doseConfezione.getDescrizione());
            cs.setString(8, doseConfezione.getInviante().getCodiceFiscale());
            cs.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void addDosePrincipioAttivo(MedicationDosePrincipioAttivo dosePrincipioAttivo, LocalDate giorno, String codiceFiscale) throws DaoException {
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call add_dose_pa(?,?,?,?,?,?,?,?)}");
            cs.setString(1, codiceFiscale);
            cs.setString(2, dosePrincipioAttivo.getCodice());
            cs.setInt(3, dosePrincipioAttivo.getQuantita());
            cs.setString(4, dosePrincipioAttivo.getUnitaMisura());
            cs.setDate(5, java.sql.Date.valueOf(giorno));
            cs.setTime(6, Time.valueOf(dosePrincipioAttivo.getOrario()));
            cs.setString(7, dosePrincipioAttivo.getDescrizione());
            cs.setString(8, dosePrincipioAttivo.getInviante().getCodiceFiscale());
            cs.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

}
