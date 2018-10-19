package gametest;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Menu extends GameObject{
    
    private String imageDirectory;
    private Image texture;
    
    public Menu(int x, int y, ID id, int width, int height, String imageDirectory) {
        super(x, y, id, width, height);
        this.imageDirectory = imageDirectory;
    }

    
    public void tick(){
        
        if(id == ID.Menu){
            if(y > 590){
                    velY = -5;
            }
            else{
                velY = 0;
            }
        }
        
        x += velX;
        y += velY;
        
    }
    
    public void render(Graphics g){
        texture = new ImageIcon(imageDirectory).getImage();
        g.drawImage(texture, x, y, width, height, null);
    }
    

}
