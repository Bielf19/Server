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

/**
 * La classe Window1 gestionarà tots els procediments i funcions que tinguin a veure amb la interfície gràfica
 * del servidor a través de la qual l'usuari hi podra interactuar.
 */

public class Window1 extends JFrame {

    private JTabbedPane serverTabs;

    //VARIBALES: AFEGIR USUARIS
    private JTextField jtfNickname;
    private JTextField jtfEmail;
    private JPasswordField jpfPassword;
    private JButton jbAddUser;

    //VARIABLES: SONG FILES
    private JScrollPane jspSongFiles;
    private ArrayList<JButton> conjuntDeleteFile = new ArrayList<>();
    private JTable table;
    private TableCellRenderer buttonRenderer;

    //VARIABLES: USERS EVOLUTION
    private JTabbedPane jtpEvolutionTabs;
    private LinkedList<Evolution> lastWeekEvolucio;
    private LinkedList<Evolution> lastMonthEvolucio;
    private LinkedList<Evolution> lastYearEvolucio;

    //VARIABLES: TOP 5 SONGS
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
        jpUsers.add(jpAddUser, BorderLayout.NORTH);

        //Afegim el panell al nostre conjunt de pestanyes
        getContentPane().add("Add user", jpUsers);

        //*************************************PANELL DELS FITXERS DE LES CANCONS*************************************//

        //Creem el panell
        jspSongFiles = new JScrollPane();

        //Components del panell

        //Afegim el panell al nostre conjunt de pestanyes
        getContentPane().add("Song files",jspSongFiles);

        //**************************************PANELL DE L'EVOLUCIO DELS USUARIS*************************************//

        //Creem el panell
        jtpEvolutionTabs = new JTabbedPane();
        LinkedList<Evolution> inicial = new LinkedList<>();
        jtpEvolutionTabs.add("Last week evolution", new Graphic(inicial));
        jtpEvolutionTabs.add("Last month evolution", new Graphic(inicial));
        jtpEvolutionTabs.add("Last year evolution", new Graphic(inicial));

        //Afegim el panell al nostre conjunt de pestanyes
        getContentPane().add("Users evolution", jtpEvolutionTabs);


        //**********************************PANELL DEL TOP 5 DE CANCONS MES POPULARS**********************************//

        //Creem el panell
        jpTopFiveSongs = new JScrollPane();

        //Afegim el panell al nostre conjunt de pestanyes
        getContentPane().add("Top 5 songs", jpTopFiveSongs);


    }

    /**
     *
     * @param songFiles LinkedList<Song>; conté la llista amb totes les cançons dels usuaris.
     * @return JTable que conté les cançons de tots els usuaris registrats al servidor
     */

    public synchronized JTable generaLlistaFiles (LinkedList<Song> songFiles){

        String[] columnNames = {"Song Name", "Owner", "Private/Public", "Delete"};
        Object[][] information = new Object[songFiles.size()][4];

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

    /**
     * Procediment que, a través d'un JDialog, s'utilitzatrà per a consultar a l'usuari si es vol eliminar el fitxer de
     * canço.
     * @return Integer que adoptarà el valor d'1 o de 0 en funció de la resposta de l'usuari
     */

    public int consultaDelete () {

        JDialog missatge = new JDialog();
        int reply = JOptionPane.showConfirmDialog(missatge, "Are you sure you want to delete this file?", "SmartPiano",JOptionPane.YES_NO_OPTION);
        return reply;

    }

    /**
     * Procediment que mostra un missatge d'error si el nickname del usuari es invalid.
     */

    public void nicknameError () {

        JDialog missatge = new JDialog();
        JOptionPane.showMessageDialog(missatge, "Error! Invalid user nickname: The nickname is currently in use. Please, try to reintroduce a valid nickname.", "Error - Invalid user nickname", JOptionPane.ERROR_MESSAGE);

    }

    /**
     * Procediment que mostra un missatge d'error si la direcció de correu electronic del usuari es invalida.
     */

    public void emailError () {

        JDialog missatge = new JDialog();
        JOptionPane.showMessageDialog(missatge, "Error! Invalid email address: The address is currently in use. Please, try to reintroduce a valid address.", "Error - Invalid email address", JOptionPane.ERROR_MESSAGE);

    }

    /**
     * Procediment que mostra un missatge d'error si el registre de l'usuari no supera les comprovacions del client.
     */

    public void clientError () {

        JDialog missatge = new JDialog();
        JOptionPane.showMessageDialog(missatge, "Error! Invalid information from client patterns detected. Please, try to reintroduce a valid information format.", "Error - Invalid information from client", JOptionPane.ERROR_MESSAGE);

    }

    /**
     * Procediment que mostra un missatge informatiu conforme un usuari ha sigut registrat amb exit.
     */

    public void addSuccessful(){

        JDialog missatge = new JDialog();
        JOptionPane.showMessageDialog(missatge,"The user has successfully registered!","Information - Registration completed", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Funcio que retorna el nickname introduit per l'usuari.
     * @return String que conte el nickname introduit per l'usuari.
     */

    public String getUserNickname (){

        return jtfNickname.getText();

    }

    /**
     * Funcio que retorna la direccio de correu electrnic introduida per l'usuari.
     * @return String que conte la direccio de correu electrnic introduida per l'usuari.
     */

    public String getUserEmail () {

        return jtfEmail.getText();

    }

    /**
     * Funcio que retorna la contrasenya introduida per l'usuari.
     * @return String que conte la contrasenya introduida per l'usuari.
     */

    public String getUserPassword () {

        char[] userPassword;
        String userPasswordOk;

        userPassword = jpfPassword.getPassword();
        userPasswordOk = String.valueOf(userPassword);

        return userPasswordOk;

    }

    /**
     * Procediment que, un cop registrat l'usuari, esborra els JTextfields de l'usuari, la direccio de correu electronic
     * i la contrasenya.
     */

    public void cleanJTextfields () {

        jtfNickname.setText("");
        jtfEmail.setText("");
        jpfPassword.setText("");

    }

    /**
     * Procediment que s'utilitza per a relacionar les accions que es duuen a terme a la finestra amb el primer controlador.
     * @param controller1 Controller1; variable que conté el primer controlador.
     */

    public void registraControlador(Controller1 controller1) {

        jbAddUser.removeActionListener(controller1);
        jbAddUser.addActionListener(controller1);
        jbAddUser.setActionCommand("ADD_USER");

        for (int i = 0; i < conjuntDeleteFile.size(); i++) {

            conjuntDeleteFile.get(i).removeActionListener(controller1);
            conjuntDeleteFile.get(i).addActionListener(controller1);

        }

        serverTabs.addMouseListener(controller1);

    }

    /**
     * Procediment que s'utilitza per a relacionar les accions que es duuen a terme a la finestra amb el segon controlador.
     * @param controller2 Controller2; variable que conté el primer controlador.
     */

    public void registraControlador2(Controller2 controller2) {
        jspSongFiles.addMouseListener(controller2);
        table.addMouseListener(controller2);

    }

    /**
     * Procediment que s'utilitza per organitzar la informacio de l'evolucio
     * d'usuaris de l'ultima setmana en una LinkedList.
     * @param dadesEvolucio LinkedLIst; conte informació sobre l'activtat dels usuaris durant la darera setmana
     */

    public void setLastWeekEvolucio(LinkedList<Evolution> dadesEvolucio) {
        lastWeekEvolucio = dadesEvolucio;
    }

    /**
     * Procediment que s'utilitza per organitzar la informacio de l'evolucio
     * d'usuaris de l'ultim mes en una LinkedList.
     * @param dadesEvolucio LinkedLIst; conte informació sobre l'activtat dels usuaris durant el darrer mes.
     */

    public void setLastMonthEvolucio(LinkedList<Evolution> dadesEvolucio) {
        lastMonthEvolucio = dadesEvolucio;
    }

    /**
     * Procediment que s'utilitza per organitzar la informacio de l'evolucio
     * d'usuaris de l'ultim any en una LinkedList
     * @param dadesEvolucio LinkedLIst; conte informació sobre l'activtat dels usuaris durant l'ultim any
     */

    public void setLastYearEvolucio(LinkedList<Evolution> dadesEvolucio) {
        lastYearEvolucio = dadesEvolucio;
    }

    /**
     * Procediment que s'utilitzarà per obtenir les llistes dels tres procediments anteriors
     */

    public void getLlistes() {
        setLastWeekEvolucio(lastWeekEvolucio);
        setLastMonthEvolucio(lastMonthEvolucio);
        setLastYearEvolucio(lastYearEvolucio);
        jtpEvolutionTabs.removeAll();
        jtpEvolutionTabs.add("Last week evolution", new Graphic(lastWeekEvolucio));
        jtpEvolutionTabs.add("Last month evolution", new Graphic(lastMonthEvolucio));
        jtpEvolutionTabs.add("Last year evolution", new Graphic(lastYearEvolucio));
    }

    /**
     * Procediment que s'utilitzara per dipositar una canço en la LinkedList que contindra el top 5 de canccons mes escoltades
     * @param top5 LinkedList;
     */

    public void setTop5(LinkedList<Song> top5) {
        this.top5 = top5;
    }

    /**
     * Procediment que s'utilitzara per generar una JTable amb les cinc cançons mes escoltades.
     */

    public synchronized void generaTaulaTop5() {
        String[] columnNames = {"Ranking","Song",
                "# Playback"};
        Object[][] data = new Object[5][3];
        for (int i = 0; i < top5.size(); i++) {

                data[i][0] = "#" + (i+1);
                data[i][1] = top5.get(i).getTitol();
                data[i][2] = top5.get(i).getnReproduccions();

        }

        JTable table = new JTable(data, columnNames);
        table.getColumnModel().getColumn(0).setMaxWidth(100);
        table.getColumnModel().getColumn(1).setMaxWidth(200);
        table.getColumnModel().getColumn(2).setMaxWidth(100);

        jpTopFiveSongs.removeAll();
        jpTopFiveSongs = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        serverTabs.setComponentAt(3, jpTopFiveSongs);
        jpTopFiveSongs.updateUI();

    }

    /**
     * Procediment que s'utilitza per a obtenir els botons registrats a l'ArrayList corresponent
     * @return
     */

    public ArrayList getBotons() {
        return conjuntDeleteFile;
    }
}
