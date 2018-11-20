package gametest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
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

    WorldPhaseEntity(int x, int y, int width, int height, String charName, String imageDirectory, ID id) {
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

    public void tick() {
        x += velX;
        y += velY;

        for(int i = 0; i < cornerList.size(); i++)
        {
              cornerList.get(i).x += velX;
              cornerList.get(i).y += velY;
        }
    }

    public void render(Graphics g) {
        charImg = new ImageIcon(imageDirectory).getImage();
        g.drawImage(charImg, x, y, width, height, null);
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
}
