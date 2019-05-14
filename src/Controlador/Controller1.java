package Controlador;

import Model.Model;
import Model.Usuari;
import Vista.Window1;
import Model.Song;
import Model.Evolution;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class Controller1 implements ActionListener, MouseListener {

    private Model model;
    private Window1 window1;

    public Controller1 (Model model, Window1 window1) {
        this.model = model;
        this.window1 = window1;
        System.out.println("prova4");
        window1.setLastWeekEvolucio(model.getPeriodEvolucio(7));
        System.out.println("prova5");
        window1.setLastMonthEvolucio(model.getPeriodEvolucio(30));
        window1.setLastYearEvolucio(model.getPeriodEvolucio(365));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String actionCommand = e.getActionCommand();
        LinkedList<Usuari> users = new LinkedList<Usuari>();
        boolean nicknameOk;
        boolean emailOk;
        Integer fitxer_trobat;
        boolean clientOk;

        /*if (actionCommand.equals("ADD_USER")){

            String userNickname = window1.getUserNickname();
            String userEmail = window1.getUserEmail();
            String userPassword = window1.getUserPassword();

            clientOk = model.comprovaClient(userNickname,userEmail,userPassword);

            if (clientOk) {

                nicknameOk = model.comprovaNickname(userNickname,users);
                emailOk = model.comprovaEmail(userEmail,users);

                if (nicknameOk && emailOk) {

                    window1.cleanJTextfields();
                    model.addUser(userNickname, userEmail, userPassword);
                    window1.addSuccessful();

                } else {

                    if (!nicknameOk) {

                        window1.cleanJTextfields();
                        window1.nicknameError();

                    }

                    if (!emailOk) {

                        window1.cleanJTextfields();
                        window1.emailError();

                    }

                }

            } else {

                window1.cleanJTextfields();
                window1.clientError();

            }

        }*/

        //fitxer_trobat = model.findSongFile(actionCommand, songFiles);
        //window1.consultaDelete();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Object o = e.getSource();
        Integer index;
        LinkedList<Song> songFiles;
        JTable table = new JTable();

        if (o instanceof JTabbedPane) {

            index = ((JTabbedPane) o).getSelectedIndex();

            if (index == 0) {
                //Reconeixem en quin tabbedPane ens trobem mitjançant el getTitleAt
                if (((JTabbedPane) o).getTitleAt(0).equals("Add user")) {

                }

                if (((JTabbedPane) o).getTitleAt(0).equals("Last week evolution")) {

                }

            }

            if (index == 1) {

                if (((JTabbedPane) o).getTitleAt(1).equals("Song files")) {

                    System.out.println("ENTRAAAAA");
                    songFiles = model.getAllSongs();
                    window1.generaLlistaFiles(songFiles);

                }

                if (((JTabbedPane) o).getTitleAt(1).equals("Last month evolution")) {

                }

            }

            if (index == 2) {
                if (((JTabbedPane) o).getTitleAt(2).equals("Users evolution")) {
                    //Enviem les llistes d'evolucio per a poder pintar les gràfiques
                    window1.setLastWeekEvolucio(model.getPeriodEvolucio(7));
                    window1.setLastMonthEvolucio(model.getPeriodEvolucio(30));
                    window1.setLastYearEvolucio(model.getPeriodEvolucio(365));
                    window1.getLlistes();

                }

                if (((JTabbedPane) o).getTitleAt(2).equals("Last year evolution")) {

                }

            }

            if (index == 3) {
                if (((JTabbedPane) o).getTitleAt(3).equals("Top 5 songs")) {
                    Top5Thread thread = new Top5Thread(model, window1);
                    thread.start();
                    System.out.println("TOP5TOP5TOP5TOP5TOP5TOP5TOP5TOP5TOP5TOP5TOP5TOP5");
                    //window1.setTop5(model.getTop5());
                    //window1.generaTaulaTop5();

                }
            }

            System.out.println("Index = "+((JTabbedPane) o).getSelectedIndex());

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


}
