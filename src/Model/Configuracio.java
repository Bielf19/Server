package Model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Configuracio {

    private Integer portBase;
    private String IP;
    private String nomBase;
    private String usuariAcces;
    private String contrasenyaAcces;
    private String portClient;

    public Integer getPortBase() {
        return portBase;
    }

    public void setPortBase(Integer portBase) {
        this.portBase = portBase;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getNomBase() {
        return nomBase;
    }

    public void setNomBase(String nomBase) {
        this.nomBase = nomBase;
    }

    public String getUsuariAcces() {
        return usuariAcces;
    }

    public void setUsuariAcces(String usuariAcces) {
        this.usuariAcces = usuariAcces;
    }

    public String getContrasenyaAcces() {
        return contrasenyaAcces;
    }

    public void setContrasenyaAcces(String contrasenyaAcces) {
        this.contrasenyaAcces = contrasenyaAcces;
    }

    public String getPortClient() {
        return portClient;
    }

    public void setPortClient(String portClient) {
        this.portClient = portClient;
    }

    public Configuracio llegeixJson () throws FileNotFoundException {

        Configuracio config;

        //En primer lloc, s'obtindrà el path actual.

        String f = new File("").getAbsolutePath();
        Gson gson = new Gson();

        //En segon lloc, s'obrirà el JSON corresponent.

        File file = new File(f.concat("/roots/config.json"));

        JsonReader reader = new JsonReader(new FileReader(f.concat("/roots/config.json")));
        config = gson.fromJson(reader, Configuracio.class);

        return config;

    }

}
