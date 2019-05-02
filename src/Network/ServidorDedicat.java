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
public class  ServidorDedicat extends Thread{

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


            while(true) {
                try {
                    //Rebem un String que indicarà quina de les opcions volem realitzar
                    String option = (String) oi.readObject();
                    switch (option) {

                        case "1":
                            //Rebem un login
                            oo.writeObject("1");
                            Login login = (Login) oi.readObject();
                            System.out.println("PROVAPROVA");
                            System.out.println(login.getNomUsuari());
                            boolean loginOK;
                            Usuari user = new Usuari();
                            LinkedList<Usuari> users = model.getAllUsers();
                            //Mirem si el camp correu es ple o no per a saber si fa login per nomUsuari o per correu
                            if (login.getCorreu() == null) {
                                loginOK = model.authenticationNickname(login.getNomUsuari(), users);
                                if(loginOK) {
                                    user = model.getUser(login.getNomUsuari(), 0);
                                }
                            } else {
                                loginOK = model.authenticationEmail(login.getCorreu(), users);
                                if (loginOK) {
                                    user = model.getUser(login.getCorreu());
                                }
                            }
                            if (!loginOK) {
                                //Passem un boolea per a que el programa client sàpiga si s'ha fet bé el login
                                oo.writeObject(false);
                            } else {

                                //Comprovem que la contrasenya sigui correcte
                                boolean passwordOK = model.authenticationPassword(user.getLogin().getNomUsuari(), login.getPassword(), users);

                                //Passem un boolea per a que el programa client sàpiga si s'ha fet bé el login
                                oo.writeObject(passwordOK);
                                if (passwordOK) {
                                    //Passem la configuració del teclat d'aquell usuari
                                    oo.writeObject(user.getTecles());
                                    oo.writeObject(model.getNomAmics(user.getUser_id()));
                                    LinkedList<Integer> idsongs = model.getAllUserSongs_id(user.getUser_id(),model.getAllUserSongs());
                                    LinkedList<String> songs = new LinkedList<>();
                                    for (int i = 0; i < idsongs.size(); i++) {
                                        songs.add(model.getSong(idsongs.get(i)).getTitol());
                                    }
                                    songs = model.getSongsPopularitat(songs);
                                    oo.writeObject(songs);


                                    /**
                                     * HAURIEM DE PASSAR ELS TITOLS DE LES CANÇONS QUE POT REPRODUIR
                                     */
                                }
                            }
                            break;

                        case "2":
                            oo.writeObject("2");
                            //Preguntar com identificarem quin usuari és. (ho podriem fer amb el correu)
                            //Rebem una llista amb la nova configuració del teclat que guardrem a la BBDD
                            //Rebem l'id de l'usuari que canvia la configuració
                            int user_id = (int) oi.readObject();
                            LinkedList<Tecla> teclat = (LinkedList<Tecla>) oi.readObject();
                            //Guardem nova configuaració a la BBDD
                            model.updateTeclat(user_id, teclat);
                            //Actualitzem el nou teclat (NO CAL REALMENT)
                            oo.writeObject(teclat);
                            break;

                        case "3":
                            oo.writeObject("3");
                            //Rebem el user_id de l'usuari que fa la cançó
                            user_id = (int) oi.readObject();
                            //Rebem una canço que hagi creat un usuari i la guardem a la BBDD
                            Song song = (Song) oi.readObject();
                            boolean titolOk = model.comprovaTitol(song.getTitol(), model.getAllSongs());
                            if (!titolOk) {
                                //Enviem un false al client per a que canvïin el títol de la cançó
                                oo.writeObject(false);
                            } else {
                                //Associem la canço amb l'usuari
                                song.setPropietari(""+user_id);
                                model.addSong(song);
                                song = model.getSong(song.getTitol());
                                model.addSongToUser(user_id, song.getSong_id());
                                //Enviem un true al client confirmant que s'ha afegit la cançó
                                oo.writeObject(true);
                                //Enviem una llista amb els titols de les cançons disponibles per a aquest usuari
                                oo.writeObject(model.getTitolsDisponibles(user_id, model.getAmics(user_id), model.getAllSongs()));
                            }
                            break;

                        case "4":
                            oo.writeObject("4");
                            /**
                             * Potser no caldria, parlar-ho
                             */
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
                             * S'ha d'incrementar el numero de reproduccions
                             */
                            //Extreiem de la base de dades el fitxer de la canço i incrementem el numero de reproduccions
                            song = model.getSong(nom);
                            song.setnReproduccions(song.getnReproduccions() + 1);
                            //Passem el fitxer pel socket
                            oo.writeObject(song.getFitxer());

                            break;

                        case "6":
                            oo.writeObject("6");
                            //Rebem el codi d'amistat i l'id de l'usuari que l'envia i comprovem que coincideixi amb algun usuari de la BBDD
                            String codi = (String) oi.readObject();
                            user_id = (int) oi.readObject();
                            //Busquem el codi a la BBDD i si el trobem afegim l'usuari del codi com amic
                            boolean existeix = model.addAmic(codi, user_id);
                            if (existeix) {
                                //Passem un booleà que indica si s'ha afegit l'amic correctament
                                oo.writeObject(true);
                                //Passem la llista de noms de cançons que ara pot escoltar
                                oo.writeObject(model.getTitolsDisponibles(user_id, model.getAmics(user_id), model.getAllSongs()));

                            } else {
                                //Passem un booleà indicant que no s'ha afegit l'amic
                                oo.writeObject(false);
                            }
                            break;

                        case "7":
                            oo.writeObject("7");
                            /**
                             * PODRIA SER NOMES EL user_id
                             */
                            //Rebem un usuari per eliminar-lo
                            user = (Usuari) oi.readObject();
                            LinkedList<Integer> song_ids = model.getAllUserSongs_id(user.getUser_id(), model.getAllUserSongs());
                            for (int i = 0; i < song_ids.size(); i++ ) {
                                model.deleteSong(song_ids.get(i));
                            }
                            model.deleteUserSong(user.getUser_id());
                            model.deleteAmic(user.getUser_id());
                            model.deleteUser(user.getUser_id());
                            break;

                        case "8":
                            oo.writeObject("8");
                            //Rebem un login
                            users = model.getAllUsers();
                            login = (Login) oi.readObject();
                            boolean nomUsuariOK = model.comprovaNickname(login.getNomUsuari(),users);
                            boolean correuOk = model.comprovaEmail(login.getCorreu(), users);
                            /**
                             * Falta afegir tot el que va fer la ona
                             */
                            boolean passwordOk = true;
                            if (!(nomUsuariOK && correuOk && passwordOk)) {
                                //Passem un false per a que el programa client sàpiga que no s'ha fet be el registre
                                oo.writeObject(false);
                            } else {
                                //Passem un true conforme s'ha fet bé el registre i s'ha afegit l'usuari a la BBDD
                                oo.writeObject(true);
                                //Afegim l'usuari a la BBDD
                                model.addUser(login.getNomUsuari(), login.getCorreu(), login.getPassword());
                                //Passem la configuració del teclat
                                oo.writeObject(model.getTeclat(login.getCorreu()));
                                /**
                                 * HAURIEM DE PASSAR ELS TITOLS DE LES CANÇONS QUE POT REPRODUIR
                                 */
                            }


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
