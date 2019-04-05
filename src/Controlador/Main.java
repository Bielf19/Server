package Controlador;

import Model.Model;
import Network.Servidor;
import Vista.Window1;
import Model.Configuracio;
import Model.DataBase;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                try {

                    Model model = new Model();
                    Window1 window1 = new Window1();
                    Configuracio config = new Configuracio();
                    config = config.llegeixJson();
                    DataBase BBDD = new DataBase(config);
                    Controller1 controller1 = new Controller1(model,window1);
                    Servidor servidor = new Servidor();
                    servidor.openServer(model);

                    window1.registraControlador(controller1);
                    window1.setVisible(true);

                } catch (Exception e) {

                    System.out.println(e.getMessage());

                }

            }
        });

    }
}
