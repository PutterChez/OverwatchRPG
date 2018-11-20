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
    List<GameObject> renderList;
    
    RenderModule()
    {
        renderList = new ArrayList<>();
    }
    
    public void render(Graphics g)
    {
        for (GameObject o : renderList)
            o.render(g);
    }
    
    public void add(GameObject p)
    {
        renderList.add(p);
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
}
