package gametest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

//HUD for displaying HP and such
public class HUD extends GameObject{

    private int nameX, nameY, velX, velY, speed, limit, gap;
    protected Party party;
    protected int originalSize;
    protected boolean showSkills = false;
    protected int changeColor = 0;
    protected int selectedPlayer = 0;
    protected String name;
    
    public HUD(int x, int y, int nameX, int nameY,int speed,int limit,int gap, Party party, String name) {
        super(x, y, 100, 100, "HUD", ID.Background);
        this.nameX = nameX;
        this.nameY = nameY;
        this.party = party;
        this.speed = speed;
        this.limit = limit;
        this.gap = gap;
        this.originalSize = party.memberList.size();
        this.name = name;
    }

    public void tick() {
    }
    
    public void render(Graphics g) {
        if(showSkills == true){
            g.setFont(new Font("Minecraft Bold", Font.PLAIN, 30));
            g.setColor(Color.BLACK);
            g.drawString(party.memberList.get(selectedPlayer).entity.getCharName(), x - 420, y + 55);
            g.setFont(new Font("Minecraft Bold", Font.PLAIN, 25));
            g.setColor(Color.white);
            g.drawString("Skills : ", x - 560, y + 90);
            
            Color textColor;
            g.setFont(new Font("Minecraft Bold", Font.PLAIN, 20));
            
            
            
            for(int i = 1;i < party.memberList.get(selectedPlayer).entity.skillList.size()+1;i++){
                //Changing text color
                changeColor++;
                
                if(changeColor < 50){
                    textColor = party.memberList.get(selectedPlayer).entity.skillList.get(i-1).getColor();
                }
                else if(changeColor < 100){
                    textColor = Color.WHITE;
                }
                else{
                    textColor = party.memberList.get(selectedPlayer).entity.skillList.get(i-1).getColor();
                    changeColor = 0;
                }
                
                //If skill is an ultimate, change color
                if(!party.memberList.get(selectedPlayer).entity.skillList.get(i-1).isUltimate()){
                    g.setColor(party.memberList.get(selectedPlayer).entity.skillList.get(i-1).getColor());
                }
                
                else{
                    g.setColor(textColor);
                }
                
                g.drawString(party.memberList.get(selectedPlayer).entity.skillList.get(i-1).skillName, x - 560, y + 90 + (30* i));
            }
        }
        
        
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
                if((i == 3) && (originalSize > 3) && (name.equals("Enemy Party"))){
                    barX = x + 350;
                    barY = y + gap * 1 + 8;
                }
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
            int barX = super.x + 100;
            int barY = y + gap * (i + 1) + 28;
            
            if((party.memberList.size() > 4) && (i >= 3)){
                barX = x + 350;
                barY = y + gap * (i - 3 + 1)+ 28;
            }
            else{
                barY = y + gap * (i + 1)+ 28;
                if((i == 3) && (originalSize > 3) && (name.equals("Enemy Party"))){
                    barX = x + 350;
                    barY = y + gap * 1 + 28;
                }
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

    public boolean isShowSkills() {
        return showSkills;
    }

    public void setShowSkills(boolean showSkills) {
        this.showSkills = showSkills;
    }

    public int getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(int selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }
}
