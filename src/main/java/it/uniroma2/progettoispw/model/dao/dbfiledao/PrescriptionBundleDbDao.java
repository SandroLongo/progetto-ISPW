package it.uniroma2.progettoispw.model.dao.dbfiledao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.PrescriptionBundleDao;
import it.uniroma2.progettoispw.model.domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionBundleDbDao extends DbDao implements PrescriptionBundleDao {

    private void addDosiInviataConfezione(ResultSet rs, SentPrescriptionBundle sentPrescriptionBundle) throws SQLException {
        try {
            while (rs.next()) {
                sentPrescriptionBundle.addDoseInviata(new Prescription(new MedicationDoseConfezione(new MedicinalProduct(rs.getInt(1)), rs.getInt(5),
                        rs.getString(6), rs.getTime(7).toLocalTime(), rs.getString(8), sentPrescriptionBundle.getInviante()),
                        rs.getInt(3), rs.getDate(2).toLocalDate(), rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDosiInviataPrincipioAttivo(ResultSet rs, SentPrescriptionBundle sentPrescriptionBundle) throws SQLException {
        try {
            while (rs.next()) {
                sentPrescriptionBundle.addDoseInviata(new Prescription(new MedicationDosePrincipioAttivo(new ActiveIngridient(rs.getString(1)), rs.getInt(5),
                        rs.getString(6), rs.getTime(7).toLocalTime(), rs.getString(8), sentPrescriptionBundle.getInviante()),
                        rs.getInt(3), rs.getDate(2).toLocalDate(), rs.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SentPrescriptionBundle> getRichisteOfPaziente(Patient patient) throws DaoException {
        List<SentPrescriptionBundle> richieste = new ArrayList<SentPrescriptionBundle>();
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call get_richieste(?)}");
            cs.setString(1, patient.getCodiceFiscale());
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    richieste.add(new SentPrescriptionBundle(rs.getInt(1), rs.getDate(2).toLocalDate(), patient,
                            new Doctor(rs.getString(3))));
                }
                //da completare la creazione del DOTTORE inviante
                for (SentPrescriptionBundle sentPrescriptionBundle : richieste) {
                    status = cs.getMoreResults();
                    if (status) {
                        rs = cs.getResultSet();
                        addDosiInviataConfezione(rs, sentPrescriptionBundle);
                    }
                    status = cs.getMoreResults();
                    if (status) {
                        rs = cs.getResultSet();
                        addDosiInviataPrincipioAttivo(rs, sentPrescriptionBundle);
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
    public int addRichiesta(SentPrescriptionBundle sentPrescriptionBundle) throws DaoException {
        int id;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call add_richiesta(?,?,?,?)}");
            cs.setDate(1, Date.valueOf(sentPrescriptionBundle.getInvio()));
            cs.setString(2, sentPrescriptionBundle.getRicevente().getCodiceFiscale());
            cs.setString(3, sentPrescriptionBundle.getInviante().getCodiceFiscale());
            cs.registerOutParameter(4, java.sql.Types.INTEGER);
            cs.execute();
            id = cs.getInt(4);
            CallableStatement cs1 = conn.prepareCall("{call add_invio_confezione(?,?,?,?,?,?,?,?,?)}");
            CallableStatement cs2 = conn.prepareCall("{call add_invio_pa(?,?,?,?,?,?,?,?,?)}");
            cs1.setInt(1, id);
            cs2.setInt(1, id);
            for (Prescription prescription : sentPrescriptionBundle.getMedicinali()) {
                MedicationDose medicationDose = prescription.getDose();
                cs1.setDate(3, Date.valueOf(prescription.getInizio()));
                cs1.setInt(4, prescription.getNumGiorni());
                cs1.setInt(5, prescription.getRateGiorni());
                cs1.setInt(6, medicationDose.getQuantita());
                cs1.setString(7, medicationDose.getUnitaMisura());
                cs1.setTime(8, Time.valueOf(medicationDose.getOrario()));
                cs1.setString(9, medicationDose.getDescrizione());
                switch (medicationDose.isType()) {
                    case CONFEZIONE -> {
                        cs1.setInt(2, Integer.parseInt(medicationDose.getCodice()));
                        cs1.execute();
                    }
                    case PRINCIPIOATTIVO -> {
                        cs1.setString(2, medicationDose.getCodice());
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
