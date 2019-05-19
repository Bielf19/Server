package Model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * La classe Usuari s'utilitzarà per emmagatzemar tota la informació que s'hagi de tenir en compte a l'hora de definir
 * un usuari registrat al sistema.
 */

public class Usuari implements Serializable {
    private int user_id;
    private LinkedList<Song> songs;
    private Login login;
    private LinkedList<Integer> amics;
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

    public LinkedList<Integer> getAmics() {
        return amics;
    }

    public void setAmics(LinkedList<Integer> amics) {
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
