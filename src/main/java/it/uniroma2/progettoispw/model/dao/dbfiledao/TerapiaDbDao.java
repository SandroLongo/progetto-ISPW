package it.uniroma2.progettoispw.model.dao.dbfiledao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.TerapiaDao;
import it.uniroma2.progettoispw.model.domain.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class TerapiaDbDao extends DbDao implements TerapiaDao {

    private void addDosiConfezione(ResultSet rs, TerapiaGiornaliera terapia) throws SQLException {
        try {
            while (rs.next()) {
                terapia.addDose(new DoseConfezione(new Confezione(rs.getInt(6)), rs.getInt(1),
                        rs.getString(2), rs.getTime(3).toLocalTime(), rs.getString(4), new Dottore(rs.getString(5))));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void addDosiPrincipioAttivo(ResultSet rs, TerapiaGiornaliera terapia) throws SQLException {
        try {
            while (rs.next()) {
                terapia.addDose(new DosePrincipioAttivo(new PrincipioAttivo(rs.getString(6)), rs.getInt(1),
                        rs.getString(2), rs.getTime(3).toLocalTime(), rs.getString(4), new Dottore(rs.getString(5))));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public TerapiaGiornaliera getTerapiaGiornaliera(String codiceFiscale, LocalDate data) throws DaoException {
        TerapiaGiornaliera terapia = new TerapiaGiornaliera(data);
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call get_terapia_giornaliera(?,?)}");
            cs.setString(1, codiceFiscale);
            cs.setDate(2, java.sql.Date.valueOf(data));
            boolean Status = cs.execute();
            // DosiConfezioni
            if (Status) {
                ResultSet rs = cs.getResultSet();
                addDosiConfezione(rs, terapia);

            }
            cs.getMoreResults();
            if (Status) {
                ResultSet rs = cs.getResultSet();
                addDosiPrincipioAttivo(rs, terapia);
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return terapia;
    }

    @Override
    public void addDoseConfezione(DoseConfezione doseConfezione, LocalDate giorno, String codiceFiscale) throws DaoException {
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call add_dose_confezione(?,?,?,?,?,?,?,?)}");
            cs.setString(1, codiceFiscale);
            cs.setInt(2, doseConfezione.getCodice());
            cs.setInt(3, doseConfezione.getQuantita());
            cs.setString(4, doseConfezione.getUnita_misura());
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
    public void addDosePrincipioAttivo(DosePrincipioAttivo dosePrincipioAttivo, LocalDate giorno, String codiceFiscale) throws DaoException {
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call add_dose_pa(?,?,?,?,?,?,?,?)}");
            cs.setString(1, codiceFiscale);
            cs.setString(2, dosePrincipioAttivo.getCodice());
            cs.setInt(3, dosePrincipioAttivo.getQuantita());
            cs.setString(4, dosePrincipioAttivo.getUnita_misura());
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
