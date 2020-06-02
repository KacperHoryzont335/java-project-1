import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

class Okno extends JFrame {
    private JTextField komunikat = new JTextField();
    private JTabbedPane tp = new JTabbedPane();
    private JPanel p1 = new JPanel();
    private JPanel p2 = new JPanel();
    private JPanel p3 = new JPanel();
    private JTextField pole_pesel = new JTextField();
    private JTextField pole_im = new JTextField();
    private JTextField pole_naz = new JTextField();
    private JTextField pole_ur = new JTextField();

    private JTextField pole_isbn=new JTextField();
    private JTextField pole_autor=new JTextField();
    private JTextField pole_tytul=new JTextField();
    private JTextField pole_typ=new JTextField();
    private JTextField pole_wydawnictwo=new JTextField();
    private JTextField pole_rok=new JTextField();
    private JTextField pole_cena=new JTextField();

    private JButton przyc_zapisz_kl = new JButton("zapisz");
    private JButton przyc_usun = new JButton("Usun");
    private DefaultListModel<Klient> lm1_kli = new DefaultListModel<>();
    private JList<Klient>l1_kli = new JList<>(lm1_kli);
    private JScrollPane sp1_kli = new JScrollPane(l1_kli);

    private JButton przyc_zapisz_ks = new JButton("zapisz");
    private JButton przyc_usun_ks = new JButton("usun");
    private JButton przyc_zmodyfikuj_ks = new JButton("edytuj");

    private DefaultListModel<Ksiazka> lm1_ks = new DefaultListModel<>();
    private JList<Ksiazka>l1_ks = new JList<>(lm1_ks);
    private JScrollPane sp1_ks = new JScrollPane(l1_ks);

    private JButton zloz_zamowienie=new JButton("zloz zamowienie");
    private JButton zmien_status=new JButton("status zapłacone");
    private JButton zmien_status_oczek=new JButton("status oczekujące");
    private JButton zmien_status_wyslane=new JButton("status wysłane");


    private DefaultListModel<Ksiazka> zamdlm_ks = new DefaultListModel<>();
    private JList<Ksiazka>zam_ks = new JList<>(zamdlm_ks);
    private JScrollPane spz_ks = new JScrollPane(zam_ks);

    private DefaultListModel<Klient> zamdlm_kli = new DefaultListModel<>();
    private JList<Klient>zam_kli = new JList<>(zamdlm_kli);
    private JScrollPane spz_kli = new JScrollPane(zam_kli);

    private DefaultListModel<Zamowienie> zamowienia_dlm = new DefaultListModel<>();
    private JList<Zamowienie>zamowienie_kli = new JList<>(zamowienia_dlm);
    private JScrollPane spzamowienie_kli = new JScrollPane(zamowienie_kli);


    private String jdbcUrl = "jdbc:mysql://localhost:3306/ksiegarnia3?serverTimezone=UTC", jdbcUser = "***", jdbcPass = "***";

    //Wyswietlanie list
    private void AktualnaListaKlientów(JList lis) throws SQLException {
        Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
        Statement stmt = conn.createStatement();
        String sql = "SELECT pesel, imie, nazwisko, ur FROM klienci ORDER BY nazwisko";
        ResultSet res = stmt.executeQuery(sql);
        lm1_kli.clear();
        while(res.next()) {
            Klient klient=new Klient();
            klient.setPesel(res.getString(1));
            klient.setImie(res.getString(2));
            klient.setNazwisko(res.getString(3));
            klient.setDate(res.getDate(4));
            lm1_kli.addElement(klient);
        }
        conn.close();
    }


    private void AktualnaListaKsiazek(JList lis) throws SQLException {
        Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM ksiazki ORDER BY tytul";
        ResultSet res = stmt.executeQuery(sql);
        lm1_ks.clear();

        while(res.next()) {
            Ksiazka ksiazka=new Ksiazka();
            ksiazka.setIsbn(res.getString(1));
            ksiazka.setAutor(res.getString(2));
            ksiazka.setTytul(res.getString(3));
            ksiazka.setTyp(TypKsiazki.valueOf(res.getString(4).toUpperCase()));
            ksiazka.setWydawnictwo(res.getString(5));
            ksiazka.setRok(res.getInt(6));
            ksiazka.setCena(res.getDouble(7));
            lm1_ks.addElement(ksiazka);
        }
        conn.close();
    }

    //Wyswietlanie list
    private void AktualnaListaKlientówZam(JList lis) throws SQLException {
        Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
        Statement stmt = conn.createStatement();
        String sql = "SELECT pesel, imie, nazwisko, ur FROM klienci ORDER BY nazwisko";
        ResultSet res = stmt.executeQuery(sql);
        zamdlm_kli.clear();
        while(res.next()) {
            Klient klient=new Klient();
            klient.setPesel(res.getString(1));
            klient.setImie(res.getString(2));
            klient.setNazwisko(res.getString(3));
            klient.setDate(res.getDate(4));
            zamdlm_kli.addElement(klient);
        }
        conn.close();
    }


    private void AktualnaListaKsiazekZam(JList lis) throws SQLException {
        Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM ksiazki ORDER BY tytul";
        ResultSet res = stmt.executeQuery(sql);
        zamdlm_ks.clear();

        while(res.next()) {
            Ksiazka ksiazka=new Ksiazka();
            ksiazka.setIsbn(res.getString(1));
            ksiazka.setAutor(res.getString(2));
            ksiazka.setTytul(res.getString(3));
            ksiazka.setTyp(TypKsiazki.valueOf(res.getString(4).toUpperCase()));
            ksiazka.setWydawnictwo(res.getString(5));
            ksiazka.setRok(res.getInt(6));
            ksiazka.setCena(res.getDouble(7));
            zamdlm_ks.addElement(ksiazka);
        }
        conn.close();
    }

    private void AktualnaListaKlientówZamowien(JList lis) throws SQLException {
        Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM zamowienia";
        ResultSet res = stmt.executeQuery(sql);

        zamowienia_dlm.clear();

        while (res.next()) {
            Zamowienie zamowienie = new Zamowienie();
            zamowienie.setId(res.getInt(1));
            zamowienie.setKlient(res.getString(2));
            zamowienie.setKiedy(res.getDate(3));
            String name = res.getString(4).toUpperCase();
            StatusTyp[] statys = StatusTyp.values();
            Optional<StatusTyp> first = Arrays.stream(statys).filter(t -> t.name().equals(name)).findFirst();
            zamowienie.setStatus(first.get());
            zamowienia_dlm.addElement(zamowienie);
        }
        conn.close();

    }










    ///////////////////////ZAPISYWANIE///////////////////////////////
    private ActionListener akc_zap_kl = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            long pesel = 0;
            try { pesel = Long.parseLong(pole_pesel.getText()); }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Okno.this, "błąd w polu z peselm");
                pole_pesel.setText("");
                pole_pesel.requestFocus();
                return;
            }

            String imie = pole_im.getText();
            String nazwisko = pole_naz.getText();
            String ur = pole_ur.getText();
            if (imie.equals("") || nazwisko.equals("") || ur.equals("")) {
                JOptionPane.showMessageDialog(Okno.this, "nie wypełnione pole z imieniem lub nazwiskiem lub datą urodzenia");
                return;
            }
            try {
                Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
                Statement stmt = conn.createStatement();
                String sql = "INSERT INTO klienci(pesel, imie, nazwisko, ur) VALUES('" + pole_pesel.getText() + "', '" + pole_im.getText() + "', '" + pole_naz.getText() + "', '" + pole_ur.getText() + "')";
                int res = stmt.executeUpdate(sql);
                if (res == 1) {
                    komunikat.setText("OK");
                    AktualnaListaKlientów(l1_kli);
                    l1_kli.repaint();

                    AktualnaListaKlientówZam(zamowienie_kli);
                    zamowienie_kli.repaint();
                }
                else komunikat.setText("Błąd: nie wpisano klienta do bazy");
                conn.close();
            }
            catch(SQLException ex) {
                komunikat.setText("błąd SQL!!!");
            }

        }
    };

    private ActionListener akc_zap_ks = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {

            String isbn = pole_isbn.getText();
            String autor = pole_autor.getText();
            String tytul = pole_tytul.getText();
            String typ = pole_typ.getText();
            String wydawnictwo = pole_wydawnictwo.getText();
            String rok = pole_rok.getText();
            String cena = pole_cena.getText();



            if (isbn.equals("") || autor.equals("") || tytul.equals("") || typ.equals("") || wydawnictwo.equals("") || rok.equals("") || cena.equals("")) {
                JOptionPane.showMessageDialog(Okno.this, "Musisz wypełnić wszystkie pola");
                return;
            }
            try {
                Connection conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
                Statement stmt = conn.createStatement();
                String sql = "INSERT INTO ksiazki(isbn, autor, tytul, typ, wydawnictwo, rok, cena) VALUES('" + pole_isbn.getText()
                        + "', '" + pole_autor.getText()
                        + "', '" + pole_tytul.getText()
                        + "', '" + pole_typ.getText()
                        + "', '" +  pole_wydawnictwo.getText()
                        + "', '" + pole_rok.getText()
                        + "', '" + pole_cena.getText() + "')";
                int res = stmt.executeUpdate(sql);
                if (res == 1) {
                    komunikat.setText("OK");
                    AktualnaListaKsiazek(l1_kli);
                    l1_ks.repaint();


                    AktualnaListaKlientówZam(zamowienie_kli);
                    zamowienie_kli.repaint();

                }
                else komunikat.setText("Błąd: nie wpisano ksiazki do bazy");
                conn.close();
            }
            catch(SQLException ex) {
                komunikat.setText("błąd SQL!!!");
            }

        }
    };


    ////////USUWANIE/////////////////////////

    private ActionListener deleteClient = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            JOptionPane.showMessageDialog(Okno.this, "Użytkownik zostanie usunięty");
            Klient client = lm1_kli.getElementAt(l1_kli.getSelectedIndex());
            String s=client.getPesel();



            Connection conn= null;
            Statement stmt=null;
            String sql=null;

            try {
                conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
                stmt = conn.createStatement();

                String sqlCheck="select count(id) from zamowienia where klient="+s;

                ResultSet resultSet = stmt.executeQuery(sqlCheck);
                int quantity=0;
                if(resultSet.next())
                {
                    quantity=resultSet.getInt(1);

                }

                if(quantity<=0)
                {
                    sql = "DELETE FROM klienci WHERE pesel="+s;
                    stmt.executeUpdate(sql);
                    conn.close();
                    lm1_kli.remove(l1_kli.getSelectedIndex());
                    zamowienie_kli.repaint();


                    AktualnaListaKlientówZam(zamowienie_kli);
                    zamowienie_kli.repaint();

                }else{
                    JOptionPane.showMessageDialog(Okno.this,"Nie możesz usunąć klienta, ponieważ dokonał zamówienia");
                    conn.close();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    };

    private ActionListener deleteBook = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            JOptionPane.showMessageDialog(Okno.this, "Książka zostanie usunięta");
            Ksiazka ksiazka = lm1_ks.getElementAt(l1_ks.getSelectedIndex());
            String s=ksiazka.getIsbn();

            Connection conn= null;
            Statement stmt=null;
            String sql=null;

            try {
                conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
                stmt = conn.createStatement();

                String sqlCheck="select count(zamowienie) from zestawienia where ksiazka="+s;

                int quantity=0;
                ResultSet resultSet = stmt.executeQuery(sqlCheck);
                if(resultSet.next())
                {
                    quantity=resultSet.getInt(1);
                }

                if(quantity<=0){

                    sql = "DELETE FROM ksiazki WHERE isbn="+s;
                    stmt.executeUpdate(sql);
                    conn.close();
                    lm1_ks.remove(l1_ks.getSelectedIndex());
                    zamowienie_kli.repaint();
                    zam_ks.repaint();
                    spz_kli.repaint();

                }else{
                    JOptionPane.showMessageDialog(Okno.this,"Nie możesz usunąć książki, ponieważ jest na liście zamówionych");
                    conn.close();
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    };

///////////////////////////EDYCJA///////////////////////////////////

    private ActionListener editBook = new ActionListener() {
    public void actionPerformed(ActionEvent actionEvent) {

        String nowaCena = JOptionPane.showInputDialog("Wprowadz nowa cene");

        Ksiazka ksiazka=null;
        try {
            ksiazka = lm1_ks.getElementAt(l1_ks.getSelectedIndex());
        }catch(Exception e)
        {
            komunikat.setText("Zaznacz ksiazke!!!");
        }

        if(nowaCena.matches("[0-9]+\\.{1}[0-9]{1,2}")){

            String id=ksiazka.getIsbn();

            Connection conn= null;
            Statement stmt=null;
            String sql=null;

            try {
                conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
                stmt = conn.createStatement();
                sql = "UPDATE ksiazki " +
                        "SET cena="+nowaCena+" " +
                        "WHERE isbn="+id;

                stmt.executeUpdate(sql);

                AktualnaListaKsiazek(l1_kli);
                l1_ks.repaint();
                zamowienie_kli.repaint();

                conn.close();

            } catch (SQLException throwables) {
                komunikat.setText("Nie udalo sie zmienic ceny");
                throwables.printStackTrace();
            }


        }
        else{
            komunikat.setText("Wprowadz poprawna cene!!!");
        }

    }
};


    /////////////ZAMOWIENIA/////////////////////////////////////////////
    private ActionListener akc_zamowienie_zloz = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            JFrame fr = new JFrame();
            JPanel panel = new JPanel();
            panel.setSize(200, 200);
            panel.setLocation(40, 200);
            panel.setVisible(true);
            panel.setLayout(null);
            fr.setVisible(true);
            fr.setSize(400, 400);
            fr.setLocation(40, 200);

            panel.add(spz_ks);
            spz_ks.setSize(250, 210);
            spz_ks.setLocation(40, 20);
            try {
                AktualnaListaKsiazekZam(zam_ks);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


            Klient klient=null;
            try{
                klient = zamdlm_kli.getElementAt(zam_kli.getSelectedIndex());
            }catch(Exception e)
            {
                JOptionPane.showMessageDialog(Okno.this,"Zaznacz klienta");
            }

            JTextField ilosc=new JTextField();

            Klient finalKlient = klient;
            ActionListener dodawanie_ks = new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {

                    String iloscKsiazek = ilosc.getText();
                    if(iloscKsiazek.matches("[0-9]+")) {

                        int ilk=Integer.valueOf(iloscKsiazek);

                        String pesel= finalKlient.getPesel();

                        Ksiazka ksiazka = zamdlm_ks.getElementAt(zam_ks.getSelectedIndex());

                        Connection conn=null;
                        Statement stmt=null;
                        String sql="";

                        LocalDate d=LocalDate.now();

                        try{
                            conn=DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
                            stmt=conn.createStatement();
                            sql="INSERT INTO zamowienia(klient,kiedy) values('"
                                    +pesel
                                    +"','" + d
                                    +"')";
                            stmt.executeUpdate(sql);



                            String sql1="SELECT max(id)from zamowienia";
                            String zamowienieId="";

                            ResultSet resultSet = stmt.executeQuery(sql1);
                            if(resultSet.next()){
                                zamowienieId = resultSet.getString(1);
                            }

                            double cena = ilk*ksiazka.getCena();

                            sql="INSERT INTO zestawienia(zamowienie,ksiazka,ile,cena) values('"
                                    +zamowienieId
                                    +"','" + ksiazka.getIsbn()
                                    +"','" + ilk
                                    +"','" + cena
                                    +"')";
                            stmt.executeUpdate(sql);
                            conn.close();
                            AktualnaListaKlientówZamowien(zamowienie_kli);
                            zamowienie_kli.repaint();

                            komunikat.setText("OK");
                            fr.dispose();
                        }catch(Exception e) {
                            komunikat.setText("Nie udalo sie wykonac akcji");
                            e.printStackTrace();
                        }

                    }



                }
            };

            JButton button = new JButton("dodaj książkę");
            JLabel label=new JLabel();
            ilosc.setText("0");

            button.addActionListener(dodawanie_ks);

            panel.add(button);
            panel.add(ilosc);
            panel.add(label);

            label.setText("ilość");
            label.setLocation(40,250);
            label.setSize(40,25);

            ilosc.setLocation(80,250);
            ilosc.setSize(40, 25);

            button.setSize(200,40);
            button.setLocation(40,280);
            fr.add(panel);
        }

    };

        private ActionListener akc_status_zmien = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                Connection conn= null;
                Statement stmt=null;
                String sql=null;

                Zamowienie zamowienie = zamowienia_dlm.getElementAt(zamowienie_kli.getSelectedIndex());

                try {
                    conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
                    stmt = conn.createStatement();
                    sql = "UPDATE zamowienia " +
                            "SET status="+"'zaplacone'"+
                            "WHERE id="+zamowienie.getId();
                    stmt.executeUpdate(sql);

                    AktualnaListaKlientówZamowien(zamowienie_kli);
                    zamowienie_kli.repaint();

                    conn.close();

                } catch (SQLException throwables) {
                    komunikat.setText("Nie udalo sie zmienic statusu");
                }


            }
        };

    private ActionListener akc_status_zmien_oczek = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {

            Connection conn= null;
            Statement stmt=null;
            String sql=null;

            Zamowienie zamowienie = zamowienia_dlm.getElementAt(zamowienie_kli.getSelectedIndex());

            try {
                conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
                stmt = conn.createStatement();
                sql = "UPDATE zamowienia " +
                        "SET status="+"'oczekuje'"+
                        "WHERE id="+zamowienie.getId();
                stmt.executeUpdate(sql);

                AktualnaListaKlientówZamowien(zamowienie_kli);
                zamowienie_kli.repaint();

                conn.close();

            } catch (SQLException throwables) {
                komunikat.setText("Nie udalo sie zmienic statusu");
            }


        }
    };

    private ActionListener akc_status_zmien_wyslane = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {


            Connection conn= null;
            Statement stmt=null;
            String sql=null;

            Zamowienie zamowienie = zamowienia_dlm.getElementAt(zamowienie_kli.getSelectedIndex());

            try {
                conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
                stmt = conn.createStatement();
                sql = "UPDATE zamowienia " +
                        "SET status="+"'wyslane'"+
                        "WHERE id="+zamowienie.getId();
                stmt.executeUpdate(sql);

                AktualnaListaKlientówZamowien(zamowienie_kli);
                zamowienie_kli.repaint();

                conn.close();

            } catch (SQLException throwables) {
                komunikat.setText("Nie udalo sie zmienic statusu");
            }


        }
    };


    public Okno() throws SQLException {
        super("Zarządzanie księgarnią");
        setSize(800, 340);
        setLocation(100, 100);
        setResizable(false);

        // panel 1 - klient
        p1.setLayout(null);

        JLabel lab1 = new JLabel("pesel:");
        p1.add(lab1);
        lab1.setSize(100, 20);
        lab1.setLocation(40, 40);
        lab1.setHorizontalTextPosition(JLabel.RIGHT);
        p1.add(pole_pesel);
        pole_pesel.setSize(200, 20);
        pole_pesel.setLocation(160, 40);

        JLabel lab2 = new JLabel("imię:");
        p1.add(lab2);
        lab2.setSize(100, 20);
        lab2.setLocation(40, 80);
        lab2.setHorizontalTextPosition(JLabel.RIGHT);
        p1.add(pole_im);
        pole_im.setSize(200, 20);
        pole_im.setLocation(160, 80);

        JLabel lab3 = new JLabel("nazwisko:");
        p1.add(lab3);
        lab3.setSize(100, 20);
        lab3.setLocation(40, 120);
        lab3.setHorizontalTextPosition(JLabel.RIGHT);
        p1.add(pole_naz);
        pole_naz.setSize(200, 20);
        pole_naz.setLocation(160, 120);

        JLabel lab4 = new JLabel("data urodzenia:");
        p1.add(lab4);
        lab4.setSize(100, 20);
        lab4.setLocation(40, 160);
        lab4.setHorizontalTextPosition(JLabel.RIGHT);
        p1.add(pole_ur);
        pole_ur.setSize(200, 20);
        pole_ur.setLocation(160, 160);

        p1.add(przyc_zapisz_kl);
        przyc_zapisz_kl.setSize(200, 20);
        przyc_zapisz_kl.setLocation(160, 200);
        przyc_zapisz_kl.addActionListener(akc_zap_kl);

        p1.add(przyc_usun);
        przyc_usun.setSize(200, 20);
        przyc_usun.setLocation(160, 230);
        przyc_usun.addActionListener(deleteClient);

        p1.add(sp1_kli);
        sp1_kli.setSize(240, 180);
        sp1_kli.setLocation(400, 40);
        AktualnaListaKlientów(l1_kli);

        tp.addTab("klienci", p1);
        tp.addTab("książki", p2);
        tp.addTab("zamówienia", p3);
        getContentPane().add(tp, BorderLayout.CENTER);
        komunikat.setEditable(false);
        getContentPane().add(komunikat, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        //panel - 2
        p2.setLayout(null);

        JLabel isbn = new JLabel("isbn:");
        p2.add(isbn);
        isbn.setSize(100, 20);
        isbn.setLocation(40, 20);
        isbn.setHorizontalTextPosition(JLabel.RIGHT);
        p2.add(pole_isbn);
        pole_isbn.setSize(200, 20);
        pole_isbn.setLocation(160, 20);

        JLabel autor = new JLabel("autor:");
        p2.add(autor);
        autor.setSize(100, 20);
        autor.setLocation(40, 40);
        autor.setHorizontalTextPosition(JLabel.RIGHT);
        p2.add(pole_autor);
        pole_autor.setSize(200, 20);
        pole_autor.setLocation(160, 40);

        JLabel tytul = new JLabel("tytul:");
        p2.add(tytul);
        tytul.setSize(100, 20);
        tytul.setLocation(40, 60);
        tytul.setHorizontalTextPosition(JLabel.RIGHT);
        p2.add(pole_tytul);
        pole_tytul.setSize(200, 20);
        pole_tytul.setLocation(160, 60);

        JLabel typ = new JLabel("typ:");
        p2.add(typ);
        typ.setSize(100, 20);
        typ.setLocation(40, 80);
        typ.setHorizontalTextPosition(JLabel.RIGHT);
        p2.add(pole_typ);
        pole_typ.setSize(200, 20);
        pole_typ.setLocation(160, 80);

        JLabel wydawnictwo = new JLabel("wydawnictwo:");
        p2.add(wydawnictwo);
        wydawnictwo.setSize(100, 20);
        wydawnictwo.setLocation(40, 100);
        wydawnictwo.setHorizontalTextPosition(JLabel.RIGHT);
        p2.add(pole_wydawnictwo);
        pole_wydawnictwo.setSize(200, 20);
        pole_wydawnictwo.setLocation(160, 100);

        JLabel rok = new JLabel("rok:");
        p2.add(rok);
        rok.setSize(100, 20);
        rok.setLocation(40, 120);
        rok.setHorizontalTextPosition(JLabel.RIGHT);
        p2.add(pole_rok);
        pole_rok.setSize(200, 20);
        pole_rok.setLocation(160, 120);

        JLabel cena = new JLabel("cena:");
        p2.add(cena);
        cena.setSize(100, 20);
        cena.setLocation(40, 140);
        cena.setHorizontalTextPosition(JLabel.RIGHT);
        p2.add(pole_cena);
        pole_cena.setSize(200, 20);
        pole_cena.setLocation(160, 140);


        p2.add(przyc_zapisz_ks);
        przyc_zapisz_ks.setSize(200, 20);
        przyc_zapisz_ks.setLocation(160, 180);
        przyc_zapisz_ks.addActionListener(akc_zap_ks);

        p2.add(przyc_usun_ks);
        przyc_usun_ks.setSize(200, 20);
        przyc_usun_ks.setLocation(160, 210);
        przyc_usun_ks.addActionListener(deleteBook);

        p2.add(przyc_zmodyfikuj_ks);
        przyc_zmodyfikuj_ks.setSize(200, 20);
        przyc_zmodyfikuj_ks.setLocation(160, 240);
        przyc_zmodyfikuj_ks.addActionListener(editBook);


        p2.add(sp1_ks);
        sp1_ks.setSize(340, 220);
        sp1_ks.setLocation(400, 20);
        AktualnaListaKsiazek(l1_ks);

        // panel 3
        p3.add(spz_kli);
        p3.setLayout(null);

        spz_kli.setSize(200, 240);
        spz_kli.setLocation(550, 10);
        AktualnaListaKlientówZam(zam_kli);

        p3.add(zloz_zamowienie);
        zloz_zamowienie.setSize(200, 20);
        zloz_zamowienie.setLocation(120, 210);
        zloz_zamowienie.addActionListener(akc_zamowienie_zloz);

        p3.add(zmien_status);
        zmien_status.setSize(140, 20);
        zmien_status.setLocation(10, 240);
        zmien_status.addActionListener(akc_status_zmien);

        p3.add(zmien_status_wyslane);
        zmien_status_wyslane.setSize(140, 20);
        zmien_status_wyslane.setLocation(150, 240);
        zmien_status_wyslane.addActionListener(akc_status_zmien_wyslane);

        p3.add(zmien_status_oczek);
        zmien_status_oczek.setSize(140, 20);
        zmien_status_oczek.setLocation(290, 240);
        zmien_status_oczek.addActionListener(akc_status_zmien_oczek);



        p3.add(spzamowienie_kli);
        spzamowienie_kli.setSize(520,200);
        spzamowienie_kli.setLocation(20,10);
        AktualnaListaKlientówZamowien(zamowienie_kli);

    }

}

public class Ksiegarnia {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        new Okno();
    }
}
