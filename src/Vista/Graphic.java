package Vista;

import Model.Evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;

public class Graphic extends JPanel {

    private LinkedList<Evolution> e;

    public Graphic (LinkedList<Evolution> e){
        this.e = e;
    }
    @Override
    public void paint(Graphics g) {

        //Eixos de coordenades
        g.drawLine(75,250,440,250);
        g.drawLine(75,250,75,25);

        if (e.size() >= 1) {
            //Establim l'espai que tindrà cada unitat del nombre de reproduccions. Com l'espai és limitat i no podem posar eixos decimals, si ens
            //introdueixen nombre grans, posarem 2 unitats en cada espai, si encara així no cap, posarem 4, i així successivament
            double max = getMaxnUser();
            double espaiY = 225/max;
            int count = 1;
            while(espaiY < 1) {
                max = max/2;
                count = count *2;
                espaiY = 225/max;
            }
            int Y = (int)(espaiY * 1000);
            double espaiX = 365/(e.size()-1);
            int X = (int)(espaiX * 1000);
            //Eix de coordenades
            g.drawLine(75,250,X/1000 * (e.size()-1),250);
            g.drawLine(75,250,75,250 - Y/1000 * getMaxnUser()/count);
            for (int i = 0; i < e.size(); i++) {
                g.setColor(Color.RED);
                try {

                    g.drawLine(75 + X/1000 * i, 250 - e.get(i).getnUsers() * Y/1000/count, 75 + X/1000 * (i + 1), 250 - e.get(i + 1).getnUsers() * Y/1000/count);
                } catch (Exception ex) {

                }
            }
            g.setColor(Color.BLACK);
            g.drawString(e.getFirst().getDate(), 55, 270);
            g.drawString(e.getLast().getDate(),440, 270);
            g.drawString(("" + getMaxnUser()), 40, 250 - getMaxnUser() * Y/1000/count + 10);


        }

    }

    private int getMaxnUser () {
        int max = 0;
        for (int i = 0; i < e.size(); i++){
            if (e.get(i).getnUsers() > max) {
                max = e.get(i).getnUsers();
            }
        }
        return max;
    }

}
