/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest;

import java.awt.Color;
import java.awt.Font;
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
public class Player extends WorldPhaseEntity{
    protected int currentSpeed = 4;
    protected Direction direction = Direction.North;
    protected WorldPhaseEntity interactW, interactA, interactS, interactD;
    protected LinkedList<WorldPhaseEntity> interactList; 
    protected boolean interacting;
    protected Menu dialogueBox;
    protected String dialogue;
    
    Player(int x, int y, ID id, int width, int height, String imageDirectory, String charName) {
        super(x, y, id, width, height, imageDirectory, charName);
        interactW = new WorldPhaseEntity(x, y, ID.Default, width, height,"..\\resources\\characters\\BlueSquare.png", "interactArea" );
        interactA = new WorldPhaseEntity(x, y, ID.Default, width, height,"..\\resources\\characters\\BlueSquare.png", "interactArea" );
        interactS = new WorldPhaseEntity(x, y, ID.Default, width, height,"..\\resources\\characters\\BlueSquare.png", "interactArea" );
        interactD = new WorldPhaseEntity(x, y, ID.Default, width, height,"..\\resources\\characters\\BlueSquare.png", "interactArea" );
        
        interactList = new LinkedList<>();
        interactList.add(interactW);
        interactList.add(interactA);
        interactList.add(interactS);
        interactList.add(interactD);
        
        interactW.y -= interactW.height;
        interactA.x -= interactW.width; 
        interactS.y += height;
        interactD.x += width;
        
        //Please adjust x and y
        dialogueBox = new Menu(x, y, ID.Default, 1400, 250, "..\\resources\\ui\\game\\hud_box_new.png");
    }
    
    public WorldPhaseEntity getInteractArea()
    {
        switch(direction)
        {
            case North: return interactW;
            case West: return interactA;
            case South: return interactS;
            case East: return interactD;
        }
        return null;
    }        
            
    /* RIP DOES NOT 
    public boolean checkInteractColision(WorldPhaseEntity e)
    {
        switch(direction)
        {
            case North: return interactW.checkColision(e);
            case West: return interactA.checkColision(e);
            case South: return interactS.checkColision(e);
            case East: return interactD.checkColision(e);
        }
        return false;
    }
    */
    
    public void setDirection(int side)
    {
        if (side == 0)
            direction = Direction.North;
        else if (side == 1)
            direction = Direction.West;
        else if (side == 2)
            direction = Direction.South;
        else if (side == 3)
            direction = Direction.East;
        else
            System.out.println("Direcction Error");
    }
    
    /*
    public void setInteractAreaDirection(int side) 
    {
        resetInteractAreaPosition();
        // W = 0, A = 1, S = 2, D = 3
        if (side == 0)
        {
            interactW.y -= interactW.height;
        }
        else if (side == 1)
        {
            interactW.x -= interactW.width; 
        }
        else if (side == 2)
        {
            interactW.y += height;
        }
        else if (side == 3)
        {
            interactW.x += width;
        }
        else
        {
            System.out.println("Interaction Error");
        }
        interactW.updateCorner();
    }
    
    public void resetInteractAreaPosition()
    {
        interactW.x = x;
        interactW.y = y;
        interactW.updateCorner();
    }
    */
    
    public void tick() {
        x += velX;
        y += velY;
        updateCorner();
        
        for (WorldPhaseEntity interact : interactList)
        {
            interact.x = x;
            interact.y = y;
        }
        
        interactW.y -= interactW.height;
        interactA.x -= interactW.width; 
        interactS.y += height;
        interactD.x += width;
        
        for (WorldPhaseEntity interact : interactList)
            interact.updateCorner();
        
        setDialogueBoxPosition(x - 700, y + 200);
    }
    
    public void render(Graphics g)
    {
        super.render(g);
        
        switch(direction)
        {
            case North: interactW.render(g); break;
            case West: interactA.render(g); break;
            case South: interactS.render(g); break;
            case East: interactD.render(g); break;
        }
        
        if(interacted)
        {
            dialogueBox.render(g);
            g.setFont(new Font("Minecraft Bold", Font.PLAIN, 30));
            g.setColor(Color.white);
            
            g.drawString(dialogue, dialogueBox.x + 100, dialogueBox.y + 100);
        }
    }
    
    public void setDialogueBoxPosition(int x, int y)
    {
        dialogueBox.x = x;
        dialogueBox.y = y;
    }
    
    public void setDialogue(String s)
    {
        dialogue = s;
    }
    
    public void interacted(){ interacted = true; }
    
    public void unInteracted(){ interacted = false; }
    
    
}
