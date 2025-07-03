package it.uniroma2.progettoispw.model.dao.dbfiledao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.UtenteDao;
import it.uniroma2.progettoispw.model.domain.Dottore;
import it.uniroma2.progettoispw.model.domain.Paziente;
import it.uniroma2.progettoispw.model.domain.Utente;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UtenteDbDao extends DbDao implements UtenteDao {

    private Utente createUtente(ResultSet rs, int isdottore) {
        Utente utente;
        try {
            utente = switch (isdottore) {
                case 1 -> new Dottore(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4).toLocalDate(),
                        rs.getString(5), rs.getString(6));
                case 0 -> new Paziente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4).toLocalDate(),
                        rs.getString(5), rs.getString(6), null);
                default -> throw new DaoException("Utente non trovato");
            };
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utente;
    }

    @Override
    public Paziente getPaziente(String codiceFiscale) throws DaoException {
        Paziente paziente = null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call get_paziente(?)}");
            cs.setString(1, codiceFiscale);
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()) {
                    paziente = (Paziente)createUtente(rs, 0);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return paziente;

    }

    @Override
    public Dottore getDottore(String codiceFiscale) throws DaoException {
        Dottore dottore = null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call get_dottore(?)}");
            cs.setString(1, codiceFiscale);
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()) {
                    dottore = (Dottore)createUtente(rs, 1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dottore;
    }

    @Override
    public void addPaziente(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono, String pass) throws DaoException {
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call add_paziente(?,?,?,?,?,?,?)}");
            cs.setString(1, codiceFiscale);
            cs.setString(2, nome);
            cs.setString(3, cognome);
            cs.setDate(4, java.sql.Date.valueOf(nascita));
            cs.setString(5, email);
            cs.setString(6, telefono);
            cs.setString(7, pass);
            cs.execute();
            System.out.println("add paziente eseguita" + codiceFiscale);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public int addDottore(String codiceFiscale, String nome, String cognome, LocalDate nascita, String email, String telefono, String pass) throws DaoException {
        int id;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call add_dottore(?,?,?,?,?,?,?,?)}");
            cs.setString(1, codiceFiscale);
            cs.setString(2, nome);
            cs.setString(3, cognome);
            cs.setDate(4, java.sql.Date .valueOf(nascita));
            cs.setString(5, email);
            cs.setString(6, telefono);
            cs.setString(7, pass);
            cs.registerOutParameter(8, java.sql.Types.INTEGER);
            cs.execute();
            id = cs.getInt(8);

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return id;
    }

    @Override
    public Utente login(String codiceFiscale, String password, int isDottore, int codiceDottore) throws DaoException {
        Utente utente = null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call login(?,?,?,?)}");
            cs.setString(1, codiceFiscale);
            cs.setString(2, password);
            cs.setInt(3, isDottore);
            cs.setInt(4, codiceDottore);
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()) {
                    utente = createUtente(rs, isDottore);
                    if (isDottore == 0){
                        Paziente paziente = (Paziente) utente;
                        status = cs.getMoreResults();
                        if (status) {
                            rs = cs.getResultSet();
                            while(rs.next()) {
                                Dottore dottore = (Dottore)createUtente(rs, 1);
                                paziente.addDottore(dottore);
                            }
                        }

                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utente;
    }

    @Override
    public void addDottoreAssociato(String codicePaziente, String codiceDottore) throws DaoException {
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call add_dottore_associato(?,?)}");
            cs.setString(1, codicePaziente);
            cs.setString(2, codiceDottore);
            cs.execute();

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Utente getInfoUtente(String codiceFiscale) throws DaoException {
        return getPaziente(codiceFiscale);
    }
}