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
        ResultSet resultat = DataBase.getInstance().selectQuery(query);
        try {
            while (resultat.next()) {
                login.setNomUsuari(resultat.getString("nomUsuari"));
                login.setPassword(resultat.getString("password"));
                login.setCorreu(resultat.getString("correu"));
                user.setLogin(login);
                user.setCodiAmistat(resultat.getString("codiAmistat"));
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
