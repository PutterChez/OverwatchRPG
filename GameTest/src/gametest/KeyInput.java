/*
package gametest;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//Class for key inputs
public class KeyInput extends KeyAdapter {

    private Handler handler;
    private boolean PopUp = false;
    private int cursorPos = 0, finalPos = 620;
    
    public KeyInput(Handler handler) {
        this.handler = handler;
        
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        //Movement Control
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId() == ID.PopUp){
                if(key == KeyEvent.VK_ENTER){
                    if(tempObject.getY() > 550){
                        tempObject.setY(530);
                        PopUp = true;
                    }
                }
                if(key == KeyEvent.VK_BACK_SPACE){
                    tempObject.setY(1000);
                    PopUp = false;
                }
            }
            
            if(tempObject.getId() == ID.Cursor){
                if(PopUp == true){
                    if(key == KeyEvent.VK_DOWN){
                        if(cursorPos < 2)
                            cursorPos++;
                        
                        finalPos = (620 + cursorPos * 50);
                    }
                    
                    else if(key == KeyEvent.VK_UP){
                        if(cursorPos != 0)
                            cursorPos--;
                        
                        finalPos = (620 + cursorPos * 50);
                    }
                    
                    if(key == KeyEvent.VK_A){
                        if(cursorPos == 0){
                            System.out.println("Attack");
                        }
                        else if(cursorPos == 1){
                            System.out.println("Items");
                        }
                        else if(cursorPos == 2){
                            System.out.println("Run");
                        }
                    }
                    tempObject.setY(finalPos);
                }
                
                else{
                    tempObject.setY(1000);
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
*/
