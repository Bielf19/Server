package Model;

public class ValidaDades {

    private String user;
    private String pass;

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.pass = password;
    }

    //validarNom
    //validarCorreu
    //validarContrasenya
    //confirmaContrasenya


    public boolean usernameOK(String nom){

        if(!nom.equals("")){

            return true;

        }else {

            return false;

        }
    }

    public boolean passwordOK (String password){

        if(!password.equals("")){

            return true;

        }else{

            return false;

        }
    }


    public boolean emailOK(String email){

        if(!email.equals("")){

            return true;

        }else{

            return false;

        }
    }


    public boolean isValidEmailAddress(String email) {

        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();

    }


    public  boolean formatPassword(String pass) {

        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        return pass.matches(pattern);

    }

}
