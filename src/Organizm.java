import java.awt.Color;

import javax.swing.JTextArea;

public abstract class Organizm {
    protected int sila;
    protected int inicjatywa;
    protected int wiek;
    protected int x;
    protected int y;
    protected char symbol;
    protected Color color;
    protected Swiat swiat; // Referencja do świata, w którym znajduje się organizm

    public Organizm(int sila, int inicjatywa, int x, int y, int wiek, Swiat swiat) {
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.x = x;
        this.y = y;
        this.wiek = wiek;
        this.swiat = swiat;
    }

    public abstract void akcja(); // Metoda abstrakcyjna, implementacja zależy od konkretnych organizmów
    public abstract int kolizja(Organizm organizm); // Metoda abstrakcyjna, implementacja zależy od konkretnych organizmów
    public abstract String rysowanie(); // Metoda abstrakcyjna, implementacja zależy od konkretnych organizmów
    public abstract Organizm rozmnoz(int x, int y);

    // Gettery
    public int getSila() {
        return sila;
    }

    public int getInicjatywa() {
        return inicjatywa;
    }

    public int getWiek() {
        return wiek;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getSymbol() {
        return symbol;
    }

    public void inkrementujWiek() {
        wiek++;
    }

    public void dodajSile(int dodaj) {
        sila += dodaj;
    }

    // Settery
    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }
    
    // Graficzna reprezentacja informacji
    public void wyswietlInformacje(String komunikat) {
        if (swiat != null) {
            Gra gra = swiat.getGra();
            if (gra != null) {
                JTextArea textArea = gra.getTextArea();
                textArea.append(komunikat + "\n");
            }
        }
    }
}
