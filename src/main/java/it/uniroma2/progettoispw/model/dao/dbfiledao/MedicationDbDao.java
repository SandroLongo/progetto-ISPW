package it.uniroma2.progettoispw.model.dao.dbfiledao;

import it.uniroma2.progettoispw.controller.bean.InvalidFormatException;
import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.MedicationDao;
import it.uniroma2.progettoispw.model.domain.MedicinalProduct;
import it.uniroma2.progettoispw.model.domain.ActiveIngredient;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicationDbDao extends DbDao implements MedicationDao {

    private MedicinalProduct creaConfezione(ResultSet rs) throws SQLException {
        try{
            MedicinalProduct medicinalProduct = new MedicinalProduct(String.valueOf(rs.getInt(1)));
            medicinalProduct.setCodFarmaco(rs.getInt(2));
            medicinalProduct.setCodConfezione(rs.getInt(3));
            medicinalProduct.setDenominazione(rs.getString(4));
            medicinalProduct.setDescrizione(rs.getString(5));
            medicinalProduct.setCodiceDitta(rs.getInt(6));
            medicinalProduct.setRagioneSociale(rs.getString(7));
            medicinalProduct.setStatoAmministrativo(rs.getString(8));
            medicinalProduct.setTipoProcedura(rs.getString(9));
            medicinalProduct.setForma(rs.getString(10));
            medicinalProduct.setCodiceAtc(rs.getString(11));
            medicinalProduct.setPaAssociati(rs.getString(12));
            medicinalProduct.setQuantita(rs.getInt(14));
            medicinalProduct.setUnitaMisura(rs.getString(15));
            medicinalProduct.setLink(rs.getString(13));
            return medicinalProduct;
        } catch(SQLException e){
            throw new DaoException(e.getMessage());
        }


    }

    private ActiveIngredient creaPrincipioAttivo(ResultSet rs) throws SQLException {
        try {
            return new ActiveIngredient(rs.getString(1), rs.getString(2));
        } catch(SQLException e){
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public List<String> getMPNameByPartialName(String name) throws DaoException {
        List<String> nomiConfezioni = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call search_nome_confezione(?)}");
            cs.setString(1, name);
            boolean status = cs.execute();

            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    nomiConfezioni.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return nomiConfezioni;
    }

    @Override
    public List<MedicinalProduct> getMPbyName(String name) throws DaoException {
        List<MedicinalProduct> nomiConfezioni = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call search_confezione_by_nome(?)}");
            cs.setString(1, name);
            boolean status = cs.execute();

            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    MedicinalProduct medicinalProduct = creaConfezione(rs);
                    nomiConfezioni.add(medicinalProduct);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return nomiConfezioni;
    }

    @Override
    public MedicinalProduct getMedicinalProductByID(String id) throws DaoException {
        MedicinalProduct medicinalProduct = null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call search_confezione_by_aic(?)}");
            cs.setInt(1, Integer.parseInt(id));
            boolean status = cs.execute();

            if (status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()) {
                    medicinalProduct = creaConfezione(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("the Medicinal Product Id must be an integer");
        }
        return medicinalProduct;
    }

    @Override
    public List<String> getAINamesByPartialName(String name) throws DaoException {
        List<String> nomi = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call search_nome_pa(?)}");
            cs.setString(1, name);
            boolean status = cs.execute();

            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    nomi.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return nomi;
    }

    @Override
    public ActiveIngredient getAIByName(String name) throws DaoException {
        ActiveIngredient activeIngredient = null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call search_pa_by_nome(?)}");
            cs.setString(1, name);
            boolean status = cs.execute();

            if (status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()) {
                    activeIngredient = creaPrincipioAttivo(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return activeIngredient;
    }

    @Override
    public List<MedicinalProduct> getMPByAIID(String id) throws DaoException {
        List<MedicinalProduct> confezioni = new ArrayList<>();

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call search_confezione_by_pa(?)}");
            cs.setString(1, id);
            boolean status = cs.execute();

            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    MedicinalProduct medicinalProduct = creaConfezione(rs);
                    confezioni.add(medicinalProduct);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return confezioni;
    }

    @Override
    public ActiveIngredient getAIByID(String id) throws DaoException {
        ActiveIngredient activeIngredient = null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call search_pa_by_codice(?)}");
            cs.setString(1, id);
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()){
                    activeIngredient = creaPrincipioAttivo(rs);
                }
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return activeIngredient;
    }

}
