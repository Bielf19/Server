package Model;

import java.io.File;
import java.io.Serializable;

public class Song implements Serializable {
    private int song_id;
    private boolean privat;
    private int nReproduccions;
    private File fitxer;
    private String propietari;
    private String titol;

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public boolean isPrivat() {
        return privat;
    }

    public void setPrivat(boolean privat) {
        this.privat = privat;
    }

    public int getnReproduccions() {
        return nReproduccions;
    }

    public void setnReproduccions(int nReproduccions) {
        this.nReproduccions = nReproduccions;
    }

    public File getFitxer() {
        return fitxer;
    }

    public void setFitxer(File fitxer) {
        this.fitxer = fitxer;
    }

    public String getPropietari() {
        return propietari;
    }

    public void setPropietari(String propietari) {
        this.propietari = propietari;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }
}
