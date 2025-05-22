import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class Gra {
    private Swiat swiat;
    private JFrame frame;
    private JTextArea textArea;
    private JPanel worldPanel; // Panel do wyświetlania świata

    public Gra(int rows, int cols, String swiatType) {
    	if(swiatType=="krata") {
    		swiat = new swiatKratowy(rows,cols, this);
    	}
    	else {
    		swiat = new swiatHex(rows,cols, this);
    	}
    	
    	swiat.setType(swiatType);
        initUI(rows, cols, swiatType);

        // Inicjalizacja organizmów
        double zageszczenieOrganizmow = 4;
        int losowyX, losowyY;
        for (int i = 0; i < rows * cols / zageszczenieOrganizmow; i++) {
            do {
                losowyX = (int) (Math.random() * rows);
                losowyY = (int) (Math.random() * cols);
            } while (swiat.organizmNaPozycji(losowyX, losowyY) || (losowyX == 0 && losowyY == 0));

            switch ((int) (Math.random() * 10)) {
                case 0:
                    swiat.dodajOrganizm(new Wilk(losowyX, losowyY, 0, swiat), losowyX, losowyY);
                    break;
                case 1:
                    swiat.dodajOrganizm(new Owca(losowyX, losowyY, 0, swiat), losowyX, losowyY);
                    break;
                case 2:
                    swiat.dodajOrganizm(new Lis(losowyX, losowyY, 0, swiat), losowyX, losowyY);
                    break;
                case 3:
                    swiat.dodajOrganizm(new Zolw(losowyX, losowyY, 0, swiat), losowyX, losowyY);
                    break;
                case 4:
                    swiat.dodajOrganizm(new Antylopa(losowyX, losowyY, 0, swiat), losowyX, losowyY);
                    break;
                case 5:
                    swiat.dodajOrganizm(new Trawa(losowyX, losowyY, 0, swiat), losowyX, losowyY);
                    break;
                case 6:
                    swiat.dodajOrganizm(new Mlecz(losowyX, losowyY, 0, swiat), losowyX, losowyY);
                    break;
                case 7:
                    swiat.dodajOrganizm(new Guarana(losowyX, losowyY, 0, swiat), losowyX, losowyY);
                    break;
                case 8:
                    swiat.dodajOrganizm(new Wilcze_jagody(losowyX, losowyY, 0, swiat), losowyX, losowyY);
                    break;
                case 9:
                    swiat.dodajOrganizm(new Barszcz_sosnowskiego(losowyX, losowyY, 0, swiat), losowyX, losowyY);
                    break;
                default:
                    break;
            }
        }

        // Czlowiek
        swiat.dodajOrganizm(new Czlowiek(0, 0, 0, swiat), 0, 0);
    }

    private void initUI(int rows, int cols, String swiatType) {
        frame = new JFrame("Symulator wirtualnego świata - Igor Tomkowicz s194103");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Ustawienie okna na pełny ekran
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        worldPanel = new JPanel(); // Tworzymy panel do wyświetlania świata
        frame.add(worldPanel, BorderLayout.NORTH); // Dodajemy panel do ramki

        JPanel panel = new JPanel();
        JButton saveButton = new JButton("Zapisz");
        JButton loadButton = new JButton("Wczytaj");
        JButton nextTurnButton = new JButton("Następna Tura");

        saveButton.addActionListener(e -> saveGame(swiatType));
        loadButton.addActionListener(e -> loadGame(swiatType));
        nextTurnButton.addActionListener(e -> nextTurn());

        panel.add(saveButton);
        panel.add(loadButton);
        panel.add(nextTurnButton);

        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);

        updateDisplay();
    }

    private void saveGame(String swiatType) {
        String nazwaPliku = JOptionPane.showInputDialog(frame, "Wpisz nazwę pliku:");
        if (nazwaPliku != null && !nazwaPliku.trim().isEmpty()) {
            zapiszStanDoPliku(nazwaPliku, swiatType);
        }
    }

    private void loadGame(String swiatType) {
        String nazwaPliku = JOptionPane.showInputDialog(frame, "Podaj nazwę pliku do wczytania:");
        if (nazwaPliku != null && !nazwaPliku.trim().isEmpty()) {
            wczytajStanZPliku(nazwaPliku, swiatType);
            updateDisplay();
        }
    }

    private void nextTurn() {
    	textArea.setText("");  // Czyszczenie okienka z komunikatem
    	swiat.wykonajTure(textArea);
    	updateDisplay();
    }

    private void updateDisplay() {
    	worldPanel.setBackground(Color.WHITE);
        swiat.wyswietlSwiat(worldPanel);
    }

    public void zapiszStanDoPliku(String nazwaPliku, String swiatType) {
        try {
            FileWriter writer = new FileWriter(nazwaPliku);
            // Zapis typu świata
            writer.write(swiatType + "\n");
            // Zapis rozmiaru świata
            writer.write(swiat.getRows() + " " + swiat.getCols() + "\n");

            // Zapis organizmów do pliku
            for (int i = 0; i < swiat.getRows(); i++) {
                for (int j = 0; j < swiat.getCols(); j++) {
                    Organizm organizm = swiat.getOrganizm(i, j);
                    if (organizm != null) {
                        // Zapis symbolu organizmu
                        char symbol = organizm.getSymbol();
                        writer.write(symbol + " ");
                        // Zapis współrzędnych organizmu
                        writer.write(i + " " + j + " ");
                        // Zapis siły i inicjatywy organizmu
                        writer.write(organizm.getSila() + " " + organizm.getInicjatywa() + " ");
                        // Jeżeli organizm to człowiek, zapisujemy informacje o umiejętności
                        if (organizm instanceof Czlowiek) {
                            Czlowiek czlowiek = (Czlowiek) organizm;
                            writer.write(czlowiek.getCzasUmiejetnosc() + " ");
                        }
                        writer.write("\n");
                    }
                }
            }
            writer.close();
            JOptionPane.showMessageDialog(frame, "Zapisano stan gry do pliku " + nazwaPliku);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Błąd podczas zapisu stanu gry do pliku!", "Błąd", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void wczytajStanZPliku(String nazwaPliku, String swiatType) {
        try {
            Scanner scanner = new Scanner(new File(nazwaPliku));
            String swiatTypePlik = scanner.nextLine(); // Wczytujemy typ świata
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();
            scanner.nextLine();

            if(!swiatType.equals(swiatTypePlik)) { // Sprawdzamy, czy typy świata się różnią
                JOptionPane.showMessageDialog(frame, "Typy swiata sie roznia! \nWczytywanie przerwane.");
                return; // Przerywamy wczytywanie
            }
            
            if(swiatType=="krata") {
        		swiat = new swiatKratowy(rows,cols, this);
        	}
        	else {
        		swiat = new swiatHex(rows,cols, this);
        	}
            
            swiat.setType(swiatType);
            
            // Czyszczenie starej planszy
            worldPanel.setBackground(Color.WHITE);
            textArea.setText("");  // Czyszczenie okienka z komunikatem
            worldPanel.removeAll();
            worldPanel.revalidate();
            worldPanel.repaint();

            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                char symbol = line[0].charAt(0);
                int x = Integer.parseInt(line[1]);
                int y = Integer.parseInt(line[2]);
                int sila = Integer.parseInt(line[3]);
                int czasUmiejetnosc = 0;

                if (line.length > 5) {
                    czasUmiejetnosc = Integer.parseInt(line[5]);
                }

                switch (symbol) {
                    case 'C':
                        Czlowiek czlowiek = new Czlowiek(x, y, sila, swiat);
                        czlowiek.setCzasUmiejetnosci(czasUmiejetnosc);
                        if (czasUmiejetnosc > 0) {
                            czlowiek.setUmiejetnosc(true);
                        } else {
                            czlowiek.setUmiejetnosc(false);
                        }
                        swiat.dodajOrganizm(czlowiek, x, y);
                        break;
                    case 'W':
                        swiat.dodajOrganizm(new Wilk(x, y, sila, swiat), x, y);
                        break;
                    case 'O':
                        swiat.dodajOrganizm(new Owca(x, y, sila, swiat), x, y);
                        break;
                    case 'L':
                        swiat.dodajOrganizm(new Lis(x, y, sila, swiat), x, y);
                        break;
                    case 'Z':
                        swiat.dodajOrganizm(new Zolw(x, y, sila, swiat), x, y);
                        break;
                    case 'A':
                        swiat.dodajOrganizm(new Antylopa(x, y, sila, swiat), x, y);
                        break;
                    case 'T':
                        swiat.dodajOrganizm(new Trawa(x, y, sila, swiat), x, y);
                        break;
                    case 'M':
                        swiat.dodajOrganizm(new Mlecz(x, y, sila, swiat), x, y);
                        break;
                    case 'G':
                        swiat.dodajOrganizm(new Guarana(x, y, sila, swiat), x, y);
                        break;
                    case 'J':
                        swiat.dodajOrganizm(new Wilcze_jagody(x, y, sila, swiat), x, y);
                        break;
                    case 'B':
                        swiat.dodajOrganizm(new Barszcz_sosnowskiego(x, y, sila, swiat), x, y);
                        break;
                    default:
                        System.err.println("Nieznany symbol organizmu: " + symbol);
                        break;
                }
            }
            scanner.close();
            JOptionPane.showMessageDialog(frame, "Wczytano stan gry z pliku " + nazwaPliku);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Błąd podczas odczytu stanu gry z pliku!", "Błąd", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public void wyswietlInformacje(String komunikat) {
        if (komunikat != null && !komunikat.isEmpty()) {
            textArea.append(komunikat + "\n");
        }
    }
    
    public JTextArea getTextArea() {
        return textArea;
    }
}
