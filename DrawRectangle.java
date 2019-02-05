import java.awt.*;
import javax.swing.*;

public class DrawRectangle {

    public static void main(String[] arguments) {

        MyPanel panel = new MyPanel();

        // create a basic JFrame
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("JFrame Color Example");
        frame.setSize(320, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add panel to main frame
        frame.add(panel);

        frame.setVisible(true);

    }
}

// create a panel that you can draw on.
class MyPanel extends JPanel {
    public GameOfLife test;

    public MyPanel() {
        this.test = new GameOfLife(10);
    }

    public void paint(Graphics g) {
        int ix = 0;
        int iy = 16;
        // g.setColor(Color.BLACK);
        // g.fillRect(ix, iy, 16, 16);
        while (true) {
            this.test.draw(g);
            try {
                
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        // g.setColor(Color.red);
        // g.fillRect(10,10,100,100);
    }
}