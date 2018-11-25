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
public class MoneyItem extends Item {
    MoneyItem(ID id, String name, int price)
    {
        super(id, name, price);
    }
    
    public void use(BattlePhaseEntity e){ System.out.println("You cannot use money"); }
    public void use(WorldPhaseEntity e){ System.out.println("You cannot use money"); }
}
