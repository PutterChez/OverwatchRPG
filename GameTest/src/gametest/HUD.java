package gametest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

//HUD for displaying HP and such
public class HUD extends GameObject{

    private int x, y, nameX, nameY, velX, velY, speed, limit, gap;
    private Party party;
    
    public HUD(int x, int y, int nameX, int nameY,int speed,int limit,int gap, Party party) {
        super(x, y, 100, 100, "HUD", ID.Background);
        this.nameX = nameX;
        this.nameY = nameY;
        this.party = party;
        this.speed = speed;
        this.limit = limit;
        this.gap = gap;
    }

    public void tick() {
        //Health decrease animaiton test
        if (y > limit) {
                velY = speed;
        } 
        else {
                velY = 0;
        }
    
        x += velX;
        y += velY;
    }

    public void render(Graphics g) {
        //HP Bar, gray background, green bar, and white outline
        for (int i = 0; i < party.memberList.size(); i++) {
            int barX = x + 100;
            int barY = y + gap * (i + 1)+ 8;
            
            if((party.memberList.size() > 4) && (i >= 3)){
                barX = x + 350;
                barY = y + gap * (i - 3 + 1)+ 8;
            }
            else{
                barY = y + gap * (i + 1)+ 8;
            }
            
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
            int barY = y + gap * (i + 1) + 28;
            
            if((party.memberList.size() > 4) && (i >= 3)){
                barX = x + 350;
                barY = y + gap * (i - 3 + 1)+ 28;
            }
            else{
                barY = y + gap * (i + 1)+ 28;
            }
            
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
