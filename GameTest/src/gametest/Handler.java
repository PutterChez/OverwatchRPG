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
    boolean battlePhase = false;
    
    RenderModule battleRender;
    RenderModule worldRender;
    
    List<GameObject> objectList;
    
    Handler()
    {
        this.battleRender = new RenderModule();
        this.worldRender = new RenderModule();
        objectList = new ArrayList<>();
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
    
    public void addBattlePhaseObject(GameObject p)
    {
       battleRender.add(p);
       objectList.add(p);
    }
    
    public void addWorldPhaseObject(GameObject p)
    {
        worldRender.add(p);
        objectList.add(p);
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
    
    public void battlePhaseOn(){ battlePhase = true;}
    public void battlePhaseOff(){ battlePhase = false;}
    public boolean battlePhaseStatus(){ return battlePhase; }
    
}

