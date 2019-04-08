package Vista;

import Controlador.Controller1;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class Window1 extends JFrame {

    //AFEGIR USUARIS
    private JTextField jtfNickname;
    private JTextField jtfEmail;
    private JTextField jtfPassword;
    private JButton jbAddUser;
    //SONG FILES
    private JPanel jpSongFiles;
    private JScrollPane jpScrollFiles;
    private JPanel jpFile;
    private JButton jbDeleteFile;
    private ArrayList<JButton> conjuntDeleteFile = new ArrayList<>();

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
        jpSongFiles = new JPanel();
        jpSongFiles.setLayout(new BoxLayout(jpSongFiles,BoxLayout.PAGE_AXIS));
        jpSongFiles.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Song files"));
        jpScrollFiles = new JScrollPane(jpSongFiles);
        fitxersPredeterminats();

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

    public void fitxersPredeterminats() {

        for (int i=0; i < 3; i++) {

            jpFile = new JPanel ();
            jpFile.setLayout (new GridLayout(1,2));

            jpFile.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"File number "+(i+1)));

            //Nom del fitxer
            JLabel jlFileName = new JLabel("File name: DEFAULT FILE "+(i+1));
            jlFileName.setHorizontalAlignment(SwingConstants.LEFT);
            jpFile.add(jlFileName);

            //Borrar fitxer
            jbDeleteFile = new JButton("Delete");
            jbDeleteFile.setEnabled(false);
            jpFile.add(jbDeleteFile);

            jpSongFiles.add(jpFile);

        }

        getContentPane().add("Song files", jpScrollFiles);

    }

    public void generaLlistaFiles (LinkedList<File> files) {

        jpSongFiles.removeAll();
        //conjuntApply.clear();

        fitxersPredeterminats();

        for (int i=0; i< files.size(); i++){

            JPanel jpFile = new JPanel ();
            jpFile.setLayout (new BoxLayout(jpFile,BoxLayout.PAGE_AXIS));

            jpFile.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"File number "+i+4));

            //Nom del fitxer
            JLabel jlFileName = new JLabel("File name: "+files.get(i).getName());
            jlFileName.setHorizontalAlignment(SwingConstants.LEFT);
            jpFile.add(jlFileName);

            //Borrar fitxer
            jbDeleteFile = new JButton("Delete");
            jpFile.add(jbDeleteFile);

            jpSongFiles.add(jpFile);

        }

        jpScrollFiles.add(jpSongFiles);

        getContentPane().add("Song files", jpScrollFiles);
        jpSongFiles.revalidate();
        this.repaint();

    }

    public void consultaDelete () {

        JDialog missatge = new JDialog();
        JOptionPane consultaAccio = new JOptionPane("Are you sure you want to delete this file?",JOptionPane.QUESTION_MESSAGE,JOptionPane.YES_NO_OPTION);
        missatge.add(consultaAccio);

    }

    public String getUserNickname (){

        return jtfNickname.getText();

    }

    public String getUserEmail () {

        return jtfEmail.getText();

    }

    public String getUserPassword () {

        return jtfPassword.getText();

    }

    public void registraControlador(Controller1 controller1) {

        jbAddUser.removeActionListener(controller1);
        jbAddUser.addActionListener(controller1);
        jbAddUser.setActionCommand("ADDUSER");

        for (int i = 0; i < conjuntDeleteFile.size(); i++) {

            conjuntDeleteFile.get(i).removeActionListener(controller1);
            conjuntDeleteFile.get(i).addActionListener(controller1);

        }

    }

}
