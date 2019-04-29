package Model.BaseDades.DAO;

import Model.BaseDades.DataBase;
import Model.Evolution;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.LinkedList;


public class EvolucioDAO {

    public void add_Dies () {
        LinkedList<Evolution> evolucio = getDadesEvolucio();
        Evolution e = new Evolution();
        if (evolucio.size() == 0) {
            String date = e.getDates(0);
            addData(date);
        } else {
            String date = e.getDates(0);
            while (date != evolucio.getLast().getDate()) {

            }
        }
        /*String date = e.getDates(0);
        String query = "INSERT INTO Evolucio (data) VALUES ('"+date+"');";
        DataBase.getInstance().insertQuery(query);*/
    }


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
        System.out.println(evolucio.size());

        return evolucio;
    }

    public void addData (String date) {

        String query = "INSERT INTO Evolucio (data) VALUES ('"+date+"');";
        DataBase.getInstance().insertQuery(query);

    }






}
