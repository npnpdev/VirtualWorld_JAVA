import java.util.Random;

public abstract class Zwierze extends Organizm {
    public Zwierze(int sila, int inicjatywa, int x, int y, int wiek, Swiat swiat) {
        super(sila, inicjatywa, x, y, wiek, swiat);
    }

    @Override
    public void akcja() {
        Random rand = new Random();
        int dx, dy;
        int wynikKolizji = 0;
        int new_x = 0, new_y = 0;

        if (swiat.getType().equals("krata")) {
            do {
                dx = rand.nextInt(3) - 1; // -1, 0, 1
                dy = rand.nextInt(3) - 1; // -1, 0, 1

                new_x = getX() + dx;
                new_y = getY() + dy;

            } while ((new_x == getX() && new_y == getY()) || 
                     (new_x < 0 || new_x >= swiat.getRows()) || 
                     (new_y < 0 || new_y >= swiat.getCols()));
        } else if (swiat.getType().equals("hex")) {
            int[][] hexMoves = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, -1}
            };

            int randIdx = rand.nextInt(hexMoves.length);
            dx = hexMoves[randIdx][0];
            dy = hexMoves[randIdx][1];

            new_x = getX() + dx;
            new_y = getY() + dy;

            // Sprawdzenie, czy nowe pole znajduje się w granicach świata dla hexa
            while (new_x < 0 || new_x >= swiat.getRows() || new_y < 0 || new_y >= swiat.getCols()) {
                randIdx = rand.nextInt(hexMoves.length);
                dx = hexMoves[randIdx][0];
                dy = hexMoves[randIdx][1];
                new_x = getX() + dx;
                new_y = getY() + dy;
            }
        }

        if (swiat.organizmNaPozycji(new_x, new_y)) {
            Organizm organizm = swiat.getOrganizm(new_x, new_y);
            wynikKolizji = organizm.kolizja(this);
        }
        
        if (wynikKolizji != 2 && getX() != -1) {
            int stary_x = getX();
            int stary_y = getY();
            swiat.usunOrganizm(stary_x, stary_y);
            setX(new_x);
            setY(new_y);
            swiat.dodajOrganizm(this, new_x, new_y);
            wyswietlInformacje(" przemiescil sie z (" + stary_x + ", " + stary_y + ") na (" + new_x + ", " + new_y + ").");
        }
    }

    @Override
    public int kolizja(Organizm organizm) {
        if (organizm.getSymbol() == this.getSymbol()) {
            int[] nowaPozycja = {getX(), getY()};
            boolean znalezionoPustePole = swiat.znajdzPustePoleObok(nowaPozycja);
            if (znalezionoPustePole) {
                Zwierze noweZwierze = rozmnoz(nowaPozycja[0], nowaPozycja[1]);
                swiat.dodajOrganizm(noweZwierze, nowaPozycja[0], nowaPozycja[1]);
                wyswietlInformacje(" - nowe zwierze tego samego gatunku narodzilo sie na pozycji (" + nowaPozycja[0] + ", " + nowaPozycja[1] + ").");
            }
            return 2;
        } else {
            if (organizm.getSila() >= this.getSila()) {
                swiat.usunOrganizm(this.getX(), this.getY());
                this.setX(-1);
                wyswietlInformacje(" Zwierze o sile " + this.getSila() + " zostalo zabite przez organizm na pozycji (" + organizm.getX() + ", " + organizm.getY() + ").");
                return 1;
            } else {
                swiat.usunOrganizm(organizm.getX(), organizm.getY());
                organizm.setX(-1);
                wyswietlInformacje(" Zwierze o sile " + organizm.getSila() + " zostalo zabite przez organizm na pozycji (" + this.getX() + ", " + this.getY() + ").");
                return 1;
            }
        }
    }

    @Override
    public String rysowanie() {
        String colorString = String.format("%d,%d,%d", color.getRed(), color.getGreen(), color.getBlue());
        return colorString + " " + symbol;
    }

    @Override
    public abstract Zwierze rozmnoz(int x, int y);
}