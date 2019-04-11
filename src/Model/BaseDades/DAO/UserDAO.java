package Model.BaseDades.DAO;

import Model.BaseDades.DataBase;
import Model.Login;
import Model.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static String codiAmistat;

    public void addUser(Usuari user) {
        String query = "INSERT INTO Usuaris (nomUsuari, password, correu, codiAmistat) VALUES ('"+user.getLogin().getNomUsuari()+"', '"+user.getLogin().getPassword()+"', '"+user.getLogin().getCorreu()+"', '"+codiAmistat+"');";
        System.out.println(query);
        DataBase.getInstance().insertQuery(query);
    }


    public Usuari getUser(int id, String nomUsuari, String correu, String codiAmistat) {
        Usuari user = new Usuari();
        Login login = new Login();
        String query = "";
        if (id != 0) {
            query = "SELECT nomUsuari, password, correu, codiAmistat FROM Usuaris WHERE user_id = '"+id+"';";
        }
        if (!(nomUsuari.equals(null))) {
            query = "SELECT user_id, password, correu, codiAmistat FROM Usuaris WHERE nomUsuari = '"+nomUsuari+"';";
        }
        if (correu != null) {
            query = "SELECT nomUsuari, password, user_id, codiAmistat FROM Usuaris WHERE correu = '"+correu+"';";
        }
        if (codiAmistat != null) {
            query = "SELECT nomUsuari, password, correu, user_id FROM Usuaris WHERE codiAmistat = '"+codiAmistat+"';";
        }
        ResultSet resultat = DataBase.getInstance().selectQuery(query);
        try {
            while (resultat.next()) {
                try {
                    user.setUser_id(resultat.getInt("user_id"));
                } catch (Exception e) {
                    user.setUser_id(id);
                }
                try {
                    login.setNomUsuari(resultat.getString("nomUsuari"));
                } catch (Exception e) {
                    login.setNomUsuari(nomUsuari);
                }
                login.setPassword(resultat.getString("password"));
                try {
                    login.setCorreu(resultat.getString("correu"));
                } catch (Exception e) {
                    login.setCorreu(correu);
                }
                user.setLogin(login);
                try {
                    user.setCodiAmistat(resultat.getString("codiAmistat"));
                } catch (Exception e) {
                    user.setCodiAmistat(codiAmistat);
                }
            }
            /**
             * Falta afegir amics i songs i teclat, i controlar si el que ens introdueixen no existeix
             */

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    private void generaCodiAmistat(){

    }
}
