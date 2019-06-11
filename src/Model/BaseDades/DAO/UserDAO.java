package Model.BaseDades.DAO;

import Model.*;
import Model.BaseDades.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Classe que gestiona la taula de base de dades Usuari
 * Autors: Pol Caubet, Dani Ulied, Ona Rof, Anna Aguareles, Enric Sasselli, Biel Fernández
 */
public class UserDAO {

    /**
     * Procediment que permet afegir un usuari a la BBDD
     * @param user Usuari
     */
    public void addUser(Usuari user) {
        String codiAmistat = generaCodiAmistat();
        String query = "INSERT INTO Usuaris (nomUsuari, password, correu, codiAmistat) VALUES ('"+user.getLogin().getNomUsuari()+"', '"+user.getLogin().getPassword()+"', '"+user.getLogin().getCorreu()+"', '"+codiAmistat+"');";
        DataBase.getInstance().insertQuery(query);
    }


    /**
     * Retorna tots els usuaris de la base de dades
     * @return LinkedList <Usuari>
     */
    public LinkedList<Usuari> getAllUsers() {
        LinkedList<Usuari> usuaris = new LinkedList<>();
        Usuari user = new Usuari();
        String query = "SELECT user_id FROM Usuaris;";
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
     * @param id int
     * @param nomUsuari String
     * @param correu String
     * @param codiAmistat String
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
     * @param id int
     * @param t LinkedList<Tecla>
     */
    public void updateTeclat(int id, LinkedList<Tecla> t) {
        for (int i = 0; i < t.size(); i++) {
            String query = "UPDATE Usuaris SET '"+t.get(i).getNota()+"' = '" + t.get(i).getTecla() + "' WHERE user_id = '" + id + ";";
            DataBase.getInstance().updateQuery(query);
        }

    }


    /**
     * Mètode on agafarem de la base de dades tota la configuració del teclat de l'usuari que correspongui el correu
     * @param correu
     * @return Tecla[]  array de la classe tecla amb tota la configuració
     */
    public Tecla[] getTeclat(String correu) {
        Tecla[] tecles = new Tecla[25];
        for(int i =0;i<tecles.length;i++){
            tecles[i]= new Tecla();
        }
        String query = "SELECT * FROM Usuaris WHERE correu = '"+correu+"';";
        ResultSet result = DataBase.getInstance().selectQuery(query);
        try {

            while (result.next()) {
                Tecla t = new Tecla();
                //tecles.add(t);
                String tecla = result.getString("C4");
                //t.setTecla(tecla.charAt(0));
                //t.setNota("C4");
                tecles[0].setNota("C4");
                tecles[0].setTecla(tecla.charAt(0));

                // System.out.println("Nota: " + tecles.get(0).getNota() + " Tecla: " + tecles.get(0).getTecla());
                tecla = result.getString("D4");
                tecles[1].setNota("D4");
                tecles[1].setTecla(tecla.charAt(0));
                /*t.setTecla(tecla.charAt(0));
                t.setNota("D4");
                tecles.add(t);*/
                tecla = result.getString("E4");
                tecles[2].setNota("E4");
                tecles[2].setTecla(tecla.charAt(0));
                /*t.setTecla(tecla.charAt(0));
                t.setNota("E4");
                tecles.add(t);*/
                tecla = result.getString("F4");
                tecles[3].setNota("F4");
                tecles[3].setTecla(tecla.charAt(0));

                tecla = result.getString("G4");
                tecles[4].setNota("G4");
                tecles[4].setTecla(tecla.charAt(0));

                tecla = result.getString("A4");
                tecles[5].setNota("A4");
                tecles[5].setTecla(tecla.charAt(0));

                tecla = result.getString("B4");
                tecles[6].setNota("B4");
                tecles[6].setTecla(tecla.charAt(0));

                tecla = result.getString("C5");
                tecles[7].setNota("C5");
                tecles[7].setTecla(tecla.charAt(0));

                tecla = result.getString("D5");
                tecles[8].setNota("D5");
                tecles[8].setTecla(tecla.charAt(0));

                tecla = result.getString("E5");
                tecles[9].setNota("E5");
                tecles[9].setTecla(tecla.charAt(0));

                tecla = result.getString("F5");
                tecles[10].setNota("F5");
                tecles[10].setTecla(tecla.charAt(0));

                tecla = result.getString("G5");
                tecles[11].setNota("G5");
                tecles[11].setTecla(tecla.charAt(0));

                tecla = result.getString("A5");
                tecles[12].setNota("A5");
                tecles[12].setTecla(tecla.charAt(0));

                tecla = result.getString("B5");
                tecles[13].setNota("B5");
                tecles[13].setTecla(tecla.charAt(0));

                tecla = result.getString("C6");
                tecles[14].setNota("C6");
                tecles[14].setTecla(tecla.charAt(0));

                tecla = result.getString("C#4");
                tecles[15].setNota("C#4");
                tecles[15].setTecla(tecla.charAt(0));

                tecla = result.getString("D#4");
                tecles[16].setNota("D#4");
                tecles[16].setTecla(tecla.charAt(0));

                tecla = result.getString("F#4");
                tecles[17].setNota("F#4");
                tecles[17].setTecla(tecla.charAt(0));

                tecla = result.getString("G#4");
                tecles[18].setNota("G#4");
                tecles[18].setTecla(tecla.charAt(0));

                tecla = result.getString("A#4");
                tecles[19].setNota("A#4");
                tecles[19].setTecla(tecla.charAt(0));

                /*tecla = result.getString("B#4");
                tecles[20].setNota("B#4");
                tecles[20].setTecla(tecla.charAt(0));*/

                tecla = result.getString("C#5");
                tecles[20].setNota("C#5");
                tecles[20].setTecla(tecla.charAt(0));

                tecla = result.getString("D#5");
                tecles[21].setNota("D#5");
                tecles[21].setTecla(tecla.charAt(0));

                tecla = result.getString("F#5");
                tecles[22].setNota("F#5");
                tecles[22].setTecla(tecla.charAt(0));

                tecla = result.getString("G#5");
                tecles[23].setNota("G#5");
                tecles[23].setTecla(tecla.charAt(0));

                tecla = result.getString("A#5");
                tecles[24].setNota("A#5");
                tecles[24].setTecla(tecla.charAt(0));
                /*tecla = result.getString("F4");
                t.setTecla(tecla.charAt(0));
                t.setNota("F4");
                tecles.add(t);
                tecla = result.getString(GE4");
                tecles[2].setNota("E4");
                tecles[2].setTecla(tecla.charAt(0));
                System.out.println("Nota: " + tecles.get(3).getNota() + " Tecla: " + tecles.get(0).getTecla());
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
                System.out.println("Nota: " + tecles.get(23).getNota() + " Tecla: " + tecles.get(23).getTecla());
                tecla = result.getString("A#5");
                t.setTecla(tecla.charAt(0));
                t.setNota("A#5");
                tecles.add(t);
                System.out.println("Nota: " + tecles.get(23).getNota() + " Tecla: " + tecles.get(23).getTecla());
                for(int i = 0; i < tecles.size(); i++) {
                    System.out.println("Tecles2: " + tecles.get(i).getTecla());
                }*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < tecles.length; i++) {
            System.out.println("Tecles: " + tecles[i].getTecla());
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


    /**
     * Funció que permet eliminar un usuari a partir del seu id
     * @param user_id int
     */
    public void deleteUser(int user_id) {
        String query = "DELETE FROM Usuaris WHERE user_id = '"+user_id+"';";
        DataBase.getInstance().deleteQuery(query);
    }
}
