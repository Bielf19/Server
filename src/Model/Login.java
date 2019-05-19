package Model;

import java.io.Serializable;

/**
 * La classe Login emmagatzemara tota la informacio necessaria per a enregistrar un usuari al sistema: el nickname
 * de l'usuari, la seva direccio de correu electronic i la contrasenya.
 */

public class Login implements Serializable {
    private String nomUsuari;
    private String password;
    private String correu;

    public String getNomUsuari() {
        return nomUsuari;
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreu() {
        return correu;
    }

    public void setCorreu(String correu) {
        this.correu = correu;
    }
}
