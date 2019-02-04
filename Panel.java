import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Panel extends JPanel implements KeyListener, Runnable {
    // game variables
    private Thread game;
    private boolean running = false;
    private boolean mapFinished = false;

    // panel variables
    static final int Width = 480;
    static final int Height = 432;
    static final Dimension dim = new Dimension(Width, Height);

    // maps
    GameOfLife map1;
    // map map2;
    // map map3;
    // map map4;

    // drawing variables
    private BufferedImage image;
    private Graphics g;
    private long time = 6 * 1000000;

    public Panel() {
        map1 = new GameOfLife(10);
        setPreferredSize(new Dimension(Width, Height));
        setFocusable(true);
        requestFocus();
    }

    public void run() {
        long startTime;
        long elapsedTime;
        long diff;
        long sleepTime;
        long overSleep = 0;

        image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
        g = image.getGraphics();
        running = true;

        while (running) {
            startTime = System.nanoTime();
            // gameUpdate();
            gameRender();
            gameDraw();
            elapsedTime = System.nanoTime();
            diff = elapsedTime - startTime;
            if (diff < time) {
                sleepTime = time - diff - overSleep;
                try {
                    game.sleep(sleepTime / 1000000);
                } catch (Exception e) {

                }
            } else {
                overSleep = diff - time;
            }
        }
    }

    private void gameRender() {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Width, Height);
        g.setColor(Color.BLACK);
        map1.draw(g);
    }

    public void gameDraw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent key) {
    }

    public void keyPressed(KeyEvent key) {

        int code = key.getKeyCode();

    }

    public void keyReleased(KeyEvent key) {
        int code = key.getKeyCode();
    }

    public void addNotify() {
        super.addNotify();
        if (game == null) {
            game = new Thread(this);
            game.start();
        }
        addKeyListener(this);
    }

    public void startGame() {
        if (running == false) {
            running = true;
        }
    }

    public void stopGame() {
        if (running == true) {
            running = false;
        }
    }

    public boolean boolTimer() {
        long now = System.currentTimeMillis();
        long end = now + 1 * 3000;
        long current = System.currentTimeMillis();
        while (current < end) {
            current = System.currentTimeMillis();
        }
        return true;
    }

}