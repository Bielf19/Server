package Model.BaseDades.DAO;

import Model.BaseDades.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class AmicsDAO {

    /**
     * Mètode que permet afegir parelles d'amics a la BBDD rebent el codi d'amistat i l'id de l'usuari que ha enviat el codi d'amistat
     * @param CodiAmistat
     * @param user_id
     */
    public void addAmic (String CodiAmistat, int user_id) {
        UserDAO ud = new UserDAO();
        int id_amic = ud.searchCodiAmistat(CodiAmistat);
        if (id_amic != 0 && id_amic != user_id) {
            String query = "INSERT INTO Amic (user_id1, user_id2) VALUES ('"+user_id+"', '"+id_amic+"');";
            DataBase.getInstance().insertQuery(query);
        }
    }


    /**
     * Aquest mètode ens permet obtindre una llista d'eneters amb totes els id dels amics d'un usuari
     * @param user_id
     * @return llista d'eneters
     */
    public LinkedList<Integer> getAmics (int user_id){
        LinkedList<Integer> amics = new LinkedList<>();
        //Obtenim les parelles d'id
        String query = "SELECT user_id1, user_id2 FROM Amic;";
        ResultSet result = DataBase.getInstance().selectQuery(query);
        try {
            while (result.next()) {
                 int user_id1 = result.getInt("user_id1");
                 int user_id2 = result.getInt("user_id2");
                 //Mirem si algun dels 2 coincideix per a afegir a l'altra a la llista d'amics
                 if (user_id1 == user_id) {
                     //COmprovem que no l'hàgim afegit ja
                     if (!amics.contains(user_id2)) {
                         amics.add(user_id2);
                     }
                 }
                if (user_id2 == user_id) {
                    if (!amics.contains(user_id1)) {
                        amics.add(user_id1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amics;
    }
}
