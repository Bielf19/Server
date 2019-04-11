package Model.BaseDades.DAO;

import Model.BaseDades.DataBase;
import Model.Usuari;

public class UserDAO {

    private static String codiAmistat;

    public void addUser(Usuari user) {
        String query = "INSERT INTO Usuaris (nomUsuari, password, correu, codiAmistat) VALUES ('"+user.getLogin().getNomUsuari()+"', '"+user.getLogin().getPassword()+"', '"+user.getLogin().getCorreu()+"', '"+codiAmistat+"');";
        System.out.println(query);
        DataBase.getInstance().insertQuery(query);
    }


    public Usuari getLastUser() {
        Usuari user = new Usuari();
        String query = "SELECT COUNT (user_id) FROM Usuaris";
        return user;
    }
    private void generaCodiAmistat(){

    }
}
