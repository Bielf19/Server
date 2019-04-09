package Model.BaseDades;
import Model.Configuracio;

import java.sql.*;

public class DataBase {

    private static String NOM_BBDD;
    private static String USERNAME;
    private static String PASSWORD;
    private static int PORT;
    private static final String CONN_URL = "jdbc:mysql://localhost/%s?user=%s&password=%s";
    private static Connection conn;
    private static Statement s;
    private static DataBase instance;

    public DataBase (String user, String password, String nomBBDD, int port){
        NOM_BBDD = nomBBDD;
        USERNAME = user;
        PASSWORD = password;
        PORT = port;
        this.instance = null;

    }

    public static DataBase getInstance(){
        if(instance == null){
            instance = new DataBase(USERNAME, PASSWORD, NOM_BBDD, PORT);
            instance.connect();
        }
        return  instance;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Connection");
            conn = (Connection) DriverManager.getConnection(CONN_URL, USERNAME, PASSWORD);
            if (conn != null) {
                System.out.println("Connexió a base de dades "+CONN_URL+" ... Ok");
            }
        }
        catch(SQLException ex) {
            System.out.println("Problema al connecta-nos a la BBDD --> "+CONN_URL);
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }

    public void insertQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Inserir --> " + ex.getSQLState());
        }
    }

    public void updateQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Modificar --> " + ex.getSQLState());
        }
    }

    public void deleteQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
        }

    }

    public ResultSet selectQuery(String query){
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public void disconnect(){
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Problema al tancar la connexió --> " + e.getSQLState());
        }
    }



}
