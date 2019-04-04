package Vista;

import Controlador.Controller1;

import javax.swing.*;

public class Window1 extends JFrame {

    public Window1() {

        //Inicialitzem la finestra grafica
        setTitle("SmartPianoServer");
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    public void registraControlador(Controller1 controller1) {
    }
}
