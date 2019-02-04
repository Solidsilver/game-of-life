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
 
public class Panel extends JPanel implements KeyListener, Runnable
{
    //game variables
    private Thread game;
    private boolean running = false;
    private boolean mapFinished = false;
     
    //panel variables
    static final int Width = 480;
    static final int Height = 432;
    static final Dimension dim = new Dimension(Width,Height);
     
    //maps
    map map1;
    map map2;
    map map3;
    map map4;
     
     
    boolean map1Finished;
    boolean map2Finished;
    boolean map3Finished;
    boolean map4Finished;
     
    //drawing variables
    private BufferedImage image;
    private Graphics g;
    private long time = 6*1000000;
     
    public panel(){
        map1 = new map("tester.txt");
        map2 = new map("tester2.txt");
        map3 = new map("tester3.txt");
        map4 = new map("tester4.txt");
        p1 = new player(map1);
        ng = new goal(map1);
        e = new enemy(map1);
        setPreferredSize(new Dimension(Width,Height));
        setFocusable(true);
        requestFocus();
    }
     
    public void run(){
        long startTime;
        long elapsedTime;
        long diff;
        long sleepTime;
        long overSleep=0;
         
        image=new BufferedImage(Width,Height,BufferedImage.TYPE_INT_RGB);
        g=image.getGraphics();
        running = true;
         
        while (running){
            startTime=System.nanoTime();
            gameUpdate();
            gameRender();
            gameDraw();
            elapsedTime=System.nanoTime();
            diff=elapsedTime-startTime;
            if(diff<time){
                sleepTime=time-diff-overSleep;
                try{
                    game.sleep(sleepTime/1000000);
                }
                catch(Exception e){
                    
                }
            }
            else{
                overSleep=diff-time;
            }
        }
    }
     
    private void gameUpdate(){
        p1.update();
         
        if((p1.playerRec.x/48)==(ng.goalRec.x/48) && (p1.playerRec.y/48)==(ng.goalRec.y/48)){
            if(!map1Finished){
                map1Finished=true;
            }
            else if(map1Finished&&!map2Finished){
                map2Finished=true;
            }
            else if(map2Finished&&!map3Finished){
                map3Finished=true;
            }
            else if(map3Finished&&!map4Finished){
                map4Finished=true;
            }
             
        }
         
        changeSpawn();
    }
     
    private void gameRender(){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,Width,Height);
        g.setColor(Color.BLACK);
        if (!map1Finished){
            map1.draw(g);
            p1.draw(g);
        }
         
   
        if (map1Finished&&!map2Finished){
            map2.draw(g);
            p1.draw(g);
            winningImage=new ImageIcon("sprites/level1.png").getImage();
            g.drawImage(winningImage,5,5,null);
        }
        if (map2Finished&&!map3Finished){
            map3.draw(g);
            p1.draw(g);
            winningImage=new ImageIcon("sprites/level1.png").getImage();
            g.drawImage(winningImage,5,5,null);
        }
        if (map3Finished&&!map4Finished){
            map4.draw(g);
            p1.draw(g);
            winningImage=new ImageIcon("sprites/level1.png").getImage();
            g.drawImage(winningImage,5,5,null);
        }
        //g.drawString(""+map1.tileMap[1][7], 100, 100);
    }
     
    public void gameDraw(){
        Graphics g2=this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }
     
    public void keyTyped(KeyEvent key) {}
     
    public void keyPressed(KeyEvent key) {
         
    int code = key.getKeyCode();    
    if(code == KeyEvent.VK_LEFT) {
            p1.setLeft(true);
    }
    if(code == KeyEvent.VK_RIGHT) {
            p1.setRight(true);
    }
    if(code == KeyEvent.VK_UP) {
            p1.setUp(true);
    }
    if(code == KeyEvent.VK_DOWN) {
            p1.setDown(true);
    }
    }
     
    public void keyReleased(KeyEvent key) {
    int code = key.getKeyCode();
    if(code == KeyEvent.VK_LEFT) {
        p1.setLeft(false);
    }
    if(code == KeyEvent.VK_RIGHT) {
        p1.setRight(false);
    }
    }
     
    public void addNotify(){
        super.addNotify();
        if(game==null){
            game=new Thread(this);
            game.start();
        }
        addKeyListener(this);
    }
     
    public void startGame(){
        if (running == false){
            running = true; 
        }
    }
     
    public void stopGame(){
        if (running == true)
        {
            running = false; 
        }
    }
 
    public boolean boolTimer(){
        long now=System.currentTimeMillis();
        long end = now+1*3000;
        long current = System.currentTimeMillis();
        while(current<end){
            current = System.currentTimeMillis();
        }
        return true;
    }
     
    public void changeSpawn(){
        if(map1Finished==true && map2Spawn==false){
            p1=new player(map2);
            ng=new goal(map2);
            map2Spawn=true;
        }
         
        else if(map2Finished==true && map3Spawn==false){
            p1=new player(map3);
            ng=new goal(map3);
            map3Spawn=true;
        }
        else if(map3Finished==true && map4Spawn==false){
            p1=new player(map4);
            ng=new goal(map4);
            map4Spawn=true;
        }
    }
}