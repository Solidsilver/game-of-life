
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

class GOLUI extends JFrame {
    private static final long serialVersionUID = -1871627221971734056L;
    private int width, height;

    private JPanel jPanel2;
    JScrollPane scroller;

    public GOLUI() {
        this(1024, 512);
        // initComponents();
    }

    public GOLUI(int width, int height) {
        this.width = width;
        this.height = height;
        initComponents();
    }

    private void initComponents() {
        jPanel2 = new Panel2(this.width, this.height);
        scroller = new JScrollPane(jPanel2);
        // ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        // ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // scroller.setPreferredSize( new Dimension (420,420));
        // scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollBar vertScroll = new JScrollBar(JScrollBar.VERTICAL) {

            @Override
            public boolean isVisible() {
                return true;
            }
        };
        JScrollBar horScroll = new JScrollBar(JScrollBar.HORIZONTAL) {

            @Override
            public boolean isVisible() {
                return true;
            }
        };
        scroller.setVerticalScrollBar(vertScroll);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroller.setHorizontalScrollBar(horScroll);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.getVerticalScrollBar().setUnitIncrement(8);
        scroller.getHorizontalScrollBar().setUnitIncrement(8);
        this.setContentPane(scroller);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    class Panel2 extends JPanel {
        private static final long serialVersionUID = -9029402562216619412L;
        // Dimension d = new Dimension(1435, 895);
        Dimension d;
        int tileSize = 4;
        private static final int TIMER_DELAY = 60;
        private List<Tile> shapeList = new ArrayList<>();
        private GameOfLife gol;

        Panel2() {
            this(1024, 512);
        }

        Panel2(int width, int height) {
            this.d = new Dimension(width, height);
            gol = new GameOfLife(d.width / tileSize, d.height / tileSize);
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
            // g2.setStroke(STROKE);
            for (Tile tile : shapeList) {
                g2.setColor(tile.getColor());
                g2.fill(tile);
            }
        }
    }

    public static void main(String args[]) {
        int runWidth;
        int runHeight;

        runWidth = 1024;
        runHeight = 512;
        if (args.length > 0) {
            if (args.length == 1) {
                try {
                    runWidth = Integer.parseInt(args[0]);
                    runHeight = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    runWidth = 1024;
                    runHeight = 512;
                }
            } else if (args.length == 2) {
                try {
                    runWidth = Integer.parseInt(args[0]);
                    runHeight = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    runWidth = 1024;
                    runHeight = 512;
                }
            }
        }
        new GOLUI(runWidth, runHeight).setVisible(true);
        /*
         * EventQueue.invokeLater(new Runnable() { public void run() { new
         * GOLUI(runWidth, runHeight).setVisible(true); } });
         */
    }
}