package Model.BaseDades.DAO;

import Model.BaseDades.DataBase;
import Model.Evolution;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.LinkedList;


public class EvolucioDAO {

    /**
     * Aquest mètode permet comptabilitzar usuaris a més d'actualitzar la data a la base de dades
     */
    public void update_nUsuaris (int addUser) {
        LinkedList<Evolution> evolucio = getDadesEvolucio();
        Evolution e = new Evolution();
        if (evolucio.size() == 0) {
            String date = e.getDates(0);
            addData(date);
        } else {
            int counter = 0;
            String date = e.getDates(counter);
            while (!date.equals(evolucio.getLast().getDate())) {
                counter ++;
                date = e.getDates(counter);
            }
            while (counter != 0) {
                counter --;
                addData(e.getDates(counter));
            }
        }
        int n = get_nUsuaris(e.getDates(0)) + addUser;
        String query = "UPDATE Evolucio SET nUsuaris = '" + n + "' WHERE data = '" + e.getDates(0) + "';";
        DataBase.getInstance().updateQuery(query);
    }


    /**
     * Funcio que extreu de la base de dades la data i el nombre d'usuaris
     * @return LinkedList<Evolution> amb la data i el nombre d'usuaris
     */
    public LinkedList<Evolution> getDadesEvolucio () {
        LinkedList<Evolution> evolucio = new LinkedList<>();
        String query = "SELECT data, nUsuaris FROM Evolucio;";
        ResultSet result = DataBase.getInstance().selectQuery(query);
        try {
            while (result.next()) {
                String data = result.getString("data");
                int nUsuaris = result.getInt("nUsuaris");
                Evolution e = new Evolution();
                e.setDate(data);
                e.setnUsers(nUsuaris);
                evolucio.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evolucio;
    }


    /**
     * Inserir data al camp Evolucio
     * @param date String
     */
    public void addData (String date) {

        String query = "INSERT INTO Evolucio (data) VALUES ('"+date+"');";
        DataBase.getInstance().insertQuery(query);

    }


    /**
     * Seleccionar el nombre d'usuaris del camp Evolucio
     * @param date String
     * @return int nombre d'usuaris
     */
    public int get_nUsuaris (String date) {
        int n = 0;
        String query = "SELECT nUsuaris FROM Evolucio;";
        ResultSet result = DataBase.getInstance().selectQuery(query);
        try {
            while (result.next()) {
                n = result.getInt("nUsuaris");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }


}
