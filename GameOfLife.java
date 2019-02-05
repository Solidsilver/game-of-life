import java.awt.Color;
import java.util.Random;

import java.util.ArrayList;
import java.util.List;

public class GameOfLife {
    private boolean[][] grid;
    private boolean[][] prevState;
    private int sizeX;
    private int sizeY;

    private int sleepTime;

    int boarderSize = 10;

    public GameOfLife() {
        this(10, 10);
    }

    public GameOfLife(int size) {
        this(size, size);
    }

    public GameOfLife(int width, int height) {
        this.sizeX = width+(2*boarderSize);
        this.sizeY = height+(2*boarderSize);
        this.grid = new boolean[width + 2*boarderSize][height + 2*boarderSize];
        this.sleepTime = 75;
        generateBoard();
        
    }
    

    public static void main(String[] args) {
        GameOfLife GOL = new GameOfLife();
        if (args.length > 0) {
            int sizeOne = Integer.parseInt(args[0]);
            if (args.length > 1) {
                int sizeTwo = Integer.parseInt(args[1]);
                GOL = new GameOfLife(sizeOne, sizeTwo);
                //GOL = new GameOfLife()
            } else {
                GOL = new GameOfLife(sizeOne);
            }
        }
        GOL.run();
    }

    public List<Tile> draw(int tileSize)
        {
            List<Tile> shList = new ArrayList<>();
            this.updateGrid();
            for (int x = this.boarderSize-1; x < this.sizeX-this.boarderSize; x++) {
                for (int y = this.boarderSize-1; y < this.sizeY-this.boarderSize; y++) {
                    if (this.grid[x][y]) {
                        shList.add(new Tile(tileSize*(x-boarderSize), tileSize*(y-boarderSize), tileSize, Color.WHITE));
                    } else {
                        shList.add(new Tile(tileSize*(x-boarderSize), tileSize*(y-boarderSize), tileSize, Color.BLACK));
                    }
                }
            }
            return shList;
        }

    public void run() {
        //generateBoard();
        while (true) {
            System.out.print("\033[H\033[2J");
            this.printGrid();
            updateGrid();
            try {
                Thread.sleep(this.sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("Finished simulation - Iterations: " + iterations);
    }


    public void generateBoard() {
        Random rnd = new Random();
        int maxTiles = this.sizeX*this.sizeY/10;
        for (int x = this.boarderSize; x < this.sizeX && maxTiles > 0; x++) {
            for (int y = this.boarderSize; y < this.sizeY; y++) {
                boolean putOne = false;
                int nCount = this.getNeighborCount(this.grid, x, y);
                if (nCount < 2) {
                    putOne = rnd.nextBoolean()&& rnd.nextBoolean()&& rnd.nextBoolean();
                } else if (nCount == 2) {
                    putOne = rnd.nextBoolean()&& rnd.nextBoolean();
                } else {
                    putOne = rnd.nextBoolean() && rnd.nextBoolean() && rnd.nextBoolean()&& rnd.nextBoolean()&& rnd.nextBoolean();
                }
                if (putOne) {
                    this.grid[x][y] = true;
                    maxTiles--;
                }
            }
        }
    }

    private void updateGrid() {
        this.setPrevState();
        int nCount;
        for (int x = 0; x < this.sizeX; x++) {
            for (int y = 0; y < this.sizeY; y++) {
                nCount = getNeighborCount(x, y);

                if (this.grid[x][y]) {
                    if (nCount == 1 || nCount == 0) {
                        this.grid[x][y] = false;
                    } else if (nCount >= 4) {
                        this.grid[x][y] = false;
                    }
                } else {
                    if (nCount == 3) {
                        this.grid[x][y] = true;
                    }
                }
            }
        }
    }

    private int getNeighborCount(int x, int y) {
        return this.getNeighborCount(this.prevState, x, y);
    }

    private int getNeighborCount(boolean[][] grid, int x, int y){
        int count = 0;
        int startX, endX, startY, endY;
        startX = x-1;
        endX = x+1;
        startY = y+1;
        endY = y-1;
        if (x == 0) {
            startX = x;
        } else if (x == this.sizeX - 1) {
            endX = x;
        }

        if (y == 0) {
            endY = y;
        } else if (y == this.sizeY - 1) {
            startY = y;
        }

        for (int loopX = startX; loopX <= endX; loopX++) {
            for (int loopY = startY; loopY >= endY; loopY--) {
                if (grid[loopX][loopY])
                count ++;
            }
        }
        if (grid[x][y]) {
            count--;
        }
        return count;
    }

    private int totalGridCount() {
        int count = 0;
        for (int x = 0; x < this.sizeX; x++) {
            for (int y = 0; y < this.sizeY; y++) {
                if (this.grid[x][y])
                count ++;
            }
        }
        return count;
    }

    private void printGrid() {
        for (int printX = this.boarderSize-1; printX < this.sizeX-this.boarderSize; printX++) {
            for (int printY = this.boarderSize-1; printY < this.sizeY-this.boarderSize; printY++) {
                if (this.grid[printX][printY]) {
                    //System.out.print("⬛");
                    //System.out.print("⚫");
                    //System.out.print("🔴");
                    System.out.print("⬜");
                    //System.out.print(" ◼");
                    //System.out.print("⚪");
                } else {
                    //System.out.print("⬜");
                    //System.out.print("⚪");
                    //System.out.print(" ◻");
                    //System.out.print("🔄");
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    private boolean[][] getState() {
        return this.grid;
    }

    private boolean isInState(boolean[][] other) {
        if (other == null) {
            return false;
        }
        for (int x = 0; x < this.sizeX; x++) {
            for (int y = 0; y < this.sizeY; y++){
                if (this.grid[x][y] != other[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setPrevState() {
        if (this.prevState == null) {
            this.prevState = new boolean[this.sizeX][this.sizeY];
        }
        for (int x = 0; x < this.sizeX; x++) {
            for (int y = 0; y < this.sizeY; y++) {
                this.prevState[x][y] = this.grid[x][y];
            }
        }
    }
    
}