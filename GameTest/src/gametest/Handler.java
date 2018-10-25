package gametest;

import java.util.LinkedList;
import java.awt.Graphics;

//Handler for rendering objects retrived from an objectlist
public class Handler {

    LinkedList<BaseObject> object = new LinkedList<BaseObject>();

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            BaseObject tempObject = object.get(i);

            tempObject.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            BaseObject tempObject = object.get(i);

            tempObject.render(g);
        }
    }

    public void addObject(BaseObject object) {
        this.object.add(object);
    }

    public void removeObject(BaseObject object) {
        this.object.remove(object);
    }
}
