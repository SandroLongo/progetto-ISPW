package it.uniroma2.progettoispw.model.dao.dbfiledao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/farmaciaifa";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static Connection connection;

    static {

        try{
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection(){
        return connection;
    }

}
