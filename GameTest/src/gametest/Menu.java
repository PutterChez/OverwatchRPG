package gametest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Menu extends GameObject {

    private String imageDirectory;
    private Image texture;
    public Menu(int x, int y, ID id, int width, int height, String imageDirectory) {
        super(x, y, width, height, "Menu", id);
        this.imageDirectory = imageDirectory;
    }

    public void tick() {
        x += velX;
        y += velY;

    }

    public void render(Graphics g) {
        texture = new ImageIcon(imageDirectory).getImage();
        g.drawImage(texture, x, y, width, height, null);
        
        if (id == ID.PopUp){
            g.setFont(new Font("Minecraft Bold", Font.PLAIN, 30));
            g.setColor(Color.white);
            
            g.drawString("Attack", x + 100,y + 110);
            g.drawString("Items", x + 100,y + 160);
            g.drawString("Run", x + 100,y + 210);
        }
    }
    
}
