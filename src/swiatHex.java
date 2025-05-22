import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class swiatHex extends Swiat {
    private int rows;
    private int cols;
    private int hexWidth = 700 / grid.length; // szerokość sześciokąta
    private int hexHeight = 700 / grid.length; // wysokość sześciokąta
    private int horizontalSpacing = 0; // odstęp poziomy między sześciokątami
    private Pole[][] siatka; // siatka świata

    public swiatHex(int rows, int cols, Gra gra) {
        super(rows, cols, gra);
        this.rows = rows;
        this.cols = cols;
        this.siatka = new Pole[rows][cols];
        grid = new Organizm[n][m];

        // Inicjalizacja siatki
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                siatka[i][j] = new Pole(i, j);
            }
        }
    }

    private void wyczyscPlansze(JPanel panel, int x, int y, int row, int col, Graphics2D g2d) {
    	// Rysowanie kształtu organizmu
        int symbolWidth = hexWidth / 2; // Szerokość symbolu
        int symbolHeight = hexHeight / 2; // Wysokość symbolu
        
        // Obliczanie współrzędnych X i Y dla położenia środka symbolu
        int symbolX = x + (hexWidth - symbolWidth) / 2; // Początkowe położenie X symbolu
        int symbolY = y + (hexHeight - symbolHeight) / 2; // Początkowe położenie Y symbolu
        
        g2d.setColor(Color.WHITE);
        
        // Rysowanie białego prostokąta
        g2d.fillRect(symbolX, symbolY, symbolWidth, symbolHeight);
        
        g2d.setColor(Color.WHITE);
    }
    
    @Override
    public void wyswietlSwiat(JPanel panel) {    	
    	// Ustawienie rozmiaru panelu
        int panelWidth = cols * (hexWidth + horizontalSpacing) + hexWidth / 2;
        int panelHeight = rows * (hexHeight * 3 / 4) + hexHeight / 2;
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        // Pętla rysująca sześciokąty
        panel.removeAll(); // Usunięcie poprzednich komponentów
        for (int row = 0; row < rows; row++) {
            int startX = row * (hexWidth + horizontalSpacing) / 2; // Początkowe położenie X dla pierwszego sześciokąta w rzędzie
            int startY = row * (hexHeight * 3 / 4); // Początkowe położenie Y dla pierwszego sześciokąta w rzędzie

            for (int col = 0; col < cols; col++) {
                // Obliczanie współrzędnych x i y sześciokąta
                int x = startX + col * (hexWidth + horizontalSpacing);
                int y = startY;

                Graphics2D g2d = (Graphics2D) panel.getGraphics(); // Pobranie kontekstu graficznego panelu

                // Czyszczenie komórki
                wyczyscPlansze(panel, x, y, row, col, g2d);

                g2d.setColor(Color.BLACK);
                drawHexagon(g2d, x, y, hexWidth, hexHeight);

                if(grid[row][col] != null) {
                    // Rysowanie kształtu organizmu
                    int symbolWidth = hexWidth / 2; // Szerokość symbolu
                    int symbolHeight = hexHeight / 2; // Wysokość symbolu

                    // Obliczanie współrzędnych X i Y dla położenia środka symbolu
                    int symbolX = x + (hexWidth - symbolWidth) / 2; // Początkowe położenie X symbolu
                    int symbolY = y + (hexHeight - symbolHeight) / 2; // Początkowe położenie Y symbolu

                    String[] parts = grid[row][col].rysowanie().split(" "); // Rozdzielamy kolor i symbol na części
                    Color color = new Color(Integer.parseInt(parts[0].split(",")[0]), Integer.parseInt(parts[0].split(",")[1]), Integer.parseInt(parts[0].split(",")[2])); // Tworzymy kolor na podstawie wartości RGB
                    g2d.setColor(color);

                    // Rysowanie prostokąta jako symbolu organizmu
                    g2d.fillRect(symbolX, symbolY, symbolWidth, symbolHeight);

                    // Ustawienie koloru tekstu na biały
                    g2d.setColor(Color.WHITE);

                    // Rysowanie symbolu organizmu
                    String symbol = parts[1];
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(symbol);
                    int textHeight = fm.getHeight();

                    // Obliczanie współrzędnych X i Y dla początku tekstu, aby umieścić go na środku pola
                    int textX = symbolX + (symbolWidth - textWidth) / 2;
                    int textY = symbolY + (symbolHeight + textHeight) / 2;

                    // Rysowanie tekstu na środku pola
                    g2d.drawString(symbol, textX, textY);
                }
                else {
                	// Dodawanie guzików na puste pola
                	JButton button = new JButton("");
                    button.setBorderPainted(false);
                    button.setContentAreaFilled(false);
                    button.setOpaque(false);
                    button.setBounds(x+hexWidth/4, y+hexHeight/4, hexWidth/2, hexHeight/2);

                    int currentRow = row;
                    int currentCol = col;

                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String[] animals = {"Wilk", "Owca", "Lis", "Żółw", "Antylopa", "Trawa", "Mlecz", "Guarana", "Wilcze jagody", "Barszcz sosnowskiego"};
                            String selectedAnimal = (String) JOptionPane.showInputDialog(panel, "Wybierz zwierzę:", "Wybór zwierzęcia", JOptionPane.PLAIN_MESSAGE, null, animals, animals[0]);

                            if (selectedAnimal != null) {
                                JOptionPane.showMessageDialog(panel, "Wybrano zwierzę: " + selectedAnimal + " na pozycji: (" + currentRow + ", " + currentCol + ")");
                                dodajZwierze(selectedAnimal, currentRow, currentCol);
                                // Usunięcie przycisku po dodaniu zwierzęcia
                                panel.remove(button);
                            }
                        }
                    });
                    panel.add(button);
                }
            }
        }
    }

    private void drawHexagon(Graphics2D g2d, int x, int y, int width, int height) {
        int[] xPoints = {x + width / 4, x + width / 2 + width / 4, x + width, x + width / 2 + width / 4, x + width / 4, x}; 
        int[] yPoints = {y, y, y + height / 2, y + height, y + height, y + height / 2};

        // Tworzymy obiekt AffineTransform i obracamy go o 90 stopni w lewo
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(-90), x + width / 2.0, y + height / 2.0);

        // Przekształcamy współrzędne punktów za pomocą obrotu
        Polygon rotatedHexagon = new Polygon();
        for (int i = 0; i < xPoints.length; i++) {
            Point point = new Point(xPoints[i], yPoints[i]);
            rotation.transform(point, point);
            rotatedHexagon.addPoint((int) point.getX(), (int) point.getY());
        }

        // Rysujemy obrócony sześciokąt
        g2d.drawPolygon(rotatedHexagon);
    }
    
    public boolean znajdzPustePoleObok(int[] pozycja) {
        // Tworzymy listę par (x, y), reprezentujących wszystkie możliwe pozycje obok pola (x, y)
        List<int[]> wolnePola = new ArrayList<>();

        // Definiowanie sześciu kierunków dla hexa
        int[][] hexMoves = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, -1}
        };

        // Iterujemy przez wszystkie możliwe położenia obok danego pola (x, y)
        for (int[] move : hexMoves) {
            int i = pozycja[0] + move[0];
            int j = pozycja[1] + move[1];

            // Sprawdzamy, czy pole jest w granicach świata i czy jest puste
            if (i >= 0 && i < n && j >= 0 && j < m && grid[i][j] == null) {
                wolnePola.add(new int[]{i, j});
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
