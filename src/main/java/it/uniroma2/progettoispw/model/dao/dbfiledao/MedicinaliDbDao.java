package it.uniroma2.progettoispw.model.dao.dbfiledao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.MedicinaliDao;
import it.uniroma2.progettoispw.model.domain.Confezione;
import it.uniroma2.progettoispw.model.domain.PrincipioAttivo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicinaliDbDao extends DbDao implements MedicinaliDao {

    private Confezione creaConfezione(ResultSet rs) throws SQLException {
        try{
            return new Confezione(rs.getInt(1),rs.getInt(2), rs.getInt(3), rs.getString(4),
                    rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9),
                    rs. getString(10), rs.getString(11), rs.getString(12), rs.getInt(14),
                    rs.getString(15), rs.getString(13));
        } catch(SQLException e){
            throw new SQLException(e);
        }

    }

    private PrincipioAttivo creaPrincipioAttivo(ResultSet rs) throws SQLException {
        try {
            return new PrincipioAttivo(rs.getString(1), rs.getString(2));
        } catch(SQLException e){
            throw new SQLException(e);
        }
    }

    @Override
    public List<String> getNomiConfezioniByNomeParziale(String nome) throws DaoException {
        List<String> nomiConfezioni = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call search_nome_confezione(?)}");
            cs.setString(1, nome);
            boolean status = cs.execute();

            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    nomiConfezioni.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nomiConfezioni;
    }

    @Override
    public List<Confezione> getConfezioniByNome(String nome) throws DaoException {
        List<Confezione> nomiConfezioni = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call search_confezione_by_nome(?)}");
            cs.setString(1, nome);
            boolean status = cs.execute();

            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    Confezione confezione= creaConfezione(rs);
                    nomiConfezioni.add(confezione);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nomiConfezioni;
    }

    @Override
    public Confezione getConfezioneByCodiceAic(int codice_aic) throws DaoException {
        Confezione confezione = null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call search_confezione_by_nome(?)}");
            cs.setInt(1, codice_aic);
            boolean status = cs.execute();

            if (status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()) {
                    confezione = creaConfezione(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return confezione;
    }

    @Override
    public List<String> getNomiPrincipioAttivoByNomeParziale(String nome) throws DaoException {
        List<String> nomi = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call search_nome_pa(?)}");
            cs.setString(1, nome);
            boolean status = cs.execute();

            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    nomi.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nomi;
    }

    @Override
    public PrincipioAttivo getPrincipioAttvoByNome(String nome) throws DaoException {
        PrincipioAttivo principio_attivo = null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call search_pa_by_nome(?)}");
            cs.setString(1, nome);
            boolean status = cs.execute();

            if (status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()) {
                    principio_attivo = creaPrincipioAttivo(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return principio_attivo;
    }

    @Override
    public List<Confezione> getConfezioniByCodiceAtc(String codice_atc) throws DaoException {
        List<Confezione> confezioni = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call search_confezione_by_pa(?)}");
            cs.setString(1, codice_atc);
            boolean status = cs.execute();

            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    Confezione confezione= creaConfezione(rs);
                    confezioni.add(confezione);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return confezioni;
    }

    @Override
    public PrincipioAttivo getPrincipioAttvoByCodiceAtc(String codice_atc) throws DaoException {
        PrincipioAttivo principio_attivo = null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call get_pa_by_codice(?)}");
            cs.setString(1, codice_atc);
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()){
                    principio_attivo = creaPrincipioAttivo(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return principio_attivo;
    }

}
