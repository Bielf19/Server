package Model;

import java.sql.*;

public class DataBase {
    Configuracio config = new Configuracio;

    private static final String NOM_BBDD = config.getNomBase();
    private static final String USERNAME = config.getUsuariAcces;
    private static final String PASSWORD = config.getContrasenyaAcces;
    private static final String CONN_URL = "jdbc:mysql://localhost/%s?user=%s&password=%s";

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(String.format(CONN_URL, NOM_BBDD,USERNAME,PASSWORD));
    }

    public static ResultSet runQuery(Connection c, String query) throws SQLException{

        Statement s = c.createStatement();
        return s.executeQuery(query);
    }


    public static int runUpdate(Connection c, String query) throws SQLException{

        Statement s = c.createStatement();
        return s.executeUpdate(query);

    }
}
