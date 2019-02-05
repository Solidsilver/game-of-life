import javax.swing.JFrame;
 
public class Frame extends JFrame
{
    Panel ng;
 
    public Frame()
        {
            ng = new Panel();
            setSize(500,500);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false); 
            add(ng);
        }
        public static void main(String [] args)
        {
            Frame n = new Frame();
            System.out.println("RUNNING");
            //n.ng.run();
        }
}