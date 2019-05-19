package Model;

/**
 * La classe ValidaDades s'utilitzarà per a implementar les comprovacions del client a l'hora de registrar usuaris
 * en el servidor.
 */

public class ValidaDades {

    private String user;
    private String pass;

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.pass = password;
    }

    /**
     * Procediment que s'utilitzara per a comprovar que el camp del usuari a l'hora de realitzar el registre no estigui
     * buit.
     * @param nom String; conte el nickname a comprovar.
     * @return Un boolea que indica si el nickname es correcte o no.
     */

    public boolean usernameOK(String nom){

        if(!nom.equals("")){

            return true;

        }else {

            return false;

        }
    }

    /**
     * Procediment que s'utilitzara per a comprovar que el camp de la contrasenya a l'hora de realitzar el registre no
     * estigui buit.
     * @param password String; conte la contrasenya a comprovar.
     * @return Un boolea que indica si la contrasenya es correcte o no.
     */

    public boolean passwordOK (String password){

        if(!password.equals("")){

            return true;

        }else{

            return false;

        }
    }

    /**
     * Procediment que s'utilitzara per a comprovar que el camp de la direccio de correu electronic a l'hora de
     * realitzar el registre no esta buida.
     * @param email String; conte la direccio de correu electronic a comprovar
     * @return Un boolea que indica si la direccio de correu electronic es correcte o no.
     */

    public boolean emailOK(String email){

        if(!email.equals("")){

            return true;

        }else{

            return false;

        }
    }

    /**
     * Funcio que s'utilitzara per a comprovar que la direccio de correu electronica introduida a l'hora de registrar
     * un usuari es valida o no.
     * @param email String; conte la direccio de correu electronic a comprovar.
     * @return  Un boolea que indica si la direccio de correu electronic es correcte o no.
     */

    public boolean isValidEmailAddress(String email) {

        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();

    }

    /**
     * Funcio que s'utilitzara per a comprovar que la contrasenya introduida a l'hora de registrar un usuari es valida
     * o no.
     * @param pass String; conte contrasenya a comprovar
     * @return Un boolea que indica si la contrasenya es correcte o no.
     */

    public  boolean formatPassword(String pass) {

        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        return pass.matches(pattern);

    }

}
