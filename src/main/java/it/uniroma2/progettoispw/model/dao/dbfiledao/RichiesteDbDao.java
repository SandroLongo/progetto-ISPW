package it.uniroma2.progettoispw.model.dao.dbfiledao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.RichiesteDao;
import it.uniroma2.progettoispw.model.domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RichiesteDbDao extends DbDao implements RichiesteDao {

    private void addDosiInviataConfezione(ResultSet rs, Richiesta richiesta) throws SQLException {
        try {
            while (rs.next()) {
                richiesta.addDoseInviata(new DoseInviata(new DoseConfezione(new Confezione(rs.getInt(1)), rs.getInt(5),
                        rs.getString(6), rs.getTime(7).toLocalTime(), rs.getString(8), richiesta.getInviante()),
                        rs.getInt(3), rs.getDate(2).toLocalDate(), rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDosiInviataPrincipioAttivo(ResultSet rs, Richiesta richiesta) throws SQLException {
        try {
            while (rs.next()) {
                richiesta.addDoseInviata(new DoseInviata(new DosePrincipioAttivo(new PrincipioAttivo(rs.getString(1)), rs.getInt(5),
                        rs.getString(6), rs.getTime(7).toLocalTime(), rs.getString(8), richiesta.getInviante()),
                        rs.getInt(3), rs.getDate(2).toLocalDate(), rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Richiesta> getRichisteOfPaziente(Paziente paziente) throws DaoException {
        List<Richiesta> richieste = new ArrayList<Richiesta>();
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call get_richieste(?)}");
            cs.setString(1, paziente.getCodiceFiscale());
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    richieste.add(new Richiesta(rs.getInt(1), rs.getDate(2).toLocalDate(), paziente,
                            new Dottore(rs.getString(3))));
                }
                //da completare la creazione del DOTTORE inviante
                for (Richiesta richiesta : richieste) {
                    status = cs.getMoreResults();
                    if (status) {
                        rs = cs.getResultSet();
                        addDosiInviataConfezione(rs, richiesta);
                    }
                    status = cs.getMoreResults();
                    if (status) {
                        rs = cs.getResultSet();
                        addDosiInviataPrincipioAttivo(rs, richiesta);
                    }
                }

            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return richieste;
    }

    @Override
    public void deleteRichiesta(int id) throws DaoException {
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
    public int addRichiesta(Richiesta richiesta) throws DaoException {
        int id;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call add_richiesta(?,?,?,?)}");
            cs.setDate(1, Date.valueOf(richiesta.getInvio()));
            cs.setString(2, richiesta.getRicevente().getCodiceFiscale());
            cs.setString(3, richiesta.getInviante().getCodiceFiscale());
            cs.registerOutParameter(4, java.sql.Types.INTEGER);
            cs.execute();
            id = cs.getInt(4);
            CallableStatement cs1 = conn.prepareCall("{call add_invio_confezione(?,?,?,?,?,?,?,?,?)}");
            CallableStatement cs2 = conn.prepareCall("{call add_invio_pa(?,?,?,?,?,?,?,?,?)}");
            cs1.setInt(1, id);
            cs2.setInt(1, id);
            for (DoseInviata doseInviata : richiesta.getMedicinali()) {
                Dose dose = doseInviata.getDose();
                cs1.setDate(3, Date.valueOf(doseInviata.getInizio()));
                cs1.setInt(4, doseInviata.getNumGiorni());
                cs1.setInt(5, doseInviata.getRateGiorni());
                cs1.setInt(6, dose.getQuantita());
                cs1.setString(7, dose.getUnitaMisura());
                cs1.setTime(8, Time.valueOf(dose.getOrario()));
                cs1.setString(9, dose.getDescrizione());
                switch (dose.isType()) {
                    case CONFEZIONE -> {
                        cs1.setInt(2, Integer.parseInt(dose.getCodice()));
                        cs1.execute();
                    }
                    case PRINCIPIOATTIVO -> {
                        cs1.setString(2, dose.getCodice());
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
}
