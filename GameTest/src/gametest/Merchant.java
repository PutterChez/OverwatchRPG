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
public class Merchant extends WorldPhaseEntity{
    protected Menu shopMenu;
    protected Menu itemList;
    protected Menu playerItemList;
    
    Merchant(int x, int y, int width, int height, String imageDirectory, String charName)
    {
        super(x, y, ID.Merchant, width, height, imageDirectory, charName);
        
        shopMenu = new Menu(x, y, ID.Default, width, height, "..\\resources\\ui\\hud_box_full_res.png");
        itemList = new Menu(x, y, ID.Default, width, height, "..\\resources\\ui\\hud_box_full_res.png");
        playerItemList = new Menu(x, y, ID.Default, width, height, "..\\resources\\ui\\hud_box_full_res.png");
    }
    
    
}
