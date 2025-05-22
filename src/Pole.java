public class Pole {
    private Organizm organizm;
    private int x;
    private int y;

    public Pole(int x, int y) {
        this.x = x;
        this.y = y;
        this.organizm = null; // Na początku pole jest puste
    }

    public Organizm getOrganizm() {
        return organizm;
    }

    public void setOrganizm(Organizm organizm) {
        this.organizm = organizm;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean czyPuste() {
        return organizm == null;
    }
}
