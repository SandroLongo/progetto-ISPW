package it.uniroma2.progettoispw.model.dao;

import it.uniroma2.progettoispw.model.domain.Dottore;
import it.uniroma2.progettoispw.model.domain.Paziente;
import it.uniroma2.progettoispw.model.domain.Utente;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class UtenteDbDao extends DbDao implements UtenteDao {

    private Utente createUtente(ResultSet rs, int isdottore) {
        Utente utente = null;
        try {
            switch (isdottore) {
                case 1:
                    utente = new Dottore(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4),
                            rs.getString(5), rs.getString(6));
                break;
                case 0:
                    utente = new Paziente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4),
                            rs.getString(5), rs.getString(6), null);

                break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utente;
    }

    @Override
    public Paziente getPaziente(String codice_fiscale) throws DaoException {
        return null;
    }

    @Override
    public Dottore getDottore(String codice_fiscale) throws DaoException {
        return null;
    }

    @Override
    public void addPaziente(String codice_fiscale, String nome, String cognome, LocalDate nascita, String email, String telefono, String pass) throws DaoException {
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call add_paziente(?,?,?,?,?,?,?)}");
            cs.setString(1, codice_fiscale);
            cs.setString(2, nome);
            cs.setString(3, cognome);
            cs.setDate(4, java.sql.Date.valueOf(nascita));
            cs.setString(5, email);
            cs.setString(6, telefono);
            cs.setString(7, pass);
            cs.execute();

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void addDottore(String codice_fiscale, String nome, String cognome, LocalDate nascita, String email, String telefono, String pass, int codice) throws DaoException {
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call add_dottore(?,?,?,?,?,?,?,?)}");
            cs.setString(1, codice_fiscale);
            cs.setString(2, nome);
            cs.setString(3, cognome);
            cs.setDate(4, java.sql.Date .valueOf(nascita));
            cs.setString(5, email);
            cs.setString(6, telefono);
            cs.setString(7, pass);
            cs.setInt(8, codice);
            cs.execute();

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Utente login(String codice_fiscale, String password, int is_dottore, int codice_dottore) throws DaoException {
        Utente utente = null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call login(?,?,?,?)}");
            cs.setString(1, codice_fiscale);
            cs.setString(2, password);
            cs.setInt(3, is_dottore);
            cs.setInt(4, codice_dottore);
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()) {
                    utente = createUtente(rs, is_dottore);
                    if (is_dottore == 0){
                        Paziente paziente = (Paziente) utente;
                        status = cs.getMoreResults();
                        if (status) {
                            rs = cs.getResultSet();
                            while (rs.next()) {
                                while(rs.next()) {
                                    Dottore dottore = (Dottore)createUtente(rs, 1);
                                    paziente.addDottore(dottore);
                                }
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
}