package Controlador;

import Model.Model;
import Model.Usuari;
import Vista.Window1;
import Model.Song;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class Controller1 implements ActionListener {

    private Model model;
    private Window1 window1;

    public Controller1 (Model model, Window1 window1) {
        this.model = model;
        this.window1 = window1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String actionCommand = e.getActionCommand();
        LinkedList<Usuari> users = new LinkedList<Usuari>();
        boolean nicknameOk;
        boolean emailOk;
        Integer fitxer_trobat;
        ArrayList<Song> songFiles;

        if (actionCommand.equals("ADD_USER")){

            //usuaris = getUsers(); Agafariem els usuaris de la base de dades

            String userNickname = window1.getUserNickname();
            String userEmail = window1.getUserEmail();
            String userPassword = window1.getUserPassword();

            nicknameOk = model.comprovaNickname(userNickname,users);
            emailOk = model.comprovaEmail (userEmail,users);

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

        }

        //songFiles = new ArrayList<Song>();
        //songFiles = ompleLlistaFitxers; Funcio que agafara els fitxers de la base de dades i els dipositara a la llista

        //fitxer_trobat = model.findSongFile(actionCommand, songFiles);

        //window1.consultaDelete();

    }
}
