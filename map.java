import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
 
public class map
{
 
    private int[][] tileMap;
        private int height;
        private int width;
        private Image BLOCK_DK, BLOCK_LT;
 
 
    public map(int[][] grid)
    {
            BLOCK_DK = new ImageIcon("dark.jpg").getImage();
            BLOCK_LT = new ImageIcon("light.png").getImage();
            int[] tileNums;
            try
            {
                width=grid.length;
                height=grid[0].length;
                tileNums= new int[width];
                tileMap=grid;
            }
            catch(Exception e)
            {
 
            }
    }
        public void draw(Graphics g)
        {
            
            int ix=0;
            int iy=0;
            for(int row=0;row<height;row++)
            {
                for(int col=0;col<height;col++)
                {
                    if(tileMap[row][col]==0)
                    {
                         g.drawImage(BLOCK_LT, ix, iy, null);
                         ix=ix+16;
                    } else {
                        g.drawImage(BLOCK_DK, ix, iy, null);
                         ix=ix+16;
                    }
                }
            }
        }
}