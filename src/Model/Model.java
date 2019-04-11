package Model;

import Model.BaseDades.DAO.UserDAO;

import java.util.LinkedList;

public class Model {

    public void addUser (String nickname, String email, String password){

        Usuari newUser = new Usuari();
        Login newLogin = new Login();

        //Afegim el login desitjat.
        newLogin.setNomUsuari(nickname);
        newLogin.setCorreu(email);
        newLogin.setPassword(password);
        newUser.setLogin(newLogin);

        addBaseDades(newUser);

    }

    public void addBaseDades (Usuari newUser) {

        UserDAO ud = new UserDAO();
        ud.addUser(newUser);

    }

    public LinkedList<String> getSongsPopularitat() {
        LinkedList<String> nomSongs = new LinkedList<>();
        return nomSongs;
    }
}
