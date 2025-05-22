import java.awt.Color;
import java.util.Random;

public class Lis extends Zwierze {
    public Lis(int x, int y, int wiek, Swiat swiat) {
        super(3, 7, x, y, wiek, swiat);
        symbol = 'L';
        color = Color.orange;
    }

    @Override
    public void akcja() {
        int dx, dy;
        int new_x, new_y;
        int wynikKolizji = 0;

        Random rand = new Random();

        if (swiat.getType().equals("krata")) {
            // Ruch dla świata typu krata
            do {
                dx = rand.nextInt(3) - 1; // -1, 0, 1
                dy = rand.nextInt(3) - 1; // -1, 0, 1

                // Obliczamy nowe współrzędne
                new_x = getX() + dx;
                new_y = getY() + dy;

                // Sprawdzenie, czy nowe pole jest w granicach świata
            } while ((new_x == getX() && new_y == getY()) || // Nowe współrzędne są równe obecnym
                     (new_x < 0 || new_x >= swiat.getRows()) || // Nowe współrzędne wykraczają poza zakres wierszy
                     (new_y < 0 || new_y >= swiat.getCols())); // Nowe współrzędne wykraczają poza zakres kolumn
        } else {
            // Ruch dla świata typu hex
            int[][] hexMoves = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, -1}
            };

            do {
                int moveIndex = rand.nextInt(hexMoves.length);
                dx = hexMoves[moveIndex][0];
                dy = hexMoves[moveIndex][1];

                // Oblicz nowe współrzędne
                new_x = getX() + dx;
                new_y = getY() + dy;

                // Sprawdzenie, czy nowe pole jest w granicach świata
            } while ((new_x < 0 || new_x >= swiat.getRows()) || // Nowe współrzędne wykraczają poza zakres wierszy
                     (new_y < 0 || new_y >= swiat.getCols())); // Nowe współrzędne wykraczają poza zakres kolumn
        }

        // Sprawdzenie, czy na nowym polu znajduje się inny organizm
        if (swiat.organizmNaPozycji(new_x, new_y)) {
            // Pobranie organizmu znajdującego się na nowym polu
            Organizm organizm = swiat.getOrganizm(new_x, new_y);

            if (organizm.getSila() > this.getSila()) {
                wyswietlInformacje("Lis skorzystał z dobrego węchu!");
                return;
            } else {
                wynikKolizji = organizm.kolizja(this);
            }
        }
        if (wynikKolizji == 2) {
            return;
        }

        if (getX() != -1) {
            int stary_x = getX();
            int stary_y = getY();

            swiat.usunOrganizm(stary_x, stary_y);

            setX(new_x);
            setY(new_y);

            swiat.dodajOrganizm(this, new_x, new_y);

            wyswietlInformacje("Lis przemieścił się z (" + stary_x + ", " + stary_y + ") na (" + new_x + ", " + new_y + ").");
        }
    }

    @Override
    public int kolizja(Organizm organizm) {
        return super.kolizja(organizm);
    }

    @Override
    public Lis rozmnoz(int x, int y) {
        return new Lis(x, y, 0, swiat);
    }
}