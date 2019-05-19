package Model.BaseDades;
import Model.Configuracio;

import java.io.FileNotFoundException;
import java.sql.*;

/**
 * Inicialitzem la base de dades i indiquem quines son les principals queries
 * Autors: Pol Caubet, Dani Ulied, Ona Rof, Anna Aguareles, Enric Sasselli, Biel Fernández
 */
public class DataBase {

    private static String NOM_BBDD;
    private static String USERNAME;
    private static String PASSWORD;
    private static int PORT;
    private static String CONN_URL = "jdbc:mysql://localhost";
    private static Connection conn;
    private static Statement s;
    private static DataBase instance;

    public DataBase (String user, String password, String nomBBDD, int port){
        this.NOM_BBDD = nomBBDD;
        this.USERNAME = user;
        this.PASSWORD = password;
        this.PORT = port;
        this.CONN_URL += ":"+port+"/";
        this.CONN_URL += nomBBDD;
        this.instance = null;

    }

    /**
     * Inicialitzacio de la BBDD
     * @return DataBase
     */
    public static DataBase getInstance(){
        Configuracio config = new Configuracio();
        try {
            config = config.llegeixJson();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(instance == null){
            instance = new DataBase(config.getUsuariAcces(),config.getContrasenyaAcces(),config.getNomBase(),config.getPortBase());
            //instance = new DataBase("root", "root", "PianoData", 3306);
            System.out.println(USERNAME + "     " + PASSWORD + "    " + NOM_BBDD + "     " + PORT + "     " + CONN_URL);
            instance.connect();
        }
        return  instance;
    }

    /**
     * Establim la connexió amb la base de dades
     */
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Connection");
            conn = (Connection) DriverManager.getConnection(CONN_URL,USERNAME,PASSWORD);
            if (conn != null) {
                System.out.println("Connexió a base de dades "+CONN_URL+" ... Ok");
            }
        }
        catch(SQLException ex) {
            System.out.println("Problema al connecta-nos a la BBDD --> "+CONN_URL);
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }

    }

    /**
     * Query que permet inserir dades
     * @param query
     */
    public void insertQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Inserir --> " + ex.getSQLState());
        }
    }

    /**
     * Actualitzem dades de la BBDD
     * @param query
     */
    public void updateQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Modificar --> " + ex.getSQLState());
            ex.printStackTrace();
        }
    }

    /**
     * Esborrem dades de la BBDD
     * @param query
     */
    public void deleteQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
        }

    }


    /**
     * Seleccionem dades de la BBDD
     * @param query
     * @return
     */
    public ResultSet selectQuery(String query){
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            ex.printStackTrace();
        }
        return rs;
    }

    /**
     * Ens desconnectem de la BBDD
     */
    public void disconnect(){
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Problema al tancar la connexió --> " + e.getSQLState());
        }
    }



}
