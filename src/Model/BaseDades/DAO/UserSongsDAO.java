package Model.BaseDades.DAO;

import Model.BaseDades.DataBase;
import Model.UserSongs;
import Model.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class UserSongsDAO {

    public void addSongToUser (int user_id, int song_id) {
        String query = "INSERT INTO UserSongs (user_id, song_id) VALUES ('"+user_id+"', '"+song_id+"');";
        DataBase.getInstance().insertQuery(query);
    }

    public LinkedList<UserSongs> getAllUserSongs () {
        LinkedList<UserSongs> usList = new LinkedList<>();
        UserSongs us = new UserSongs();
        String query = "SELECT user_id, song_id FROM UserSongs;";
        ResultSet resultat = DataBase.getInstance().selectQuery(query);
        try {
            while (resultat.next()) {
                us.setUser_id(resultat.getInt("user_id"));
                us.setSong_id(resultat.getInt("song_id"));
                usList.add(us);
            }
            System.out.println(usList.getFirst().getSong_id());
            return usList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
