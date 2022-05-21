package pt.ulusofona.aed.deisiRockstar2021;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class LoadFilesTest {
    public static void loadFiles() {
        Main.clearStructures();
        loadFilesSongs();
        loadFilesDetails();
        loadFilesArtists();
    }

    public static void loadFilesSongs() {
        try {
            Main.numeroLinhasOkFile1 = 0;
            Main.numeroLinhasIgnoradasFile1 = 0;
            FileReader fr = new FileReader("test-files/songs.txt");
            BufferedReader reader = new BufferedReader(fr);
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split("@");
                if (dados.length != 3) {
                    Main.numeroLinhasIgnoradasFile1++;
                    continue;
                }
                String id = dados[0].trim();
                String titulo = dados[1].trim();
                int anoDeLancamento = Integer.parseInt(dados[2].trim());
                if (id.equals("") || titulo.equals("") || anoDeLancamento < 0 || anoDeLancamento > 2021 ||
                        Main.ordMemory.get(id) != null) {
                    Main.numeroLinhasIgnoradasFile1++;
                    continue;
                }
                titulo = LoadFilesFunctions.removeQuotations(titulo);
                Main.numeroLinhasOkFile1++;
                Song tema = new Song(id, titulo, anoDeLancamento);
                Main.ordMemory.put(tema.id, tema);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro: o ficheiro " + "songs.txt" + " não foi encontrado.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro durante a leitura.");
        }
    }

    public static void loadFilesDetails() {
        try {
            Main.numeroLinhasOkFile2 = 0;
            Main.numeroLinhasIgnoradasFile2 = 0;
            FileReader fr = new FileReader("test-files/song_details.txt");
            BufferedReader reader = new BufferedReader(fr);
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split("@");
                if (dados.length != 7) {
                    Main.numeroLinhasIgnoradasFile2++;
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
                    Main.numeroLinhasIgnoradasFile2++;
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
            Main.numeroLinhasOkFile3 = 0;
            Main.numeroLinhasIgnoradasFile3 = 0;
            FileReader fr = new FileReader("test-files/song_artists.txt");
            BufferedReader reader = new BufferedReader(fr);
            String linha;
            HashSet<String> IdSemDups = new HashSet<>();
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split("@");
                String id = dados[0].trim();
                if (id.equals("") || dados.length != 2) {
                    Main.numeroLinhasIgnoradasFile3++;
                    continue;
                } else if (!IdSemDups.add(id)) {
                    Main.numeroLinhasOkFile3++;
                    continue;
                }
                String artistas = dados[1].trim();
                artistas = LoadFilesFunctions.getArtista(artistas);
                HashSet<String> artistasSemDup = LoadFilesFunctions.processArtists(artistas);
                if (artistasSemDup == null) {
                    Main.numeroLinhasIgnoradasFile3++;
                    continue;
                }
                LoadFilesFunctions.addArtistsToStructure(artistasSemDup, id);
                artistasSemDup.clear();
            }
            reader.close();
            Main.ordMemorySongs.addAll(Main.ordMemory.values());
        } catch (FileNotFoundException e) {
            System.out.println("Erro: o ficheiro " + "song_artists.txt" + " não foi encontrado.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro durante a leitura.");
        }
    }
}
