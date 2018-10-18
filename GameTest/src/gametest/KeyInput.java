package gametest;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
    
    private Handler handler;
    
    public KeyInput(Handler handler){
        this.handler = handler;
    }
    
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        
        /*Movement controls*/
        for(int i = 0;i < handler.object.size();i++){
            GameObject tempObject = handler.object.get(i);
            
            
            if(tempObject.getId() == ID.Player){
                //Key events for player 1
                if(key == KeyEvent.VK_Z){
                    ((Player) tempObject).act("Attack");
                    tempObject.setVelX(5);
                }
                if(key == KeyEvent.VK_X){
                    ((Player) tempObject).act("Items");
                    tempObject.setVelY(5);
                }
                if(key == KeyEvent.VK_C){
                    ((Player) tempObject).act("Run");
                    tempObject.setVelX(-5);
                }
            }
            
            if(key == KeyEvent.VK_ESCAPE) System.exit(1);
        }
        
    }
    
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        
        for(int i = 0;i < handler.object.size();i++){
            GameObject tempObject = handler.object.get(i);
            
            /*
            if(tempObject.getId() == ID.Player){
                if(key == KeyEvent.VK_Z){
                    tempObject.setVelX(0);
                }
                if(key == KeyEvent.VK_X){
                    tempObject.setVelY(0);
                }
                if(key == KeyEvent.VK_C){
                    tempObject.setVelX(0);
                }
            }*/
            
            if(tempObject.getId() == ID.Player2){
                //Key events for player 1
                if(key == KeyEvent.VK_UP) tempObject.setVelY(0);
                if(key == KeyEvent.VK_DOWN) tempObject.setVelY(0);
                if(key == KeyEvent.VK_RIGHT) tempObject.setVelX(0);
                if(key == KeyEvent.VK_LEFT) tempObject.setVelX(0);
            }
        }
    }
}
