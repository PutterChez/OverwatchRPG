package gametest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */

import java.util.*;
import java.awt.Graphics;


//Handler for rendering objects retrived from an objectlist
public class Handler {
    boolean interacting = false;
    boolean battlePhase = false;
    boolean colision = false;
    boolean inventory = false;
    boolean merchantInteracting = false;
    boolean openUI = false;
    
    Sound bgm;
    
    RenderModule battleRender;
    RenderModule worldRender;
    
    LinkedList<GameObject> objectList;
    LinkedList<WorldPhaseEntity> colisionList; 
    
    Handler()
    {
        this.battleRender = new RenderModule();
        this.worldRender = new RenderModule();
        objectList = new LinkedList<>();
        colisionList = new LinkedList<>();
        bgm = new Sound();
    }
    
    public void tick() {
        for (GameObject obj : objectList)
        {
            obj.tick();
        }   
    }

    public void render(Graphics g) {
        if(battlePhase)  
            battleRender.render(g);
        else
            worldRender.render(g);          
    }
    
    public void updateBattleObject(WorldPhaseEntity player)
    {
        for(GameObject obj : battleRender.renderList)
        {
            obj.x += player.velX;
            obj.y += player.velY;
        }
    }
    
    public void addBattlePhaseObject(GameObject p)
    {
       battleRender.add(p);
       objectList.add(p);
    }
    
    //add PassableObject to WorldPhase
    public void addWorldPhaseObject(GameObject p)
    {
        worldRender.add(p);
        objectList.add(p);
    }
    
    //add ImpassableObject to WorldPhase
    public void addWorldColisionObject(WorldPhaseEntity p)
    {
        worldRender.add(p);
        colisionList.add(p);
    }
    
    public void removeObject(String name)
    {
        worldRender.remove(name);
        battleRender.remove(name);
        
        for(GameObject o : objectList)
            if (o.getName().equals(name))
            {
                objectList.remove(o);
                break;
            }
    }
    
    public void removeColisionObject(String name)
    {
        for(WorldPhaseEntity o : colisionList)
            if(o.getName().equals(name))
            {
                colisionList.remove(o);
                break;
            }
    }
    
    public boolean checkColision(WorldPhaseEntity p)
    {
        for(WorldPhaseEntity c: colisionList)
            if(c.checkColision(p))
                return true;
        return false;
    }
    
    public void playBGM()
    {
        bgm.play();
    }
    
    public void stopBGM()
    {
        bgm.stop();
    }
    
    public void setBGM(String url)
    {
        bgm.setSoundDirectory(url);
    }
      
    public void battlePhaseOn(){ battlePhase = true;}
    public void battlePhaseOff(){ battlePhase = false;}
    public boolean battlePhaseStatus(){ return battlePhase; }
    
    public void colisionDetected(){ colision = true; }
    public void colisionNotDetected(){ colision = false; }
    public boolean colisionStatus(){ return colision; }
    
    public void interacted(){ interacting = true; }
    public void uninteracted() { interacting = false; }
    public boolean interactStatus() { return interacting; }
    
    public void inventoryOpen(){ inventory = true; }
    public void inventoryClose() { inventory = false; }
    public boolean inventoryStatus() { return inventory; }
    
    public void merchantOpen(){ merchantInteracting = true; }
    public void merchantClose() { merchantInteracting = false; }
    public boolean merchantStatus() { return merchantInteracting; }
    
    public void UIOpen(){ openUI = true; }
    public void UIClose(){ openUI = false; }
    public boolean UIStatus(){ return openUI; }
    
    
}

