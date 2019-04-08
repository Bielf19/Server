package Model;

public class Model {

    public void addUser (String nickname, String email, String password){

        Usuari newUser = new Usuari();
        Login newLogin = new Login();

        //Afegim el login desitjat.
        newLogin.setNomUsuari(nickname);
        newLogin.setCorreu(email);
        newLogin.setPassword(password);
        newUser.setLogin(newLogin);

        //La llista d'amics i la configuració de les tecles estaran a "null" inicialment.
        newUser.setAmics(null);
        newUser.setTecles(null);

        addBaseDades(newUser);

    }

    public void addBaseDades (Usuari newUser) {

    }

}
