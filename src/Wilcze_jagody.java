import java.awt.Color;

public class Wilcze_jagody extends Roslina {
    public Wilcze_jagody(int x, int y, int wiek, Swiat swiat) {
        super(99, 0, x, y, wiek, swiat);
        symbol = 'J';
        color = Color.blue;
    }

    @Override
    public void akcja() {
    	wyswietlInformacje("Wilcze Jagody");
        super.akcja();
    }

    @Override
    public int kolizja(Organizm organizm) {
        if (organizm.getInicjatywa() != 0) {
            swiat.usunOrganizm(organizm.getX(), organizm.getY());
            swiat.usunOrganizm(this.getX(), this.getY());

            wyswietlInformacje(" zjadł wilcze jagody i zginął!");
            return 2;
        }

        return super.kolizja(organizm);
    }

    @Override
    public Wilcze_jagody rozmnoz(int x, int y) {
        return new Wilcze_jagody(x, y, 0, swiat);
    }
}
