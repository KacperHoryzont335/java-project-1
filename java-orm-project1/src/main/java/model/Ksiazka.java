package model;

public class Ksiazka {

    private String isbn;
    private String autor;
    private String tytul;
    private TypKsiazki typ;
    private String wydawnictwo;
    private int rok;
    private double cena;

    public Ksiazka() {
    }

    public Ksiazka(String isbn, String autor, String tytul, TypKsiazki typ, String wydawnictwo, int rok, double cena) {
        this.isbn = isbn;
        this.autor = autor;
        this.tytul = tytul;
        this.typ = typ;
        this.wydawnictwo = wydawnictwo;
        this.rok = rok;
        this.cena = cena;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public TypKsiazki getTyp() {
        return typ;
    }

    public void setTyp(TypKsiazki typ) {
        this.typ = typ;
    }

    public String getWydawnictwo() {
        return wydawnictwo;
    }

    public void setWydawnictwo(String wydawnictwo) {
        this.wydawnictwo = wydawnictwo;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        return autor +" "+tytul+" "+rok+" "+cena+" "+typ+" "+wydawnictwo+" "+isbn;
    }

}
