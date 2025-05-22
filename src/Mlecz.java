import java.awt.Color;

public class Mlecz extends Roslina {
    private static final int iloscProb = 3;

    public Mlecz(int x, int y, int wiek, Swiat swiat) {
        super(0, 0, x, y, wiek, swiat);
        symbol = 'M';
        color = Color.yellow;
    }

    @Override
    public void akcja() {
        wyswietlInformacje("Mlecz");
        for (int i = 0; i < iloscProb; i++) {
            super.akcja();
        }
    }

    @Override
    public int kolizja(Organizm organizm) {
        return super.kolizja(organizm);
    }

    @Override
    public Mlecz rozmnoz(int x, int y) {
        return new Mlecz(x, y, 0, swiat);
    }
}
