package Network;
import Model.Usuari;
import Model.Model;
import Model.Tecla;
import Model.Song;

import java.io.File;
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
            LinkedList<String> nomSongs = model.getSongsPopularitat();
            oo.writeObject(nomSongs);

            while(true) {
                try {
                    //Rebem un String que indicarà quina de les opcions volem realitzar
                    String option = (String) oi.readObject();

                    switch (option) {

                        case "1":
                            //Rebem un login
                            oo.writeObject("1");
                            Login login = (Login) oi.readObject();

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
                            break;

                        case "2":
                            oo.writeObject("2");
                            //Preguntar com identificarem quin usuari és. (ho podriem fer amb el correu)
                            //Rebem una llista amb la nova configuració del teclat que guardrem a la BBDD
                            LinkedList<Tecla> teclat = (LinkedList<Tecla>) oi.readObject();
                            /**
                             * Guardem nova configuaració a la BBDD
                             */
                            //Actualitzem el nou teclat
                            oo.writeObject(teclat);
                            break;

                        case "3":
                            oo.writeObject("3");
                            //Rebem una canço que hagi creat un usuari i la guardem a la BBDD
                            Song song = (Song) oi.readObject();
                            /**
                             * Afegim canço a la BBDD  i associem la canço a l'usuari
                             */
                            /**
                             * Actualitzar cançons disponibles
                             */
                            break;

                        case "4":
                            oo.writeObject("4");
                            //Passem una llista amb els noms de cançons ordenades per popularitat
                            //S'ha d'inicialitzar al principi de la vista de client
                            //LinkedList<String> nomSongs = model.getSongsPopularitat();
                            //oo.writeObject(nomSongs);
                            break;

                        case "5":
                            oo.writeObject("5");
                            //Rebem el nom de la canço que es vol reproduir
                            String nom = (String) oi.readObject();
                            /**
                             * Extreiem de la base de dades el fitxer de la canço
                             */
                            //Aquesta línia no existirà, s'haurà extret de la BBDD
                            String fitxer = new String("");
                            oo.writeObject(fitxer);

                            break;

                        case "6":
                            oo.writeObject("6");
                            //Rebem el codi d'amistat i comprovem que coincideixi amb algun usuari de la BBDD
                            boolean existeix = false;
                            String codi = (String) oi.readObject();
                            /**
                             * Busquem el codi a la base de dades
                             */
                            if (existeix) {
                                /**
                                 * Extreiem les noves cançons que podra escoltar l'usuari
                                 */
                                //Passem un booleà que indica si s'ha afegit l'amic correctament
                                oo.writeObject(existeix);
                                //Passem la llista de noms de cançons que ara pot escoltar
                                LinkedList<String> updateSongs = model.getSongsPopularitat();
                                oo.writeObject(updateSongs);
                            } else {
                                oo.writeObject(existeix);
                            }
                            break;

                        case "7":
                            oo.writeObject("7");
                            //Rebem un usuari per eliminar-lo
                            Usuari user = (Usuari) oi.readObject();
                            break;

                    }


                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }




        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
