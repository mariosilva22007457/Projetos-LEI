package pt.ulusofona.aed.deisiRockstar2021;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static int numeroLinhasOkFile1;
    static int numeroLinhasIgnoradasFile1;
    static int numeroLinhasOkFile2;
    static int numeroLinhasIgnoradasFile2;
    static int numeroLinhasOkFile3;
    static int numeroLinhasIgnoradasFile3;
    static LinkedHashMap<String, Song> ordMemory = new LinkedHashMap<>();
    static ArrayList<Song> ordMemorySongs = new ArrayList<>();
    static HashMap<String, String> ordMemoryArtistas = new HashMap<>();

    public static void loadFiles() {
        clearStructures();
        loadFilesSongs();
        loadFilesDetails();
        loadFilesArtists();
    }

    public static void loadFilesSongs() {
        int counter =0;
        try {
            numeroLinhasOkFile1 = 0;
            numeroLinhasIgnoradasFile1 = 0;
            FileReader fr = new FileReader("songs.txt");
            BufferedReader reader = new BufferedReader(fr);
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split("@");
                if (dados.length != 3) {
                    numeroLinhasIgnoradasFile1++;
                    continue;
                }
                String id = dados[0].trim();
                String titulo = dados[1].trim();
                int anoDeLancamento = Integer.parseInt(dados[2].trim());
                if (id.equals("") || titulo.equals("") || anoDeLancamento < 0 || anoDeLancamento > 2021 ||
                        ordMemory.get(id) != null) {
                    numeroLinhasIgnoradasFile1++;
                    continue;
                }
                if( anoDeLancamento % 10 == 0){
                    counter ++;

                }

                titulo = LoadFilesFunctions.removeQuotations(titulo);
                numeroLinhasOkFile1++;
                Song tema = new Song(id, titulo, anoDeLancamento);
                ordMemory.put(tema.id, tema);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro: o ficheiro " + "songs.txt" + " não foi encontrado.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro durante a leitura.");
        }
        System.out.println("econtrei: "+ counter);
    }


    public static void loadFilesDetails() {
        try {
            numeroLinhasOkFile2 = 0;
            numeroLinhasIgnoradasFile2 = 0;
            FileReader fr = new FileReader("song_details.txt");
            BufferedReader reader = new BufferedReader(fr);
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split("@");
                if (dados.length != 7) {
                    numeroLinhasIgnoradasFile2++;
                    continue;
                }
                String id = dados[0].trim();
                int duracaoDoTema = Integer.parseInt(dados[1].trim());
                boolean letraExplicita = (Integer.parseInt(dados[2].trim()) != 0);
                double popularidade = Double.parseDouble(dados[3].trim());
                double grauDeDancabilidade = Double.parseDouble(dados[4].trim());
                double grauDeVivacidade = Double.parseDouble(dados[5].trim());
                double volumeMedio = Double.parseDouble(dados[6].trim());
                if ((Integer.parseInt(dados[2].trim()) != 1 && Integer.parseInt(dados[2].trim()) != 0)) {
                    numeroLinhasIgnoradasFile2++;
                    continue;
                }
                LoadFilesFunctions.addDetailsToStructure(id, duracaoDoTema, letraExplicita, popularidade,
                        grauDeDancabilidade, grauDeVivacidade, volumeMedio);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro: o ficheiro " + "song_details.txt" + " não foi encontrado.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro durante a leitura.");
        }
    }

    public static void loadFilesArtists() {
        try {
            numeroLinhasOkFile3 = 0;
            numeroLinhasIgnoradasFile3 = 0;
            FileReader fr = new FileReader("song_artists.txt");
            BufferedReader reader = new BufferedReader(fr);
            String linha;
            HashSet<String> IdSemDups = new HashSet<>();
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split("@");
                String id = dados[0].trim();
                if (id.equals("") || dados.length != 2) {
                    numeroLinhasIgnoradasFile3++;
                    continue;
                } else if (!IdSemDups.add(id)) {
                    numeroLinhasOkFile3++;
                    continue;
                }
                String artistas = dados[1].trim();
                artistas = LoadFilesFunctions.getArtista(artistas);
                HashSet<String> artistasSemDup = LoadFilesFunctions.processArtists(artistas);
                if (artistasSemDup == null) {
                    numeroLinhasIgnoradasFile3++;
                    continue;
                }
                LoadFilesFunctions.addArtistsToStructure(artistasSemDup, id);
                artistasSemDup.clear();
            }
            reader.close();
            ordMemorySongs.addAll(ordMemory.values());
        } catch (FileNotFoundException e) {
            System.out.println("Erro: o ficheiro " + "song_artists.txt" + " não foi encontrado.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro durante a leitura.");
        }
    }

    public static void clearStructures() {
        ordMemory.clear();
        PerguntasFunctions.ordTags.clear();
        ordMemorySongs.clear();
        ordMemoryArtistas.clear();
    }

    public static ParseInfo getParseInfo(String fileName) {
        ParseInfo getParseInfoClass = new ParseInfo();
        if (fileName == null || fileName.length() == 0 || !fileName.equals("songs.txt") &&
                !fileName.equals("song_details.txt") && !fileName.equals("song_artists.txt")) {
            return null;
        } else if (fileName.equals("songs.txt")) {
            getParseInfoClass.numeroLinhasOk = numeroLinhasOkFile1;
            numeroLinhasOkFile1 = 0;
            getParseInfoClass.numeroLinhasIgnoradas = numeroLinhasIgnoradasFile1;
            numeroLinhasIgnoradasFile1 = 0;
            return getParseInfoClass;
        } else if (fileName.equals("song_details.txt")) {
            getParseInfoClass.numeroLinhasOk = numeroLinhasOkFile2;
            numeroLinhasOkFile2 = 0;
            getParseInfoClass.numeroLinhasIgnoradas = numeroLinhasIgnoradasFile2;
            numeroLinhasIgnoradasFile2 = 0;
            return getParseInfoClass;
        } else {
            getParseInfoClass.numeroLinhasOk = numeroLinhasOkFile3;
            numeroLinhasOkFile3 = 0;
            getParseInfoClass.numeroLinhasIgnoradas = numeroLinhasIgnoradasFile3;
            numeroLinhasIgnoradasFile3 = 0;
            return getParseInfoClass;
        }
    }

    public static ArrayList<Song> getSongs() {
        return ordMemorySongs;
    }

    public static void main(String[] args) {
        loadFiles();
        System.out.println(getSongs().get(0));

        System.out.println("Welcome to DEISI Rockstar!");
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        while (line != null && !line.equals("KTHXBYE")) {
            long start = System.currentTimeMillis();
            String result = execute(line);
            long end = System.currentTimeMillis();
            System.out.println(result);
            System.out.println("(took " + (end - start) + " ms)");
            line = in.nextLine();
        }
        System.out.println("Adeus.");

    }

    public static String execute(String line) {
        String[] fullLine = line.split(" ");
        switch (fullLine[0]) {
            case ("COUNT_SONGS_YEAR"):
                return PerguntasFunctions.countSongsYear(Integer.parseInt(fullLine[1]));
            case ("COUNT_DUPLICATE_SONGS_YEAR"):
                return PerguntasFunctions.countDuplicateSongsYear(Integer.parseInt(fullLine[1]));
            case ("GET_ARTISTS_FOR_TAG"):
                return PerguntasFunctions.getArtistsForTag(fullLine[1]);
            case ("GET_MOST_DANCEABLE"):
                return PerguntasFunctions.getMostDanceable(Integer.parseInt(fullLine[1]), Integer.parseInt(fullLine[2]),
                        Integer.parseInt(fullLine[3]));
            case ("GET_ARTISTS_ONE_SONG"):
                return PerguntasFunctions.getArtistsOneSong(Integer.parseInt(fullLine[1]), Integer.parseInt(fullLine[2]));
            case ("GET_TOP_ARTISTS_WITH_SONGS_BETWEEN"):
                return PerguntasFunctions.getTopArtistsWithSongsBetween(Integer.parseInt(fullLine[1]),
                        Integer.parseInt(fullLine[2]), Integer.parseInt(fullLine[3]));
            case ("MOST_FREQUENT_WORDS_IN_ARTIST_NAME"):
                return PerguntasFunctionsSup.mostFrequentWordsInArtistName(Integer.parseInt(fullLine[1]),
                        Integer.parseInt(fullLine[2]), Integer.parseInt(fullLine[3]));
            case("GET_ARTISTS_MOST_TAGS"):
                return PerguntasFunctionsSup.getArtistsMostTags(Integer.parseInt(fullLine[1]));
            default:
                return executeNext(line);
        }
    }

    public static String executeNext(String line) {
        String[] fullLine = line.split(" ");
        switch (fullLine[0]) {
            case ("GET_UNIQUE_TAGS"):
                return PerguntasFunctions.getUniqueTags();
            case ("GET_UNIQUE_TAGS_IN_BETWEEN_YEARS"):
                return PerguntasFunctions.getUniqueTagsInBetweenYears(Integer.parseInt(fullLine[1]),
                        Integer.parseInt(fullLine[2]));
            case ("GET_RISING_STARS"):
                return PerguntasFunctionsSup.getRisingStars(Integer.parseInt(fullLine[1]), Integer.parseInt(fullLine[2]),
                        fullLine[3]);
            case ("ADD_TAGS"):
                return PerguntasFunctions.addTags(line);
            case ("REMOVE_TAGS"):
                return PerguntasFunctions.removeTags(line);
            case ("CLEANUP"):
                return PerguntasFunctionsSup.cleanup();
            case ("GET_MOST_DANCEABLE_UNTIL_VALUE_IN_YEAR"):
                return PerguntasFunctions.getMostDanceableUntilValueInYear(Integer.parseInt(fullLine[1]),
                        Double.parseDouble(fullLine[2]));
            case("GET_ARTISTS_LOUDER_THAN"):
                return PerguntasFunctionsSup.getArtistsLounder(Integer.parseInt(fullLine[1]),Double.parseDouble(fullLine[2]));

            default:
                return "Illegal command. Try again";
        }
    }

    public static String getCreativeQuery() {
        return "GET_MOST_DANCEABLE_UNTIL_VALUE_IN_YEAR";
    }

    public static String getVideoUrl() {
        return "https://www.youtube.com/watch?v=dUgvrQ5Vsxg";
    }

    public static int getTypeOfSecondParameter() {
        return 2;
    }
}  