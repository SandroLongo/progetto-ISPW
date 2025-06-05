package it.uniroma2.progettoispw.model.dao.dbfiledao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.RichiesteDao;
import it.uniroma2.progettoispw.model.domain.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            CallableStatement cs = conn.prepareCall("{call add_dose_confezione(?)}");
            cs.setString(1, paziente.getCodiceFiscale());
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    richieste.add(new Richiesta(rs.getInt(1), rs.getDate(2).toLocalDate(), paziente,
                            new Dottore(rs.getString(3))));
                }
                //da completare la creazione del Dottore inviante
                for (Richiesta richiesta : richieste) {
                    status = cs.getMoreResults();
                    if (status) {
                        addDosiInviataConfezione(rs, richiesta);
                    }
                    status = cs.getMoreResults();
                    if (status) {
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
    public void deleteRichiesta(Richiesta richiesta) throws DaoException {

    }

    @Override
    public void addRichiesta(Richiesta richiesta) throws DaoException {

    }
}
