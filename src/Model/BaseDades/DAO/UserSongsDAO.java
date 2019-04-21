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


    /**
     * Funció que permet obtindre els titols de totes les cançons privades d'un usuari
     * @param user_id
     * @param titols
     * @param usList
     * @return
     */
    public LinkedList<String> getUserSongs_titolsPrivats (int user_id, LinkedList<String> titols, LinkedList<UserSongs> usList) {
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
     * Funció que permet obtindre els song_id de cada canço que conté un usuari determinat
     * @param user_id
     * @param usList
     * @return
     */
    public LinkedList<Integer> getAllUserSongs_id (int user_id, LinkedList<UserSongs> usList) {
        LinkedList<Integer> song_ids = new LinkedList<>();
        for (int i = 0; i < usList.size(); i++) {
            if (usList.get(i).getUser_id() == user_id) {
                song_ids.add(usList.get(i).getSong_id());
            }
        }
        return song_ids;
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


    /**
     * Aquest mètode elimina totes les associacions a diferentes cançons que té un usuari a partir del seu id
     * @param user_id
     */
    public void deleteUserSong(int user_id) {
        String query = "DELETE FROM UserSongs WHERE user_id = '"+user_id+"';";
        DataBase.getInstance().deleteQuery(query);
    }
}
