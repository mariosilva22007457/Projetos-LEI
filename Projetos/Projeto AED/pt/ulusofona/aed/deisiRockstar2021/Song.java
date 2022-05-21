package pt.ulusofona.aed.deisiRockstar2021;
import java.util.HashMap;

public class Song {
    String id;
    String titulo;
    String[] artistas;
    int anoDeLancamento;
    int duracaoDoTema;
    Boolean letraExplicita;
    double popularidade;
    double grauDeDancabilidade;
    double grauDeVivacidade;
    double volumeMedio;

    public Song(String id, String titulo, String[] artistas, int anoDeLancamento, int duracaoDoTema,
                Boolean letraExplicita, double popularidade, double grauDeDancabilidade, double grauDeVivacidade,
                double volumeMedio) {
        this.id = id;
        this.titulo = titulo;
        this.artistas = artistas;
        this.anoDeLancamento = anoDeLancamento;
        this.duracaoDoTema = duracaoDoTema;
        this.letraExplicita = letraExplicita;
        this.popularidade = popularidade;
        this.grauDeDancabilidade = grauDeDancabilidade;
        this.grauDeVivacidade = grauDeVivacidade;
        this.volumeMedio = volumeMedio;
    }

    public Song(String id, String titulo, int anoDeLancamento) {
        this.id = id;
        this.titulo = titulo;
        this.anoDeLancamento = anoDeLancamento;
    }

    public Song(String id, String[] artistas) {
        this.id = id;
        this.artistas = artistas;
    }

    public Song(String id, int duracaoDoTema, Boolean letraExplicita, double popularidade, double grauDeDancabilidade,
                double grauDeVivacidade, double volumeMedio) {
        this.id = id;
        this.duracaoDoTema = duracaoDoTema;
        this.letraExplicita = letraExplicita;
        this.popularidade = popularidade;
        this.grauDeDancabilidade = grauDeDancabilidade;
        this.grauDeVivacidade = grauDeVivacidade;
        this.volumeMedio = volumeMedio;
    }

    static int getNrTemas(String artista, HashMap<String, String> memory) {
        int count = 0;
        if(memory.get(artista) != null){
            String IDs = memory.get(artista).substring(0, memory.get(artista).length()-1).trim();
            String[] IDsPerArtist = IDs.split(",");
            count = IDsPerArtist.length;
        }
        return count;
    }

    public String toString() {
        String artistas1 = "";
        String numeroTemasPorArtista = "";
        if (artistas != null) {
            for (int i = 0; i < artistas.length; i++) {
                int numeroTemas = getNrTemas(artistas[i], Main.ordMemoryArtistas);
                if (i + 1 != artistas.length) {
                    artistas1 += artistas[i] + " / ";
                    numeroTemasPorArtista += numeroTemas + " / ";
                } else {
                    artistas1 += artistas[i];
                    numeroTemasPorArtista += numeroTemas;
                }
            }
        }

        int minutes = (duracaoDoTema / 1000) / 60;
        int seconds = (duracaoDoTema / 1000) % 60;
        return id + " | " + titulo + " | " + anoDeLancamento + " | " + minutes + ":" + seconds + " | " + popularidade + " | " +
                artistas1 + " | (" + numeroTemasPorArtista + ")";
    }
}