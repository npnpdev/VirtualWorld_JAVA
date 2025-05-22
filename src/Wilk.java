import java.awt.Color;

public class Wilk extends Zwierze {
    public Wilk(int x, int y, int wiek, Swiat swiat) {
        super(9, 5, x, y, wiek, swiat);
        symbol = 'W';
        color = Color.GRAY;
    }

    @Override
    public void akcja() {
    	wyswietlInformacje("Wilk");
        super.akcja();
    }

    @Override
    public int kolizja(Organizm organizm) {
        return super.kolizja(organizm);
    }

    @Override
    public Wilk rozmnoz(int x, int y) {
        return new Wilk(x, y, 0, swiat);
    }
}
