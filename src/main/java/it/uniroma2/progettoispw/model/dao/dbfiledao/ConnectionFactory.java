package it.uniroma2.progettoispw.model.dao.dbfiledao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/farmaciaifa";
    private static final String DB_USER = "utente";
    private static final String DB_PASSWORD = "12345678";
    private static Connection connection;

    static {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static Connection getConnection(){
        return connection;
    }

}
