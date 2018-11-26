/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest;

/**
 *
 * @author DELL
 */
public class HPItem extends Item{
    protected int speed, evasion, defense, attack, healHP, healMP = 0;
    
    HPItem(ID id, String name, int price)
    {
        super(id, name, price);
    }
    
    public void setAttributeAtk(int e)
    {
        attack = e;
    }
    
    public void setAttributeDef(int e)
    {
        defense = e;
    }
    
    public void setAttributeSpd(int e)
    {
        speed = e;
    }
    
    public void setAttributeEva(int e)
    {
        evasion = e;
    }
    
    public void setAttributeHP(int e)
    {
        healHP = e;
    }
    
    public void setAttributeMP(int e)
    {
        healMP = e;
    }
    
    public void use(BattlePhaseEntity e)
    {
        e.HP += healHP;
        e.MP += healMP;
        
        if(e.HP > e.maxHP)
            e.HP = e.maxHP;
        if(e.MP > e.maxMP)
            e.MP = e.maxMP;
        
        e.attack += attack;
        e.defense += defense;
        e.speed += speed;
        e.evasion += evasion;
    }
    
    public void use(WorldPhaseEntity e)
    {
        ;
    }
    
    
}
