package pt.ulusofona.aed.deisiRockstar2021;

import java.util.*;

public class PerguntasFunctions {
    static LinkedHashMap<String, ArrayList<String>> ordTags = new LinkedHashMap<>();

    static String countSongsYear(int ano) {
        int count = 0;
        for (int i = 0; i < Main.ordMemorySongs.size(); i++) {
            if (Main.ordMemorySongs.get(i).anoDeLancamento == ano) {
                count++;
            }
        }
        return String.valueOf(count);
    }

    static String countDuplicateSongsYear(int ano) {
        int counter = 0;
        ArrayList<String> ordList = new ArrayList<>();
        for (int i = 0; i < Main.ordMemorySongs.size(); i++) {
            if (Main.ordMemorySongs.get(i).anoDeLancamento == ano) {
                ordList.add(Main.ordMemorySongs.get(i).titulo);
            }
        }

        ordList.sort(Comparator.comparing((String tema) -> tema));
        for (int i = 1; i < ordList.size() - 1; i++) {
            if (ordList.get(i).equals(ordList.get(i + 1)) &&
                    !(ordList.get(i + 1).equals(ordList.get(i + 3)))) {
                counter++;
            }
        }
        return String.valueOf(counter);
    }

    static String getArtistsForTag(String tag) {
        String text = "";
        boolean hasArtists = false;
        for (Map.Entry<String, ArrayList<String>> entry : ordTags.entrySet()) {
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            for (int i = 0; i < value.size(); i++) {
                if (value.get(i).equals(tag.toUpperCase())) {
                    hasArtists = true;
                    text += key + ";";
                }
            }
        }
        if (!hasArtists) {
            text = "No results";
        }
        text = text.substring(0, text.length() - 1);
        return text;
    }


    static String getMostDanceable(int anoI, int anoF, int nrRes) {
        ArrayList<Song> ordList = new ArrayList<>();
        for (int i = 0; i < Main.ordMemorySongs.size(); i++) {
            if (Main.ordMemorySongs.get(i).anoDeLancamento >= anoI && Main.ordMemorySongs.get(i).anoDeLancamento <= anoF) {
                ordList.add(Main.ordMemorySongs.get(i));
            }
        }
        Collections.sort(ordList, Comparator.comparingDouble((Song tema) -> tema.grauDeDancabilidade).reversed());
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < nrRes; i++) {
            text.append(ordList.get(i).titulo).append(" : ").append(ordList.get(i).anoDeLancamento).
                    append(" : ").append(ordList.get(i).grauDeDancabilidade).append("\n");
        }
        return text.toString();
    }

    static String getArtistsOneSong(int anoI, int anoF) {
        if (anoI >= anoF) {
            return "Invalid period";
        }
        HashMap<String, String> ordMemoryOneSong = new HashMap<>();
        ArrayList<Artista> ordList = new ArrayList<>();
        for (int i = 0; i < Main.ordMemorySongs.size(); i++) {
            if (Main.ordMemorySongs.get(i).anoDeLancamento >= anoI && Main.ordMemorySongs.get(i).anoDeLancamento <= anoF &&
                    Main.ordMemorySongs.get(i).artistas != null) {
                for (int j = 0; j < Main.ordMemorySongs.get(i).artistas.length; j++) {
                    String IDs = ordMemoryOneSong.get(Main.ordMemorySongs.get(i).artistas[j]);
                    Artista artista = new Artista(Main.ordMemorySongs.get(i).artistas[j], Main.ordMemorySongs.get(i));
                    ordList.add(artista);
                    if (IDs == null) {
                        IDs = "";
                        ordMemoryOneSong.put(Main.ordMemorySongs.get(i).artistas[j], IDs + (Main.ordMemorySongs.get(i).id + ","));
                    } else {
                        ordMemoryOneSong.remove(Main.ordMemorySongs.get(i).artistas[j]);
                        ordMemoryOneSong.put(Main.ordMemorySongs.get(i).artistas[j], IDs + (Main.ordMemorySongs.get(i).id + ","));
                    }
                }
            }
        }
        Collections.sort(ordList, Comparator.comparing((Artista artista) -> artista.nome));
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < ordList.size(); i++) {
            if (Song.getNrTemas(ordList.get(i).nome, ordMemoryOneSong) == 1) {
                text.append(ordList.get(i).nome).append(" | ").append(ordList.get(i).tema.titulo).append(" | ")
                        .append(ordList.get(i).tema.anoDeLancamento).append("\n");
            }
        }
        return text.toString();
    }

    static String getTopArtistsWithSongsBetween(int nrRes, int temasI, int temasF) {
        ArrayList<Artista> ordList = new ArrayList<>();
        for (Map.Entry<String, String> entry : Main.ordMemoryArtistas.entrySet()) {
            String key = entry.getKey();
            if (Song.getNrTemas(key, Main.ordMemoryArtistas) >= temasI && Song.getNrTemas(key, Main.ordMemoryArtistas) <= temasF) {
                Artista artista = new Artista(key, Song.getNrTemas(key, Main.ordMemoryArtistas));
                ordList.add(artista);
            }
        }
        Collections.sort(ordList, Comparator.comparingInt((Artista artista) -> artista.numeroTemas).reversed());
        String text = "";
        if (nrRes < ordList.size()) {
            for (int i = 0; i < nrRes; i++) {
                text += ordList.get(i).nome + " " + ordList.get(i).numeroTemas + "\n";
            }
        } else {
            for (int i = 0; i < ordList.size(); i++) {
                text += ordList.get(i).nome + " " + ordList.get(i).numeroTemas + "\n";
            }
        }
        if (text == "") {
            text = "No results";
        }
        return text;
    }

    static String getUniqueTags() {
        HashMap<String, Integer> tagOc = new HashMap<>();
        ArrayList<Tag> ordList = new ArrayList<>();
        String text = "";
        for (Map.Entry<String, ArrayList<String>> entry : ordTags.entrySet()) {
            ArrayList<String> value = entry.getValue();
            for (int i = 0; i < value.size(); i++) {
                if (tagOc.get(value.get(i)) == null) {
                    tagOc.put(value.get(i), 1);
                } else {
                    int ocurrencies = tagOc.get(value.get(i));
                    tagOc.remove(value.get(i));
                    tagOc.put(value.get(i), ++ocurrencies);
                }
            }
        }
        for (Map.Entry<String, Integer> entry : tagOc.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            Tag tag = new Tag(key, value);
            ordList.add(tag);
        }
        Collections.sort(ordList, Comparator.comparingInt((Tag tag) -> tag.nrOc));
        if (ordList.size() == 0) {
            text = "No results";
        } else {
            for (int i = 0; i < ordList.size(); i++) {
                text += ordList.get(i).nome + " " + ordList.get(i).nrOc + "\n";
            }
        }
        return text;
    }

    static ArrayList<String> getUniqueTagsInBetweenYearsSup2(int anoI, int anoF) {
        ArrayList<String> ordArtists = new ArrayList<>();
        for (int i = 0; i < Main.ordMemorySongs.size(); i++) {
            if (Main.ordMemorySongs.get(i).anoDeLancamento >= anoI && Main.ordMemorySongs.get(i).anoDeLancamento <= anoF &&
                    Main.ordMemorySongs.get(i).artistas != null) {
                Collections.addAll(ordArtists, Main.ordMemorySongs.get(i).artistas);
            }
        }
        return ordArtists;
    }

    static HashMap<String, ArrayList<String>> getUniqueTagsInBetweenYearsSup(int anoI, int anoF) {
        ArrayList<String> ordArtists = getUniqueTagsInBetweenYearsSup2(anoI, anoF);
        HashMap<String, ArrayList<String>> newOrdTags = new HashMap<>();
        for (int i = 0; i < ordArtists.size(); i++) {
            if (ordTags.containsKey(ordArtists.get(i))) {
                newOrdTags.put(ordArtists.get(i), ordTags.get(ordArtists.get(i)));
            }
        }
        return newOrdTags;
    }



    static String getUniqueTagsInBetweenYears(int anoI, int anoF) {
        HashMap<String, ArrayList<String>> newOrdTags = getUniqueTagsInBetweenYearsSup(anoI, anoF);
        HashMap<String, Integer> tagOc = new HashMap<>();
        ArrayList<Tag> ordList = new ArrayList<>();
        String text = "";
        for (Map.Entry<String, ArrayList<String>> entry : newOrdTags.entrySet()) {
            ArrayList<String> value = entry.getValue();
            for (int i = 0; i < value.size(); i++) {
                if (tagOc.get(value.get(i)) == null) {
                    tagOc.put(value.get(i), 1);
                } else {
                    int ocurrencies = tagOc.get(value.get(i));
                    tagOc.remove(value.get(i));
                    tagOc.put(value.get(i), ++ocurrencies);
                }
            }
        }
        for (Map.Entry<String, Integer> entry : tagOc.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            Tag tag = new Tag(key, value);
            ordList.add(tag);
        }
        Collections.sort(ordList, Comparator.comparingInt((Tag tag) -> tag.nrOc).reversed());
        if (ordList.size() == 0) {
            text = "No results";
        } else {
            for (int i = 0; i < ordList.size(); i++) {
                text += ordList.get(i).nome + " " + ordList.get(i).nrOc + "\n";
            }
        }
        return text;
    }

    static String addTags(String line) {
        String lineNoQuerie = line.replace("ADD_TAGS ", "");
        String[] arrayTags = lineNoQuerie.split(";");
        ArrayList<String> tags = ordTags.get(arrayTags[0]);
        String text = arrayTags[0] + " | ";
        if (ordTags.get(arrayTags[0]) == null) {
            return "Inexistent artist";
        }

        for (int i = 1; i < arrayTags.length; i++) {
            if (!tags.contains(arrayTags[i])) {
                tags.add(arrayTags[i].toUpperCase());
            }
        }
        ordTags.remove(arrayTags[0]);
        ordTags.put(arrayTags[0], tags);
        for (int i = 0; i < ordTags.get(arrayTags[0]).size(); i++) {
            text += ordTags.get(arrayTags[0]).get(i) + ",";
        }
        return text.substring(0, text.length() - 1);
    }

    static String removeTags(String line) {
        String lineNoQuerie = line.replace("REMOVE_TAGS ", "");
        String[] arrayTags = lineNoQuerie.split(";");
        ArrayList<String> tags = ordTags.get(arrayTags[0]);
        String text = arrayTags[0] + " | ";
        if (ordTags.get(arrayTags[0]) == null) {
            return "Inexistent artist";
        }
        for (int i = 1; i < arrayTags.length; i++) {
            if (tags.contains(arrayTags[i].toUpperCase())) {
                tags.remove(arrayTags[i].toUpperCase());
            }
        }
        ordTags.remove(arrayTags[0]);
        ordTags.put(arrayTags[0], tags);
        if (!(tags.size() == 0)) {
            for (int i = 0; i < ordTags.get(arrayTags[0]).size(); i++) {
                text += ordTags.get(arrayTags[0]).get(i) + ",";
            }
        } else {
            text += "No tags";
            return text;
        }
        return text.substring(0, text.length() - 1);
    }

    static String getMostDanceableUntilValueInYear(int ano, double maxDanc) {
        ArrayList<Song> ordList = new ArrayList<>();
        String text = "";
        for (int k = 0; k < Main.ordMemorySongs.size(); k++) {
            if (Main.ordMemorySongs.get(k).anoDeLancamento == ano) {
                ordList.add(Main.ordMemorySongs.get(k));
            }
        }
        Collections.sort(ordList, Comparator.comparingDouble((Song tema) -> tema.grauDeDancabilidade).reversed());
        for (int i = 0; i < ordList.size(); i++) {
            if (ordList.get(i).grauDeDancabilidade < maxDanc) {
                text += ordList.get(i).titulo + " : " + ordList.get(i).grauDeDancabilidade + "\n";
            }
        }
        return text;
    }


}
