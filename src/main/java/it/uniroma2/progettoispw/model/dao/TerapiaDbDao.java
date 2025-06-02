package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TerapiaDbDao extends DbDao implements TerapiaDao {

    private Dottore createDottore(ResultSet rs, List<Integer> indici) throws SQLException {
        Dottore dottore = null;
        try {
            dottore = new Dottore(rs.getString(indici.get(0)), rs.getString(indici.get(1)), rs.getString(indici.get(2)), rs.getDate(indici.get(3)),
                    rs.getString(indici.get(4)), rs.getString(indici.get(5)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dottore;
    }

    private void addDosiConfezione(ResultSet rs, TerapiaGiornaliera terapia) throws SQLException {
        try {
            while (rs.next()) {
                terapia.addDose(new DoseConfezione(new Confezione(rs.getInt(6), rs.getInt(7), rs.getInt(8),
                        rs.getString(9), rs.getString(10), rs.getInt(11), rs.getString(12),
                        rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16),
                        rs.getString(17), 0, "non fornita", rs.getString(18)), rs.getInt(1),
                        rs.getString(2), rs.getTime(3).toLocalTime(), rs.getString(4), createDottore(rs, List.of(1,2,3,4, 5, 6))));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void addDosiPrincipioAttivo(ResultSet rs, TerapiaGiornaliera terapia) throws SQLException {
        try {
            while (rs.next()) {
                terapia.addDose(new DosePrincipioAttivo(new PrincipioAttivo(rs.getString(6), rs.getString(7)), rs.getInt(1),
                        rs.getString(2), rs.getTime(3).toLocalTime(), rs.getString(4), createDottore(rs, List.of(8, 9, 10, 11, 12, 13, 14))));
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
    public void addTerapiaByRichiesta(Richiesta richiesta) throws DaoException {
        try {
            Dottore inviante = richiesta.getInviante();
            Paziente ricevente = richiesta.getRicevente();
            List<DoseInviata> dosiInviate= richiesta.getMedicinali();

            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs;

            for (DoseInviata dose: dosiInviate) {

                for () {
                    cs = conn.prepareCall("{call add_dose_confezione(?,?,?,?,?,?,?,?)}");
                    cs.setString(1, codiceFiscale);
                    cs.setDate(2, java.sql.Date.valueOf(data));
                    boolean Status = cs.execute();
                }
            }
        }
    }

    @Override
    public void addDoseConfezione(DoseConfezione doseConfezione) throws DaoException {

    }

    @Override
    public void addDosePincipioAttivo(PrincipioAttivo principioAttivo) throws DaoException {

    }
}
