package Vista;

import Model.Song;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Top5 extends JPanel {

    private LinkedList<Song> top5;


    public Top5 (LinkedList<Song> top5) {
        this.top5 = top5;
    }

    @Override
    public synchronized void paint(Graphics g) {
        if (top5.size() > 0) {
            g.drawLine(75, 290, 495, 290);
            //Establim l'espai que tindrà cada unitat del nombre de reproduccions. Com l'espai és limitat i no podem posar eixos decimals, si ens
            //introdueixen nombre grans, posarem 2 unitats en cada espai, si encara així no cap, posarem 4, i així successivament
            double espaiY = 250/getMaxnReproduccions();
            double max = getMaxnReproduccions();
            int count = 1;
            while(espaiY < 1) {
                max = max/2;
                count = count *2;
                espaiY = 250/max;
            }
            int Y = (int)(espaiY * 1000);
            for (int i = 0; i < top5.size(); i++) {
                g.fillRect(95 + i*80, 290 - top5.get(i).getnReproduccions() * Y/(count * 1000), 60, top5.get(i).getnReproduccions() * Y/(1000*count));
                if(parell(i)) {
                    g.drawString(top5.get(i).getTitol(), 125 - top5.get(i).getTitol().length() * 3 + i * 80, 305);
                    System.out.println("length = " + top5.get(i).getTitol().length()/2);
                } else {
                    g.drawString(top5.get(i).getTitol(), 125 - top5.get(i).getTitol().length()* 3 + i * 80, 325);
                }
                g.drawString(("" + top5.get(i).getnReproduccions()), 120 + i * 80, 270 - top5.get(i).getnReproduccions() * Y/(1000*count));
            }
        }
    }


    private int getMaxnReproduccions () {
        int max = 0;
        for (int i = 0; i < top5.size(); i++){
            if (top5.get(i).getnReproduccions() > max) {
                max = top5.get(i).getnReproduccions();
            }
        }
        return max;
    }

    private boolean parell(int i) {

            float t = (float)i/2;
        System.out.println("t = " + t);
            if(t%1 == 0) {
                return true;
            }
            return false;
    }





}
