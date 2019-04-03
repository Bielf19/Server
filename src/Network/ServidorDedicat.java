package Network;

import Model.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * El servidor dedicat obrirà un fil d'execució per a cada client que es connecti i permetrà realitzar qualsevol tasca
 * que impliqui accedir o emmagatzemar a la base de dades.
 */
public class ServidorDedicat extends Thread{

    private Model model;
    private Socket s;

    /**
     * Inicialitzem el Servidor dedicat
     * @param s; rebem el socket
     * @param model; rebem el model per gestionar la informació
     */
    public ServidorDedicat (Socket s, Model model) {
        this.model = model;
        this.s = s;
    }

    /**
     * Comencem el fil d'execució. Segons la necessitat del client es realitzaran unes funcionalitats o unes altres
     * segons un primer parametre que ens enviaran.
     */
    @Override
    public void run() {
        try {
            //Creo els streams
            ObjectOutputStream oo = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream oi = new ObjectInputStream(s.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
