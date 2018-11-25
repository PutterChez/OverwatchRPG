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
    protected int currentSpeed = 6;
    protected Direction direction = Direction.North;
    protected WorldPhaseEntity interactW, interactA, interactS, interactD;
    protected LinkedList<WorldPhaseEntity> interactList; 
    
    protected boolean interacting;
    protected boolean inventoryStatus = false;
    protected boolean partyView = false;
    
    protected Party playerParty;
    
    protected Menu dialogueBox;
    protected Menu partyViewer;
    protected String dialogue;
    protected LinkedList<String> imgList;
    protected Inventory inventory;
    
    Player(int x, int y, ID id, int width, int height, String imageDirectory, String charName) {
        super(x, y, id, width, height, imageDirectory, charName);
        interactW = new WorldPhaseEntity(x, y, ID.Default, 35, 35,"..\\resources\\characters\\BlueSquare.png", "interactArea" );
        interactA = new WorldPhaseEntity(x, y, ID.Default, 35, 35,"..\\resources\\characters\\BlueSquare.png", "interactArea" );
        interactS = new WorldPhaseEntity(x, y, ID.Default, 35, 35,"..\\resources\\characters\\BlueSquare.png", "interactArea" );
        interactD = new WorldPhaseEntity(x, y, ID.Default, 35, 35,"..\\resources\\characters\\BlueSquare.png", "interactArea" );
        
        interactList = new LinkedList<>();
        interactList.add(interactW);
        interactList.add(interactA);
        interactList.add(interactS);
        interactList.add(interactD);
        
        /*
        interactW.y -= interactW.height;
        interactA.x -= interactW.width;
        interactS.y += height; 
        interactD.x += width;
        */
        
        //Please adjust x and y
        dialogueBox = new Menu(x, y, ID.Default, 1400, 200, "..\\resources\\ui\\hud_box_full_res.png");
        partyViewer = new Menu(x, y, ID.Menu, 1000, 571, "..\\resources\\misc\\party_ui.png");
        
        imgList = new LinkedList<>();
        //North
        imgList.add("..\\resources\\player\\player_back_1.png");
        imgList.add("..\\resources\\player\\player_back_2.png");
        imgList.add("..\\resources\\player\\player_back_3.png");
        //West
        imgList.add("..\\resources\\player\\player_left_1.png");
        imgList.add("..\\resources\\player\\player_left_2.png");
        imgList.add("..\\resources\\player\\player_left_3.png");
        //South
        imgList.add("..\\resources\\player\\player_front_1.png");
        imgList.add("..\\resources\\player\\player_front_2.png");
        imgList.add("..\\resources\\player\\player_front_3.png");
        //East
        imgList.add("..\\resources\\player\\player_right_1.png");
        imgList.add("..\\resources\\player\\player_right_2.png");
        imgList.add("..\\resources\\player\\player_right_3.png");
        
        //Inventory
        inventory = new Inventory();
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
    
    public void addItem(Item e)
    {
        if(inventory.itemList.size() >= 21)
            System.out.println("Exceed Storage limit");
        inventory.addItem(e);
    }
    
    public void removeItem(String name)
    {
        inventory.removeItem(name);
    }
    
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
    
    public void addPicture(String imgDirect)
    {
        imgList.add(imgDirect);
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
            interact.x = x + width/2 - width/4;
            interact.y = y + height/2 - height/4;
        }
        

        interactW.y -= interactW.height + height/4;
        interactW.x -= 5;
        
        interactA.x -= interactW.width + width/4 + 1; 
        
        interactS.y += height - height/4;
        interactS.x -= 5;
        
        interactD.x += width - width/4 - 1;

        
        for (WorldPhaseEntity interact : interactList)
            interact.updateCorner();
        
        setDialogueBoxPosition(x - 700, y + 200);
        inventory.setInventoryPosition(x - 500, y - 300);
        partyViewer.x = x - 500;
        partyViewer.y = y - 300;
    }
    
    public void render(Graphics g)
    {
        switch(direction)
        {
            case North: imageDirectory = imgList.get(1); break;
            case West: imageDirectory = imgList.get(4); break;
            case South: imageDirectory = imgList.get(7); break;
            case East: imageDirectory = imgList.get(10); break;
        }
        
        //RedSquare for testing
        //imageDirectory = "..\\resources\\characters\\RedSquare.png";
        
        charImg = new ImageIcon(imageDirectory).getImage();
        g.drawImage(charImg, x, y, width, height, null);
        
        /* Interaction Test
        switch(direction)
        {
            case North: interactW.render(g); break;
            case West: interactA.render(g); break;
            case South: interactS.render(g); break;
            case East: interactD.render(g); break;
        }
        */
    }
    
    public void renderMenu(Graphics g){
        if(interacted)
        {
            dialogueBox.render(g);
            g.setFont(new Font("Minecraft Bold", Font.PLAIN, 30));
            g.setColor(Color.white);
            
            g.drawString(dialogue, dialogueBox.x + 100, dialogueBox.y + 100);
        }
        
        //Render Inventory
        if(inventoryStatus)
        {
            inventory.itemViewer.render(g);
            g.setFont(new Font("Minecraft Bold", Font.PLAIN, 25));
            g.setColor(Color.white);
            
            
            int posX = inventory.itemViewer.x + 125;
            int posY = inventory.itemViewer.y + 185;
            int rowCount = 0;
            
            for(Item e : inventory.itemList)
            {
                g.drawString(e.itemName, posX, posY);
                posX += 200;
                rowCount += 1;
                if(rowCount == 4)
                {
                    rowCount = 0;
                    posY += 70;
                    posX = inventory.itemViewer.x + 125;
                }
            }        
            
            g.setFont(new Font("Minecraft Bold", Font.PLAIN, 30));
            g.setColor(Color.yellow);
            g.drawString("Money : " + inventory.currentMoney + " $", x + 150, y - 200);
        }
        
        if(partyView)
        {
            partyViewer.render(g);
            
            int posX, posY, rowCount = 0;
            posX = partyViewer.x + 110;
            posY = partyViewer.y + 185;
            
            for(PartyMember p : playerParty.memberList)
            {
                imageDirectory = p.entity.getCharIcon();
                charImg = new ImageIcon(imageDirectory).getImage();
                g.drawImage(charImg, posX - 30, posY - 30, 100, 100, null);
                
                int offset = 90;
                
                g.setColor(Color.cyan);
                g.setFont(new Font("Minecraft Bold", Font.PLAIN, 30));
                g.drawString(p.entity.getCharName(), posX + offset, posY);
                
                g.setColor(Color.white);
                g.setFont(new Font("Minecraft Bold", Font.PLAIN, 20));
                
                g.drawString("HP: " + p.entity.getHP(), posX + offset, posY + 30);
                g.drawString("MP: " + p.entity.getMP(), posX + 140 + offset, posY + 30);
                
                g.drawString("Attack: " + p.entity.getAttack(), posX + offset, posY + 50);
                g.drawString("Defense: " + p.entity.getDefense(), posX + 140 + offset, posY + 50);
                
                g.drawString("Speed: " + p.entity.getSpeed(), posX + offset, posY + 70);
                g.drawString("Evasion: " + p.entity.getEvasion(), posX + 140 + offset, posY + 70);
                
                rowCount++;
                posY += 175;
                
                if(rowCount >= 2)
                {
                    posX += 420;
                    posY = partyViewer.y + 185;
                    rowCount = 0;
                }
            }
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
    
    public void inventoryOpen(){ inventoryStatus = true; }
    public void inventoryClose(){ inventoryStatus = false; }
    
    public void partyViewOpen() {partyView = true;}
    public void partyViewClosed(){partyView = false;}

    public Party getPlayerParty() {
        return playerParty;
    }

    public void setPlayerParty(Party playerParty) {
        this.playerParty = playerParty;
    }
    
    
}
