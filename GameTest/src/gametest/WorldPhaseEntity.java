/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author DELL
 */
public class WorldPhaseEntity extends GameObject{
    protected String charName;
    private Image charImg;
    private String imageDirectory;

    WorldPhaseEntity(int x, int y, ID id, int width, int height, String imageDirectory, String charName) {
        super(x, y, id, width, height);
        this.imageDirectory = imageDirectory;
        this.charName = charName;
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
        charImg = new ImageIcon(imageDirectory).getImage();
        g.drawImage(charImg, x, y, width, height, null);
    }

    //Act test function
    public void act(String action) {
        if (action.equals("Run")) {
            System.out.println("The player flees!");
        } else if (action.equals("Attack")) {
            System.out.println("The player attacks!");
        } else if (action.equals("Items")) {
            System.out.println("The player uses an item!");
        }
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }
}
