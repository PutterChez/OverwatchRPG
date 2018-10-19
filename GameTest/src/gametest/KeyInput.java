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
            
            if(tempObject.getId() == ID.PopUp){
                if(key == KeyEvent.VK_ENTER){
                    System.out.println("Enter pressed");
                    if(tempObject.getY() > 550){
                        tempObject.setY(550);
                        ((Menu) (tempObject)).setPop(true);
                    }
                }
                if(key == KeyEvent.VK_BACK_SPACE){
                    tempObject.setY(1000);
                    ((Menu) (tempObject)).setPop(false);
                }
            }
            //Temporary exit game method
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
