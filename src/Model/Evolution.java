package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

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


    public String getDates(int index) {
        LocalDateTime ldt = LocalDateTime.now().minusDays(index);
        DateTimeFormatter formmat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        String date = formmat1.format(ldt);
        return date;
    }

}
