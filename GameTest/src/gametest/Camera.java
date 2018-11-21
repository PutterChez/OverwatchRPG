package gametest;

import java.awt.Graphics;

public class Camera extends GameObject{
    WorldPhaseEntity player;
    public Camera(int x, int y, ID id, int width, int height, WorldPhaseEntity player) {
        super(x, y, width, height, "Camera", ID.Camera);
        this.player = player;
    }
    
   
    public void tick(){
        x = -player.getX() + Game.WIDTH/2 - player.width/2;
        y = -player.getY() + Game.HEIGHT/2 - player.height/2;
    }
    
    public void render(Graphics g){
        ;
    }
    
}
