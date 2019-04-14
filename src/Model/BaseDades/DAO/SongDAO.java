package Model.BaseDades.DAO;

import Model.BaseDades.DataBase;
import Model.Song;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongDAO {

    public Song getSong (int song_id, String titol) {
        Song song = new Song();
        String query = "";
        if (song_id != 0) {
            query = "SELECT privat, fitxer, nReproduccions, propietari, titol FROM Song WHERE song_id = '" + song_id + "';";
        }
        if (titol != null) {
            query = "SELECT * FROM Song WHERE titol = '" + titol + "';";
        }
        ResultSet result = DataBase.getInstance().selectQuery(query);
        try {
            while (result.next()) {
                song.setPrivat((Boolean) result.getObject("privat"));
                try {
                    song.setSong_id(result.getInt("song_id"));
                } catch (Exception e) {
                    song.setSong_id(song_id);
                }
                song.setFitxer(result.getString("fitxer"));
                song.setnReproduccions(result.getInt("nReproduccions"));
                song.setPropietari(result.getString("propietari"));
                try {
                    song.setTitol(result.getString("titol"));
                } catch (Exception e) {
                    song.setTitol(titol);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return song;
    }
}
