package gametest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

//HUD for displaying HP and such
public class HUD {

    private int x, y, velX, velY;
    private ArrayList<Entity> party;
    
    public HUD(int x, int y, ArrayList<Entity> party) {
        this.x = x;
        this.y = y;
        this.party = party;
    }

    public void tick() {
        //Health decrease animaiton test
        if (y > 590) {
                velY = -5;
        } 
        else {
                velY = 0;
        }
    
        for (int i = 0; i < party.size(); i++) {
            int health, mp;
            health = party.get(i).getHP();
            mp = party.get(i).getMP();
            if(party.get(i).getId() == ID.Rein){
                health += 3;
                mp += 1;
            }
            
            else{
                health += 2;
                mp += 3;
            }
            health = Game.clamp(health, 0, party.get(i).getMaxHP());
            mp = Game.clamp(mp, 0, party.get(i).getMaxMP());
            party.get(i).setHP(health);
            party.get(i).setMP(mp);
        }
        
        x += velX;
        y += velY;
    }

    public void render(Graphics g) {
        //HP Bar, gray background, green bar, and white outline
        for (int i = 0; i < party.size(); i++) {
            int barX = x + 100;
            int barY = y + 50 * (i + 1)+ 8;
            double hpPercent = (double)( party.get(i).getHP()) / (double) (party.get(i).getMaxHP());
            int currentHP = (int) (hpPercent * 200.0);
            
            g.setColor(Color.GRAY);
            g.fillRect(barX - 100, barY, 200, 16);
            g.setColor(Color.GREEN);
            g.fillRect(barX - 100, barY, currentHP, 16);
            g.setColor(Color.WHITE);
            g.drawRect(barX - 100, barY, 200, 16);

            g.setFont(new Font("Minecraft Bold", Font.PLAIN, 15));
            g.setColor(Color.white);

            g.drawString(party.get(i).getCharName(), barX - 425, barY + 15);
            g.drawString(party.get(i).getHP() + " / " + party.get(i).getMaxHP(), barX - 90, barY + 14);
        }
        
        for (int i = 0; i < party.size(); i++) {
            int barX = x + 100;
            int barY = y + 50 * (i + 1) + 28;
            double mpPercent = (double)( party.get(i).getMP()) / (double) (party.get(i).getMaxMP());
            int currentMP = (int) (mpPercent * 200.0);
            
            g.setColor(Color.GRAY);
            g.fillRect(barX - 100, barY, 200, 16);
            g.setColor(Color.BLUE);
            g.fillRect(barX - 100, barY, currentMP, 16);
            g.setColor(Color.WHITE);
            g.drawRect(barX - 100, barY, 200, 16);

            g.setFont(new Font("Minecraft Bold", Font.PLAIN, 15));
            g.setColor(Color.white);
            
            g.drawString(party.get(i).getMP() + " / " + party.get(i).getMaxMP(), barX - 90, barY + 14);
        }
    }
}
