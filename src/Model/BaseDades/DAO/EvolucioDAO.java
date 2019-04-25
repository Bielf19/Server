package Model.BaseDades.DAO;

import Model.BaseDades.DataBase;
import Model.Evolution;


public class EvolucioDAO {

    public void add_Dies () {
        Evolution e = new Evolution();
        String date = e.getDates(0);
        String query = "INSERT INTO Evolucio (data) VALUES ('"+date+"');";
        DataBase.getInstance().insertQuery(query);
    }




}
