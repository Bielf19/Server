package Controlador;

import Model.Model;
import Vista.Window1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        if (actionCommand.equals("ADD_USER")){

            String userNickname = window1.getUserNickname();
            String userEmail = window1.getUserEmail();
            String userPassword = window1.getUserPassword();

            model.addUser(userNickname,userEmail,userPassword);

        }

        if (actionCommand.equals("DELETE")){

            window1.consultaDelete();
            //Esborrar fitxer

        }

    }
}
