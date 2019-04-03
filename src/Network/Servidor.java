package Network;

import Model.Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * La classe servidor és l'encarregada de d'obrir el servidor per a tots els possibles clients, ja que inicialitza el
 * ServerSocket i dóna accés al servidor dedicat.
 */
public class Servidor {
    /**
     * Aquest mètode permet obrir el servidor i inicialitzar el servidor dedicat
     * @param model; rep el model per passar-li al servidor dedicat
     */
    public void openServer(Model model) {
        try {
            //Creo server socket que escoltara clients
            ServerSocket sServer = new ServerSocket(2000);

            while (true) {
                //Espero a que un client es connecti
                Socket s = sServer.accept();
                ServidorDedicat sd = new ServidorDedicat(s,model);
                sd.start();
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
