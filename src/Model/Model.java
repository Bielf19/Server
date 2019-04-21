package Model;

import Model.BaseDades.DAO.AmicsDAO;
import Model.BaseDades.DAO.SongDAO;
import Model.BaseDades.DAO.UserDAO;
import Model.BaseDades.DAO.UserSongsDAO;

import java.util.LinkedList;

public class Model {

    private UserDAO ud;
    private UserSongsDAO usd;
    private SongDAO sd;
    private AmicsDAO ad;

    public Model () {
        ud = new UserDAO();
        sd = new SongDAO();
        usd = new UserSongsDAO();
        ad = new AmicsDAO();
    }

    public LinkedList<String> getSongsPopularitat(LinkedList <String> nomSongs) {

        //Metode d'ordenació
        for (int i = 1; i < nomSongs.size(); i++) {
            for (int j = nomSongs.size() - 1; j >= i; j--) {
                if (getSong(nomSongs.get(j-1)).getnReproduccions() < getSong(nomSongs.get(j)).getnReproduccions()) {
                    String aux = nomSongs.get(j-1);
                    nomSongs.set(j-1, nomSongs.get(j));
                    nomSongs.set(j, aux);
                }
            }
        }
        return nomSongs;

    }

//*************************************************REGISTRE***********************************************************//

    //Comrpova que el nickname introduit en el registre no estigui repetit

    public boolean comprovaNickname (String userNickname, LinkedList<Usuari> users) {

        boolean nicknameOk = true;
        Login login;
        String nicknameDataBase;

        for (int i = 0; i < users.size(); i++) {

            login = users.get(i).getLogin();
            nicknameDataBase = login.getNomUsuari();

            if (nicknameDataBase.equals(userNickname)) {

                nicknameOk = false;

            }

        }

        return nicknameOk;

    }

    //Comrpova que el email introduit en el registre no estigui repetit

    public boolean comprovaEmail (String userEmail, LinkedList<Usuari> users) {

        boolean emailOk = true;
        Login login;
        String emailDataBase;

        for (int i = 0; i < users.size(); i++) {

            login = users.get(i).getLogin();
            emailDataBase = login.getCorreu();

            if (emailDataBase.equals(userEmail)) {

                emailOk = false;

            }

        }

        return emailOk;

    }



    //Funció per comprovar que el titol de la nova cançó no es repetit
    public boolean comprovaTitol (String titol, LinkedList<Song> songs) {
        boolean titolOk = true;
        for (int i = 0; i < songs.size(); i++) {
            if (titol.equals(songs.get(i).getTitol())) {
                titolOk = false;
            }
        }
        return titolOk;
    }

    //Afegeix l'usuari a la base de dades

    public void addUser (String nickname, String email, String password){

        Usuari newUser = new Usuari();
        Login newLogin = new Login();

        //Afegim el login desitjat.
        newLogin.setNomUsuari(nickname);
        newLogin.setCorreu(email);
        newLogin.setPassword(password);
        newUser.setLogin(newLogin);

        ud.addUser(newUser);

    }



    //Retorna tots els usuaris de la base de dades

    public LinkedList<Usuari> getAllUsers () {
        return ud.getAllUsers();
    }

    //Retorna l'usuari indicat per l'id
    public Usuari getUser(int id) {
        return ud.getUser(id, null, null, null);
    }


    //Retorna l'usuari indicat pel correu
    public Usuari getUser(String correu) {
        return ud.getUser(0, null, correu, null);
    }


    //Retorna l'usuari indicat pel nomUsuari. L'entern que ens passen es simplement per diferenciar de la funció anterior
    public Usuari getUser(String nomUsuari, int i) {
        return ud.getUser(0, nomUsuari, null, null);
    }


    //Estableix una amistat segons el codi d'Amistat

    public void addAmic (String CodiAmistat, int user_id) {
        ad.addAmic(CodiAmistat, user_id);
    }

    //Llista de tots els amics d'un usuari
    public LinkedList<Integer> getAmics (int user_id){
        return ad.getAmics(user_id);
    }


    //Actualització del teclat de l'usuari indicat
    public void updateTeclat(int id, LinkedList<Tecla> t) {
        ud.updateTeclat(id,t);
    }


    //Obtenim una cançó a partir de l'id de la cançó
    public Song getSong (int song_id) {
        return sd.getSong(song_id, null);
    }

    //Obtenim una cançó a partir del titol de la cançó
    public Song getSong (String titol) {
        return sd.getSong(0, titol);
    }


    //Associem una cançó a un usuari
    public void addSongToUser (int user_id, int song_id) {
        usd.addSongToUser(user_id, song_id);
    }


    //Obtenim totes les cançons de la BBDD
    public LinkedList<Song> getAllSongs() {
        return sd.getAllSongs();
    }


    //Obtenim la configuració del teclat a partir del correu
    public LinkedList<Tecla> getTeclat(String correu) {
        return ud.getTeclat(correu);
    }


    //Afegir una cançó a la base de dades
    public void addSong (Song song) {
        sd.addSong(song);
    }



    //Funció que extreu les cançons disponibles que té cada usuari
    public LinkedList<String> getTitolsDisponibles (int user_id, LinkedList<Integer> amics, LinkedList<Song> songs) {
        LinkedList<String> titols = new LinkedList<>();
        LinkedList<UserSongs> usList = getAllUserSongs();
        //Afegim les cançons del propi usuari
        titols = getUserSongs_titols(user_id, titols, usList);
        //Afegm el titol de les cançons privades dels amics
        for (int i = 0; i < amics.size(); i++) {
            titols = getUserSongs_titols(amics.get(i), titols, usList);
        }
        //Afegim el títol de les cançons públiques
        for (int i = 0; i < songs.size(); i++) {
            if (!songs.get(i).isPrivat()) {
                titols.add(songs.get(i).getTitol());
            }
        }
        //Ordenem per popularitat
        titols = getSongsPopularitat(titols);
        return titols;

    }



    //Funció que retorna la llista de les cançons associades als usuaris
    public LinkedList<UserSongs> getAllUserSongs () {
        return usd.getAllUserSongs();
    }


    //Funció que permet obtindre el títol de les cançons privades d'un usuari determinat
    public LinkedList<String> getUserSongs_titols (int user_id, LinkedList<String> titols, LinkedList<UserSongs> usList) {
        return usd.getUserSongs_titols(user_id, titols, usList);
    }

//**********************************************AUTENTIFICACIO********************************************************//

    //Funcio que s'utilitzarà per autentificar el email del usuari en l'inici de sessió

    public boolean authenticationEmail (String emailIntroduit, LinkedList<Usuari> users) {

        boolean authenticationOk = false;
        Login login;

        for (int i = 0; i < users.size(); i++) {

            login = users.get(i).getLogin();

            if (login.getNomUsuari().equals(emailIntroduit)) {

                authenticationOk = true;

            }

        }

        return authenticationOk;

    }

    //Funcio que s'utilitzarà per autentificar el nom d'usuari en l'inici de sessió

    public boolean authenticationNickname (String nicknameIntroduit, LinkedList<Usuari> users) {

        boolean authenticationOk = false;
        Login login;

        for (int i = 0; i < users.size(); i++) {

            login = users.get(i).getLogin();

            if (login.getNomUsuari().equals(nicknameIntroduit)) {

                authenticationOk = true;

            }

        }

        return authenticationOk;

    }

    //Funcio que s'utilitzara per autenticar la contraseña en l'inici de sessió d'un usuari

    public boolean authenticationPassword (String nickname, String passwordIntroduit, LinkedList<Usuari> users)  {

        boolean authenticationOk = false;
        Login login;

        for (int i = 0; i < users.size(); i++) {

            login = users.get(i).getLogin();

            if (login.getNomUsuari().equals(nickname)) {

                if (login.getPassword().equals(passwordIntroduit)) {

                    authenticationOk = true;

                } else {

                    authenticationOk = false;

                }

            }

        }

        return authenticationOk;

    }






//**************************************************MOSTRAR FITXERS***************************************************//



}
