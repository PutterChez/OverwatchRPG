/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author DELL
 */
public class NPC extends WorldPhaseEntity{
    String dialogue;
    Menu dialogueBox;
    
    NPC(int x, int y, ID id, int width, int height, String imageDirectory, String charName)
    {
        super(x, y, id, width, height, imageDirectory, charName);
        dialogueBox = new Menu(-150, 700, ID.NPC, 1400, 200, "..\\resources\\ui\\game\\menu.png");
    }
    
    public void setDialogue(String script)
    {
        dialogue = script;
    }
    public String getDialogue(){ return dialogue; }
    
    public void setDialogueXY(int x, int y)
    {
        dialogueBox.x = x;
        dialogueBox.y = y;
    }
    
    public void interacted()
    {
        //Pop-Up here
        interacted = true;
        System.out.println(dialogue);
    }
    
    public void unInteracted(){ interacted = false; }
    
    public void render(Graphics g)
    {
        super.render(g);
        if(interacted)
        {
            dialogueBox.render(g);
            g.setFont(new Font("Minecraft Bold", Font.PLAIN, 30));
            g.setColor(Color.white);
            
            g.drawString(dialogue, dialogueBox.x + 100, dialogueBox.y + 100);
        }
    }
    
}
