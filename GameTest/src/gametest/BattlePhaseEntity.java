package gametest;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

//Main entities of game: player, enemy, npc, etc.
public class BattlePhaseEntity extends GameObject {
    protected int HP, MP, maxHP, maxMP, speed, evasion, defense, attack;
    protected String charName;
    protected ArrayList<Skill> skillList;
    protected Skill selectSkill;
    protected boolean alive = true;

    private Image charImg;
    private String imageDirectory;

    public BattlePhaseEntity(int x, int y, ID id, int width, int height, String imageDirectory, int maxHP, int maxMP, String charName, int attack, int defense, int speed, int evasion) {
        super(x, y, width, height, charName, id);
        this.imageDirectory = imageDirectory;
        this.skillList = new ArrayList<Skill>();
        
        this.maxHP = maxHP;
        this.HP = maxHP;
        this.maxMP = maxMP;
        this.MP = maxMP;
        this.charName = charName;
        
        this.speed = speed;
        this.evasion = evasion;
        this.defense = defense;
        this.attack = attack;
    }

    public void tick() {
        if(HP <= 0)
        {
            HP = 0;
            die();
        }
            
        //x += velX;
        //y += velY;

        //Clamp function to not let entity go offscreen
        //x = Game.clamp(x, 0, Game.WIDTH - 37);
        //y = Game.clamp(y, 0, Game.HEIGHT - 66);
    }

    public void render(Graphics g) {
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
    public void act(String action) {
        if (action.equals("Run")) {
            System.out.println("The player flees!");
        } else if (action.equals("Attack")) {
            System.out.println("The player attacks!");
        } else if (action.equals("Items")) {
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

    public int getMP() {
        return MP;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    public int getMaxMP() {
        return maxMP;
    }

    public void setMaxMP(int maxMP) {
        this.maxMP = maxMP;
    }
    
    public void addSkill(Skill skill){
        this.skillList.add(skill);
    }

    public Skill getSelectSkill() {
        return selectSkill;
    }

    public void setSelectSkill(Skill selectSkill) {
        this.selectSkill = selectSkill;
    }
} 
