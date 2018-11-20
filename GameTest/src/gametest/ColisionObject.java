/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author DELL
 */
public class ColisionObject extends GameObject{
    private Image objectImg;
    private String imageDirectory;
    protected List<Coordinate> cornerList;
    
    ColisionObject(int x, int y, ID id, int width, int height) {
        super(x, y, width, height, "ColisionObject", id);
        cornerList = new ArrayList<>();
        
        //Coordinate x and y for each corner
        cornerList.add(new Coordinate(x, y));
        cornerList.add(new Coordinate(x + width, y));
        cornerList.add(new Coordinate(x, y + height));
        cornerList.add(new Coordinate(x + width, y + height));
        
        //cornerList index for each corner
        // 0 1
        // 2 3
    }
    
    public void tick() {
        x += velX;
        y += velY;
        
        //Update cornerList Position
        for(int i = 0; i < cornerList.size(); i++)
        {
              cornerList.get(i).x += velX;
              cornerList.get(i).y += velY;
        }
        
        
        //for(int i = 0; i < cornerList.size(); i++)
        //{
        //      System.out.println("Colision Object Corner " + i + " :" + cornerList.get(i).x + "|" + cornerList.get(i).y );
        //}
        
        //Clamp function to not let entity go offscreen
        //x = Game.clamp(x, 0, Game.WIDTH - 37);
        //y = Game.clamp(y, 0, Game.HEIGHT - 66);
    }
    
    public void setImageDirectory(String directory)
    {
        imageDirectory = directory;
    }
    
    public boolean checkColision(WorldPhaseEntity p)
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

    public void render(Graphics g) {
        objectImg = new ImageIcon(imageDirectory).getImage();
        g.drawImage(objectImg, x, y, width, height, null);
    }
    
}
