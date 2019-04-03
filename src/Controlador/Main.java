package Controlador;

import Model.Model;
import Network.Servidor;
import Vista.Window1;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        Window1 window1 = new Window1();
        Controller1 controller1 = new Controller1(model,window1);
        Servidor servidor = new Servidor();
        servidor.openServer(model);

        window1.registraControlador(controller1);
        window1.setVisible(true);

    }
}
