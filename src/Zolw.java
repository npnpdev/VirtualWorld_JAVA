import java.awt.Color;
import java.util.Random;

public class Zolw extends Zwierze {
    private static final int SZANSA_NA_ZMIANE_POZYCJI = 25; // W procentach

    public Zolw(int x, int y, int wiek, Swiat swiat) {
        super(2, 1, x, y, wiek, swiat);
        symbol = 'Z';
        color = Color.MAGENTA;
    }

    @Override
    public void akcja() {
        if (new Random().nextInt(100) < SZANSA_NA_ZMIANE_POZYCJI) {
            super.akcja();
        } else {
            wyswietlInformacje("Zolw nie przemiescil sie w tej turze");
        }
    }

    @Override
    public int kolizja(Organizm organizm) {
        if (organizm != null && organizm.getSila() < 5 && organizm.getSymbol() != this.symbol) {
            // Napastnik ma mniejszą siłę niż 5, więc musi wrócić na swoje poprzednie pole
            int poprzedni_x = organizm.getX();
            int poprzedni_y = organizm.getY();

            swiat.usunOrganizm(organizm.getX(), organizm.getY());

            // Przywrócenie napastnika na jego poprzednie pole
            swiat.dodajOrganizm(organizm, poprzedni_x, poprzedni_y);

            wyswietlInformacje(" zostal odepchniety przez zolwia na jego poprzednie pole.");
            return 2;
        } else {
            return super.kolizja(organizm);
        }
    }

    @Override
    public Zolw rozmnoz(int x, int y) {
        return new Zolw(x, y, 0, swiat);
    }
}
