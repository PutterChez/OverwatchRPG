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
public class Wall extends Map{
    
    Wall(int x, int y, ID id,int width, int height, Image mapImg, String imgDirectory)
    {
        super(x, y, id, width, height, mapImg, imgDirectory);
    }
    
    public void tick() {
        //Collision Detection
    }

    public void render(Graphics g) {
        /* Old Square Player
         if(id == ID.Player){
         g.setColor(Color.blue);
         g.fillRect(x, y, width, height);
         }*/

        //New Texture
        mapImage = new ImageIcon(super.imageDirectory).getImage();
        g.drawImage(mapImage, x, y, width, height, null);
    }
    
}
