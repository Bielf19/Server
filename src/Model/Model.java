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

    public LinkedList<String> getSongsPopularitat() {

        LinkedList<String> nomSongs = new LinkedList<>();
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


    //Estableix una amistat segons el codi d'Amistat

    public void addAmic (String CodiAmistat, int user_id) {
        ad.addAmic(CodiAmistat, user_id);
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
