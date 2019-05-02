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
    private JPasswordField jpfPassword;
    private JButton jbAddUser;
    //SONG FILES
    private JTabbedPane jtpSongFiles;
    private JScrollPane jpScrollAllFiles;
    private JScrollPane jpScrollPublicFiles;
    private JScrollPane jpScrollPrivateFiles;
    private JPanel jpAllFiles;
    private JPanel jpPrivateFiles;
    private JPanel jpPublicFiles;
    private JPanel jpFile;
    private JButton jbDeleteFile;
    private ArrayList<JButton> conjuntDeleteFile = new ArrayList<>();
    //EVOLUTION


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

        //************************************PANELL D'AFEGIR USUARIS*************************************************//

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
        jpfPassword = new JPasswordField();
        jpfPassword.setEchoChar('*');
        jpAddUser.add(jpfPassword,5);
        jpAddUser.add(new JPanel(),6);
        jpAddUser.add(new JPanel(),7);
        jbAddUser = new JButton("Add user");
        jpAddUser.add(jbAddUser, 8);

        //Afegim el panell al nostre conjunt de pestanyes
        jpUsers.add(jpAddUser, BorderLayout.NORTH);
        getContentPane().add("Add user", jpUsers);

        //*************************************PANELL DELS FITXERS DE LES CANCONS*************************************//

        //Creem el panell
        jtpSongFiles = new JTabbedPane();

        //Components del panell
        jpAllFiles = new JPanel();
        jpPrivateFiles = new JPanel();
        jpPublicFiles = new JPanel();
        jpAllFiles.setLayout(new BoxLayout(jpAllFiles,BoxLayout.PAGE_AXIS));
        jpAllFiles.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Song files"));
        jpPrivateFiles.setLayout(new BoxLayout(jpPrivateFiles,BoxLayout.PAGE_AXIS));
        jpPrivateFiles.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Song files"));
        jpPublicFiles.setLayout(new BoxLayout(jpPublicFiles,BoxLayout.PAGE_AXIS));
        jpPublicFiles.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Song files"));
        jpScrollAllFiles = new JScrollPane();
        jpScrollPublicFiles = new JScrollPane();
        jpScrollPrivateFiles = new JScrollPane();

        fitxersPredeterminats();

        //**************************************PANELL DE L'EVOLUCIO DELS USUARIS*************************************//

        //Creem el panell
        JTabbedPane jtpEvolutionTabs = new JTabbedPane();

        //Components del panell
        JPanel jpWeekEvolution = new JPanel();
        //System.out.println("Prova2");
        jpWeekEvolution.paintComponents(getGraphics());
        JPanel jpMonthEvolution = new JPanel();
        JPanel jpYearEvolution = new JPanel();
        jtpEvolutionTabs.add("Last week evolution", jpWeekEvolution);
        jtpEvolutionTabs.add("Last month evolution", jpMonthEvolution);
        jtpEvolutionTabs.add("Last year evolution", jpYearEvolution);

        //Afegim el panell al nostre conjunt de pestanyes
        getContentPane().add("Users evolution", jtpEvolutionTabs);

        //**********************************PANELL DEL TOP 5 DE CANCONS MES POPULARS**********************************//

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
            JLabel jlFileName = new JLabel("Song name: DEFAULT NAME "+(i+1));
            jlFileName.setHorizontalAlignment(SwingConstants.LEFT);
            jpFile.add(jlFileName);

            //Borrar fitxer
            jbDeleteFile = new JButton("Delete");
            jbDeleteFile.setEnabled(false);
            jpFile.add(jbDeleteFile);

            jbDeleteFile.setActionCommand(""+(i+1));
            conjuntDeleteFile.add(jbDeleteFile);

            jpAllFiles.add(jpFile);
            jpPublicFiles.add(jpFile);

        }

        jpScrollAllFiles.setViewportView(jpAllFiles);
        jpScrollPublicFiles.setViewportView(jpPublicFiles);
        jpScrollPrivateFiles.setViewportView(jpPrivateFiles);
        jtpSongFiles.add("Private files", jpScrollPrivateFiles);
        jtpSongFiles.add("Public files", jpScrollPublicFiles);
        jtpSongFiles.add("All files", jpScrollAllFiles);
        getContentPane().add("Song files", jtpSongFiles);

    }

    public void generaLlistaFiles (LinkedList<File> files) {

        jtpSongFiles.removeAll();
        conjuntDeleteFile.clear();

        fitxersPredeterminats();

        for (int i=0; i< files.size(); i++){

            JPanel jpFile = new JPanel ();
            jpFile.setLayout (new BoxLayout(jpFile,BoxLayout.PAGE_AXIS));

            jpFile.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"File number "+i+4));

            //Nom del fitxer
            JLabel jlFileName = new JLabel("Song name: "+files.get(i).getName());
            jlFileName.setHorizontalAlignment(SwingConstants.LEFT);
            jpFile.add(jlFileName);

            //Borrar fitxer
            jbDeleteFile = new JButton("Delete");
            jpFile.add(jbDeleteFile);

            jtpSongFiles.add(jpFile);

            conjuntDeleteFile.add(jbDeleteFile);

        }

        //jpScrollFiles.add(jtpSongFiles);

        //getContentPane().add("Song files", jpScrollFiles);
        jtpSongFiles.revalidate();
        this.repaint();

    }

    public void consultaDelete () {

        JDialog missatge = new JDialog();
        JOptionPane consultaAccio = new JOptionPane("Are you sure you want to delete this file?",JOptionPane.QUESTION_MESSAGE,JOptionPane.YES_NO_OPTION);
        missatge.add(consultaAccio);

    }

    public void nicknameError () {

        JDialog missatge = new JDialog();
        JOptionPane.showMessageDialog(missatge, "Error! Invalid user nickname: The nickname is currently in use. Please, ", "Error - Invalid user nickname", JOptionPane.ERROR_MESSAGE);

    }

    public void emailError () {

        JDialog missatge = new JDialog();
        JOptionPane.showMessageDialog(missatge, "Error! Invalid email address: The address is currently in use. Please, ", "Error - Invalid email address", JOptionPane.ERROR_MESSAGE);

    }

    public void addSuccessful(){

        JDialog missatge = new JDialog();
        JOptionPane.showMessageDialog(missatge,"The user has successfully registered!","Information - Registration completed", JOptionPane.INFORMATION_MESSAGE);

    }

    public String getUserNickname (){

        return jtfNickname.getText();

    }

    public String getUserEmail () {

        return jtfEmail.getText();

    }

    public String getUserPassword () {

        char[] userPassword;
        String userPasswordOk;

        userPassword = jpfPassword.getPassword();
        userPasswordOk = String.valueOf(userPassword);

        return userPasswordOk;

    }

    public void cleanJTextfields () {

        jtfNickname.setText("");
        jtfEmail.setText("");
        jpfPassword.setText("");

    }

    public void registraControlador(Controller1 controller1) {

        jbAddUser.removeActionListener(controller1);
        jbAddUser.addActionListener(controller1);
        jbAddUser.setActionCommand("ADD_USER");

        for (int i = 0; i < conjuntDeleteFile.size(); i++) {

            conjuntDeleteFile.get(i).removeActionListener(controller1);
            conjuntDeleteFile.get(i).addActionListener(controller1);

        }

    }


    public void paintComponents(Graphics g) {
        super.paintComponents(g);
       System.out.println("Prova1");
        g.setColor(Color.GREEN);
        g.fillOval(30,102,321,89);
        g.drawLine(30,200,300,50);

    }

}
