/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author DELL
 */
public class WorldPhaseEntity extends GameObject{
    
    protected List<Coordinate> cornerList;
    protected String charName;
    private Image charImg;
    private String imageDirectory;

    WorldPhaseEntity(int x, int y, ID id, int width, int height, String imageDirectory, String charName) {
        super(x, y, id, width, height);
        this.imageDirectory = imageDirectory;
        this.charName = charName;
        cornerList = new ArrayList<>();
        
        //Coordinate x and y for each corner
        cornerList.add(new Coordinate(x, y));
        cornerList.add(new Coordinate(x + width, y));
        cornerList.add(new Coordinate(x, y + height));
        cornerList.add(new Coordinate(x + width, y + height));
    }
    
    
    public void tick() {
        x += velX;
        y += velY;
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
