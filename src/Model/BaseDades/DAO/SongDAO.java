package Model.BaseDades.DAO;

import Model.BaseDades.DataBase;
import Model.Song;
import Model.Usuari;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Classe que gestiona la taula de base de dades Song
 * Autors: Pol Caubet, Dani Ulied, Ona Rof, Anna Aguareles, Enric Sasselli, Biel Fernández
 */
public class SongDAO {


    /**
     * Funció que permet afegir una cançó a la BBDD
     * @param song Song
     */
    public void addSong (Song song) {
        String query = "INSERT INTO Song (privat, fitxer, propietari, titol) VALUES ("+song.isPrivat()+", '"+song.getFitxer()+"', '"+song.getPropietari()+"', '"+song.getTitol()+"');";
        System.out.println("PROVO: " + query);
        DataBase.getInstance().insertQuery(query);
    }



    /**
     * Funció que permet obtenir de la BBDD una cançó ja sigui segons el títol o segons l'id de la cançó a la BBDD
     * @param song_id int
     * @param titol String
     * @return retorna una Song
     */
    public Song getSong (int song_id, String titol) {
        Song song = new Song();
        String query = "";
        if (song_id != 0) {
            query = "SELECT privat, fitxer, nReproduccions, propietari, titol FROM Song WHERE song_id = " + song_id + ";";
        }
        if (titol != null) {
            query = "SELECT * FROM Song WHERE titol = '" + titol + "';";
        }
        //System.out.println(song_id+" "+titol);
        ResultSet result = DataBase.getInstance().selectQuery(query);
        try {
            while (result.next()) {
                song.setPrivat((Boolean) result.getObject("privat"));
                try {
                    song.setSong_id(result.getInt("song_id"));
                } catch (Exception e) {
                    song.setSong_id(song_id);
                }
                song.setFitxer((String) result.getObject("fitxer"));
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


    /**
     * Aquest mètode permet obtindre una llista amb totes les cançons disponibles a la base de dades
     * @return LinkedList<Song>
     */
    public LinkedList<Song> getAllSongs() {
        LinkedList<Song> songs = new LinkedList<>();
        String query = "SELECT song_id FROM Song;";
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


    /**
     * Funció amb la que eliminarem una canço de la base de dades a partir de  l'id de la canço
     * @param song_id int
     */
    public void deleteSong(int song_id) {
        String query = "DELETE FROM Song WHERE song_id = '"+song_id+"';";
        DataBase.getInstance().deleteQuery(query);
    }


}
