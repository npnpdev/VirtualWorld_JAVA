import java.awt.Color;

public class Owca extends Zwierze {
    public Owca(int x, int y, int wiek, Swiat swiat) {
        super(4, 4, x, y, wiek, swiat);
        symbol = 'O';
        color = Color.LIGHT_GRAY;
    }

    @Override
    public void akcja() {
        wyswietlInformacje("Owca");
        super.akcja();
    }

    @Override
    public int kolizja(Organizm organizm) {
        return super.kolizja(organizm);
    }

    @Override
    public Owca rozmnoz(int x, int y) {
        return new Owca(x, y, 0, swiat);
    }
}
