package Controlador;

import Model.BaseDades.DAO.AmicsDAO;
import Model.BaseDades.DAO.UserDAO;
import Model.BaseDades.DAO.UserSongsDAO;
import Model.Model;
import Network.Servidor;
import Vista.Window1;
import Model.Configuracio;
import Model.BaseDades.DataBase;
import Model.Usuari;
import Model.Login;

import javax.swing.*;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                try {

                    Model model = new Model();
                    Window1 window1 = new Window1();
                    Controller1 controller1 = new Controller1(model,window1);
                    Servidor servidor = new Servidor(model);
                    servidor.start();
                    /*LinkedList<Usuari> usuaris = model.getAllUsers();
                    for (int i = 0; i < usuaris.size(); i++) {
                        System.out.println(usuaris.get(i).getUser_id());
                    }*/
                    LinkedList<String> titols = model.getTitolsDisponibles(2,model.getAmics(2), model.getAllSongs());
                    for (int i = 0; i < titols.size(); i++) {
                        System.out.println(titols.get(i));
                    }
                    window1.registraControlador(controller1);
                    window1.setVisible(true);

                } catch (Exception e) {

                    System.out.println(e.getMessage());

                }

            }
        });

    }
}
