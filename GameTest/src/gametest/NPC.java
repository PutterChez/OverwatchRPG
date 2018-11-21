/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest;

/**
 *
 * @author DELL
 */
public class NPC extends WorldPhaseEntity{
    String dialogue;
    
    NPC(int x, int y, ID id, int width, int height, String imageDirectory, String charName)
    {
        super(x, y, id, width, height, imageDirectory, charName);
    }
    
    protected void setDialogue(String script)
    {
        dialogue = script;
    }
    
    public void interacted()
    {
        //Pop-Up here
        System.out.println(dialogue);
    }
    
}
