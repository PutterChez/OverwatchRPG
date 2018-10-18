package gametest;

import java.awt.Color;
import java.awt.Graphics;

//HUD for displaying HP and such

public class HUD {
    
    public int HEALTH, MAX_HEALTH;

    public HUD(Entity p) {
        HEALTH = p.getHP();
        MAX_HEALTH = HEALTH;
    }
    
    public void tick(){ 
        //Health decrease animaiton test
        HEALTH --;
        //Clamp the health so it doesn't go below or above limit
        HEALTH = Game.clamp(HEALTH,0,MAX_HEALTH);
    }
    
    public void render(Graphics g){
        //HP Bar, gray background, green bar, and white outline
        g.setColor(Color.GRAY);
        g.fillRect(15, 15, 200, 32);
        g.setColor(Color.GREEN);
        g.fillRect(15, 15, HEALTH * 2, 32);
        g.setColor(Color.WHITE);
        g.drawRect(15, 15, 200, 32);
    }
}
