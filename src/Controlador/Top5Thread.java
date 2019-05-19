package Controlador;

import Model.Model;
import Model.Song;

import Vista.Window1;

import java.util.LinkedList;

/**
 * Classe que exten de Thread per a poder actualitzar en temps real els valors de la taula Top5
 */
public class Top5Thread extends Thread {

    private Model model;
    private Window1 window1;

    public Top5Thread (Model model, Window1 window1) {
        this.window1 = window1;
        this.model = model;
    }

    /**
     * Executem un thread per a actualitzar els valors de la taula de Top5
     */
    @Override
    public synchronized void run() {
        while (true) {
            LinkedList<Song> top5 = model.getTop5();
            window1.setTop5(top5);
            window1.generaTaulaTop5();
        }
    }
}
