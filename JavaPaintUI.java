import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

class JavaPaintUI extends JFrame {
    private static final Color FILL_COLOR = Color.BLUE;
    private static final Color BORDER_COLOR = Color.RED;
    public static final Stroke STROKE = new BasicStroke(4f);

    private JPanel jPanel2;
    JScrollPane scroller;

    public JavaPaintUI() {
        initComponents();
    }

    private void initComponents() {
        jPanel2 = new Panel2();
        scroller = new JScrollPane(jPanel2); 
        //ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // scroller.setPreferredSize( new Dimension (420,420));
        scroller.getVerticalScrollBar().setUnitIncrement(16);
        scroller.getHorizontalScrollBar().setUnitIncrement(16);
        this.setContentPane(scroller);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    class Panel2 extends JPanel {
        Dimension d = new Dimension(1435, 895);
        int tileSize = 4;
        private static final int TIMER_DELAY = 60;
        private Random random = new Random();
        private List<Tile> shapeList = new ArrayList<>();
        private GameOfLife gol = new GameOfLife(d.width / tileSize, d.height / tileSize);

        Panel2() {
            setPreferredSize(new Dimension(d.width, d.height));
            new Timer(TIMER_DELAY, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent evt) {
                    shapeList = gol.draw(tileSize);
                    repaint();
                }
            }).start();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            // g.drawString("BLAH", 20, 20);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(STROKE);
            for (Tile tile : shapeList) {
                g2.setColor(tile.getColor());
                g2.fill(tile);
                // g2.setColor(BORDER_COLOR);
                // g2.draw(tile);
            }
        }
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JavaPaintUI().setVisible(true);
            }
        });
    }
}