package gametest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

//HUD for displaying HP and such

public class HUD {
    
    private int x,y;
    private ArrayList<Entity> party;
    
    public HUD(int x,int y,ArrayList<Entity> party) {
        this.x = x;
        this.y = y;
        this.party = party;
    }
    
    public void tick(){ 
        //Health decrease animaiton test
        for(int i = 0;i < party.size(); i++){
            int health = Game.clamp(party.get(i).getHP(),1,party.get(i).getMaxHP()-1);
            
            party.get(i).setHP(health+1);
        }
        //Clamp the health so it doesn't go below or above limit
        //HEALTH = Game.clamp(HEALTH,0,MAX_HEALTH);
    }
    
    public void render(Graphics g){
        //HP Bar, gray background, green bar, and white outline
        for(int i = 0;i < party.size(); i++){
            int barX = x + 100;
            int barY = y + 50 * (i + 1);
            g.setColor(Color.GRAY);
            g.fillRect(barX, barY, party.get(i).getMaxHP(), 32);
            g.setColor(Color.GREEN);
            g.fillRect(barX,barY, party.get(i).getHP(), 32);
            g.setColor(Color.WHITE);
            g.drawRect(barX, barY, party.get(i).getMaxHP(), 32);

            g.setFont(new Font("Minecraft Bold", Font.PLAIN, 30)); 
            g.setColor(Color.white);
            
            
            g.drawString(party.get(i).getCharName(),barX - 425, barY + 25);
            g.drawString(party.get(i).getHP() + " / " + party.get(i).getMaxHP(), barX + 5, barY + 25);
        }
    }
}
