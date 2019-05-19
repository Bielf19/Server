package Controlador;

import Model.Model;
import Model.Usuari;
import Vista.Window1;
import Model.Song;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

/**
 * La classe Controller1 s'utilitzarà per a gestionar la comunicacio i la transmissio de dades entre la finestra i la
 * resta del sistema
 */

public class Controller1 implements ActionListener, MouseListener {

    private Model model;
    private Window1 window1;
    private int index;
    private Controller2 controller2;

    public Controller1 (Model model, Window1 window1) {
        this.model = model;
        this.window1 = window1;
        window1.setLastWeekEvolucio(model.getPeriodEvolucio(7));
        window1.setLastMonthEvolucio(model.getPeriodEvolucio(30));
        window1.setLastYearEvolucio(model.getPeriodEvolucio(365));
    }

    /**
     * Procediment que s'encarregara d'interpretar i gestionar les accions que duugui a terme l'usuari a través de
     * la interficie grafica.
     * @param e Action Event; variable que conté el tipus d'accio que ha duut a terme l'usuari
     */

    @Override
    public void actionPerformed(ActionEvent e) {

        String actionCommand = e.getActionCommand();
        LinkedList<Usuari> users = model.getAllUsers();
        boolean nicknameOk;
        boolean emailOk;
        boolean clientOk;

        if (actionCommand.equals("ADD_USER")){

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

        }

    }

    /**
     * Procediment que s'encarregara d'interpretar i gestionar les accions que duugui a terme l'usuari a través de
     * la interficie grafica, concretament en les que hi interve el mouse de l'ordinador.
     * @param e MouseEvent
     */

    @Override
    public void mouseClicked(MouseEvent e) {

        Object o = e.getSource();
        System.out.println(o.getClass());
        LinkedList<Song> songFiles;
        JTable table = new JTable();

        if (o instanceof JTabbedPane) {

            index = ((JTabbedPane) o).getSelectedIndex();

            if (index == 0) {

                //Reconeixem en quin tabbedPane ens trobem mitjançant el getTitleAt
                if (((JTabbedPane) o).getTitleAt(0).equals("Add user")) {

                }

            }

            if (index == 1) {

                if (((JTabbedPane) o).getTitleAt(1).equals("Song files")) {

                    songFiles = model.getAllSongs();
                    table = window1.generaLlistaFiles(songFiles);
                    controller2 = new Controller2(window1, table, model);
                    window1.registraControlador2(controller2);

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

            }

            if (index == 3) {

                if (((JTabbedPane) o).getTitleAt(3).equals("Top 5 songs")) {
                    Top5Thread thread = new Top5Thread(model, window1);
                    thread.start();

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
