package gametest;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

//Main entities of game: player, enemy, npc, etc.

public class Entity extends GameObject {
    Random r = new Random();
    
    protected int HP,MP,maxHP, maxMP,speed,evasion,defense,attack;
    protected String charName;
    protected ArrayList<Skill> skillList;
    
    private Image charImg;
    private String imageDirectory;
    
    
    public Entity(int x,int y,ID id,int width,int height,String imageDirectory, int maxHP,int maxMP,String charName){
        super(x,y,id,width,height);
        this.imageDirectory = imageDirectory;
        
        this.maxHP = maxHP;
        this.HP = maxHP;
        this.maxMP = maxMP;
        this.MP = maxMP;
        this.charName = charName;
    }



    public void tick(){
        x += velX;
        y += velY;
        
        //Clamp function to not let entity go offscreen
        x = Game.clamp(x, 0, Game.WIDTH - 37);
        y = Game.clamp(y, 0, Game.HEIGHT - 66);
    }
    
    public void render(Graphics g){
        /* Old Square Player
        if(id == ID.Player){
            g.setColor(Color.blue);
            g.fillRect(x, y, width, height);
        }*/
        
        
        //New Texture
        charImg = new ImageIcon(imageDirectory).getImage();
        g.drawImage(charImg, x, y, width, height, null);
    }
    
    //Act test function
    public void act(String action){
        if(action.equals("Run")){
            System.out.println("The player flees!");
        }
        else if(action.equals("Attack")){
            System.out.println("The player attacks!");
        }
        else if(action.equals("Items")){
            System.out.println("The player uses an item!");
        }
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }
}
