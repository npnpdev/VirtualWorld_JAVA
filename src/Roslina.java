public abstract class Roslina extends Organizm {
    private static final int SZANSA_ROZMNOZENIA = 10; // Wartość procentowa

    public Roslina(int sila, int inicjatywa, int x, int y, int wiek, Swiat swiat) {
        super(sila, inicjatywa, x, y, wiek, swiat);
    }

    @Override
    public void akcja() {
    	String komunikat = " ";
        if ((int)(Math.random() * 100) < SZANSA_ROZMNOZENIA) {
            // Znalezienie pustego pola obok aktualnej pozycji
        	int[] nowaPozycja = {getX(), getY()};
            if (swiat.znajdzPustePoleObok(nowaPozycja)) {
                // Dodanie nowej rośliny na znalezionym pustym polu
                Roslina nowaRoslina = rozmnoz(nowaPozycja[0], nowaPozycja[1]);
                swiat.dodajOrganizm(nowaRoslina, nowaPozycja[0], nowaPozycja[1]);

                komunikat += " zostala rozmnozona";
            }
        } else {
            komunikat += " w tej rundzie sie nie rozmnozyla";
        }
        wyswietlInformacje(komunikat);
    }

    @Override
    public int kolizja(Organizm organizm) {
        Zwierze zwierze = organizm instanceof Zwierze ? (Zwierze)organizm : null;
        
        // Jeśli organizm jest zwierzęciem, wywołujemy jego metodę kolizja, w przeciwnym razie zwracamy 0 (roślina)
        if (zwierze != null) {
            return zwierze.kolizja(this);
        } else {
            return 0;
        }
    }

    @Override
    public String rysowanie() {
        String colorString = String.format("%d,%d,%d", color.getRed(), color.getGreen(), color.getBlue());
        return colorString + " " + symbol;
    }
    
    public abstract Roslina rozmnoz(int x, int y);
}
