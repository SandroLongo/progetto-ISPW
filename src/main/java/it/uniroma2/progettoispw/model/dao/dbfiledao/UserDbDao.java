package it.uniroma2.progettoispw.model.dao.dbfiledao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.UserDao;
import it.uniroma2.progettoispw.model.domain.Doctor;
import it.uniroma2.progettoispw.model.domain.Patient;
import it.uniroma2.progettoispw.model.domain.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserDbDao extends DbDao implements UserDao {

    private User createUtente(ResultSet rs, int isdottore) {
        User user;
        try {
            user = switch (isdottore) {
                case 1 -> new Doctor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4).toLocalDate(),
                        rs.getString(5), rs.getString(6));
                case 0 -> new Patient(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4).toLocalDate(),
                        rs.getString(5), rs.getString(6));
                default -> throw new DaoException("User non trovato");
            };
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return user;
    }

    @Override
    public Patient getPatient(String taxCode) throws DaoException {
        Patient patient = null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call get_paziente(?)}");
            cs.setString(1, taxCode);
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()) {
                    patient = (Patient)createUtente(rs, 0);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return patient;

    }

    @Override
    public Doctor getDoctor(String taxCode) throws DaoException {
        Doctor doctor = null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call get_dottore(?)}");
            cs.setString(1, taxCode);
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()) {
                    doctor = (Doctor)createUtente(rs, 1);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return doctor;
    }

    @Override
    public void addPatient(String taxCode, String name, String surname, LocalDate birthDate, String email, String phoneNumber, String pass) throws DaoException {
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call add_paziente(?,?,?,?,?,?,?)}");
            cs.setString(1, taxCode);
            cs.setString(2, name);
            cs.setString(3, surname);
            cs.setDate(4, java.sql.Date.valueOf(birthDate));
            cs.setString(5, email);
            cs.setString(6, phoneNumber);
            cs.setString(7, pass);
            cs.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public int addDoctor(String taxCode, String name, String surname, LocalDate birthDate, String email, String phoneNumber, String pass) throws DaoException {
        int id;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call add_dottore(?,?,?,?,?,?,?,?)}");
            cs.setString(1, taxCode);
            cs.setString(2, name);
            cs.setString(3, surname);
            cs.setDate(4, java.sql.Date .valueOf(birthDate));
            cs.setString(5, email);
            cs.setString(6, phoneNumber);
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
    public User login(String taxCode, String password, int isDoctor, int doctorCode) throws DaoException {
        User user = null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call login(?,?,?,?)}");
            cs.setString(1, taxCode);
            cs.setString(2, password);
            cs.setInt(3, isDoctor);
            cs.setInt(4, doctorCode);
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()) {
                    user = createUtente(rs, isDoctor);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return user;
    }


    @Override
    public User getUserInformation(String taxCode) throws DaoException {
        return getPatient(taxCode);
    }
}