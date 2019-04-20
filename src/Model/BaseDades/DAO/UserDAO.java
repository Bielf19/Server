package Model.BaseDades.DAO;

import Model.*;
import Model.BaseDades.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class UserDAO {


    public void addUser(Usuari user) {
        String codiAmistat = generaCodiAmistat();
        String query = "INSERT INTO Usuaris (nomUsuari, password, correu, codiAmistat) VALUES ('"+user.getLogin().getNomUsuari()+"', '"+user.getLogin().getPassword()+"', '"+user.getLogin().getCorreu()+"', '"+codiAmistat+"');";
        DataBase.getInstance().insertQuery(query);
    }



    public LinkedList<Usuari> getAllUsers() {
        LinkedList<Usuari> usuaris = new LinkedList<>();
        Usuari user = new Usuari();
        String query = "SELECT * FROM Usuaris;";
        ResultSet result = DataBase.getInstance().selectQuery(query);
        try {
            while (result.next()) {
                int id = result.getInt("user_id");
                user = getUser(id, null, null, null);
                usuaris.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuaris;
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
        AmicsDAO ad = new AmicsDAO();
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
                    songs.add(sd.getSong(usList.get(i).getSong_id(), null));
                    count ++;
                }
            }

            user.setSongs(songs);

            //Afegim una llista amb tots els id dels amics
            LinkedList <Integer> amics = ad.getAmics(user.getUser_id());
            user.setAmics(amics);

            user.setTecles(getTeclat(user.getLogin().getCorreu()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    /**
     * Aquest mètode permetrà actaulitzar a la BBDD la configuració del teclat
     * @param id
     * @param t
     */
    public void updateTeclat(int id, LinkedList<Tecla> t) {
        for (int i = 0; i < t.size(); i++) {
            String query = "UPDATE Usuaris SET '"+t.get(i).getNota()+"' = '" + t.get(i).getTecla() + "' WHERE user_id = '" + id + "';";
            DataBase.getInstance().updateQuery(query);
        }

    }


    /**
     * Mètode on agafarem de la base de dades tota la configuració del teclat de l'usuari que correspongui el correu
     * @param correu
     * @return llista de la classe tecla amb tota la configuració
     */
    public LinkedList<Tecla> getTeclat(String correu) {
        LinkedList<Tecla> tecles = new LinkedList<>();
        String query = "SELECT * FROM Usuaris WHERE correu = '"+correu+"';";
        ResultSet result = DataBase.getInstance().selectQuery(query);
        try {

            while (result.next()) {
                Tecla t = new Tecla();
                String tecla = result.getString("C4");
                t.setTecla(tecla.charAt(0));
                t.setNota("C4");
                tecles.add(t);
                tecla = result.getString("D4");
                t.setTecla(tecla.charAt(0));
                t.setNota("D4");
                tecles.add(t);
                tecla = result.getString("E4");
                t.setTecla(tecla.charAt(0));
                t.setNota("E4");
                tecles.add(t);
                tecla = result.getString("F4");
                t.setTecla(tecla.charAt(0));
                t.setNota("F4");
                tecles.add(t);
                tecla = result.getString("G4");
                t.setTecla(tecla.charAt(0));
                t.setNota("G4");
                tecles.add(t);
                tecla = result.getString("A4");
                t.setTecla(tecla.charAt(0));
                t.setNota("A4");
                tecles.add(t);
                tecla = result.getString("B4");
                t.setTecla(tecla.charAt(0));
                t.setNota("B4");
                tecles.add(t);
                tecla = result.getString("C5");
                t.setTecla(tecla.charAt(0));
                t.setNota("C5");
                tecles.add(t);
                tecla = result.getString("D5");
                t.setTecla(tecla.charAt(0));
                t.setNota("D5");
                tecles.add(t);
                tecla = result.getString("E5");
                t.setTecla(tecla.charAt(0));
                t.setNota("E5");
                tecles.add(t);
                tecla = result.getString("F5");
                t.setTecla(tecla.charAt(0));
                t.setNota("F5");
                tecles.add(t);
                tecla = result.getString("G5");
                t.setTecla(tecla.charAt(0));
                t.setNota("G5");
                tecles.add(t);
                tecla = result.getString("A5");
                t.setTecla(tecla.charAt(0));
                t.setNota("A5");
                tecles.add(t);
                tecla = result.getString("B5");
                t.setTecla(tecla.charAt(0));
                t.setNota("B5");
                tecles.add(t);
                tecla = result.getString("C6");
                t.setTecla(tecla.charAt(0));
                t.setNota("C6");
                tecles.add(t);
                tecla = result.getString("C#4");
                t.setTecla(tecla.charAt(0));
                t.setNota("C#4");
                tecles.add(t);
                tecla = result.getString("D#4");
                t.setTecla(tecla.charAt(0));
                t.setNota("D#4");
                tecles.add(t);
                tecla = result.getString("F#4");
                t.setTecla(tecla.charAt(0));
                t.setNota("F#4");
                tecles.add(t);
                tecla = result.getString("G#4");
                t.setTecla(tecla.charAt(0));
                t.setNota("G#4");
                tecles.add(t);
                tecla = result.getString("A#4");
                t.setTecla(tecla.charAt(0));
                t.setNota("A#4");
                tecles.add(t);
                tecla = result.getString("C#5");
                t.setTecla(tecla.charAt(0));
                t.setNota("C#5");
                tecles.add(t);
                tecla = result.getString("D#5");
                t.setTecla(tecla.charAt(0));
                t.setNota("D#5");
                tecles.add(t);
                tecla = result.getString("F#5");
                t.setTecla(tecla.charAt(0));
                t.setNota("F#5");
                tecles.add(t);
                tecla = result.getString("G#5");
                t.setTecla(tecla.charAt(0));
                t.setNota("G#5");
                tecles.add(t);
                tecla = result.getString("A#5");
                t.setTecla(tecla.charAt(0));
                t.setNota("A#5");
                tecles.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tecles;
    }


    /**
     * Mètode amb el que es genera un codi aleatori de 9 xifres que poden ser lletres en majúscula o minúscula o números
     * @return retorna el codi d'amistat
     */
    private String generaCodiAmistat(){
        String codiAmistat = "";
        int caracter = 0;
        for (int i = 0; i < 9; i++) {
                int numero = (int) (Math.random() * 3) + 1;
                switch (numero) {
                    case 1:
                        caracter = (int) (Math.random() * 26) + 97;
                        break;
                    case 2:
                        caracter = (int) (Math.random() * 26) + 65;
                        break;
                    case 3:
                        caracter = (int) (Math.random() * 10) + 48;
                        break;

                }
                char c = (char) caracter;
                codiAmistat = codiAmistat + c;

        }
        System.out.println(codiAmistat);
        return codiAmistat;
    }

    /**
     * Mètode que busca un codi d'amistat de la base de dades i retorna l'id de l'usuari al qual pertany el codi. Si no coincideix retorna un 0.
     * @param codiAmistat
     * @return id de l'amic
     */
    public int searchCodiAmistat(String codiAmistat) {
        int amic_id = 0;
        String query = "SELECT user_id FROM Usuaris WHERE codiAmistat = '"+codiAmistat+"';";
        ResultSet result = DataBase.getInstance().selectQuery(query);
        try {
            while (result.next()) {
                amic_id = result.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amic_id;
    }
}
