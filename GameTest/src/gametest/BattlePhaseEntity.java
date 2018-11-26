package gametest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

//Main entities of game: player, enemy, npc, etc.
public class BattlePhaseEntity extends GameObject implements Comparable {
    protected int HP, MP, maxHP, maxMP, speed, evasion, defense, attack;
    protected String charName;
    protected ArrayList<Skill> skillList;
    protected Skill selectSkill;
    protected boolean alive = true;
    protected boolean missed = false;

    private Image charImg;
    private String imageDirectory;
    private String charIcon;
    
    protected BattlePhaseEntity target;

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
        //New Texture
        charImg = new ImageIcon(imageDirectory).getImage();
        g.drawImage(charImg, x, y, width, height, null);
        
        
        g.setFont(new Font("Minecraft Bold", Font.PLAIN, 30));
        g.setColor(Color.WHITE);
            
        if(missed == true){
            g.setFont(new Font("Minecraft Bold", Font.PLAIN, 30));
            g.setColor(Color.WHITE);
            g.drawString("Missed!", x, y - height/2 + 180);
        }
    }

    public String getCharIcon() {
        return charIcon;
    }

    public void setCharIcon(String charIcon) {
        this.charIcon = charIcon;
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
    
    @Override
    public int compareTo(Object compareEntity) {
        int compareSpeed = ((BattlePhaseEntity)compareEntity).getSpeed();
        
        return compareSpeed - this.speed;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEvasion() {
        return evasion;
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public Image getCharImg() {
        return charImg;
    }

    public String getImageDirectory() {
        return imageDirectory;
    }

    public BattlePhaseEntity getTarget() {
        return target;
    }

    public void setTarget(BattlePhaseEntity target) {
        this.target = target;
    }

    public boolean isMissed() {
        return missed;
    }

    public void setMissed(boolean missed) {
        this.missed = missed;
    }
    
} 
