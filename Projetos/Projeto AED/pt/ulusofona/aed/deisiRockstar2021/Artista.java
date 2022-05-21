package pt.ulusofona.aed.deisiRockstar2021;

import java.util.ArrayList;

public class Artista {

String nome;
int numeroTemas;
Song tema;
double popMedia;

    public Artista(String nome) {
        this.nome = nome;
    }

    public Artista(String nome, double popMedia) {
        this.nome = nome;
        this.popMedia = popMedia;
    }

    public Artista(String nome, int numeroTemas) {
        this.nome = nome;
        this.numeroTemas = numeroTemas;
    }

    public Artista(String nome, int numeroTemas, Song tema) {
        this.nome = nome;
        this.numeroTemas = numeroTemas;
        this.tema = tema;
    }

    public Artista(String nome, Song tema) {
        this.nome = nome;
        this.tema = tema;
    }

    @Override
    public String toString() {
        return nome;
    }
}
