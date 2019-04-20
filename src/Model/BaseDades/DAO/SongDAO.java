package Model.BaseDades.DAO;

import Model.BaseDades.DataBase;
import Model.Song;
import Model.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SongDAO {


    /**
     * Funció que permet afegir una cançó a la BBDD
     * @param song
     */
    public void addSong (Song song) {
        String query = "INSERT INTO Song (privat, fitxer, propietari, titol) VALUES ('"+song.isPrivat()+"', '"+song.getFitxer()+"', '"+song.getPropietari()+"', '"+song.getTitol()+"';";
        DataBase.getInstance().insertQuery(query);
    }



    /**
     * Funció que permet obtenir de la BBDD una cançó ja sigui segons el títol o segons l'id de la cançó a la BBDD
     * @param song_id
     * @param titol
     * @return retorna una Song
     */
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



    public LinkedList<Song> getAllSongs() {
        LinkedList<Song> songs = new LinkedList<>();
        String query = "SELECT song_id FROM Usuaris;";
        ResultSet result = DataBase.getInstance().selectQuery(query);
        try {
            while (result.next()) {
                int id = result.getInt("song_id");
                Song song = getSong(id, null);
                songs.add(song);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }
}
