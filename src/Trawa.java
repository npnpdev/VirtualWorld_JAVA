import java.awt.Color;

public class Trawa extends Roslina {
    public Trawa(int x, int y, int wiek, Swiat swiat) {
        super(0, 0, x, y, wiek, swiat);
        symbol = 'T';
        color = Color.green;
    }

    @Override
    public void akcja() {
    	wyswietlInformacje("Trawa");
        super.akcja();
    }

    @Override
    public int kolizja(Organizm organizm) {
        return super.kolizja(organizm);
    }

    @Override
    public Trawa rozmnoz(int x, int y) {
        return new Trawa(x, y, 0, swiat);
    }
}
