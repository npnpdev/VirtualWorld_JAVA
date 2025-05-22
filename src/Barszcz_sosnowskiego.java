import java.awt.Color;

public class Barszcz_sosnowskiego extends Roslina {
    public Barszcz_sosnowskiego(int x, int y, int wiek, Swiat swiat) {
        super(10, 0, x, y, wiek, swiat);
        symbol = 'B';
        color = Color.pink;
    }
    
    @Override
    public void akcja() {
        // Sprawdzenie typu świata
        if (swiat.getType().equals("krata")) {
            // Iteracja po otaczających polach i zabijanie zwierząt
            for (int i = getX() - 1; i <= getX() + 1; ++i) {
                for (int j = getY() - 1; j <= getY() + 1; ++j) {
                    // Sprawdzenie, czy badane pole znajduje się w granicach planszy i czy nie jest to pole, na którym stoi barszcz
                    if (i >= 0 && i < swiat.getCols() && j >= 0 && j < swiat.getRows() && !(i == getX() && j == getY())) {
                        if (swiat.organizmNaPozycji(i, j)) {
                            Organizm organizm = swiat.getOrganizm(i, j);
                            // Zabija tylko zwierzęta, pomijając rośliny
                            if (organizm instanceof Zwierze) {
                                swiat.usunOrganizm(i, j);
                                wyswietlInformacje("Barszcz sosnowskiego zabil organizm na pozycji (" + i + ", " + j + ").");
                            }
                        }
                    }
                }
            }
        } else if (swiat.getType().equals("hex")) {
            // Definiowanie sześciu kierunków dla hexa
            int[][] hexMoves = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, -1}
            };

            // Iteracja po otaczających polach i zabijanie zwierząt
            for (int[] move : hexMoves) {
                int new_x = getX() + move[0];
                int new_y = getY() + move[1];

                // Sprawdzenie, czy badane pole znajduje się w granicach planszy
                if (new_x >= 0 && new_x < swiat.getCols() && new_y >= 0 && new_y < swiat.getRows()) {
                    if (swiat.organizmNaPozycji(new_x, new_y)) {
                        Organizm organizm = swiat.getOrganizm(new_x, new_y);
                        // Zabija tylko zwierzęta, pomijając rośliny
                        if (organizm instanceof Zwierze) {
                            swiat.usunOrganizm(new_x, new_y);
                            wyswietlInformacje("Barszcz sosnowskiego zabil organizm na pozycji (" + new_x + ", " + new_y + ").");
                        }
                    }
                }
            }
        }
        super.akcja();
    }

    @Override
    public int kolizja(Organizm organizm) {
        if (organizm.getInicjatywa() != 0) {
            swiat.usunOrganizm(organizm.getX(), organizm.getY());
            swiat.usunOrganizm(getX(), getY());
            wyswietlInformacje(" Zjadl barszcz sosnowskiego i zginal!");
            return 2; // Zwracamy 2, aby metoda nie dokonywala przemieszczenia
        }
        return 0;
    }

    @Override
    public Barszcz_sosnowskiego  rozmnoz(int x, int y) {
        return new Barszcz_sosnowskiego(x, y, 0, swiat);
    }
}
