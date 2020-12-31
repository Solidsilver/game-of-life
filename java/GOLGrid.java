public class GOLGrid {
    public int sizeX;
    public int sizeY;
    public boolean[][] cur;
    public boolean[][] prev;

    public GOLGrid(boolean[][] startState) {
        this(startState, 200, 100);
    }

    public GOLGrid(boolean[][] startState, int x, int y) {
        this.cur = startState;
        this.sizeX = x;
        this.sizeY = y;
    }

    public void copyState() {
        if (this.prev == null) {
            this.prev = new boolean[this.sizeX][this.sizeY];
        }
        for (int x = 0; x < this.sizeX; x++) {
            for (int y = 0; y < this.sizeY; y++) {
                this.prev[x][y] = this.cur[x][y];
            }
        }
    }
}
