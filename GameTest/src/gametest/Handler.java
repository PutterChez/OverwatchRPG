package gametest;

import java.util.LinkedList;
import java.awt.Graphics;

//Handler for rendering objects retrived from an objectlist
public class Handler {

    LinkedList<GameObject> object = new LinkedList<GameObject>();
    LinkedList<GameObject> colisionObject = new LinkedList<GameObject>();

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.render(g);
        }
    }
    
    public void colisionDetection(WorldPhaseEntity p)
    {
        for (int i = 0; i < colisionObject.size(); i++)
        {
            ;
            //Pseudo Code
            // if(colisionObject.get(i).checkColision
        }
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }
    
    public void addColisionObject(GameObject object)
    {
        this.colisionObject.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }
    
    public void removeColisionObject(GameObject object)
    {
        this.colisionObject.remove(object);
    }
}
