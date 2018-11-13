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
public class ColisionObject extends GameObject{
    private Image objectImg;
    private String imageDirectory;
    
    ColisionObject(int x, int y, ID id, int width, int height) {
        super(x, y, id, width, height);
    }
    
    public void tick() {
        //x += velX;
        //y += velY;

        //Clamp function to not let entity go offscreen
        //x = Game.clamp(x, 0, Game.WIDTH - 37);
        //y = Game.clamp(y, 0, Game.HEIGHT - 66);
    }
    
    public boolean checkColision(WorldPhaseEntity p)
    {
        ;
        //Because Character x and y is always 0 -> How to check colision :thonk:
        return true;
    }

    public void render(Graphics g) {
        objectImg = new ImageIcon(imageDirectory).getImage();
        g.drawImage(objectImg, x, y, width, height, null);
    }
    
}
