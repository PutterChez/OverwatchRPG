package gametest;


import java.awt.Graphics;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class RenderModule {
    LinkedList<GameObject> renderList;
    
    RenderModule()
    {
        renderList = new LinkedList<>();
    }
    
    public void render(Graphics g)
    {
        for (GameObject o : renderList)
            if(o.alive())
                o.render(g);
        
        for (GameObject o : renderList)
            if(o.getName().equals("SelectionCursor"))
                o.render(g);
    }
    
    public void add(GameObject p)
    {
        renderList.add(p);
    }
    
    public GameObject search(String name)
    {
        for(GameObject o : renderList)
            if (o.getName().equals(name))
            {
                return o;
            }
        return null;
    }
    
    public void remove(String name)
    {
        for(GameObject o : renderList)
            if (o.getName().equals(name))
            {
                renderList.remove(o);
                break;
             }
    }
    
    public void bringToFront(String name)
    {
        for (GameObject o : renderList)
            if (o.getName().equals(name))
            {
                GameObject temp = o;
                renderList.remove(o);
                renderList.addLast(temp);
            }
    }
    
    public void sentToBack(String name)
    {
        for (GameObject o : renderList)
            if (o.getName().equals(name))
            {
                GameObject temp = o;
                renderList.remove(o);
                renderList.addFirst(temp);
            }
    }
}
