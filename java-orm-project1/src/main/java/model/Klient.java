package model;

import java.util.Date;
import java.util.Set;

public class Klient {

    private String pesel;
    private String imie;
    private String nazwisko;
    private Date date;

    private Set<Ksiazka> ksiazki;

    public Klient() {
    }

    public Klient(String pesel, String imie, String nazwisko, Date date) {
        this.pesel = pesel;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.date = date;
    }

    public Set<Ksiazka> getKsiazki() {
        return ksiazki;
    }

    public void setKsiazki(Set<Ksiazka> ksiazki) {
        this.ksiazki = ksiazki;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return pesel+":"+imie+" "+nazwisko;
    }

}
