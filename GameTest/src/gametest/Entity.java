package gametest;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

//Main entities of game: player, enemy, npc, etc.

public class Entity extends GameObject {
    Random r = new Random();
    
    protected int HP,MP,speed,evasion,defense,attack;
    protected static int maxHP, maxMP;
    protected String charName;
    protected ArrayList<Skill> skillList;
    
    public Entity(int x,int y,ID id,int width,int height,int HP,int MP){
        super(x,y,id,width,height);
        this.HP = HP;
        this.MP = MP;
    }



    public void tick(){
        x += velX;
        y += velY;
        
        //Clamp function to not let entity go offscreen
        x = Game.clamp(x, 0, Game.WIDTH - 37);
        y = Game.clamp(y, 0, Game.HEIGHT - 66);
    }
    
    public void render(Graphics g){
        if(id == ID.Player) g.setColor(Color.blue);
            
        g.fillRect(x, y, width, height);
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

    public static int getMaxHP() {
        return maxHP;
    }

    public static void setMaxHP(int maxHP) {
        Entity.maxHP = maxHP;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    
}
