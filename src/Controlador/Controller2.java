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

/**
 * Aquest controlador permetrà gestionar la taula on tenim tots els song files.
 * Utilitzarem el MouseListener per a detectar si es prem algun boto delete i a partir d'aquí esborrar la Song del sistema
 */
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
        if (o instanceof JTable) {

                int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button

                int row = e.getY() / table.getRowHeight(); //get the row of the button


                /*Checking the row or column is valid or not*/
                if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                    Object value = table.getValueAt(row, column);

                    if (value instanceof JButton) {
                        /*perform a click event*/
                        ((JButton) value).doClick();
                        ArrayList<JButton> botons = window1.getBotons();
                        if (row > 2) {
                            value = table.getValueAt(row, 0);
                            int resposta = window1.consultaDelete();
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
