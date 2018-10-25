package gametest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

//HUD for displaying HP and such
public class HUD {

    private int x, y, nameX, nameY, velX, velY, speed;
    private Party party;
    
    public HUD(int x, int y, int nameX, int nameY,int speed, Party party) {
        this.x = x;
        this.y = y;
        this.nameX = nameX;
        this.nameY = nameY;
        this.party = party;
        this.speed = speed;
    }

    public void tick() {
        //Health decrease animaiton test
        if (y > 590) {
                velY = speed;
        } 
        else {
                velY = 0;
        }
    
        for (int i = 0; i < party.memberList.size(); i++) {
            int health, mp;
            health = party.memberList.get(i).entity.getHP();
            mp = party.memberList.get(i).entity.getMP();
            
            if(party.memberList.get(i).entity.getMaxHP() > 300){
                health += 3;
                mp += 1;
            }
            
            else{
                health += 2;
                mp += 3;
            }
            
            health = Game.clamp(health, 0, party.memberList.get(i).entity.getMaxHP());
            mp = Game.clamp(mp, 0, party.memberList.get(i).entity.getMaxMP());
            party.memberList.get(i).entity.setHP(health);
            party.memberList.get(i).entity.setMP(mp);
        }
        
        x += velX;
        y += velY;
    }

    public void render(Graphics g) {
        //HP Bar, gray background, green bar, and white outline
        for (int i = 0; i < party.memberList.size(); i++) {
            int barX = x + 100;
            int barY = y + 50 * (i + 1)+ 8;
            double hpPercent = (double)( party.memberList.get(i).entity.getHP()) / (double) (party.memberList.get(i).entity.getMaxHP());
            int currentHP = (int) (hpPercent * 200.0);
            
            g.setColor(Color.GRAY);
            g.fillRect(barX - 100, barY, 200, 16);
            g.setColor(Color.GREEN);
            g.fillRect(barX - 100, barY, currentHP, 16);
            g.setColor(Color.WHITE);
            g.drawRect(barX - 100, barY, 200, 16);

            g.setFont(new Font("Minecraft Bold", Font.PLAIN, 15));
            g.setColor(Color.white);

            g.drawString(party.memberList.get(i).entity.getCharName(), barX + nameX, barY + nameY);
            g.drawString(party.memberList.get(i).entity.getHP() + " / " + party.memberList.get(i).entity.getMaxHP(), barX - 90, barY + 14);
        }
        
        for (int i = 0; i < party.memberList.size(); i++) {
            int barX = x + 100;
            int barY = y + 50 * (i + 1) + 28;
            double mpPercent = (double)( party.memberList.get(i).entity.getMP()) / (double) (party.memberList.get(i).entity.getMaxMP());
            int currentMP = (int) (mpPercent * 200.0);
            
            g.setColor(Color.GRAY);
            g.fillRect(barX - 100, barY, 200, 16);
            g.setColor(Color.BLUE);
            g.fillRect(barX - 100, barY, currentMP, 16);
            g.setColor(Color.WHITE);
            g.drawRect(barX - 100, barY, 200, 16);

            g.setFont(new Font("Minecraft Bold", Font.PLAIN, 15));
            g.setColor(Color.white);
            
            g.drawString(party.memberList.get(i).entity.getMP() + " / " + party.memberList.get(i).entity.getMaxMP(), barX - 90, barY + 14);
        }
    }
}
