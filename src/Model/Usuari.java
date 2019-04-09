package Model;

import java.util.LinkedList;

public class Usuari {
    private LinkedList<Song> songs;
    private Login login;
    private LinkedList<Usuari> amics;
    private LinkedList<Tecla> tecles;
    private String codiAmistat;

    public LinkedList<Song> getSongs() {
        return songs;
    }

    public void setSongs(LinkedList<Song> songs) {
        this.songs = songs;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public LinkedList<Usuari> getAmics() {
        return amics;
    }

    public void setAmics(LinkedList<Usuari> amics) {
        this.amics = amics;
    }

    public LinkedList<Tecla> getTecles() {
        return tecles;
    }

    public void setTecles(LinkedList<Tecla> tecles) {
        this.tecles = tecles;
    }

    public String getCodiAmistat() {
        return codiAmistat;
    }

    public void setCodiAmistat(String codiAmistat) {
        this.codiAmistat = codiAmistat;
    }
}
