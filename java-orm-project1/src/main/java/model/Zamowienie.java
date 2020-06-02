package model;

import java.util.Date;

public class Zamowienie {
    private int id;
    private String klient;
    private Date kiedy;
    private StatusTyp status;

    public Zamowienie(int id, String klient, Date kiedy, StatusTyp status) {
        this.id = id;
        this.klient = klient;
        this.kiedy = kiedy;
        this.status = status;
    }

    public Zamowienie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKlient() {
        return klient;
    }

    public void setKlient(String klient) {
        this.klient = klient;
    }

    public Date getKiedy() {
        return kiedy;
    }

    public void setKiedy(Date kiedy) {
        this.kiedy = kiedy;
    }

    public StatusTyp getStatus() {
        return status;
    }

    public void setStatus(StatusTyp status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  "zamówienie przez: "+ klient +" dnia: "+ kiedy + " status: "+status;
    }

}
