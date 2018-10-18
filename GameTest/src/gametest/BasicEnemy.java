package gametest;

import java.awt.Color;
import java.awt.Graphics;

public class BasicEnemy extends GameObject implements Actor{
    int healthPoints, attackDamage;
    Color color;
    public BasicEnemy(int x, int y, ID id,int width,int height,int hp,int atkDmg,Color color) {
        super(x, y, id,width,height);
        this.healthPoints = hp;
        this.attackDamage = atkDmg;
        this.color = color;
    }
    
    public void tick(){
        x += velX;
        y += velY;
    }
    
    public void render(Graphics g){
        g.setColor(color);
        g.fillRect(x,y,width,height);
    }

    @Override
    public void act() {
        
    }

}
