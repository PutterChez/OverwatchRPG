/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author DELL
 */
public class Map extends GameObject{
    protected Image mapImage;
    protected String imageDirectory;
    
    Map(int x, int y, ID id,int width, int height, String imgDirectory)
    {
        super(x, y, id, width, height);
        this.imageDirectory = imgDirectory;
    }
    
    public void tick() {
        x += velX;
        y += velY;

        //Clamp function to not let entity go offscreen
        //x = Game.clamp(x, 0, Game.WIDTH - 37);
        //y = Game.clamp(y, 0, Game.HEIGHT - 66);
    }

    public void render(Graphics g) {
        /* Old Square Player
         if(id == ID.Player){
         g.setColor(Color.blue);
         g.fillRect(x, y, width, height);
         }*/

        //New Texture
        mapImage = new ImageIcon(imageDirectory).getImage();
        g.drawImage(mapImage, x, y, width, height, null);
    }
    
}
