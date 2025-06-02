package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.Paziente;
import it.uniroma2.progettoispw.model.domain.Richiesta;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class RichiesteDbDao extends DbDao implements RichiesteDao {

    private void addDosiInviataConfezione(ResultSet rs, Richiesta richiesta) throws SQLException {
        try {

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
