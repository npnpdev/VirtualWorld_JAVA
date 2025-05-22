import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Antylopa extends Zwierze {
	    public Antylopa(int x, int y, int wiek, Swiat swiat) {
        super(4, 4, x, y, wiek, swiat);
        symbol = 'A';
        color = Color.CYAN;
    }
    
	private void sprawdzMozliweRuchy(ArrayList<int[]> mozliweRuchy, int stary_x, int stary_y) {
		if (swiat.getType().equals("krata")) {
            for (int dx = -2; dx <= 2; dx += 2) {
                for (int dy = -2; dy <= 2; dy += 2) {
                    if (Math.abs(dx) != Math.abs(dy)) {
                        int new_x = stary_x + dx;
                        int new_y = stary_y + dy;
                        if (new_x >= 0 && new_x < swiat.getRows() && new_y >= 0 && new_y < swiat.getCols()) {
                            mozliweRuchy.add(new int[]{dx, dy});
                        }
                    }
                }
            }
        } else if (swiat.getType().equals("hex")) {
            int[][] hexMoves = {
                {-2, 0}, {2, 0}, {0, -2}, {0, 2}, 
                {-2, 2}, {2, -2}
            };
            for (int[] move : hexMoves) {
                int new_x = stary_x + move[0];
                int new_y = stary_y + move[1];
                if (new_x >= 0 && new_x < swiat.getRows() && new_y >= 0 && new_y < swiat.getCols()) {
                    mozliweRuchy.add(move);
                }
            }
        }
	}
	    
    @Override
    public void akcja() {
        // Stare współrzędne antylopy
        int stary_x = getX();
        int stary_y = getY();

        // Lista możliwych kierunków ruchu
        ArrayList<int[]> mozliweRuchy = new ArrayList<>();

        // Szukanie możliwych kierunków ruchu
        sprawdzMozliweRuchy(mozliweRuchy, stary_x, stary_y);

        // Jeśli nie ma możliwych ruchów, antylopa pozostaje na miejscu
        if (mozliweRuchy.isEmpty()) {
            wyswietlInformacje("Antylopa zostala na swoim miejscu, brak dostepnych pol do przemieszczenia.");
            return;
        }

        // Inicjalizacja losowego ruchu
        Random rand = new Random();
        int losowyIndeks = rand.nextInt(mozliweRuchy.size());
        int[] wylosowanyKierunek = mozliweRuchy.get(losowyIndeks);

        // Próba przesunięcia na nowe pole
        int new_x = stary_x + wylosowanyKierunek[0];
        int new_y = stary_y + wylosowanyKierunek[1];

        if (swiat.organizmNaPozycji(new_x, new_y)) {
            Organizm organizm = swiat.getOrganizm(new_x, new_y);
            if (organizm.kolizja(this) == 2) {
                return;
            }
        }

        if (getX() != -1) {
            swiat.usunOrganizm(getX(), getY());
            // Aktualizacja współrzędnych antylopy
            setX(new_x);
            setY(new_y);
            swiat.dodajOrganizm(this, new_x, new_y);
            wyswietlInformacje("Antylopa przemiescila sie z (" + stary_x + ", " + stary_y + ") na (" + new_x + ", " + new_y + ").");
        }
    }

    @Override
    public int kolizja(Organizm organizm) {    	
        // 50% szans na ucieczkę przed walką
        Random rand = new Random();
        if (rand.nextInt(2) == 0 && organizm.getSymbol() != this.symbol) {
            // Przesunięcie antylopy na niezajęte sąsiednie pole
            przesunNaNiezajeteSasiedniePole();
            wyswietlInformacje("Antylopa uciekla przed walka!");
            return 1;
        } else {
            // W przeciwnym razie zachowanie standardowe podczas kolizji (walka)
            return super.kolizja(organizm);
        }
    }

    @Override
    public Antylopa rozmnoz(int x, int y) {
        return new Antylopa(x, y, 0, swiat);
    }

    private void przesunNaNiezajeteSasiedniePole() {
        // Definiowanie możliwych ruchów w zależności od typu świata
        int[][] possibleMoves;

        if (swiat.getType().equals("krata")) {
            possibleMoves = new int[][] {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1} 
            };
        } else {
            possibleMoves = new int[][] {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, 1}, {1, -1} // Dodatkowe kierunki dla hex
            };
        }

        // Iteracja przez możliwe ruchy
        for (int[] move : possibleMoves) {
            int new_x = getX() + move[0];
            int new_y = getY() + move[1];
            // Sprawdzanie, czy nowe pole jest w granicach świata i nie jest zajęte
            if (new_x >= 0 && new_x < swiat.getRows() && new_y >= 0 && new_y < swiat.getCols() && !swiat.organizmNaPozycji(new_x, new_y)) {
                swiat.usunOrganizm(getX(), getY());
                setX(new_x);
                setY(new_y);
                swiat.dodajOrganizm(this, new_x, new_y);
                return;
            }
        }
    }
}