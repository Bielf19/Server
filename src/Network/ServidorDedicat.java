package Network;
import Model.Usuari;
import Model.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import Model.Login;

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

            try {
                //Rebem un String que indicarà quina de les opcions volem realitzar
                String option = (String)oi.readObject();

                switch(option) {

                    case "1":
                        //Rebem un login
                        Login login = (Login)oi.readObject();

                        //Mirem si el camp correu es ple o no per a saber si es un nou registre o un login
                        if (login.getCorreu() == null) {
                            boolean loginOK = true;
                            Usuari usuari = new Usuari();
                            /**
                             * Comprovem que contrasenya es correcte i donem accés al programa
                             */
                            //Passem un boolea per a que el programa client sàpiga si s'ha fet bé el login
                            oo.writeObject(loginOK);
                            if (loginOK) {
                                //Passem la configuració del teclat d'aquell usuari
                                oo.writeObject(usuari.getTecles());
                            }
                        } else {
                            boolean registreOK = true;
                            Usuari usuari = new Usuari();
                            /**
                             * Comprovem credencials de registre i afegim a la BBDD creant un nou usuari
                             */
                            //Passem un boolea per a que el programa client sàpiga si s'ha fet bé el registre
                            oo.writeObject(registreOK);
                            if (registreOK) {
                                //Passem la configuració del teclat d'aquell usuari
                                oo.writeObject(usuari.getTecles());
                            }

                        }



                }



            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }




        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
