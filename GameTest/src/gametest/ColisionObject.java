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
        super(x, y, id, width, height);
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
            for(int j = 0; j < cornerList.size(); j++)
            {
                Coordinate corner = cornerList.get(j);
                if (corner.x <= temp.x && corner.x + width >= temp.x && corner.y <= temp.y && corner.y + height >= temp.y)
                    return true;
            }
        }
        return false;
    }

    public void render(Graphics g) {
        objectImg = new ImageIcon(imageDirectory).getImage();
        g.drawImage(objectImg, x, y, width, height, null);
    }
    
}
