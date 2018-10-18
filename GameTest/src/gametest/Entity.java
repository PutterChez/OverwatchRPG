package gametest;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Entity extends GameObject {
    BasicEnemy target;
    Random r = new Random();
    public int HP,MP;
    private static int maxHP;
    public Entity(int x,int y,ID id,int width,int height,int HP,int MP){
        super(x,y,id,width,height);
        this.HP = HP;
        this.MP = MP;
    }
    
    public void tick(){
        x += velX;
        y += velY;
        
        x = Game.clamp(x, 0, Game.WIDTH - 37);
        y = Game.clamp(y, 0, Game.HEIGHT - 66);
    }
    
    public void render(Graphics g){
        if(id == ID.Player) g.setColor(Color.blue);
        else if(id == ID.Player2) g.setColor(Color.red);
            
        g.fillRect(x, y, width, height);
    }
    
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

    public BasicEnemy getTarget() {
        return target;
    }

    public void setTarget(BasicEnemy target) {
        this.target = target;
    }

    public static int getMaxHP() {
        return maxHP;
    }

    public static void setMaxHP(int maxHP) {
        Entity.maxHP = maxHP;
    }

    
}
