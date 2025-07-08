package it.uniroma2.progettoispw.model.dao.dbfiledao;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.DaoFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static Connection connection;

    private ConnectionFactory() {
        //per evitare la costruzione
    }

    static {

        try (InputStream input = DaoFactory.class.getClassLoader().getResourceAsStream("properties.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            String connectionUrl = properties.getProperty("DB_URL");
            String user = properties.getProperty("DB_USER");
            String pass = properties.getProperty("DB_PASSWORD");

            connection = DriverManager.getConnection(connectionUrl, user, pass);
        } catch (IOException | SQLException e) {
            throw new DaoException(e.getMessage());
        }

    }

    public static Connection getConnection(){
        return connection;
    }

}
