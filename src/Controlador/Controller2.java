package Controlador;

import Model.Model;
import Vista.Window1;
import Model.Song;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Controller2 implements MouseListener, ActionListener {

    private Window1 window1;
    private JTable table;
    private Model model;

    public Controller2 (Window1 window1, JTable table, Model model) {
        this.window1 = window1;
        this.table = table;
        this.model = model;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        System.out.println("Controller 2: " + o);
        if (o instanceof JTable) {

                int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
                System.out.println("Columna: " + column);
                int row = e.getY() / table.getRowHeight(); //get the row of the button
                System.out.println("Fila: " + row);

                /*Checking the row or column is valid or not*/
                if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                    Object value = table.getValueAt(row, column);
                    System.out.println("Value: " + value);
                    if (value instanceof JButton) {
                        /*perform a click event*/
                        ((JButton) value).doClick();
                        System.out.println("Es un boto");
                        ArrayList<JButton> botons = window1.getBotons();
                        if (row > 2) {
                            value = table.getValueAt(row, 0);
                            int resposta = window1.consultaDelete();
                            System.out.println("Resposta: " + resposta);
                            if (resposta == 0) {
                                Song song = model.getSong((String) value);
                                model.deleteOneUserSong(song.getSong_id());
                                model.deleteSong(song.getSong_id());
                                window1.generaLlistaFiles(model.getAllSongs());
                                window1.registraControlador2(this);
                            }
                        }

                    }

                }

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
