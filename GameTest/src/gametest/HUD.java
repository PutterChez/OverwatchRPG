package gametest;

import java.awt.Color;
import java.awt.Graphics;

//HUD for displaying HP and such

public class HUD {
    
    public int HEALTH, MAX_HEALTH;
    private Entity p;
    
    public HUD(Entity p) {
        HEALTH = p.getHP();
        MAX_HEALTH = HEALTH;
        this.p = p;
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
        g.fillRect(p.getX() + 100, p.getY(), 200, 32);
        g.setColor(Color.GREEN);
        g.fillRect(p.getX() + 100, p.getY(), HEALTH * 2, 32);
        g.setColor(Color.WHITE);
        g.drawRect(p.getX() + 100, p.getY(), 200, 32);
    }
}
