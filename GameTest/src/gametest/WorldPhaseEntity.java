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
        super(x, y, width, height, charName, id);
        this.imageDirectory = imageDirectory;
        this.charName = charName;
        cornerList = new ArrayList<>();
        
        //Coordinate x and y for each corner
        cornerList.add(new Coordinate(x, y));
        cornerList.add(new Coordinate(x + width, y));
        cornerList.add(new Coordinate(x, y + height));
        cornerList.add(new Coordinate(x + width, y + height));
    }
    
    public void act(){
        System.out.println("NPC do something :D");
    }
    
    public void tick() {
        x += velX;
        y += velY;
        updateCorner();
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
    
    public void updateCorner()
    {
        cornerList.get(0).x = x;
        cornerList.get(0).y = y;
        cornerList.get(1).x = x + width;
        cornerList.get(1).y = y;
        cornerList.get(2).x = x;
        cornerList.get(2).y = y + height;
        cornerList.get(3).x = x + width;
        cornerList.get(3).y = y + height;
    }
    
    public void setImageDirectory(String imgDirect)
    {
        this.imageDirectory = imgDirect;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }
    
    public boolean checkColision(Player p)
    {
        //Prototype
        for(int i = 0; i < p.cornerList.size(); i++)
        {
            Coordinate temp = p.cornerList.get(i);
            Coordinate upperLeft = cornerList.get(0);
            Coordinate lowerRight = cornerList.get(3);
            
            
            //check if temp is in ColisionObjecct cconer
            if (temp.x >= upperLeft.x && temp.x <= lowerRight.x && temp.y >= upperLeft.y && temp.y <= lowerRight.y)
                return true;
        }
        return false;
    }
    
    public void getOutOfHere(Player p)
    {
        if(p.velX < 0 && p.velY == 0)
        {
            p.x = x + width + 10;
        }
        else if (p.velX > 0 && p.velY == 0)
        {
            p.x = x - 10 - p.width;
        }
        else if (p.velX == 0 && p.velY > 0)
        {
            p.y = y - 10 - p.height;
        }
        else if (p.velX == 0 && p.velY < 0)
        {
            p.y = y + height + 10;
        }
        else
            System.out.println("Colision Error!!!");
        
        p.updateCorner();
    }

    @Override
    public String toString() {
        return "WorldPhaseEntity: " + charName;
    }
    
    
}
