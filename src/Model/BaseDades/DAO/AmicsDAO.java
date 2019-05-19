package Model.BaseDades.DAO;

import Model.BaseDades.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class AmicsDAO {

    /**
     * Mètode que permet afegir parelles d'amics a la BBDD rebent el codi d'amistat i l'id de l'usuari que ha enviat el codi d'amistat
     * @param CodiAmistat String; codi d'amistat de l'amic
     * @param user_id
     * @return boolean; mirem si s'ha afegit l'amic
     */
    public boolean addAmic (String CodiAmistat, int user_id) {
        UserDAO ud = new UserDAO();
        int id_amic = ud.searchCodiAmistat(CodiAmistat);
        if (id_amic != 0 && id_amic != user_id) {

            String query = "INSERT INTO Amic (user_id1, user_id2) VALUES ('"+user_id+"', '"+id_amic+"');";
            DataBase.getInstance().insertQuery(query);
            return true;
        }
        return false;
    }


    /**
     * Aquest mètode ens permet obtindre una llista d'enters amb tots els id dels amics d'un usuari
     * @param user_id int
     * @return llista de id's
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
                     //Comprovem que no l'hàgim afegit ja
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


    /**
     * Funció que permet veure si hi ha amics repetits per a un usuari determinat
     * @param user_id int
     * @return boolean; indica si té algun amic repetit o no
     */
    public boolean getRepetitsAmics(int user_id) {
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
                    //Comprovem que no l'hàgim afegit ja
                    if (!amics.contains(user_id2)) {
                        amics.add(user_id2);
                    } else {
                        return true;
                    }
                }
                if (user_id2 == user_id) {
                    if (!amics.contains(user_id1)) {
                        amics.add(user_id1);
                    }else {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Funció per obtindre el nom d'usuari de tots els amics
     * @param user_id
     * @return LinkedList<String> amb el nom d'usuari dels amics
     */
    public LinkedList<String> getNomAmics (int user_id) {
       LinkedList<Integer> idAmics = getAmics(user_id);
       LinkedList<String> nomAmics = new LinkedList<>();
       UserDAO ud = new UserDAO();
       for (int i = 0; i < idAmics.size(); i++) {
           nomAmics.add(ud.getUser(idAmics.get(i),null, null, null).getLogin().getNomUsuari());
       }
       return nomAmics;
    }


    /**
     * Funció que elimina totes les associacions d'amics d'un usuari determinat
     * @param user_id int
     */
    public void deleteAmic(int user_id) {
        String query = "DELETE FROM Amic WHERE user_id1 = '"+user_id+"' OR user_id2 = '"+user_id+"';";
        DataBase.getInstance().deleteQuery(query);
    }


    /**
     * Funció que permet eliminar un enllaç d'amics a partir dels id's
     * @param user_id1 int
     * @param user_id2 int
     */
    public void deleteOneFriend (int user_id1, int user_id2) {
        String query = "DELETE FROM Amic WHERE user_id1 = '"+user_id1+"' AND user_id2 = '"+user_id2+"';";
        DataBase.getInstance().deleteQuery(query);
        query = "DELETE FROM Amic WHERE user_id1 = '"+user_id2+"' OR user_id2 = '"+user_id1+"';";
        DataBase.getInstance().deleteQuery(query);
    }




}
