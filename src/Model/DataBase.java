package Model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

public class DataBase {

    private static String NOM_BBDD;
    private static String USERNAME;
    private static String PASSWORD;
    private static final String CONN_URL = "jdbc:mysql://localhost/%s?user=%s&password=%s";

    public DataBase (Configuracio config){
        NOM_BBDD = config.getNomBase();
        USERNAME = config.getUsuariAcces();
        PASSWORD = config.getContrasenyaAcces();

    }

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
