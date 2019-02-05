import java.awt.Color;
import java.awt.Rectangle;

public class Tile extends Rectangle {
    private Color color;

    public Tile(int x, int y, int size, Color color) {
        super(x, y, size, size);
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}