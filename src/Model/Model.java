package Model;

import Model.BaseDades.DAO.*;

import java.util.LinkedList;

public class Model {

    private UserDAO ud;
    private UserSongsDAO usd;
    private SongDAO sd;
    private AmicsDAO ad;
    private EvolucioDAO ed;

    public Model () {
        ud = new UserDAO();
        sd = new SongDAO();
        usd = new UserSongsDAO();
        ad = new AmicsDAO();
        ed = new EvolucioDAO();
    }


    /**
     * Mètode que ordena les cançons segons la popularitat
     * @param nomSongs LinkedList <String>
     * @return LinkedList <String>
     */
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

    /**
     * Funció per a obtenir les 5 cançons amb més popularitat
     * @return LinkedList <Song>
     */
    public LinkedList<Song> getTop5 () {
        LinkedList<Song> songs = getAllSongs();
        LinkedList<String> titols = new LinkedList<>();
        for (int i = 0; i < songs.size(); i++) {
            titols.add(songs.get(i).getTitol());
        }

        LinkedList<String> t = getSongsPopularitat(titols);
        LinkedList<Song> top5 = new LinkedList<>();
        for (int i = 0; i < 5 && i < t.size(); i++) {
            Song song = getSong(t.get(i));
            top5.add(song);
        }
        return top5;
    }
//*************************************************REGISTRE***********************************************************//


    /**
     * Comrpova que el nickname introduit en el registre no estigui repetit
     * @param userNickname String
     * @param users LinkedList<Usuari>
     * @return boolean que indica si el nickname es correcte
     */
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

    /**
     * Comrpova que el email introduit en el registre no estigui repetit
     * @param userEmail String
     * @param users LinkedList<Usuari>
     * @return boolean que indica si el nickname es correcte
     */
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





    /**
     * Funció per comprovar que el titol de la nova cançó no es repetit
     * @param titol String
     * @param songs LinkedList<Song>
     * @return boolean que indica si el nickname es correcte
     */
    public boolean comprovaTitol (String titol, LinkedList<Song> songs) {
        boolean titolOk = true;
        for (int i = 0; i < songs.size(); i++) {
            if (titol.equals(songs.get(i).getTitol())) {
                titolOk = false;
            }
        }
        return titolOk;
    }



    /**
     * Afegeix l'usuari a la base de dades
     * @param nickname String
     * @param email String
     * @param password String
     */
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




    /**
     * Retorna tots els usuaris de la base de dades
     * @return LinkedList <Usuari>
     */
    public LinkedList<Usuari> getAllUsers () {
        return ud.getAllUsers();
    }

    /**
     * Retorna l'usuari indicat per l'id
     * @param id int
     * @return Usuari
     */
    public Usuari getUser(int id) {
        return ud.getUser(id, null, null, null);
    }


    /**
     * Retorna l'usuari indicat pel correu
     * @param correu String
     * @return Usuari
     */
    public Usuari getUser(String correu) {
        return ud.getUser(0, null, correu, null);
    }


    /**
     * Retorna l'usuari indicat pel nomUsuari. L'entern que ens passen es simplement per diferenciar de la funció anterior
     * @param nomUsuari String
     * @param i int
     * @return Usuari
     */
    public Usuari getUser(String nomUsuari, int i) {
        return ud.getUser(0, nomUsuari, null, null);
    }



    /**
     * Mètode que permet afegir parelles d'amics a la BBDD rebent el codi d'amistat i l'id de l'usuari que ha enviat el codi d'amistat
     * @param CodiAmistat String; codi d'amistat de l'amic
     * @param user_id
     * @return boolean; mirem si s'ha afegit l'amic
     */
    public boolean addAmic (String CodiAmistat, int user_id) {
        return ad.addAmic(CodiAmistat, user_id);
    }

    /**
     * Aquest mètode ens permet obtindre una llista d'enters amb tots els id dels amics d'un usuari
     * @param user_id
     * @return llista de id's
     */
    public LinkedList<Integer> getAmics (int user_id){
        return ad.getAmics(user_id);
    }


    /**
     * Aquest mètode permetrà actaulitzar a la BBDD la configuració del teclat
     * @param id int
     * @param t LinkedList<Tecla>
     */
    public void updateTeclat(int id, LinkedList<Tecla> t) {
        ud.updateTeclat(id,t);
    }


    /**
     * Obtenim una cançó a partir de l'id de la cançó
     * @param song_id int
     * @return Song
     */
    public Song getSong (int song_id) {
        return sd.getSong(song_id, null);
    }

    /**
     * Obtenim una cançó a partir del titol de la cançó
     * @param titol String
     * @return Song
     */
    public Song getSong (String titol) {
        return sd.getSong(0, titol);
    }


    /**
     * Associem una cançó a un usuari, indicant que ha sigut gravada per aquest
     * @param user_id int
     * @param song_id int
     */
    public void addSongToUser (int user_id, int song_id) {
        usd.addSongToUser(user_id, song_id);
    }


    /**
     * Aquest mètode permet obtindre una llista amb totes les cançons disponibles a la base de dades
     * @return LinkedList<Song>
     */
    public LinkedList<Song> getAllSongs() {
        return sd.getAllSongs();
    }


    /**
     * Mètode on agafarem de la base de dades tota la configuració del teclat de l'usuari que correspongui el correu
     * @param correu
     * @return LinkedList<Tecla> llista de la classe tecla amb tota la configuració
     */
    public Tecla[] getTeclat(String correu) {
        return ud.getTeclat(correu);
    }


    /**
     * Funció que permet afegir una cançó a la BBDD
     * @param song Song
     */
    public void addSong (Song song) {
        sd.addSong(song);
    }


    /**
     * Funció que extreu les cançons disponibles que té cada usuari
     * @param user_id int
     * @param amics LinkedList<Integer> llista amb les associacions s'amics
     * @param songs LinkedList<Song>
     * @return LinkedList<String> amb els titols de les cançons
     */
    public LinkedList<String> getTitolsDisponibles (int user_id, LinkedList<Integer> amics, LinkedList<Song> songs) {
        LinkedList<String> titols = new LinkedList<>();
        LinkedList<UserSongs> usList = getAllUserSongs();
        //Afegim les cançons del propi usuari
        titols = getUserSongs_titolsPrivats(user_id, titols, usList);
        //Afegm el titol de les cançons privades dels amics
        for (int i = 0; i < amics.size(); i++) {
            titols = getUserSongs_titolsPrivats(amics.get(i), titols, usList);
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



    /**
     * Aquesta funció retorna una llista amb l'id de cada cançó associat a l'id de l'usuari que és propietari
     * @return LinkedList<UserSongs>
     */
    public LinkedList<UserSongs> getAllUserSongs () {
        return usd.getAllUserSongs();
    }


    /**
     * Funció que permet obtindre els titols de totes les cançons privades d'un usuari
     * @param user_id int
     * @param titols String
     * @param usList LinkedList<UserSongs> amb les associacions de cançons amb usuaris
     * @return LinkedList<String> amb tots els titols privats
     */
    public LinkedList<String> getUserSongs_titolsPrivats (int user_id, LinkedList<String> titols, LinkedList<UserSongs> usList) {
        return usd.getUserSongs_titolsPrivats(user_id, titols, usList);
    }


    /**
     * Funció que permet obtindre els song_id de cada canço que conté un usuari determinat
     * @param user_id int
     * @param usList LinkedList<UserSongs>
     * @return LinkedList<Integer> amb els id's de les cançons
     */
    public LinkedList<Integer> getAllUserSongs_id (int user_id, LinkedList<UserSongs> usList) {
        return usd.getAllUserSongs_id(user_id, usList);
    }


    /**
     * Funció amb la que eliminarem una canço de la base de dades a partir de  l'id de la canço
     * @param song_id int
     */
    public void deleteSong(int song_id) {
        sd.deleteSong(song_id);
    }

    /**
     * Funció que permet eliminar un usuari a partir del seu id
     * @param user_id int
     */
    public void deleteUser(int user_id) {
        ud.deleteUser(user_id);
    }


    /**
     * Aquest mètode elimina totes les associacions a diferentes cançons que té un usuari a partir del seu id
     * @param user_id int
     */
    public void deleteUserSong(int user_id) {
        usd.deleteUserSong(user_id);
    }


    /**
     * Funció que elimina totes les associacions d'amics d'un usuari determinat
     * @param user_id int
     */
    public void deleteAmic(int user_id) {
        ad.deleteAmic(user_id);
    }


    /**
     * Funció per obtindre el nom d'usuari de tots els amics
     * @param user_id
     * @return LinkedList<String> amb el nom d'usuari dels amics
     */
    public LinkedList<String> getNomAmics (int user_id) {
        return ad.getNomAmics(user_id);
    }


    /**
     * Aquest mètode permet comptabilitzar usuaris a més d'actualitzar la data a la base de dades
     * Actualitza les dates i afegeix 1 usuari si a addUser es posa un 1. Si es posa un 0 simplement s'actualitzen les dades
     */
    public void update_nUsuaris (int addUser) {
        ed.update_nUsuaris(addUser);
    }


    /**
     * Funcio que extreu de la base de dades la data i el nombre d'usuaris
     * @return LinkedList<Evolution> amb la data i el nombre d'usuaris
     */
    public LinkedList<Evolution> getDadesEvolucio () {
        return ed.getDadesEvolucio();
    }


    /**
     * Metode que permet obtindre les dades d'evolucio només dels dies indicats en el parametre rebut
     * @param nDies int indicant els dies
     * @return LinkedList<Evolution> llista amb les dades d'evolucio dels ultims nDies
     */
    public LinkedList<Evolution> getPeriodEvolucio(int nDies) {
        update_nUsuaris(0);
        LinkedList<Evolution> e = getDadesEvolucio();
        LinkedList<Evolution> e_dies = new LinkedList<Evolution>();
        //Ens creem una llista auxiliar ja que obtindrem
        LinkedList<Evolution> aux = new LinkedList<Evolution>();
        for (int i = e.size() - 1; i >= e.size() - nDies && i >= 0; i--) {
            aux.add(e.get(i));
        }
        for (int i = aux.size() -1 ; i >= 0; i--) {
            e_dies.add(aux.get(i));
        }
        return e_dies;
    }


    /**
     * Obtenim id a partir del nom
     * @param nom String
     * @return int amb l'id de l'usuari
     */
    public int getIdUsuari (String nom) {
        Usuari u = ud.getUser(0, nom,null, null);
        return u.getUser_id();
    }


    /**
     * Funció que permet veure si hi ha amics repetits per a un usuari determinat
     * @param user_id int
     * @return boolean; indica si té algun amic repetit o no
     */
    public boolean amicsRepetits (int user_id) {
        return ad.getRepetitsAmics(user_id);
    }


    /**
     * Funció que permet eliminar un enllaç d'amics a partir dels id's
     * @param user_id1 int
     * @param user_id2 int
     */
    public void deleteOneFriend (int user_id1, int user_id2) {
        ad.deleteOneFriend(user_id1, user_id2);
    }


    /**
     * Aquest mètode permet eliminar una associació segons l'id d'una cançó
     * @param song_id int
     */
    public void deleteOneUserSong(int song_id) {
        usd.deleteOneUserSong(song_id);
    }

//**********************************************AUTENTIFICACIO********************************************************//



    /**
     * Funcio que s'utilitzarà per autentificar el email del usuari en l'inici de sessió
     * @param emailIntroduit String
     * @param users LinkedList<Usuari>
     * @return boolean indicant si es correcte
     */
    public boolean authenticationEmail (String emailIntroduit, LinkedList<Usuari> users) {

        boolean authenticationOk = false;
        Login login;

        for (int i = 0; i < users.size(); i++) {

            login = users.get(i).getLogin();

            if (login.getCorreu().equals(emailIntroduit)) {

                authenticationOk = true;

            }

        }

        return authenticationOk;

    }



    /**
     * Funcio que s'utilitzarà per autentificar el nom d'usuari en l'inici de sessió
     * @param nicknameIntroduit String
     * @param users LinkedList<Usuari>
     * @return boolean indicant si es correcte
     */
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



    /**
     * Funcio que s'utilitzara per autenticar la contraseña en l'inici de sessió d'un usuari
     * @param nickname String
     * @param passwordIntroduit String
     * @param users LinkedList<Usuari>
     * @return boolean indicant si es correcte
     */
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

    /**
     * Funció que fa les comprovacions del format de les dades
     * @param user String nom de l'usuari
     * @param email String
     * @param password String
     * @return boolean indicant si es correcte
     */
    public boolean comprovaClient (String user, String email, String password) {

        boolean comprovacioOk;
        ValidaDades vd = new ValidaDades();

       if (vd.usernameOK(user) && vd.passwordOK(password) && vd.emailOK(email) && vd.isValidEmailAddress(email) && vd.formatPassword(password)) {

            comprovacioOk = true;

        } else {

            comprovacioOk = false;

        }

        return comprovacioOk;

    }


}
