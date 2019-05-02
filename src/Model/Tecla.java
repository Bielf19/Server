package Model;

import java.io.Serializable;

public class Tecla implements Serializable {
    private String nota;
    private char tecla;

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public char getTecla() {
        return tecla;
    }

    public void setTecla(char tecla) {
        this.tecla = tecla;
    }
}
