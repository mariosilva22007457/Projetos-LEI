package pt.ulusofona.aed.deisiRockstar2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LoadFilesFunctions {
    public static String getArtista(String artistas) {
        if (artistas.charAt(0) == '\"' && artistas.charAt(1) == '['
                && artistas.charAt(artistas.length() - 2) == ']' && artistas.charAt(artistas.length() - 1) == '\"') {
            artistas = artistas.substring(2, artistas.length() - 2);
        } else if (artistas.charAt(0) == '[' && artistas.charAt(artistas.length() - 1) == ']') {
            artistas = artistas.substring(1, artistas.length() - 1);
        }
        return artistas;
    }

    public static HashMap<String, String> getMemoryArtistas(String[] artistasArray, String id, HashMap<String, String> memory) {
        for (int i = 0; i < artistasArray.length; i++) {
            String IDs = memory.get(artistasArray[i]);
            if (IDs == null) {
                IDs = "";
                memory.put(artistasArray[i], IDs + (id + ","));
            } else {
                memory.remove(artistasArray[i]);
                memory.put(artistasArray[i], IDs + (id + ","));
            }
        }
        return memory;
    }

    public static String removeApostrophe(String artista){
        if(artista.charAt(0) == '\'' && artista.charAt(artista.length()-1) == '\''){
            artista = artista.substring(1, artista.length() - 1);
        } else if(artista.charAt(0) == '\'' && !(artista.charAt(artista.length()-1) == '\'')){
            artista = artista.substring(1);
        } else if(!(artista.charAt(0) == '\'') && artista.charAt(artista.length()-1) == '\''){
            artista = artista.substring(0, artista.length() - 1);
        }
        return artista;
    }

    public static String removeQuotations(String text) {
        if (text.charAt(text.length() - 1) == '\"' && text.charAt(text.length() - 2) == '\"'
                && text.charAt(0) == '\"' && text.charAt(1) == '\"') {
            text = text.substring(2, text.length() - 2);
        }
        return text;
    }

    public static void addDetailsToStructure(String id, int duracaoDoTema, boolean letraExplicita,
                                             double popularidade, double grauDeDancabilidade,
                                             double grauDeVivacidade, double volumeMedio) {
        if (Main.ordMemory.get(id) != null && Main.ordMemory.get(id).duracaoDoTema == 0) {
            Main.ordMemory.get(id).duracaoDoTema = duracaoDoTema;
            Main.ordMemory.get(id).letraExplicita = letraExplicita;
            Main.ordMemory.get(id).popularidade = popularidade;
            Main.ordMemory.get(id).grauDeDancabilidade = grauDeDancabilidade;
            Main.ordMemory.get(id).grauDeVivacidade = grauDeVivacidade;
            Main.ordMemory.get(id).volumeMedio = volumeMedio;
            Main.numeroLinhasOkFile2++;
        } else {
            Main.numeroLinhasIgnoradasFile2++;
        }
    }

    public static void addArtistsToStructure(HashSet<String> artistasSemDup, String id) {
        String[] artistasCompleto = new String[artistasSemDup.size()];
        int count = 0;
        for (String artista : artistasSemDup) {
            artistasCompleto[count++] = artista;
        }
        if (Main.ordMemory.get(id) != null) {
            LoadFilesFunctions.getMemoryArtistas(artistasCompleto, id, Main.ordMemoryArtistas);
            Main.ordMemory.get(id).artistas = artistasCompleto;
            Main.numeroLinhasOkFile3++;
        } else {
            Main.numeroLinhasIgnoradasFile3++;
        }
    }

    public static HashSet<String> processArtists(String artistas) {
        String[] artistasArray = artistas.split(", ");
        HashSet<String> artistasSemDup = new HashSet<>();
        for (int i = 0; i < artistasArray.length; i++) {
            if (artistasArray[i].length() > 2) {
                artistasArray[i] = removeQuotations(artistasArray[i]);
                artistasArray[i] = removeApostrophe(artistasArray[i]);
            } else if (artistasArray[i].equals("''") || artistasArray[i].equals("' '")) {
                return null;
            }
            ArrayList<String> tags = new ArrayList<>();
            PerguntasFunctions.ordTags.put(artistasArray[i], tags);
            artistasSemDup.add(artistasArray[i]);
        }
        return artistasSemDup;
    }
}
