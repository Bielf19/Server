package Vista;

import Controlador.Controller1;
import Controlador.Controller2;
import Model.Evolution;
import Model.Song;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JTable;
import javax.swing.DefaultCellEditor;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Window1 extends JFrame {

    private JTabbedPane serverTabs;
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
    private JPanel jpFilePrivat;
    private JPanel jpFilePublic;
    private JPanel jpFileAll;
    private JButton jbDeleteFilePrivat;
    private JButton jbDeleteFilePublic;
    private JButton jbDeleteFileAll;
    private ArrayList<JButton> conjuntDeleteFilePrivat = new ArrayList<>();
    private ArrayList<JButton> conjuntDeleteFilePublic = new ArrayList<>();
    private ArrayList<JButton> conjuntDeleteFileAll = new ArrayList<>();
    private JPanel jpWeekEvolution;
    private LinkedList<Song> songFiles;

    private JScrollPane jspSongFiles;
    private ArrayList<JButton> conjuntDeleteFile = new ArrayList<>();
    private JTable table;
    private TableCellRenderer buttonRenderer;

    //EVOLUTION
    private JTabbedPane jtpEvolutionTabs;
    private LinkedList<Evolution> lastWeekEvolucio;
    private LinkedList<Evolution> lastMonthEvolucio;
    private LinkedList<Evolution> lastYearEvolucio;
    //TOP 5
    private JScrollPane jpTopFiveSongs;
    private LinkedList<Song> top5;



    public Window1() {

        //Inicialitzem la finestra grafica
        setTitle("SmartPianoServer");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Creem el conjunt de pestanyes
        serverTabs = new JTabbedPane();

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
        jspSongFiles = new JScrollPane();

        //Components del panell

        getContentPane().add("Song files",jspSongFiles);

        /*
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

        songFiles = new LinkedList<Song>();
        jpScrollAllFiles.setViewportView(jpAllFiles);
        jpScrollPublicFiles.setViewportView(jpPublicFiles);
        jpScrollPrivateFiles.setViewportView(jpPrivateFiles);
        jtpSongFiles.add("Private files", jpScrollPrivateFiles);
        jtpSongFiles.add("Public files", jpScrollPublicFiles);
        jtpSongFiles.add("All files", jpScrollAllFiles);
        getContentPane().add("Song files",jtpSongFiles);
        generaLlistaFiles(songFiles);
        */

        //**************************************PANELL DE L'EVOLUCIO DELS USUARIS*************************************//

        //Creem el panell
        jtpEvolutionTabs = new JTabbedPane();
        LinkedList<Evolution> inicial = new LinkedList<>();
        /*setLastWeekEvolucio(lastWeekEvolucio);
        setLastMonthEvolucio(lastMonthEvolucio);
        setLastYearEvolucio(lastYearEvolucio);
        System.out.println(lastWeekEvolucio.size());
        System.out.println(lastMonthEvolucio.size());
        System.out.println(lastYearEvolucio.size());*/
        jtpEvolutionTabs.add("Last week evolution", new Graphic(inicial));
        jtpEvolutionTabs.add("Last month evolution", new Graphic(inicial));
        jtpEvolutionTabs.add("Last year evolution", new Graphic(inicial));

        //Afegim el panell al nostre conjunt de pestanyes
        getContentPane().add("Users evolution", jtpEvolutionTabs);


        //**********************************PANELL DEL TOP 5 DE CANCONS MES POPULARS**********************************//

        //Creem el panell
        jpTopFiveSongs = new JScrollPane();


        //Components del panell

        //Afegim el panell al nostre conjunt de pestanyes
        getContentPane().add("Top 5 songs", jpTopFiveSongs);


    }

    public synchronized JTable generaLlistaFiles (LinkedList<Song> songFiles){

        String[] columnNames = {"Song Name", "Owner", "Private/Public", "Delete"};
        Object[][] information = new Object[songFiles.size()][4];
        System.out.println("Size songFiles: " + songFiles.size());

        for (int i = 0; i < songFiles.size(); i++) {

            JButton jbDeleteFile = new JButton("Delete");
            conjuntDeleteFile.add(jbDeleteFile);

            information [i][0] = songFiles.get(i).getTitol();

            if (i <= 2) {

                information [i][1] = "Server";

                jbDeleteFile.setEnabled(false);
                information [i][3] = jbDeleteFile;

            } else {

                information [i][1] = songFiles.get(i).getPropietari();
                information [i][3] = jbDeleteFile;

            }

            if (songFiles.get(i).isPrivat()) {

                information [i][2] = "Privat";

            } else {

                information [i][2] = "Public";

            }

        }
        System.out.println("N Deletes: " + conjuntDeleteFile.size() );
        table = new JTable(information, columnNames);
        table.getColumnModel().getColumn(0).setMaxWidth(100);
        table.getColumnModel().getColumn(1).setMaxWidth(200);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(100);

        jspSongFiles.removeAll();
        jspSongFiles = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        buttonRenderer = new JTableButtonRenderer();
        table.getColumn("Delete").setCellRenderer(buttonRenderer);
        serverTabs.setComponentAt(1, jspSongFiles);
        jspSongFiles.updateUI();

        return table;

    }

    /*

    public void generaLlistaFiles (LinkedList<Song> songFiles) {

        jtpSongFiles.removeAll();
        conjuntDeleteFilePrivat.clear();
        conjuntDeleteFilePublic.clear();
        conjuntDeleteFileAll.clear();

        for (int i=0; i< songFiles.size(); i++){

            jpFilePrivat = new JPanel ();
            jpFilePublic = new JPanel ();
            jpFileAll = new JPanel ();

            jpFilePrivat.setLayout (new GridLayout(1,2));
            jpFilePublic.setLayout (new GridLayout(1,2));
            jpFileAll.setLayout (new GridLayout(1,2));

            jpFilePrivat.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"File number "+(i+1)));
            jpFilePublic.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"File number "+(i+1)));
            jpFileAll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"File number "+(i+1)));

            //Nom del fitxer
            JLabel jlFileNamePrivat = new JLabel("Song name: "+songFiles.get(i).getTitol());
            JLabel jlFileNamePublic = new JLabel("Song name: "+songFiles.get(i).getTitol());
            JLabel jlFileNameAll = new JLabel("Song name: "+songFiles.get(i).getTitol());
            jlFileNamePrivat.setHorizontalAlignment(SwingConstants.LEFT);
            jlFileNamePublic.setHorizontalAlignment(SwingConstants.LEFT);
            jlFileNameAll.setHorizontalAlignment(SwingConstants.LEFT);
            jpFilePrivat.add(jlFileNamePrivat);
            jpFilePublic.add(jlFileNamePublic);
            jpFileAll.add(jlFileNameAll);

            //Borrar fitxer
            jbDeleteFilePrivat = new JButton("Delete");
            jbDeleteFilePublic = new JButton("Delete");
            jbDeleteFileAll = new JButton("Delete");

            if (i <= 2) {

                jbDeleteFilePublic.setEnabled(false);
                jbDeleteFileAll.setEnabled(false);
                jpFilePublic.add(jbDeleteFilePublic);
                jpFileAll.add(jbDeleteFileAll);

                jbDeleteFilePublic.setActionCommand(""+(i+1));
                jbDeleteFileAll.setActionCommand(""+(i+1));

                jpPublicFiles.add(jpFilePublic);
                jpAllFiles.add(jpFileAll);

                conjuntDeleteFilePublic.add(jbDeleteFilePublic);
                conjuntDeleteFileAll.add(jbDeleteFileAll);


            } else {

                jpFileAll.add(jbDeleteFileAll);
                jbDeleteFileAll.setActionCommand(""+(i+1));
                jpAllFiles.add(jpFileAll);

                if (songFiles.get(i).isPrivat()) {

                    jpFilePrivat.add(jbDeleteFilePublic);
                    jbDeleteFilePrivat.setActionCommand(""+(i+1));
                    jpPrivateFiles.add(jpFilePrivat);

                } else {

                    jpFilePublic.add(jbDeleteFilePublic);
                    jbDeleteFilePublic.setActionCommand(""+(i+1));
                    jpPublicFiles.add(jpFilePrivat);

                }

            }

        }

        jpScrollAllFiles.setViewportView(jpAllFiles);
        jpScrollPublicFiles.setViewportView(jpPublicFiles);
        jpScrollPrivateFiles.setViewportView(jpPrivateFiles);
        jtpSongFiles.add("Private files", jpScrollPrivateFiles);
        jtpSongFiles.add("Public files", jpScrollPublicFiles);
        jtpSongFiles.add("All files", jpScrollAllFiles);

        jtpSongFiles.revalidate();
        this.repaint();

    }

    */

    public int consultaDelete () {

        JDialog missatge = new JDialog();
        int reply = JOptionPane.showConfirmDialog(missatge, "Are you sure you want to delete this file?", "SmartPiano",JOptionPane.YES_NO_OPTION);
        return reply;

    }

    public void nicknameError () {

        JDialog missatge = new JDialog();
        JOptionPane.showMessageDialog(missatge, "Error! Invalid user nickname: The nickname is currently in use. Please, try to reintroduce a valid nickname.", "Error - Invalid user nickname", JOptionPane.ERROR_MESSAGE);

    }

    public void emailError () {

        JDialog missatge = new JDialog();
        JOptionPane.showMessageDialog(missatge, "Error! Invalid email address: The address is currently in use. Please, try to reintroduce a valid address.", "Error - Invalid email address", JOptionPane.ERROR_MESSAGE);

    }

    public void clientError () {

        JDialog missatge = new JDialog();
        JOptionPane.showMessageDialog(missatge, "Error! Invalid information from client patterns detected. Please, try to reintroduce a valid information format.", "Error - Invalid information from client", JOptionPane.ERROR_MESSAGE);

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

        /*

        for (int i = 0; i < conjuntDeleteFilePrivat.size(); i++) {

            conjuntDeleteFilePrivat.get(i).removeActionListener(controller1);
            conjuntDeleteFilePrivat.get(i).addActionListener(controller1);

        }

        for (int i = 0; i < conjuntDeleteFilePublic.size(); i++) {

            conjuntDeleteFilePublic.get(i).removeActionListener(controller1);
            conjuntDeleteFilePublic.get(i).addActionListener(controller1);

        }

        for (int i = 0; i < conjuntDeleteFileAll.size(); i++) {

            conjuntDeleteFileAll.get(i).removeActionListener(controller1);
            conjuntDeleteFileAll.get(i).addActionListener(controller1);

        }

        */

        serverTabs.addMouseListener(controller1);



    }

    public void registraControlador2(Controller2 controller2) {
        jspSongFiles.addMouseListener(controller2);
        table.addMouseListener(controller2);

    }

    public void setLastWeekEvolucio(LinkedList<Evolution> dadesEvolucio) {
        lastWeekEvolucio = dadesEvolucio;
    }

    public void setLastMonthEvolucio(LinkedList<Evolution> dadesEvolucio) {
        lastMonthEvolucio = dadesEvolucio;
    }

    public void setLastYearEvolucio(LinkedList<Evolution> dadesEvolucio) {
        lastYearEvolucio = dadesEvolucio;
    }

    public void getLlistes() {
        System.out.println("prova6");
        setLastWeekEvolucio(lastWeekEvolucio);
        setLastMonthEvolucio(lastMonthEvolucio);
        setLastYearEvolucio(lastYearEvolucio);
        System.out.println(lastWeekEvolucio.size());
        System.out.println(lastMonthEvolucio.size());
        System.out.println(lastYearEvolucio.size());
        jtpEvolutionTabs.removeAll();
        jtpEvolutionTabs.add("Last week evolution", new Graphic(lastWeekEvolucio));
        jtpEvolutionTabs.add("Last month evolution", new Graphic(lastMonthEvolucio));
        jtpEvolutionTabs.add("Last year evolution", new Graphic(lastYearEvolucio));
    }

    public void setTop5(LinkedList<Song> top5) {
        this.top5 = top5;
    }


    public synchronized void generaTaulaTop5() {
        String[] columnNames = {"Ranking","Song",
                "# Playback"};
        Object[][] data = new Object[5][3];
        for (int i = 0; i < top5.size(); i++) {

                data[i][0] = "#" + (i+1);
                data[i][1] = top5.get(i).getTitol();
                data[i][2] = top5.get(i).getnReproduccions();

        }
        System.out.println("Prova20");

        JTable table = new JTable(data, columnNames);
        table.getColumnModel().getColumn(0).setMaxWidth(100);
        table.getColumnModel().getColumn(1).setMaxWidth(200);
        table.getColumnModel().getColumn(2).setMaxWidth(100);




        jpTopFiveSongs.removeAll();
        jpTopFiveSongs = new JScrollPane(table);
        //jpTopFiveSongs.add(table);
        table.setFillsViewportHeight(true);
        System.out.println("Prova21");
        serverTabs.setComponentAt(3, jpTopFiveSongs);
        jpTopFiveSongs.updateUI();
        System.out.println("Prova22");




    }


    public ArrayList getBotons() {
        return conjuntDeleteFile;
    }
}
