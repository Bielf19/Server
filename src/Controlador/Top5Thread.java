package Controlador;

import Model.Model;
import Model.Song;
import Vista.Top5;
import Vista.Window1;

import java.util.LinkedList;

public class Top5Thread extends Thread {

    private Model model;
    private Window1 window1;

    public Top5Thread (Model model, Window1 window1) {
        this.window1 = window1;
        this.model = model;
    }

    @Override
    public synchronized void run() {
        while (true) {
            System.out.println("PROVA11");
            LinkedList<Song> top5 = model.getTop5();
            window1.setTop5(top5);
            for (int i = 0; i < top5.size(); i++) {
                System.out.println(top5.get(i).getTitol());
            }
            System.out.println("PROVA12");
            //window1.setTop5(top5);
            window1.generaTaulaTop5();
            //window1.updateTop5();
        }
    }
}
