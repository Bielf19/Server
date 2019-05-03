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
        g.drawLine(75,250,475,250);
        g.drawLine(75,250,75,25);

        if (e.size() >= 1) {
            int max = getMaxnUser();
            double espaiY = 225/max;
            int Y = (int)Math.round(espaiY);
            double espaiX = 400/(e.size()-1);
            int X = (int)Math.round(espaiX);
            System.out.println(X + "  " + max);
            //Eix de coordenades
            g.drawLine(75,250,X * (e.size()-1),250);
            g.drawLine(75,250,75,250 - Y * max);
            for (int i = 0; i < e.size(); i++) {
                g.setColor(Color.RED);
                try {

                    g.drawLine(75 + X * i, 250 - e.get(i).getnUsers() * Y, 75 + X * (i + 1), 250 - e.get(i + 1).getnUsers() * Y);
                } catch (Exception ex) {

                }
            }
            String m = "" + max;
            g.setColor(Color.BLACK);
            g.drawString(e.getFirst().getDate(), 55, 270);
            g.drawString(e.getLast().getDate(),455, 270);
            g.drawString(m, 40, 250 - Y*max + 10);


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
