package br.com.midiaconverte.download.model;


import java.io.Serializable;

public class Download implements Serializable {
    private static final long serialVersionUID = 1L;

    private String teste;

    public  Download(){

    }

    public String getTeste() {
        return teste;
    }

    public void setTeste(String teste) {
        this.teste = teste;
    }
}
