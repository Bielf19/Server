package Model.BaseDades.DAO;

import Model.BaseDades.DataBase;
import Model.Song;
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


    public LinkedList<String> getUserSongs_titols (int user_id, LinkedList<String> titols, LinkedList<UserSongs> usList) {
        SongDAO sd = new SongDAO();
        for (int i = 0; i < usList.size(); i++) {
            if (usList.get(i).getUser_id() == user_id) {
                Song song = sd.getSong(usList.get(i).getSong_id(), null);
                if (song.isPrivat() == true) {
                    titols.add(song.getTitol());
                }
            }
        }
        return titols;
    }


    /**
     * Aquesta funció retorna una llista amb l'id de cada cançó associat a l'id de l'usuari que és propietari
     * @return llista de UserSongs
     */
    public LinkedList<UserSongs> getAllUserSongs () {
        LinkedList<UserSongs> usList = new LinkedList<>();

        String query = "SELECT user_id, song_id FROM UserSongs;";
        ResultSet resultat = DataBase.getInstance().selectQuery(query);
        try {
            while (resultat.next()) {
                int user_id = resultat.getInt("user_id");
                int song_id = resultat.getInt("song_id");
                UserSongs us = new UserSongs();
                us.setUser_id(user_id);
                us.setSong_id(song_id);
                usList.add(us);
            }
            return usList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
