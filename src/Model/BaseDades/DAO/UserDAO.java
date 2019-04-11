package Model.BaseDades.DAO;

import Model.BaseDades.DataBase;
import Model.Login;
import Model.Song;
import Model.UserSongs;
import Model.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class UserDAO {

    private static String codiAmistat;

    public void addUser(Usuari user) {
        String query = "INSERT INTO Usuaris (nomUsuari, password, correu, codiAmistat) VALUES ('"+user.getLogin().getNomUsuari()+"', '"+user.getLogin().getPassword()+"', '"+user.getLogin().getCorreu()+"', '"+codiAmistat+"');";
        System.out.println(query);
        DataBase.getInstance().insertQuery(query);
    }


    /**
     * Mètode amb el que podrem obtindre un usuari de la BBDD necessitant només 1 dels paràmetres que trobem a continuació
     * @param id
     * @param nomUsuari
     * @param correu
     * @param codiAmistat
     * @return usuari
     */
    public Usuari getUser(int id, String nomUsuari, String correu, String codiAmistat) {
        Usuari user = new Usuari();
        Login login = new Login();
        UserSongsDAO usd = new UserSongsDAO();
        SongDAO sd = new SongDAO();
        String query = "";
        //Fem la query segons el paràmetre que ens hagin enviat
        if (id != 0) {
            query = "SELECT nomUsuari, password, correu, codiAmistat FROM Usuaris WHERE user_id = '"+id+"';";
        }
        if (nomUsuari != null) {
            query = "SELECT user_id, password, correu, codiAmistat FROM Usuaris WHERE nomUsuari = '"+nomUsuari+"';";
        }
        if (correu != null) {
            query = "SELECT nomUsuari, password, user_id, codiAmistat FROM Usuaris WHERE correu = '"+correu+"';";
        }
        if (codiAmistat != null) {
            query = "SELECT nomUsuari, password, correu, user_id FROM Usuaris WHERE codiAmistat = '"+codiAmistat+"';";
        }
        ResultSet resultat = DataBase.getInstance().selectQuery(query);
        //Afegim els paràmetre que hem extret de la taula d'Usuari de la BBDD al usuari que hem creat anteriorment
        try {
            while (resultat.next()) {
                try {
                    user.setUser_id(resultat.getInt("user_id"));
                } catch (Exception e) {
                    user.setUser_id(id);
                }
                try {
                    login.setNomUsuari(resultat.getString("nomUsuari"));
                } catch (Exception e) {
                    login.setNomUsuari(nomUsuari);
                }
                login.setPassword(resultat.getString("password"));
                try {
                    login.setCorreu(resultat.getString("correu"));
                } catch (Exception e) {
                    login.setCorreu(correu);
                }
                user.setLogin(login);
                try {
                    user.setCodiAmistat(resultat.getString("codiAmistat"));
                } catch (Exception e) {
                    user.setCodiAmistat(codiAmistat);
                }
            }
            //Afegim les songs que té associades aquest usuari obtenint una llista de totes les cançons a quin usuari pretanyen
            //(UserSongs) i mirem quines cançons coincideixen amb aquest usuari per afegir-les a la seva llista.
            LinkedList<UserSongs> usList = usd.getAllUserSongs();
            LinkedList<Song> songs = new LinkedList<>();
            int count = 0;
            for (int i = 0; i < usList.size(); i++) {
                if (usList.get(i).getUser_id() == user.getUser_id()) {
                    System.out.println("Prova1");
                    songs.add(sd.getSong(usList.get(i).getSong_id(), null));
                    System.out.println("Prova2");
                    count ++;
                }
            }

            user.setSongs(songs);

            /**
             * Falta afegir amics i songs i teclat, i controlar si el que ens introdueixen no existeix
             */

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    private void generaCodiAmistat(){

    }
}
