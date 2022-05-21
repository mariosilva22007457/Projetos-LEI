package pt.ulusofona.aed.deisiRockstar2021;

import java.util.*;

public class PerguntasFunctionsSup {
    static HashMap<String, Integer> mostFrequentWordsInArtistNameSup(int nrTemas){
        HashMap<String, Integer> wordsOc = new HashMap<>();
        for(int i = 0; i < Main.ordMemorySongs.size(); i++){
            if(Main.ordMemorySongs.get(i).artistas != null){
                for(int j = 0; j < Main.ordMemorySongs.get(i).artistas.length; j++){
                    if(Song.getNrTemas(Main.ordMemorySongs.get(i).artistas[j], Main.ordMemoryArtistas) >= nrTemas){
                        String[] palavras = Main.ordMemorySongs.get(i).artistas[j].split(" ");
                        for(int k = 0; k < palavras.length; k++){
                            if(!wordsOc.containsKey(palavras[k])){
                                wordsOc.put(palavras[k], 1);
                            } else {
                                int ocurrencias = wordsOc.get(palavras[k]);
                                wordsOc.remove(palavras[k]);
                                wordsOc.put(palavras[k], ++ocurrencias);
                            }
                        }
                    }
                }
            }
        }
        return wordsOc;
    }
    static String mostFrequentWordsInArtistName(int nrRes, int nrTemas, int tam) {
        ArrayList<Tag> ordList = new ArrayList<>();
        HashMap<String, Integer> wordsOc = mostFrequentWordsInArtistNameSup(nrTemas);
        String text = "";
        for (Map.Entry<String, Integer> entry : wordsOc.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            Tag tag = new Tag(key, value);
            ordList.add(tag);
        }
        Collections.sort(ordList, Comparator.comparingInt((Tag tag) -> tag.nrOc));
        if(ordList.size() > nrRes){
            for (int i = 0; i < nrRes + 1; i++) {
                if(ordList.get(i).nome.length() > tam){
                    text += ordList.get(i).nome + " " + ordList.get(i).nrOc + "\n";
                }
            }
        } else {
            for (int i = 0; i < ordList.size(); i++) {
                if(ordList.get(i).nome.length() > tam){
                    text += ordList.get(i).nome + " " + ordList.get(i).nrOc + "\n";
                }
            }
        }

        return text;
    }

    static HashMap<String, ArrayList<Song>> getRisingStarsSup2(int anoI, int anoF){
        HashMap<String, ArrayList<Song>> artistsWithSongs = new HashMap<>();
        for (int i = 0; i < Main.ordMemorySongs.size(); i++) {
            if (Main.ordMemorySongs.get(i).anoDeLancamento >= anoI && Main.ordMemorySongs.get(i).anoDeLancamento <= anoF
                    && Main.ordMemorySongs.get(i).artistas != null) {
                for (int j = 0; j < Main.ordMemorySongs.get(i).artistas.length; j++) {
                    ArrayList<Song> songs;
                    if (!artistsWithSongs.containsKey(Main.ordMemorySongs.get(i).artistas[j])) {
                        songs = new ArrayList<>();
                        songs.add(Main.ordMemorySongs.get(i));
                    } else {
                        songs = artistsWithSongs.get(Main.ordMemorySongs.get(i).artistas[j]);
                        songs.add(Main.ordMemorySongs.get(i));
                        artistsWithSongs.remove(Main.ordMemorySongs.get(i).artistas[j]);
                    }
                    artistsWithSongs.put(Main.ordMemorySongs.get(i).artistas[j], songs);
                }
            }
        }
        return artistsWithSongs;
    }

    static ArrayList<Artista> getRisingStarsSup(int anoI, int anoF){
        ArrayList<Artista> ordList = new ArrayList<>();
        HashMap<String, ArrayList<Song>> artistsWithSongs = getRisingStarsSup2(anoI, anoF);
        ArrayList<String> toRemove = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Song>> entry : artistsWithSongs.entrySet()) {
            String key = entry.getKey();
            ArrayList<Song> value = entry.getValue();
            Collections.sort(value, Comparator.comparingInt((Song song) -> song.anoDeLancamento));
            double popMedia = 0.0;
            for (int i = 0; i < value.size(); i++) {
                if (i >= 1 && value.get(i).popularidade < value.get(i - 1).popularidade) {
                    toRemove.add(key);
                } else {
                    popMedia += value.get(i).popularidade;
                }
            }
            if(!toRemove.contains(key)){
                Artista artista = new Artista(key, popMedia);
                ordList.add(artista);
            }
        }
        return ordList;
    }

    static String getRisingStars(int anoI, int anoF, String ord) {
        ArrayList<Artista> ordList = getRisingStarsSup(anoI, anoF);
        ArrayList<Artista> ordList15 = new ArrayList<>();
        String text = "";
        Collections.sort(ordList, Comparator.comparingInt((Artista artista) -> (int) artista.popMedia)
                .thenComparing((Artista artista) -> artista.nome));
        if(ordList.size() >= 15){
            for (int i = 0; i < 15; i++) {
                ordList15.add(ordList.get(i));
            }
        } else {
            ordList15.addAll(ordList);
        }
        if(ord.equals("ASC")){
            Collections.sort(ordList15, Comparator.comparingInt((Artista artista) -> (int) artista.popMedia)
                    .thenComparing((Artista artista) -> artista.nome));
            for(int i = 0; i < ordList15.size(); i++){
                text += ordList15.get(i).nome + " <=> " + ordList15.get(i).popMedia + "\n";
            }
        } else if(ord.equals("DESC")){
            Collections.sort(ordList15, Comparator.comparingInt((Artista artista) -> (int) artista.popMedia).reversed()
                    .thenComparing((Artista artista) -> artista.nome));
            for(int i = 0; i < ordList15.size(); i++){
                text += ordList15.get(i).nome + " <=> " + ordList15.get(i).popMedia + "\n";
            }
        }
        return text;
    }


    static String cleanup() {
        int countRemovedMusics = 0;
        int countRemovedArtists = 0;
        for(int i = 0; i < Main.ordMemorySongs.size(); i++){
            if(Main.ordMemorySongs.get(i).artistas != null && Main.ordMemorySongs.get(i).titulo == null){
                countRemovedArtists++;
            }
            if(Main.ordMemorySongs.get(i).titulo != null && Main.ordMemorySongs.get(i).artistas == null){
                countRemovedMusics++;
            }
            if(Main.ordMemorySongs.get(i).titulo != null && Main.ordMemorySongs.get(i).duracaoDoTema == 0){
                countRemovedMusics++;
            }
        }
        return "Musicas removidas: " + countRemovedMusics + "; Artistas removidos: " + countRemovedArtists;
    }


    static String getArtistsLounder(int ano,double loudness){

      HashSet<String> arts = new HashSet<>();
      StringBuilder text = new StringBuilder();
      for( int i = 0;i < Main.ordMemorySongs.size();i++ ){

          if(Main.ordMemorySongs.get(i).volumeMedio < loudness || Main.ordMemorySongs.get(i).volumeMedio >= 0 || (Main.ordMemorySongs.get(i).anoDeLancamento != ano)){
              continue;
          }

          for(int j = 0;  Main.ordMemorySongs.get(i).artistas != null && j < Main.ordMemorySongs.get(i).artistas.length;j++){
              arts.add(Main.ordMemorySongs.get(i).artistas[j]);
          }
      }

      ArrayList<String> artistfinal = new ArrayList<>(arts);

        for (String s : artistfinal) {
            text.append(s).append("\n");
        }


        return text.toString();
    }

    static String getArtistsMostTags(int comprimentoMin){

        ArrayList<ArtistsWTags> ordList = new ArrayList<>();
        StringBuilder text = new StringBuilder();
        ArrayList<String> ola = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : PerguntasFunctions.ordTags.entrySet()) {
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            int nrTT = value.size();
            for(int i = 0; i < value.size(); i++){
                if(value.get(i).length() >= comprimentoMin){
                   ola.add(value.get(i)); }
            }
            ArtistsWTags temp = new ArtistsWTags(key, nrTT, ola.size());
            ola.clear();
            ordList.add(temp);
        }
        ordList.sort(Comparator.comparingInt((ArtistsWTags tag) -> tag.nrTC).reversed().thenComparingInt((ArtistsWTags tag) -> tag.nrTT));
        if (ordList.size() == 0) {
            text = new StringBuilder("No results");
        } else {
            for (int i = 0; i < ordList.size(); i++) {
                if(ordList.get(i).nrTT > 0|| ordList.get(i).nrTC > 0 ){
                    text.append(ordList.get(i).artist).append(" : ").append(ordList.get(i).nrTT).append(" : ").append(ordList.get(i).nrTC).append("\n");
                }
            }
        }
        return text.toString();
    }




}
