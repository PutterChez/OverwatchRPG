package gametest;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {
    
    public int HEALTH, MAX_HEALTH;

    public HUD(Entity p) {
        HEALTH = p.HP;
        MAX_HEALTH = HEALTH;
    }
    
    public void tick(){  
        HEALTH --;
        
        HEALTH = Game.clamp(HEALTH,0,MAX_HEALTH);
    }
    
    public void render(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(15, 15, 200, 32);
        g.setColor(Color.GREEN);
        g.fillRect(15, 15, HEALTH * 2, 32);
        g.setColor(Color.WHITE);
        g.drawRect(15, 15, 200, 32);
    }
}
