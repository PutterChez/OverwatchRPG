/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest;

import java.util.LinkedList;

/**
 *
 * @author DELL
 */
public class Inventory {
    protected LinkedList<Item> itemList;
    protected Menu itemViewer;
    protected int x,y;
    
    Inventory()
    {
        itemList = new LinkedList<>();
        itemViewer = new Menu(x, y, ID.Default, 1400, 850, "..\\resources\\ui\\hud_box_full_res.png");
        itemViewer.name = "PlayerInventory";
    }
    
    public void addItem(Item e)
    {
        itemList.add(e);
    }
    
    public void removeItem(String name)
    {
        for(Item i : itemList)
            if(i.itemName.equals(name))
            {
                itemList.remove(i);
                break;
            }
    }
    
    public void setInventoryPosition(int x, int y)
    {
        this.itemViewer.x = x;
        this.itemViewer.y = y;
    }
}
