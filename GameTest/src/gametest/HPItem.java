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
    protected int speed, evasion, defense, attack = 0;
    
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
    
    public void use(BattlePhaseEntity e)
    {
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
