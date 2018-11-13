package gametest;

import java.util.LinkedList;
import java.awt.Graphics;

//Handler for rendering objects retrived from an objectlist
public class Handler {

    LinkedList<GameObject> object = new LinkedList<GameObject>();
    LinkedList<ColisionObject> colisionObject = new LinkedList<ColisionObject>();

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
    
    public boolean colisionDetection(WorldPhaseEntity p)
    {
        for (int i = 0; i < colisionObject.size(); i++)
        {
            if (colisionObject.get(i).checkColision(p))
            {
                return true;
            }
        }
        return false;
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }
    
    public void addColisionObject(ColisionObject object)
    {
        this.colisionObject.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }
    
    public void removeColisionObject(ColisionObject object)
    {
        this.colisionObject.remove(object);
    }
}
