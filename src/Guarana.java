import java.awt.Color;

public class Guarana extends Roslina {
    private static final int mocGuarany = 3; // Ilość punktów siły, jaką dodaje guarana

    public Guarana(int x, int y, int wiek, Swiat swiat) {
        super(0, 0, x, y, wiek, swiat);
        symbol = 'G';
        color = Color.red;
    }

    @Override
    public void akcja() {
        wyswietlInformacje("Guarana");
        super.akcja();
    }

    @Override
    public int kolizja(Organizm organizm) {
        if (organizm.getInicjatywa() != 0) {
            organizm.dodajSile(mocGuarany); // Zwiększenie siły organizmu o 3

            // Usunięcie zjedzonej guarany z planszy
            swiat.usunOrganizm(this.getX(), this.getY());

            wyswietlInformacje("Zjadła guaranę i zwiększyła swoją siłę o " + mocGuarany);
            return 1; // Zwracamy 1, aby metoda kolizja dokonała przemieszczenia
        }

        return 0;
    }

    @Override
    public Guarana rozmnoz(int x, int y) {
        return new Guarana(x, y, 0, swiat);
    }
}
