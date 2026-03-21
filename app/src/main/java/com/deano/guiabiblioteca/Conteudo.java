package com.deano.guiabiblioteca;

public class Conteudo {

    private String titulo;
    private String texto;

    public Conteudo(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }
}

