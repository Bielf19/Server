package Network;
import Model.Usuari;
import Model.Model;
import Model.Tecla;
import Model.Song;

import java.io.*;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;

import Model.Login;

/**
 * El servidor dedicat obrirà un fil d'execució per a cada client que es connecti i permetrà realitzar qualsevol tasca
 * que impliqui accedir o emmagatzemar a la base de dades.
 * Autors: Pol Caubet, Dani Ulied, Ona Rof, Anna Aguareles, Enric Sasselli, Biel Fernández
 */
public class  ServidorDedicat extends Thread{

    private Model model;
    private Socket s;

    /**
     * Inicialitzem el Servidor dedicat
     * @param s Socket; rebem el socket
     * @param model Model; rebem el model per gestionar la informació
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

                                oo.writeObject(passwordOK);
                                if (passwordOK) {
                                    //Passem la configuració del teclat d'aquell usuari
                                    /**LinkedList<Tecla> t= new LinkedList<Tecla>();
                                    t=user.getTecles();
                                    for(int i =0;i<t.size();i++){
                                        System.out.println("PAPAYA  "+t.get(i).getNota());
                                    }*/


                                    oo.writeObject(user.getTecles());
                                    oo.writeObject(model.getNomAmics(user.getUser_id()));
                                    LinkedList<String> songs = model.getTitolsDisponibles(user.getUser_id(), model.getAmics(user.getUser_id()), model.getAllSongs());
                                    oo.writeObject(songs);
                                    oo.writeObject(user.getUser_id());
                                    oo.writeObject(user.getCodiAmistat());
                                    //Comptabilitzem un usuari
                                    model.update_nUsuaris(1);

                                }
                            }
                            break;

                        case "2":
                            oo.writeObject("2");
                            //Preguntar com identificarem quin usuari és. (ho podriem fer amb el correu)
                            //Rebem una llista amb la nova configuració del teclat que guardrem a la BBDD
                            //Rebem l'id de l'usuari que canvia la configuració
                            String user_name = (String) oi.readObject();
                            int user_id = model.getIdUsuari(user_name);
                            LinkedList<Tecla> teclat = (LinkedList<Tecla>) oi.readObject();
                            //Guardem nova configuaració a la BBDD

                            /**for(int i = 0;i<teclat.size();i++){
                             System.out.println("POMA   "+teclat.get(i).getNota()+"       "+teclat.get(i).getTecla());
                             }
                             for (int i = 0; i < teclat.size(); i++) {
                             if (teclat.get(i).getNota().equals("C#4")) {
                             teclat.get(i).setNota("C44");
                             }
                             if (teclat.get(i).getNota().equals("D#4")) {
                             teclat.get(i).setNota("D44");
                             }
                             if (teclat.get(i).getNota().equals("F#4")) {
                             teclat.get(i).setNota("F44");
                             }
                             if (teclat.get(i).getNota().equals("G#4")) {
                             teclat.get(i).setNota("G44");
                             }
                             if (teclat.get(i).getNota().equals("A#4")) {
                             teclat.get(i).setNota("A44");
                             }
                             if (teclat.get(i).getNota().equals("C#5")) {
                             teclat.get(i).setNota("C55");
                             }
                             if (teclat.get(i).getNota().equals("D#5")) {
                             teclat.get(i).setNota("D55");
                             }
                             if (teclat.get(i).getNota().equals("F#5")) {
                             teclat.get(i).setNota("F55");
                             }
                             if (teclat.get(i).getNota().equals("G#5")) {
                             teclat.get(i).setNota("G55");
                             }
                             if (teclat.get(i).getNota().equals("A#5")) {
                             teclat.get(i).setNota("A55");
                             }
                             }*/


                            model.updateTeclat(user_id, teclat);
                            //Actualitzem el nou teclat (NO CAL REALMENT)
                            oo.writeObject(teclat);
                            break;

                        case "3":
                            oo.writeObject("3");
                            //Rebem el user_id de l'usuari que fa la cançó
                            user_name = (String) oi.readObject();
                            user_id = model.getIdUsuari(user_name);
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

                            break;

                        case "5":
                            oo.writeObject("5");
                            //Rebem el nom de la canço que es vol reproduir
                            String nom = (String) oi.readObject();
                            //Extreiem de la base de dades el fitxer de la canço i incrementem el numero de reproduccions
                            song = model.getSong(nom);
                            song.setnReproduccions(song.getnReproduccions() + 1);
                            //Passem el fitxer pel socket
                            oo.writeObject(nom);
                            oo.writeObject(song.getFitxer());

                            break;

                        case "6":
                            oo.writeObject("6");
                            //Rebem el codi d'amistat i l'id de l'usuari que l'envia i comprovem que coincideixi amb algun usuari de la BBDD
                            String codi = (String) oi.readObject();
                            user_name = (String) oi.readObject();
                            user_id = model.getIdUsuari(user_name);
                            //Busquem el codi a la BBDD i si el trobem afegim l'usuari del codi com amic
                            boolean existeix = model.addAmic(codi, user_id);
                            if (existeix) {
                                //Passem un booleà que indica si s'ha afegit l'amic correctament
                                oo.writeObject(true);
                                //Mirem si esta repetit, es a dir, ja son amics
                                boolean repetit = model.amicsRepetits(user_id);
                                oo.writeObject(repetit);
                                if (!repetit) {
                                    //Passem la llista de noms de cançons que ara pot escoltar
                                    oo.writeObject(model.getTitolsDisponibles(user_id, model.getAmics(user_id), model.getAllSongs()));
                                    //Passem llista d'amics
                                    oo.writeObject(model.getNomAmics(user_id));
                                }

                            } else {
                                //Passem un booleà indicant que no s'ha afegit l'amic
                                oo.writeObject(false);
                                oo.writeObject(false);
                                oo.writeObject(false);
                            }
                            break;

                        case "7":
                            oo.writeObject("7");

                            //Rebem un usuari per eliminar-lo
                            user_id = (int) oi.readObject();
                            LinkedList<Integer> song_ids = model.getAllUserSongs_id(user_id, model.getAllUserSongs());
                            for (int i = 0; i < song_ids.size(); i++ ) {
                                model.deleteSong(song_ids.get(i));
                            }
                            model.deleteUserSong(user_id);
                            model.deleteAmic(user_id);
                            model.deleteUser(user_id);
                            break;

                        case "8":
                            oo.writeObject("8");
                            //Rebem un login
                            users = model.getAllUsers();
                            login = (Login) oi.readObject();
                            boolean clientOk = model.comprovaClient(login.getNomUsuari(),login.getCorreu(),login.getPassword());
                            boolean nomUsuariOK = model.comprovaNickname(login.getNomUsuari(),users);
                            boolean correuOk = model.comprovaEmail(login.getCorreu(), users);

                            boolean passwordOk = true;
                            if (!(nomUsuariOK && correuOk && passwordOk && clientOk)) {
                                //Passem un false per a que el programa client sàpiga que no s'ha fet be el registre
                                oo.writeObject(false);
                            } else {
                                //Passem un true conforme s'ha fet bé el registre i s'ha afegit l'usuari a la BBDD
                                oo.writeObject(true);
                                //Afegim l'usuari a la BBDD
                                model.addUser(login.getNomUsuari(), login.getCorreu(), login.getPassword());
                                Usuari usuari = model.getUser(login.getNomUsuari(), 0);

                                user_id = model.getIdUsuari(login.getNomUsuari());
                                //Passem la configuració del teclat
                                Tecla[] tecles = new Tecla[25];
                                for(int i =0;i<tecles.length;i++){
                                    tecles[i]= new Tecla();
                                }
                                tecles= model.getTeclat(login.getCorreu());
                                LinkedList<Tecla> t=new LinkedList<Tecla>();
                                for(int i=0;i<tecles.length;i++) {
                                    t.add(tecles[i]);
                                }
                                oo.writeObject(t);
                                oo.writeObject(model.getNomAmics(user_id));
                                LinkedList<String> songs = model.getTitolsDisponibles(user_id, model.getAmics(user_id), model.getAllSongs());
                                oo.writeObject(songs);
                                oo.writeObject(usuari.getUser_id());
                                oo.writeObject(usuari.getCodiAmistat());

                               /** model.update_nUsuaris(1);*/

                            }


                            break;
                        case "9":
                            oo.writeObject("9");
                            //Rebem l'usuari que vol eliminar l'amic i el nom de l'amic
                            String user_name1 = (String)oi.readObject();
                            String user_name2 = (String)oi.readObject();
                            int id1 = model.getIdUsuari(user_name1);
                            int id2 = model.getIdUsuari(user_name2);
                            model.deleteOneFriend(id1,id2);
                            //Passem la llista de noms de cançons que ara pot escoltar
                            oo.writeObject(model.getTitolsDisponibles(id1, model.getAmics(id1), model.getAllSongs()));
                            //Passem llista d'amics
                            oo.writeObject(model.getNomAmics(id1));


                            break;



                        case "10":
                            oo.close();
                            oi.close();
                            s.close();



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
