import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public abstract class Swiat {
    private Gra gra;
    protected int n; // liczba wierszy
    protected int m; // liczba kolumn
    protected Organizm[][] grid; // siatka świata
    protected String type;

    public Swiat(int rows, int cols, Gra gra) {
        n = rows;
        m = cols;
        grid = new Organizm[n][m];
        this.gra = gra;
    }

    public void wyczyscSwiat() {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                grid[i][j] = null;
            }
        }
    }
    
    protected void dodajZwierze(String selectedAnimal, int row, int col) {
        switch (selectedAnimal) {
            case "Wilk":
                this.dodajOrganizm(new Wilk(row, col, 0, this), row, col);
                break;
            case "Owca":
                this.dodajOrganizm(new Owca(row, col, 0, this), row, col);
                break;
            case "Lis":
                this.dodajOrganizm(new Lis(row, col, 0, this), row, col);
                break;
            case "Żółw":
                this.dodajOrganizm(new Zolw(row, col, 0, this), row, col);
                break;
            case "Antylopa":
                this.dodajOrganizm(new Antylopa(row, col, 0, this), row, col);
                break;
            case "Trawa":
                this.dodajOrganizm(new Trawa(row, col, 0, this), row, col);
                break;
            case "Mlecz":
                this.dodajOrganizm(new Mlecz(row, col, 0, this), row, col);
                break;
            case "Guarana":
                this.dodajOrganizm(new Guarana(row, col, 0, this), row, col);
                break;
            case "Wilcze jagody":
                this.dodajOrganizm(new Wilcze_jagody(row, col, 0, this), row, col);
                break;
            case "Barszcz sosnowskiego":
                this.dodajOrganizm(new Barszcz_sosnowskiego(row, col, 0, this), row, col);
                break;
            default:
                break;
        }
    }

    public abstract void wyswietlSwiat(JPanel panel);
    
    public void usunOrganizm(int x, int y) {
        grid[x][y] = null;
    }

    public void dodajOrganizm(Organizm organizm, int x, int y) {
        grid[x][y] = organizm;
    }

    public int getSilaOrganizmu(int x, int y) {
        // Sprawdzamy, czy podane współrzędne są poprawne
        if (x >= 0 && x < n && y >= 0 && y < m) {
            Organizm org = grid[x][y];
            if (org != null) {
                return org.getSila();
            }
        }
        return 0;
    }

    public Organizm getOrganizm(int x, int y) {
        // Sprawdzamy, czy podane współrzędne są poprawne
        if (x >= 0 && x < n && y >= 0 && y < m) {
            return grid[x][y];
        } else {
            return null;
        }
    }

    public boolean organizmNaPozycji(int x, int y) {
        if (x >= 0 && x < n && y >= 0 && y < m) {
            return grid[x][y] != null;
        } else {
            return false;
        }
    }

    public abstract boolean znajdzPustePoleObok(int [] pozycja);
    
    private class PorownywaczOrganizmow implements Comparator<Organizm> {
        @Override
        public int compare(Organizm orgA, Organizm orgB) {
            if (orgA.getInicjatywa() == orgB.getInicjatywa()) {
                return Integer.compare(orgB.getWiek(), orgA.getWiek());
            }
            return Integer.compare(orgB.getInicjatywa(), orgA.getInicjatywa());
        }
    }
    
    public void wykonajTure(JTextArea textArea) {
    	// Tworzymy listę organizmów wraz z ich współrzędnymi
        List<Organizm> organizmy = new ArrayList<>();

        // Wypełniamy listę organizmami i ich współrzędnymi
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (grid[i][j] != null) {
                    organizmy.add(grid[i][j]);
                }
            }
        }

        // Sortujemy organizmy według ich inicjatywy, a w przypadku takiej samej inicjatywy według wieku
        Collections.sort(organizmy, new PorownywaczOrganizmow());
    	
        // Wykonujemy akcję dla każdego organizmu w posortowanej kolejności
        for (Organizm organizm : organizmy) {
            int posX = organizm.getX();
            int posY = organizm.getY();
            
            // Sprawdzamy, czy dany organizm nadal znajduje się na pozycji (x, y)
            if (posX!=-1 && grid[posX][posY] == organizm) {
                organizm.akcja();
                organizm.inkrementujWiek();
                // Ewentualne odświeżenie planszy po ruchu pojedynczego organizmu
                // wyswietlSwiat(panel);
            }
        }
    }

    // Gettery i Settery
    public int getRows() {
        return n;
    }

    public int getCols() {
        return m;
    }

    public void setRows(int rows) {
        n = rows;
    }

    public void setCols(int cols) {
        m = cols;
    }
    
    public Gra getGra() {
        return gra;
    }
    
    public void setType(String worldType) {
    	type = worldType;
    }
    
    public String getType() {
    	return type;
    }
}
