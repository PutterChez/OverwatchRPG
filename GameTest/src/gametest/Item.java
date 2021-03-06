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
public abstract class Item {
    protected ID id;
    protected String itemName;
    protected int itemPrice;
    
    Item(ID id, String name, int price)
    {
        this.id = id;
        this.itemName = name;
        this.itemPrice = price;
    }
    
    public abstract void use(BattlePhaseEntity e);
    public abstract void use(WorldPhaseEntity e);
}
