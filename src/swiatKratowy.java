import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class swiatKratowy extends Swiat {
    public swiatKratowy(int rows, int cols, Gra gra) {
        super(rows, cols, gra);
    }

    public void wyswietlSwiat(JPanel panel) {
        panel.removeAll(); // Usuwamy wszystkie komponenty z panelu
        panel.setLayout(new GridLayout(n, m)); // Ustawiamy siatkowy layout dla panelu

        int cellWidth = panel.getWidth() / m;
        int cellHeight = panel.getHeight() / n;
        
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                JPanel cellPanel = new JPanel(); // Tworzymy nowy panel dla komórki
                cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Dodajemy obramowanie komórki

                int currentRow = i;
                int currentCol = j;
                
                if (grid[i][j] != null) {
                	String[] parts = grid[i][j].rysowanie().split(" "); // Rozdzielamy kolor i symbol na części
                	Color color = new Color(Integer.parseInt(parts[0].split(",")[0]), Integer.parseInt(parts[0].split(",")[1]), Integer.parseInt(parts[0].split(",")[2])); // Tworzymy kolor na podstawie wartości RGB
                    cellPanel.setBackground(color); // Ustawiamy kolor tła komórki na kolor organizmu

                    JLabel label = new JLabel(parts[1]); // Tworzymy etykietę z symbolem organizmu
                    label.setForeground(Color.WHITE); // Ustawiamy kolor tekstu na biały
                    cellPanel.add(label); // Dodajemy etykietę do panelu komórki
                } else {
                	JButton button = new JButton("");
                	 button.setBorderPainted(false); // Usunięcie wyświetlania obramowania przycisku
                     button.setContentAreaFilled(false); // Ustawienie tła przycisku jako przezroczyste
                     button.setOpaque(false);
                     button.setBounds(0, 0, cellWidth, cellHeight); // Ustawienie rozmiaru przycisku na rozmiar komórki
                     button.addActionListener(new ActionListener() {
                         @Override
                         public void actionPerformed(ActionEvent e) {
                             String[] animals = {"Wilk", "Owca", "Lis", "Żółw", "Antylopa", "Trawa", "Mlecz", "Guarana", "Wilcze jagody", "Barszcz sosnowskiego"};
                             String selectedAnimal = (String) JOptionPane.showInputDialog(panel, "Wybierz zwierzę:", "Wybór zwierzęcia", JOptionPane.PLAIN_MESSAGE, null, animals, animals[0]);

                             if (selectedAnimal != null) {
                                 JOptionPane.showMessageDialog(panel, "Wybrano zwierzę: " + selectedAnimal + " na pozycji: (" + currentRow + ", " + currentCol + ")");
                                 dodajZwierze(selectedAnimal, currentRow, currentCol);
                                 // Usunięcie przycisku po dodaniu zwierzęcia
                                 cellPanel.remove(button);
                             }
                         }
                     });                     
                	cellPanel.add(button);                	
                }

                panel.add(cellPanel); // Dodajemy panel komórki do panelu głównego
            }
        }

        panel.revalidate(); // Wymuszamy ponowne wyświetlenie panelu
        panel.repaint();
    }
    
    public boolean znajdzPustePoleObok(int [] pozycja) {
        // Tworzymy listę par (x, y), reprezentujących wszystkie możliwe pozycje obok pola (x, y)
        List<int[]> wolnePola = new ArrayList<>();

        // Iterujemy przez wszystkie możliwe położenia obok danego pola (x, y)
        for (int i = pozycja[0] - 1; i <= pozycja[0] + 1; ++i) {
            for (int j = pozycja[1] - 1; j <= pozycja[1] + 1; ++j) {
                // Pomijamy pole, na którym aktualnie znajduje się organizm
                if (i == pozycja[0] && j == pozycja[1]) {
                    continue;
                }
                // Sprawdzamy, czy pole jest w granicach świata i czy jest puste
                if (i >= 0 && i < n && j >= 0 && j < m && grid[i][j] == null) {
                    wolnePola.add(new int[]{i, j});
                }
            }
        }

        // Jeśli nie ma żadnego wolnego pola obok, zwracamy false
        if (wolnePola.isEmpty()) {
            System.out.println("Brak pustego pola!");
            return false;
        }

        // Losujemy indeks z dostępnych pól
        Random rand = new Random();
        int idx = rand.nextInt(wolnePola.size());

        // Ustawiamy współrzędne na wylosowane wolne pole
        pozycja[0] = wolnePola.get(idx)[0];
        pozycja[1] = wolnePola.get(idx)[1];

        return true;
    }
}
