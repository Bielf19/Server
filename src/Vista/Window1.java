package Vista;

import Controlador.Controller1;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Window1 extends JFrame {

    //AFEGIR USUARIS
    private JTextField jtfNickname;
    private JTextField jtfEmail;
    private JTextField jtfPassword;
    private JButton jbAddUser;
    //SONG FILES
    private JScrollPane jpScrollFiles;

    public Window1() {

        //Inicialitzem la finestra grafica
        setTitle("SmartPianoServer");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Creem el conjunt de pestanyes
        JTabbedPane serverTabs = new JTabbedPane();

        //Definim el Content Pane com a JTabbedPane
        setContentPane(serverTabs);

        //PANELL D'AFEGIR USUARIS

        //Creem el panell
        JPanel jpUsers = new JPanel(new BorderLayout());

        //Components del panell
        JPanel jpAddUser = new JPanel();
        jpAddUser.setBorder(BorderFactory.createTitledBorder("Add a user"));
        jpAddUser.setLayout(new GridLayout(3,3));
        jpAddUser.add(new JLabel("Nickname:"),0);
        jpAddUser.add(new JLabel("Email:"),1);
        jpAddUser.add(new JLabel("Password:"),2);
        jtfNickname = new JTextField();
        jpAddUser.add(jtfNickname,3);
        jtfEmail = new JTextField();
        jpAddUser.add(jtfEmail,4);
        jtfPassword = new JTextField();
        jpAddUser.add(jtfPassword,5);
        jpAddUser.add(new JPanel(),6);
        jpAddUser.add(new JPanel(),7);
        jbAddUser = new JButton("Add user");
        jpAddUser.add(jbAddUser, 8);

        //Afegim el panell al nostre conjunt de pestanyes
        jpUsers.add(jpAddUser, BorderLayout.NORTH);
        getContentPane().add("Add user", jpUsers);


        //PANELL DELS FITXERS DE LES CANCONS

        //Creem el panell
        JPanel jpSongFiles = new JPanel();
        jpSongFiles.setLayout(new BoxLayout(jpSongFiles,BoxLayout.PAGE_AXIS));
        jpSongFiles.setBorder(BorderFactory.createTitledBorder("Song files"));
        jpScrollFiles = new JScrollPane(jpSongFiles);

        //Components del panell


        //Afegim el panell al nostre conjunt de pestanyes
        getContentPane().add("Song files", jpSongFiles);


        //PANELL DE L'EVOLUCIO DELS USUARIS

        //Creem el panell
        JPanel jpUsersEvolution = new JPanel();

        //Components del panell

        //Afegim el panell al nostre conjunt de pestanyes
        getContentPane().add("Users evolution", jpUsersEvolution);


        //PANELL DEL TOP 5 DE CANCONS MES POPULARS

        //Creem el panell
        JPanel jpTopFiveSongs = new JPanel();

        //Components del panell

        //Afegim el panell al nostre conjunt de pestanyes
        getContentPane().add("Top 5 songs", jpTopFiveSongs);


    }

    public void registraControlador(Controller1 controller1) {
    }

    public void generaLlistaFiles () {

    }
}
