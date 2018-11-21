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
public class Player extends WorldPhaseEntity{
    protected int initSpeed = 4;
    protected int currentSpeed = initSpeed;
    
    Player(int x, int y, ID id, int width, int height, String imageDirectory, String charName) {
        super(x, y, id, width, height, imageDirectory, charName);
    }
    
    public void resetSpeed()
    {
        currentSpeed = initSpeed;
    }
    
    public void colisionSpeed()
    {
        currentSpeed = -currentSpeed * 2;
    }

    public int getInitSpeed() {
        return initSpeed;
    }

    public void setInitSpeed(int initSpeed) {
        this.initSpeed = initSpeed;
    }
    
    
}
