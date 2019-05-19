package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * La classe Evolution permet tenir les dades necessàries per a fer les gràfiques. Conté la data i el nombre d'usuaris que s'han connectat
 * Autors: Pol Caubet, Dani Ulied, Ona Rof, Anna Aguareles, Enric Sasselli, Biel Fernández
 */
public class Evolution {

    private String date;
    private Integer nUsers;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getnUsers() {
        return nUsers;
    }

    public void setnUsers(Integer nUsers) {
        this.nUsers = nUsers;
    }


    /**
     * Funció que permet obtindre la data en format yyyy-MM-dd
     * @param index
     * @return
     */
    public String getDates(int index) {
        LocalDateTime ldt = LocalDateTime.now().minusDays(index);
        DateTimeFormatter formmat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        String date = formmat1.format(ldt);
        return date;
    }

}
