import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Czlowiek extends Zwierze {
    private static final int CZAS_TRWANIA_UMIEJETNOSCI = 5; // Ilość tur
    private static final String KLAWISZ_UMIEJETNOSCI = "X"; // Klawisz, którym aktywujemy umiejętność człowieka

    private boolean umiejetnoscAktywna;
    private int licznikTur;
    private JFrame frame;
    private JLabel infoLabel;

    public Czlowiek(int x, int y, int wiek, Swiat swiat) {
        super(5, 4, x, y, wiek, swiat);
        symbol = 'C';
        color = Color.black;
        umiejetnoscAktywna = false;
        licznikTur = 0;
    }

    private void initGUI() {
        frame = new JFrame("Sterowanie człowiekiem");
        frame.setSize(0, 0);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Blokujemy ręczne zamykanie

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        infoLabel = new JLabel(" ");
        panel.add(infoLabel, BorderLayout.SOUTH);

        frame.add(panel);

        // Definiowanie akcji dla klawiszy
        Action moveUpAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMove(-1, 0);
            }
        };

        Action moveDownAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMove(1, 0);
            }
        };

        Action moveLeftAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMove(0, -1);
            }
        };

        Action moveRightAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMove(0, 1);
            }
        };

        Action moveUpRightAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMove(-1, 1);
            }
        };

        Action moveDownLeftAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMove(1, -1);
            }
        };

        Action abilityAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAbility();
            }
        };

        // Mapowanie klawiszy do akcji
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("W"), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke("S"), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke("A"), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke("D"), "moveRight");
        inputMap.put(KeyStroke.getKeyStroke("E"), "moveUpRight");
        inputMap.put(KeyStroke.getKeyStroke("Z"), "moveDownLeft");
        inputMap.put(KeyStroke.getKeyStroke(KLAWISZ_UMIEJETNOSCI), "useAbility");

        actionMap.put("moveUp", moveUpAction);
        actionMap.put("moveDown", moveDownAction);
        actionMap.put("moveLeft", moveLeftAction);
        actionMap.put("moveRight", moveRightAction);
        actionMap.put("moveUpRight", moveUpRightAction);
        actionMap.put("moveDownLeft", moveDownLeftAction);
        actionMap.put("useAbility", abilityAction);

        // Ustawienie okna jako niewidoczne
        frame.setUndecorated(true); // Usuwamy ramkę okna
        frame.setOpacity(0.0f); // Ustawiamy okno na przezroczyste
        frame.setVisible(true); // Okno musi być widoczne, aby odbierać zdarzenia klawiatury
    }

    private void handleMove(int dx, int dy) {
        if(przesunCzlowieka(dx, dy)) {
        	frame.dispose(); // Zamykamy okno po wykonaniu ruchu
        }
        else {
            JOptionPane.showMessageDialog(frame, "Nie możliwe przemieszczenie, proszę wybrać prawidłowy ruch!", "Błąd przemieszczenia", JOptionPane.ERROR_MESSAGE);
        } 
    }

    private void handleAbility() {
        if (!umiejetnoscAktywna && licznikTur == 0) {
            umiejetnoscAktywna = true;
            licznikTur = CZAS_TRWANIA_UMIEJETNOSCI;
            JOptionPane.showMessageDialog(frame, "Umiejętność specjalna człowieka została aktywowana!");
            // Nadal oczekujemy na wybór kierunku, więc nie zamykamy okna
        }
        else {
            JOptionPane.showMessageDialog(frame, "Umiejetnosc jest juz aktywna!", "Ponowna aktywacja umiejętności", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean przesunCzlowieka(int dx, int dy) {
        int noweX = getX() + dx;
        int noweY = getY() + dy;

        if (noweX >= 0 && noweX < swiat.getCols() && noweY >= 0 && noweY < swiat.getRows()) {
            Organizm organizmNaPolu = swiat.getOrganizm(noweX, noweY);

            if (organizmNaPolu == null || (organizmNaPolu.kolizja(this) != 2 && getX() != -1)) {
                swiat.usunOrganizm(getX(), getY());
                swiat.dodajOrganizm(this, noweX, noweY);
                setX(noweX);
                setY(noweY);
            }
            return true;
        }
        else {
        	return false;
        }
    }

    @Override
    public void akcja() {
        if (umiejetnoscAktywna) {
            if (licznikTur > 0) {
                wyswietlInformacje("Umiejętność jest jeszcze aktywna przez " + licznikTur + " tur");
                --licznikTur;
            } else {
                umiejetnoscAktywna = false;
                licznikTur = -CZAS_TRWANIA_UMIEJETNOSCI;
            }
        } else if (licznikTur == 0) {
            wyswietlInformacje("Umiejętność specjalna człowieka jest gotowa do użycia!");
        } else {
            licznikTur++;
            wyswietlInformacje("Umiejętność specjalna człowieka odnawia się jeszcze przez " + (-licznikTur) + " tur");
        }

        // Po zakończeniu działań uruchamiamy panel sterowania dla nowej tury
        SwingUtilities.invokeLater(this::initGUI);
    }

    @Override
    public int kolizja(Organizm organizm) {
        if (umiejetnoscAktywna && organizm instanceof Zwierze) {
            int[] nowaPozycja = {organizm.getX(), organizm.getY()};

            if (swiat.znajdzPustePoleObok(nowaPozycja)) {
                swiat.usunOrganizm(organizm.getX(), organizm.getY());
                organizm.setX(nowaPozycja[0]);
                organizm.setY(nowaPozycja[1]);
                swiat.dodajOrganizm(organizm, nowaPozycja[0], nowaPozycja[1]);

                wyswietlInformacje("Próbował zaatakować człowieka, jednak umiejętność sprawiła, że został przeniesiony na pole (" + nowaPozycja[0] + "," + nowaPozycja[1] + ").");
                return 2;
            } else {
            	wyswietlInformacje("Nie znaleziono nowego pustego pola!");
            }
        }
        return super.kolizja(organizm);
    }


    @Override
    public Czlowiek rozmnoz(int x, int y) {
        return new Czlowiek(x, y, 0, swiat);
    }

    public int getCzasUmiejetnosc() {
        return licznikTur;
    }

    public void setCzasUmiejetnosci(int licznik) {
        licznikTur = licznik;
    }

    public void setUmiejetnosc(boolean czyAktywna) {
        umiejetnoscAktywna = czyAktywna;
    }
}
