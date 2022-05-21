package pt.ulusofona.aed.deisiRockstar2021;

public class ParseInfo {
    int numeroLinhasOk;
    int numeroLinhasIgnoradas;

    public ParseInfo() {
    }

    public String toString() {
        return "OK: " + numeroLinhasOk + ", Ignored: " + numeroLinhasIgnoradas;
    }
}