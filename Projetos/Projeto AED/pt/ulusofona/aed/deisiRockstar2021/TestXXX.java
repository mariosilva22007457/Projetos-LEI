package pt.ulusofona.aed.deisiRockstar2021;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestXXX {
    @Test
    public void testCountSongsYear(){
        LoadFilesTest.loadFiles();
        String mensagemEsperada = "3";
        String mensagemObtida = Main.execute("COUNT_SONGS_YEAR 2020");
        assertEquals(mensagemEsperada, mensagemObtida);
    }

    @Test
    public void testCountDuplicateSongsYear(){
        LoadFilesTest.loadFiles();
        String mensagemEsperada = "0";
        String mensagemObtida = Main.execute("COUNT_DUPLICATE_SONGS_YEAR 2010");
        assertEquals(mensagemEsperada, mensagemObtida);
    }

    @Test
    public void testGetArtistsForTag(){
        LoadFilesTest.loadFiles();
        String mensagemEsperada = "Hideyoshi | HARD\n" +
                "Hideyoshi";
        String mensagemObtida = Main.execute("ADD_TAGS Hideyoshi;Hard") + "\n" + Main.execute("GET_ARTISTS_FOR_TAG Hard");
        assertEquals(mensagemEsperada, mensagemObtida);
    }

    @Test
    public void testGetMostDanceable(){
        LoadFilesTest.loadFiles();
        String mensagemEsperada = "Midnight Tokyo : 2012 : 0.795\n" +
                "\"E-GIRLS ARE RUINING MY LIFE!\" : 2013 : 0.6829999999999999\n" +
                "Need For Phonk : 2016 : 0.606\n" +
                "Motion : 2020 : 0.517\n" +
                "SHINIGAMI : 2020 : 0.4970000000000001\n";
        String mensagemObtida = Main.execute("GET_MOST_DANCEABLE 2000 2020 5");
        assertEquals(mensagemEsperada, mensagemObtida);
    }

    @Test
    public void testGetArtistsOneSong(){
        LoadFilesTest.loadFiles();
        String mensagemEsperada = "CORPSE | \"E-GIRLS ARE RUINING MY LIFE!\" | 2013\n" +
                "Dominus Soul | Need For Phonk | 2016\n" +
                "Jadeci | SHINIGAMI | 2020\n" +
                "Leon Fanourakis | Jitsuryoku | 2015\n" +
                "Noxygen | [ARCHON] | 2013\n" +
                "Prompto | Motion | 2020\n" +
                "Savage Ga$p | \"E-GIRLS ARE RUINING MY LIFE!\" | 2013\n";
        String mensagemObtida = Main.execute("GET_ARTISTS_ONE_SONG 2010 2020");
        assertEquals(mensagemEsperada, mensagemObtida);
    }

    @Test
    public void testGetTopArtistsWithSongsBetween(){
        LoadFilesTest.loadFiles();
        String mensagemEsperada = "Jadeci 2\n";
        String mensagemObtida = Main.execute("GET_TOP_ARTISTS_WITH_SONGS_BETWEEN 1 1 2");
        assertEquals(mensagemEsperada, mensagemObtida);
    }

    @Test
    public void testMostFrequentWordsInArtistName(){
        LoadFilesTest.loadFiles();
        String mensagemEsperada = "Jadeci 2\n" +
                "HAARPER 2\n" +
                "Hideyoshi 2\n";
        String mensagemObtida = Main.execute("MOST_FREQUENT_WORDS_IN_ARTIST_NAME 3 2 5");
        assertEquals(mensagemEsperada, mensagemObtida);
    }

    @Test
    public void testGetUniqueTags(){
        LoadFilesTest.loadFiles();
        String mensagemEsperada = "Hideyoshi | HARD\n" +
                "HARD 1\n";
        String mensagemObtida = Main.execute("ADD_TAGS Hideyoshi;Hard") + "\n" + Main.execute("GET_UNIQUE_TAGS");
        assertEquals(mensagemEsperada, mensagemObtida);
    }

    @Test
    public void testGetUniqueTagsInBetweenYears(){
        LoadFilesTest.loadFiles();
        String mensagemEsperada = "Hideyoshi | HARD\n" +
                "Hideyoshi | HARD,TRAP\n" +
                "HARD 1\n" +
                "TRAP 1\n";
        String mensagemObtida = Main.execute("ADD_TAGS Hideyoshi;Hard") + "\n" + Main.execute("ADD_TAGS Hideyoshi;Trap")
                + "\n" + Main.execute("GET_UNIQUE_TAGS_IN_BETWEEN_YEARS 2000 2020");
        assertEquals(mensagemEsperada, mensagemObtida);
    }

    @Test
    public void testGetRisingStars(){
        LoadFilesTest.loadFiles();
        String mensagemEsperada = "Jadeci <=> 64.0\n" +
                "Noxygen <=> 48.0\n" +
                "CORPSE <=> 43.0\n" +
                "Savage Ga$p <=> 43.0\n" +
                "Dominus Soul <=> 0.0\n" +
                "Hideyoshi <=> 0.0\n" +
                "Leon Fanourakis <=> 0.0\n" +
                "Prompto <=> 0.0\n";

        String mensagemObtida = Main.execute("GET_RISING_STARS 2000 2020 DESC");
        assertEquals(mensagemEsperada, mensagemObtida);
    }

    @Test
    public void testAddTags(){
        LoadFilesTest.loadFiles();
        String mensagemEsperada = "Hideyoshi | TRAP\n" +
                "Hideyoshi | TRAP,HARD";
        String mensagemObtida = Main.execute("ADD_TAGS Hideyoshi;Trap") + "\n" + Main.execute("ADD_TAGS Hideyoshi;Hard");
        assertEquals(mensagemEsperada, mensagemObtida);
    }

    @Test
    public void testRemoveTags(){
        LoadFilesTest.loadFiles();
        String mensagemEsperada = "Hideyoshi | TRAP\n" +
                "Hideyoshi | No tags";
        String mensagemObtida = Main.execute("ADD_TAGS Hideyoshi;Trap") + "\n" + Main.execute("REMOVE_TAGS Hideyoshi;Trap");
        assertEquals(mensagemEsperada, mensagemObtida);
    }

    @Test
    public void testCleanup(){
        LoadFilesTest.loadFiles();
        String mensagemEsperada = "Musicas removidas: 1; Artistas removidos: 1";
        String mensagemObtida = Main.execute("CLEANUP");
        assertEquals(mensagemEsperada, mensagemObtida);
    }

    @Test
    public void testCreativeQuery(){
        LoadFilesTest.loadFiles();
        String mensagemEsperada = "SHINIGAMI : 0.4970000000000001\n" +
                "Majinahanashi : 0.0\n";
        String mensagemObtida = Main.execute("GET_MOST_DANCEABLE_UNTIL_VALUE_IN_YEAR 2020 0.5");
        assertEquals(mensagemEsperada, mensagemObtida);
    }
}
