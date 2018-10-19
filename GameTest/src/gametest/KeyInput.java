package gametest;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//Class for key inputs
public class KeyInput extends KeyAdapter {

    private Handler handler;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        /*Movement controls*/
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Hero) {
                //Key events for player 1
                if (key == KeyEvent.VK_Z) {
                    ((Entity) tempObject).act("Attack");
                }
                if (key == KeyEvent.VK_X) {
                    ((Entity) tempObject).act("Items");
                }
                if (key == KeyEvent.VK_C) {
                    ((Entity) tempObject).act("Run");
                }
            }

            if (key == KeyEvent.VK_ESCAPE) {
                System.exit(1);
            }
        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

        }
    }
}
