/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author DELL
 */
class ActionControl extends KeyAdapter {

    private Handler handler;

    boolean colision = false;

    //private int mapSpeed = 1;
    Party playerParty;
    Party enemyParty;
    Party tempEnemyParty;
    Player player;
    Map currentMap;

    Random rand = new Random(System.currentTimeMillis());
    
    HUD playerHUD;
    HUD enemyHUD;

    Sound SFX;

    boolean PopUp = false;
    boolean select = false;
    boolean skillListSelect = false;
    boolean skillSelect = false;
    boolean itemSelection = false;
    boolean itemTargetSelect = false;
    boolean moreString = true;
    ArrayList<Coordinate> coord_list;
    ArrayList<Coordinate> player_coord_list;
    ArrayList<BattlePhaseEntity> attack_list;
    int cursorPos = 0, selectPos = 0, enemySelectPos = 0, selectedPlayer = 0, finalPosX = 950, finalPosY = 660;
    
    WorldPhaseEntity currentBattleNPC;
    
    Item selectItem;
    int itemCursorColumn = 0;
    int itemCursorRow = 0;
    int itemCursorCount = 0;
    int playerSelectPos = 0;

    Menu merchantCursor;
    int merchantCursorPos = 0;

    public ActionControl(Handler handler, Party player, Party enemy, Player playerUnit) {
        this.handler = handler;
        this.playerParty = player;
        this.enemyParty = enemy;
        this.player = playerUnit;

        coord_list = new ArrayList<Coordinate>();
        player_coord_list = new ArrayList<Coordinate>();
        attack_list = new ArrayList<BattlePhaseEntity>();

        SFX = new Sound();

        coord_list.add(Game.POS1);
        coord_list.add(Game.POS2);
        coord_list.add(Game.POS3);
        coord_list.add(Game.POS4);
        coord_list.add(Game.POS5);
        coord_list.add(Game.POS6);

        player_coord_list.add(Game.P_POS1);
        player_coord_list.add(Game.P_POS2);
        player_coord_list.add(Game.P_POS3);
        player_coord_list.add(Game.P_POS4);
    }

    public void setMap(Map e) {
        this.currentMap = e;
    }

    public void setPlayerHUDParty(Party p) {
        this.playerHUD.party = p;
    }

    public void setEnemyHUDParty(Party p) {
        this.enemyHUD.party = p;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(handler.ControlsPhaseStatus())
        {  
            if (key == KeyEvent.VK_SPACE) {
                SFX.setSoundDirectory("..\\resources\\sfx\\MENU A - Back.wav");
                SFX.play();
                handler.ControlsPhaseOff();
            }
        } 
        else if(handler.MainPhaseStatus())
        {
            if (handler.getBgmStatus() == false) {
                handler.setBGM("..\\resources\\music\\Overtime.wav");
                handler.bgm.setVolume(-5);
                handler.playBGM();
            }
            
            if(key == KeyEvent.VK_W)
            {
                if(handler.mainCursorPos >= 1)
                    handler.mainCursorPos -= 1;
                handler.mainCursor.x = handler.mainMenu.x + 40;
                handler.mainCursor.y = handler.mainMenu.y + 300 + (handler.mainCursorPos * 100);
            }
            
            if(key == KeyEvent.VK_S)
            {
                if(handler.mainCursorPos < 2)
                    handler.mainCursorPos += 1;
                handler.mainCursor.x = handler.mainMenu.x + 40;
                handler.mainCursor.y = handler.mainMenu.y + 300 + (handler.mainCursorPos * 100);
            }
            
            if(key == KeyEvent.VK_SPACE)
            {
                SFX.setSoundDirectory("..\\resources\\sfx\\MENU A_Select.wav");
                SFX.play();
                if(handler.mainCursorPos == 0)
                {
                    handler.MainPhaseOff();
                    handler.stopBGM();
                }
                else if (handler.mainCursorPos == 1)
                {
                    handler.ControlsPhaseOn();
                }
                else if (handler.mainCursorPos == 2)
                {
                    System.exit(0);
                }
            }
            
            if (key == KeyEvent.VK_ESCAPE) {
                System.exit(1);
            }
        }  
        else if (handler.battlePhaseStatus()) {
            if (handler.getBgmStatus() == false) {
                handler.setBGM("..\\resources\\music\\Hanamura.wav");
                handler.playBGM();
            }

            /*Movement controls*/
            for (int i = 0; i < handler.objectList.size(); i++) {

                boolean chooseRun = false;
                
                GameObject tempObject = handler.objectList.get(i);
                finalPosY = player.getY() + 230;
                finalPosX = player.getX() + 50;

                if (tempObject.getId() == ID.PopUp) {
                    if (PopUp == true) {
                        if (key == KeyEvent.VK_BACK_SPACE) {
                            System.out.println("Set Y: " + (player.getY() + 550));
                            tempObject.setY(player.getY() + 550);
                            PopUp = false;
                        }
                    } else {
                        if ((key == KeyEvent.VK_SPACE) && (skillSelect == false) && (skillListSelect == false) && (select == false)) {
                            SFX.setSoundDirectory("..\\resources\\sfx\\MENU_Pick.wav");
                            SFX.play();
                            tempObject.setY(player.getY() + 150);
                            PopUp = true;
                        } else {
                            tempObject.setY(player.getY() + 1000);
                        }
                    }
                }

                if (tempObject.getId() == ID.Cursor) {
                    if(itemTargetSelect == true)
                    {
                        if(key == KeyEvent.VK_W)
                        {
                            if(playerSelectPos > 0)
                                playerSelectPos--;
                        }
                        
                        if(key == KeyEvent.VK_S)
                        {
                            if(playerSelectPos < playerParty.memberList.size() - 1)
                                playerSelectPos++;
                        }
                        
                        tempObject.x = playerParty.memberList.get(playerSelectPos).entity.x + 100;
                        tempObject.y = playerParty.memberList.get(playerSelectPos).entity.y + 100;
                        
                        if(key == KeyEvent.VK_E)
                        {
                            selectItem.use(playerParty.memberList.get(playerSelectPos).entity);
                            handler.inventoryClose();
                            itemTargetSelect = false;
                            tempObject.setY(5000);
                            
         
                                    {
                                        for(int p = 0; p < enemyParty.memberList.size(); p++)
                                            {
                                                BattlePhaseEntity temp = enemyParty.memberList.get(p).entity;
                                                Skill selectedSkill = temp.skillList.get(rand.nextInt(temp.skillList.size()));
                                                temp.setSelectSkill(selectedSkill);
                                                temp.setTarget(playerParty.memberList.get(rand.nextInt(playerParty.memberList.size())).entity);
                                                attack_list.add(temp);
                                            }
                                    }
                                    for(int j = 0; j < attack_list.size();j++){
                                        for(int k = 0; k < enemyParty.memberList.size(); k++)
                                        {
                                            if(attack_list.get(j).getCharName().equals(enemyParty.memberList.get(k).entity.getCharName())){
                                                enemyHUD.setShowDisplay(true);
                                                enemyHUD.setSelectedPlayer(k);
                                                
                                                enemyParty.memberList.get(k).entity.setDisplayString(enemyParty.memberList.get(k).entity.getCharName() + " attacked " + enemyParty.memberList.get(k).entity.getTarget().getCharName()
                                                + " using " + enemyParty.memberList.get(k).entity.getSelectSkill().skillName);
                                                Action.attack(enemyParty.memberList.get(k).entity, enemyParty.memberList.get(k).entity.getSelectSkill(), enemyParty.memberList.get(k).entity.getTarget());
                                                enemyParty.memberList.get(k).entity.x += 100;
                                                try{
                                                    tempObject.setY(5000);
                                                    Thread.sleep(500);
                                                }
                                                catch(Exception ex)
                                                {
                                                    System.out.println(ex.toString());
                                                }
                                                enemyParty.memberList.get(k).entity.x -= 100;                           
                                            }
                                            if(enemyParty.memberList.get(k).entity.isMissed())
                                                enemyParty.memberList.get(k).entity.setMissed(false);
                                        }
                                        enemyHUD.setShowDisplay(false);
                                    }
                            
                            
                        }       
                    }
                    else if(itemSelection == true)
                    {
                        if(key == KeyEvent.VK_W)
                            {
                                //System.out.println(itemCursorCount);
                                //System.out.println(player.inventory.itemList.size());
                                if(itemCursorCount > 0)
                                {
                                    if(itemCursorColumn > 0)
                                    {
                                        itemCursorColumn--;
                                        itemCursorCount--;
                                    }
                                    else  if (itemCursorColumn <= 0)
                                    {
                                        itemCursorColumn = 3;
                                        itemCursorCount--;
                                        itemCursorRow -= 1;
                                    }

                                    tempObject.x = player.inventory.itemViewer.x + 90 + (itemCursorColumn * 200);
                                    tempObject.y = player.inventory.itemViewer.y + 160 + (itemCursorRow * 70);
                                }
                            }
                            else if (key == KeyEvent.VK_S)
                            {
                                //System.out.println(itemCursorCount);
                                //System.out.println(player.inventory.itemList.size());
                                if(itemCursorCount < player.inventory.itemList.size() - 1)
                                {
                                    if(itemCursorColumn < 3)
                                    {
                                        itemCursorColumn++;
                                        itemCursorCount++;
                                    }
                                    else  if (itemCursorColumn >=3)
                                    {
                                        itemCursorColumn = 0;
                                        itemCursorCount++;
                                        itemCursorRow += 1;
                                    }
                                }
                                tempObject.x = player.inventory.itemViewer.x + 90 + (itemCursorColumn * 200);
                                tempObject.y = player.inventory.itemViewer.y + 160 + (itemCursorRow * 70); 
                            }
                        
                            if(key == KeyEvent.VK_E)
                            {
                                selectItem = player.inventory.itemList.get(itemCursorCount);
                                player.inventoryClose();
                                itemSelection = false;
                                itemTargetSelect = true;
                                tempObject.x = playerParty.memberList.get(playerSelectPos).entity.x + 100;
                                tempObject.y = playerParty.memberList.get(playerSelectPos).entity.y + 100;
                                player.inventory.itemList.remove(selectItem);
                            }
                        }
                    
                    else if (PopUp == true) {
                        playerHUD.setShowSkills(false);
                        if (key == KeyEvent.VK_S) {
                            if (cursorPos < 2) {
                                cursorPos++;
                            } else if (cursorPos > 2) {
                                cursorPos = 0;
                            }
                        } else if (key == KeyEvent.VK_W) {
                            if (cursorPos > 0) {
                                cursorPos--;
                            }
                        }

                        finalPosY += cursorPos * 60;

                        if (key == KeyEvent.VK_E) {
                            if (cursorPos == 0) {
                                playerParty.manaRegen(10);
                                SFX.setSoundDirectory("..\\resources\\sfx\\MENU_Pick.wav");
                                SFX.play();
                                PopUp = false;
                                skillListSelect = true;
                                
                                //PunPun Edit
                                for(GameObject o : handler.objectList)
                                    if(o.getId() == ID.PopUp)
                                        o.setY(player.getY() + 1000);
                                
                                playerHUD.setShowSkills(true);
                                playerHUD.setSelectedPlayer(selectedPlayer);
                                
                            } 
                            else if (cursorPos == 1 && !handler.inventoryStatus()) {
                                if(player.inventory.itemList.size() == 0)
                                {
                                    SFX.setSoundDirectory("..\\resources\\sfx\\Error.wav");
                                    SFX.play();
                                    return;
                                }
                                
                                player.inventoryOpen();
                                handler.inventoryOpen();
                                
                                PopUp = false;
                                itemSelection = true;
                                for(GameObject obj : handler.objectList)
                                        if(obj.getId() == ID.PopUp)
                                            obj.setY(player.getY() + 1000);
                                
                                tempObject.x = player.inventory.itemViewer.x + 90;
                                tempObject.y = player.inventory.itemViewer.y + 160;
                                System.out.println("Items");
                            } else if (cursorPos == 1 && handler.inventoryStatus()) {
                                player.inventoryClose();
                                handler.inventoryClose();
                            }else if (cursorPos == 2) {
                                playerParty.manaRegen(10);
                                chooseRun = true;
                                Random randRunChance = new Random(System.currentTimeMillis());
                                
                                int runPercentage;
                                if(playerParty.getTotalHP() > enemyParty.getTotalHP())
                                    runPercentage = 80;
                                else
                                    runPercentage = 20;
                                
                                if(randRunChance.nextInt(100) < runPercentage)
                                {
                                    SFX.setSoundDirectory("..\\resources\\sfx\\Flee.wav");
                                    SFX.play();
                                    
                                    PopUp = false;
                                    
                                    for(WorldPhaseEntity o : handler.colisionList)
                                    {
                                        if(o.checkColision(player))
                                            if(o.getId() == ID.BattleNPC)
                                                o.enemyParty = tempEnemyParty;
                                    }
                                    
                                    for(GameObject obj : handler.objectList)
                                        if(obj.getId() == ID.PopUp)
                                            obj.setY(player.getY() + 1000);
                                    
                                    System.out.println("Exit Battle Phase");
                                    handler.battlePhaseOff();
                                    player.battlePhaseOff();
                                    handler.stopBGM();

                                    for (WorldPhaseEntity obj : handler.colisionList) {
                                        WorldPhaseEntity temp = player.getInteractArea();
                                        if (obj.checkColision(temp)) {
                                            if (obj.getId() == ID.BattleNPC || obj.id == ID.Boss) {
                                                String temp_String = obj.getDialogue();
                                                while (temp_String != null) {
                                                    temp_String = obj.getDialogue();
                                                    //SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                                                    //SFX.play();
                                                    //player.setDialogue(temp_String);
                                                }
                                                handler.uninteracted();
                                                player.unInteracted();    
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    SFX.setSoundDirectory("..\\resources\\sfx\\Failed.wav");
                                    SFX.play();
                                    
                                    PopUp = false;
                                    
                                    for(GameObject obj : handler.objectList)
                                        if(obj.getId() == ID.PopUp)
                                            obj.setY(player.getY() + 1000);
                                        
                                    
                                    //Function to skip player turn
                                    System.out.println("Can not RUN :P");
                                    {
                                        for(int p = 0; p < enemyParty.memberList.size(); p++)
                                            {
                                                BattlePhaseEntity temp = enemyParty.memberList.get(p).entity;
                                                Skill selectedSkill = temp.skillList.get(rand.nextInt(temp.skillList.size()));
                                                temp.setSelectSkill(selectedSkill);
                                                temp.setTarget(playerParty.memberList.get(rand.nextInt(playerParty.memberList.size())).entity);
                                                attack_list.add(temp);
                                            }
                                    }
                                    for(int j = 0; j < attack_list.size();j++){
                                        for(int k = 0; k < enemyParty.memberList.size(); k++)
                                        {
                                            if(attack_list.get(j).getCharName().equals(enemyParty.memberList.get(k).entity.getCharName())){
                                                enemyHUD.setShowDisplay(true);
                                                enemyHUD.setSelectedPlayer(k);
                                                
                                                enemyParty.memberList.get(k).entity.setDisplayString(enemyParty.memberList.get(k).entity.getCharName() + " attacked " + enemyParty.memberList.get(k).entity.getTarget().getCharName()
                                                + " using " + enemyParty.memberList.get(k).entity.getSelectSkill().skillName);
                                                Action.attack(enemyParty.memberList.get(k).entity, enemyParty.memberList.get(k).entity.getSelectSkill(), enemyParty.memberList.get(k).entity.getTarget());
                                                enemyParty.memberList.get(k).entity.x += 100;
                                                try{
                                                    tempObject.setY(5000);
                                                    Thread.sleep(500);
                                                }
                                                catch(Exception ex)
                                                {
                                                    System.out.println(ex.toString());
                                                }
                                                enemyParty.memberList.get(k).entity.x -= 100;                           
                                            }
                                            if(enemyParty.memberList.get(k).entity.isMissed())
                                                enemyParty.memberList.get(k).entity.setMissed(false);
                                        }
                                    }

                                    //PunPun Edit
                                    attack_list.clear();
                                    System.out.println("------------------------------------------");
                                    System.out.println("Finished execute attack");
                                    System.out.println("------------------------------------------");
                                    
                                    for(int j = 0; j < enemyParty.memberList.size();j++){
                                    if(enemyParty.memberList.get(j).entity.isMissed())
                                        enemyParty.memberList.get(j).entity.setMissed(false);
                                    }
                                }
                                cursorPos = 0;
                            }       
                        }
                        if(chooseRun)
                        {
                            tempObject.setY(5000);
                            chooseRun = false;
                        }
                        else if(!itemSelection)
                            tempObject.setY(finalPosY);
                        
                        if(!itemSelection)
                            tempObject.setX(finalPosX);
                        
                        //PunPun Edit
                        if(skillListSelect)
                        {
                            tempObject.setX(player.getX() + 100);
                            tempObject.setY(player.getY() + 250 + (35 * 0));
                        }
                        
                    } 
 
                    else if (skillListSelect == true) {
                        if (selectedPlayer < playerParty.memberList.size()) {
                            //playerHUD.setShowSkills(true);
                            //playerHUD.setSelectedPlayer(selectedPlayer);
                            int skillListSize = playerParty.memberList.get(selectedPlayer).entity.skillList.size() - 1;

                            if (key == KeyEvent.VK_S) {
                                if (selectPos == skillListSize) {
                                    selectPos = 0;
                                } else if (selectPos < skillListSize) {
                                    selectPos++;
                                }
                                else{
                                    selectPos = 0;
                                }
                            }

                            if (key == KeyEvent.VK_W) {
                                if (selectPos == 0) {
                                    selectPos = skillListSize;
                                } else if (selectPos > 0) {
                                    selectPos--;
                                }
                            }
                                
                            tempObject.setX(player.getX() + 100);
                            tempObject.setY(player.getY() + 255 + (35 * selectPos));
                            
                            if (key == KeyEvent.VK_E) {
                                if(playerParty.memberList.get(selectedPlayer).entity.skillList.get(selectPos).mpCost > playerParty.memberList.get(selectedPlayer).entity.MP)
                                {
                                    SFX.setSoundDirectory("..\\resources\\sfx\\Error.wav");
                                    SFX.play();
                                    System.out.println("Not enuff Mana");
                                    return;
                                }
                                
                                SFX.setSoundDirectory("..\\resources\\sfx\\MENU_Pick.wav");
                                SFX.play();
                                
                                Skill selectedSkill = playerParty.memberList.get(selectedPlayer).entity.skillList.get(selectPos);
                                playerParty.memberList.get(selectedPlayer).entity.setSelectSkill(selectedSkill);
                                attack_list.add(playerParty.memberList.get(selectedPlayer).entity);
                                
                                playerHUD.setShowSkills(false);
                                
                                if(selectedPlayer < playerParty.memberList.size())
                                {
                                    PopUp = false;
                                    skillListSelect = false;
                                    select = true;
                                    playerHUD.setSelectedPlayer(selectedPlayer);
                                    
                                    if(playerParty.memberList.get(selectedPlayer).entity.getSelectSkill() instanceof HealSkill){
                                        tempObject.setX(playerParty.memberList.get(enemySelectPos).entity.getX() + 60);
                                        tempObject.setY(playerParty.memberList.get(enemySelectPos).entity.getY() + 70);
                                    }
                                    else{
                                        tempObject.setX(enemyParty.memberList.get(enemySelectPos).entity.getX() + 60);
                                        tempObject.setY(enemyParty.memberList.get(enemySelectPos).entity.getY() + 70);
                                    }
                                    
                                }
                                else{
                                    select = false;
                                }
                            }
                        }
                    } 
                    else if (select == true) {
                        
                        if(playerParty.memberList.get(selectedPlayer).entity.getSelectSkill() instanceof HealSkill){
                            if (key == KeyEvent.VK_S) {
                                if (enemySelectPos < playerParty.memberList.size() - 1) {
                                    enemySelectPos++;
                                }
                            }

                            if (key == KeyEvent.VK_W) {
                                if (enemySelectPos > 0) {
                                    enemySelectPos--;
                                }
                            }
                            
                            tempObject.setX(playerParty.memberList.get(enemySelectPos).entity.getX() + 60);
                            tempObject.setY(playerParty.memberList.get(enemySelectPos).entity.getY() + 70);
                        }
                        else{
                            if (key == KeyEvent.VK_S) {
                                if (enemySelectPos < enemyParty.memberList.size() - 1) {
                                    enemySelectPos++;
                                }
                            }

                            if (key == KeyEvent.VK_W) {
                                if (enemySelectPos > 0) {
                                    enemySelectPos--;
                                }
                            }
                            
                            tempObject.setX(enemyParty.memberList.get(enemySelectPos).entity.getX() + 60);
                            tempObject.setY(enemyParty.memberList.get(enemySelectPos).entity.getY() + 70);
                        }

                        if (key == KeyEvent.VK_E) {
                            
                            SFX.setSoundDirectory("..\\resources\\sfx\\MENU_Pick.wav");
                            SFX.play();
                            if(playerParty.memberList.get(selectedPlayer).entity.getSelectSkill() instanceof HealSkill){
                                playerParty.memberList.get(selectedPlayer).entity.setTarget(playerParty.memberList.get(enemySelectPos).entity);
                            }
                            else{
                                playerParty.memberList.get(selectedPlayer).entity.setTarget(enemyParty.memberList.get(enemySelectPos).entity);
                            }
                            
                            /*Print test
                            System.out.println(playerParty.memberList.get(selectedPlayer).entity.getCharName() + " will attack " + 
                                    enemyParty.memberList.get(enemySelectPos).entity.getName() + " using " + 
                                    playerParty.memberList.get(selectedPlayer).entity.getSelectSkill().getSkillName());
                            */
                            
                            
                            selectedPlayer++;
                            
                            if(selectedPlayer >= playerParty.memberList.size()){
                                skillListSelect = false;
                                enemySelectPos = 0;
                                cursorPos = 0; 
                                selectPos = 0;
                                selectedPlayer = 0;
                                select = false;
                                PopUp = false;
                                playerHUD.setShowSkills(false);
                                System.out.println("Exit Player Turn");
                                
                                for(int p = 0; p < enemyParty.memberList.size(); p++)
                                {
                                    BattlePhaseEntity temp = enemyParty.memberList.get(p).entity;
                                    Skill selectedSkill = temp.skillList.get(rand.nextInt(temp.skillList.size()));
                                    temp.setSelectSkill(selectedSkill);
                                    temp.setTarget(playerParty.memberList.get(rand.nextInt(playerParty.memberList.size())).entity);
                                    attack_list.add(temp);
                                }
                                
                                
                                Collections.sort(attack_list);
                                System.out.println("------------------------------------------");
                                for(int j = 0; j < attack_list.size();j++){
                                    for(int k = 0;k < playerParty.memberList.size();k++){
                                        if(attack_list.get(j).getCharName().equals(playerParty.memberList.get(k).entity.getCharName())){
                                            playerHUD.setShowDisplay(true);
                                            playerHUD.setSelectedPlayer(k);
                                            String displayString;
                                            if(playerParty.memberList.get(k).entity.getSelectSkill() instanceof HealSkill){
                                                if(playerParty.memberList.get(k).entity.getSelectSkill().getSkillName().equals("Valkyrie"))
                                                    displayString = playerParty.memberList.get(k).entity.getCharName() + " healed everyone using Valkyrie!";
                                                else
                                                    displayString = playerParty.memberList.get(k).entity.getCharName() + " healed " + playerParty.memberList.get(k).entity.getTarget().getCharName()
                                                + " using " + playerParty.memberList.get(k).entity.getSelectSkill().skillName;
                                            }
                                            else{
                                                displayString = playerParty.memberList.get(k).entity.getCharName() + " attacked " + playerParty.memberList.get(k).entity.getTarget().getCharName()
                                            + " using " + playerParty.memberList.get(k).entity.getSelectSkill().skillName;
                                            }
                                            playerParty.memberList.get(k).entity.setDisplayString(displayString);
                                            
                                            if(playerParty.memberList.get(k).entity.getSelectSkill() instanceof HealSkill){
                                                //Single Target Heal
                                                if(playerParty.memberList.get(k).entity.getSelectSkill().getSkillName().equals("Heal"))
                                                    Action.healing(playerParty.memberList.get(k).entity, playerParty.memberList.get(k).entity.getSelectSkill(), playerParty.memberList.get(k).entity.getTarget());
                                                
                                                //Team Heal
                                                else if(playerParty.memberList.get(k).entity.getSelectSkill().getSkillName().equals("Valkyrie")){
                                                    Action.groupHealing(playerParty.memberList.get(k).entity, playerParty.memberList.get(k).entity.getSelectSkill(), playerParty);
                                                }
                                            }
                                            else{
                                                Action.attack(playerParty.memberList.get(k).entity, playerParty.memberList.get(k).entity.getSelectSkill(), playerParty.memberList.get(k).entity.getTarget());
                                            }
                                            
                                            playerParty.memberList.get(k).entity.x -= 100;
                                            try{
                                                Thread.sleep(2000);
                                            }
                                            catch(Exception ex)
                                            {
                                                System.out.println(ex.toString());
                                            }
                                            playerParty.memberList.get(k).entity.x += 100;
                                            
                                            if(playerParty.memberList.get(k).entity.isMissed())
                                                playerParty.memberList.get(k).entity.setMissed(false);
                                        }
                                        playerHUD.setShowDisplay(false);
                                    }
                                    for (int t = 0; t < enemyParty.memberList.size(); t++) {
                                        if (!enemyParty.memberList.get(t).entity.alive()) {
                                            System.out.println("Dead: " + enemyParty.memberList.get(t).entity.charName);
                                            enemyParty.deleteMember(t);
                                        }
                                    }

                                    for (int t = 0; t < playerParty.memberList.size(); t++) {
                                        if (!playerParty.memberList.get(t).entity.alive()) {
                                            System.out.println("Dead: " + playerParty.memberList.get(t).entity.charName);
                                            playerParty.deleteMember(t);
                                        }
                                    }

                                    if (enemyParty.memberList.size() <= 0) {
                                        PopUp = false;
                                        System.out.println("Exit Battle Phase");
                                        player.setDialogue(currentBattleNPC.getDialogue());
                                        
                                        
                                        handler.battlePhaseOff();
                                        player.battlePhaseOff();
                                        handler.stopBGM();
                                        break;

                                        /*
                                        for (WorldPhaseEntity obj : handler.colisionList) {
                                            WorldPhaseEntity temp = player.getInteractArea();
                                            if (obj.checkColision(temp)) {
                                                if (obj.getId() == ID.BattleNPC) {
                                                    String tempString = obj.getDialogue();
                                                    System.out.println(tempString);
                                                    player.setDialogue(tempString);
                                                }
                                            }
                                        }
                                                */
                                    }


                                    if (playerParty.memberList.size() <= 0){
                                        PopUp = false;
                                        skillListSelect = false;
                                        System.out.println("Exit Battle Phase: Player NOOB");
                                        handler.battlePhaseOff();
                                        player.battlePhaseOff();
                                        player.unInteracted();

                                        handler.stopBGM();
                                        handler.setBGM("..\\resources\\music\\SadViolin.wav");
                                        handler.playBGM();
                                        handler.gameOver = true;
                                        break;
                                    }
                                }
                                
                                
                                System.out.println("------------------------------------------");
                                for(int j = 0; j < attack_list.size();j++){
                                    for(int k = 0; k < enemyParty.memberList.size(); k++)
                                        {
                                            if(attack_list.get(j).getCharName().equals(enemyParty.memberList.get(k).entity.getCharName())){
                                                enemyHUD.setShowDisplay(true);
                                                enemyHUD.setSelectedPlayer(k);
                                                enemyParty.memberList.get(k).entity.setDisplayString(enemyParty.memberList.get(k).entity.getCharName() + " attacked " + enemyParty.memberList.get(k).entity.getTarget().getCharName()
                                                + " using " + enemyParty.memberList.get(k).entity.getSelectSkill().skillName);
                                                Action.attack(enemyParty.memberList.get(k).entity, enemyParty.memberList.get(k).entity.getSelectSkill(), enemyParty.memberList.get(k).entity.getTarget());
                                                
                                                enemyParty.memberList.get(k).entity.x += 100;
                                                try{
                                                    Thread.sleep(2000);
                                                }
                                                catch(Exception ex)
                                                {
                                                    System.out.println(ex.toString());
                                                }
                                                enemyParty.memberList.get(k).entity.x -= 100;
                                                }
                                            if(enemyParty.memberList.get(k).entity.isMissed())
                                                enemyParty.memberList.get(k).entity.setMissed(false);
                                        }
                                    enemyHUD.setShowDisplay(false);
                                }
                                
                                //PunPun Edit
                                attack_list.clear();
                                tempObject.setY(player.getY() + 1000);
                                System.out.println("------------------------------------------");
                                System.out.println("Finished execute attack");
                                System.out.println("------------------------------------------");
     
                            }
                            else{
                                /*
                                for(int j = 0; j < playerParty.memberList.size();j++){
                                    if(playerParty.memberList.get(j).entity.isMissed())
                                        playerParty.memberList.get(j).entity.setMissed(false);
                                }
                                
                                for(int j = 0; j < enemyParty.memberList.size();j++){
                                    if(enemyParty.memberList.get(j).entity.isMissed())
                                        enemyParty.memberList.get(j).entity.setMissed(false);
                                }
                                */
                                
                                enemySelectPos = 0;
                                select = false;
                                PopUp = false;
                                skillListSelect = true;
                                
                                //PunPun Edit
                                selectPos = 0;
                                tempObject.setX(player.getX() + 100);
                                tempObject.setY(player.getY() + 250 + (35 * selectPos));
                                playerHUD.setSelectedPlayer(selectedPlayer);
                                playerHUD.setShowSkills(true );
                            }
                        
                        }
                    } else {
                        tempObject.setY(player.getY() + 450);
                    }
                }
            }

            //Change to battle finishd function later
            /*
            if (key == KeyEvent.VK_ESCAPE) {
                PopUp = false;
                System.out.println("Exit Battle Phase");
                handler.battlePhaseOff();
                handler.stopBGM();

                for (WorldPhaseEntity obj : handler.colisionList) {
                    WorldPhaseEntity temp = player.getInteractArea();
                    if (obj.checkColision(temp)) {
                        if (obj.getId() == ID.BattleNPC) {
                            player.setDialogue(obj.getDialogue());
                        }
                    }
                }
            }
            */
        } else //WorldPhase part-----------------------------------------------------------------------------------------------------------
        {
            //System.out.println("CurrentMap x: " + currentMap.x);
            //System.out.println("CurrentMap y:" + currentMap.y);
            //System.out.println("Player x:" + player.x);
            //System.out.println("Player y:" + player.y);
            if (handler.getBgmStatus() == false) {
                handler.setBGM("..\\resources\\music\\Gymnopedie.wav");
                handler.playBGM();
            }
            
            if (handler.gameOver && key == KeyEvent.VK_SPACE) {
                handler.stopBGM();
                System.exit(0);
                handler.MainPhaseOn();
                handler.gameOver = false;
            }
            
            if (handler.gameWin && key == KeyEvent.VK_SPACE)
            {
                handler.stopBGM();
                System.exit(0);
            }

            if (key == KeyEvent.VK_A && handler.merchantStatus()) {
                merchantCursorPos = 0;
                merchantCursor.setX(player.x + 375 + (175 * merchantCursorPos));
            }

            if (key == KeyEvent.VK_D && handler.merchantStatus()) {
                merchantCursorPos = 1;
                merchantCursor.setX(player.x + 375 + (175 * merchantCursorPos));
            }

            if (key == KeyEvent.VK_B) {
                System.out.println("Entering Battle Phase");
                handler.battlePhaseOn();
                player.battlePhaseOn();
            }

            if (!handler.interactStatus() && !handler.UIStatus()) {
                if (key == KeyEvent.VK_W) {
                    player.setDirection(0);
                    player.setVelY(-player.currentSpeed);
                    player.setVelX(0);
                } else if (key == KeyEvent.VK_A) {
                    player.setDirection(1);
                    player.setVelX(-player.currentSpeed);
                    player.setVelY(0);
                } else if (key == KeyEvent.VK_S) {
                    player.setDirection(2);
                    player.setVelY(player.currentSpeed);
                    player.setVelX(0);
                } else if (key == KeyEvent.VK_D) {
                    player.setDirection(3);
                    player.setVelX(player.currentSpeed);
                    player.setVelY(0);
                }

                //Test button to get player coordinates
                if (key == KeyEvent.VK_E) {
                    System.out.println(player.x + " , " + player.y);
                }
            }

            if (key == KeyEvent.VK_I) {
                if (!handler.interactStatus() && !handler.inventoryStatus()) {
                    SFX.setSoundDirectory("..\\resources\\sfx\\MENU Open.wav");
                    SFX.setVolume(-5);
                    SFX.play();

                    handler.inventoryOpen();
                    player.inventoryOpen();
                    handler.UIOpen();
                } else if (!handler.interactStatus() && handler.inventoryStatus()) {
                    SFX.setSoundDirectory("..\\resources\\sfx\\MENU Close.wav");
                    SFX.setVolume(-5);
                    SFX.play();

                    handler.inventoryClose();
                    handler.UIClose();
                    player.inventoryClose();
                }
            }

            if (key == KeyEvent.VK_P) {
                if (!handler.interactStatus() && !handler.UIStatus()) {
                    SFX.setSoundDirectory("..\\resources\\sfx\\MENU Open.wav");
                    SFX.setVolume(-5);
                    SFX.play();

                    player.partyViewOpen();
                    handler.UIOpen();
                } else {
                    SFX.setSoundDirectory("..\\resources\\sfx\\MENU Close.wav");
                    SFX.setVolume(-5);
                    SFX.play();

                    player.partyViewClosed();
                    handler.UIClose();

                }
            }

            //exit to mainMenu, need to change the condition later
            if (key == KeyEvent.VK_Q)
            {
                handler.stopBGM();
                handler.gameOver = true;
                
                SFX.setSoundDirectory("..\\resources\\sfx\\Defeat.wav");
                SFX.play();
                
                try{
                Thread.sleep(SFX.clip.getMicrosecondLength()/1000);
                }
                catch(Exception except)
                {
                    System.out.println(except.toString());
                }
                
                handler.setBGM("..\\resources\\music\\SadViolin.wav");
                handler.playBGM();
            }

        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        

        if(handler.ControlsPhaseStatus())
        {
            if(key == KeyEvent.VK_A || key == KeyEvent.VK_D)
            {
                SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                SFX.play();
                handler.controlsPhaseNext();
            }
        }
        
        if (key == KeyEvent.VK_SPACE && !handler.battlePhaseStatus()) {
            for (WorldPhaseEntity obj : handler.colisionList) {
                WorldPhaseEntity temp = player.getInteractArea();
                if (obj.checkColision(temp)) {
                    if (!handler.interactStatus()) {
                        if (obj.id == ID.NPC) {
                            handler.interacted();
                            player.setDialogue(obj.getDialogue());
                            player.interacted();

                            SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                            SFX.play();
                        }

                        if (obj.id == ID.Chest) {
                            handler.interacted();
                            //Item tempLoot = obj.getLoot();
                            obj.setImageDirectory("..\\resources\\misc\\chest_opened.png");
                            //obj.addDialogue("You got " + tempLoot.itemName);
                            
                            //player.addItem(tempLoot);
                            player.setDialogue(obj.getDialogue());
                            player.interacted();

                            SFX.setSoundDirectory("..\\resources\\sfx\\ChestOpen.wav");
                            SFX.setVolume(-10);
                            SFX.play();
                        }

                        if (obj.id == ID.Merchant) {
                            handler.interacted();

                            Item tempLoot = obj.lootList.get(0);
                            obj.addDialogue("Mei: I'm selling " + tempLoot.itemName + " for " + tempLoot.itemPrice + " $");
                            obj.addDialogue("Mei: Would you like to buy it?                                            Yes         No");
                            obj.setPriceCondition(tempLoot.itemPrice);

                            player.setDialogue(obj.getDialogue());
                            player.interacted();

                            SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                            SFX.play();
                        }

                        try {
                            if (obj.id == ID.BattleNPC || obj.id == ID.Boss) {
                                handler.interacted();
                                player.setDialogue(obj.getDialogue());
                                player.interacted();

                                //PunPunEdit_Temp
                                currentBattleNPC = obj;
                                
                                SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                                SFX.play();

                                for (int i = 0; i < enemyParty.memberList.size(); i++) {
                                    String tempName = enemyParty.memberList.get(i).entity.getCharName();
                                    handler.removeObject(tempName);
                                }

                             
                                enemyParty = obj.getEnemyParty();
                                tempEnemyParty = enemyParty;
                                setEnemyHUDParty(enemyParty);

                                for (int i = 0; i < enemyParty.memberList.size(); i++) {
                                    //800, 450 = PlayerOrigin (Spawn)
                                    enemyParty.memberList.get(i).entity.x += player.x - 800;
                                    enemyParty.memberList.get(i).entity.y += player.y - 450;

                                    handler.addBattlePhaseObject(enemyParty.memberList.get(i).entity);
                                }

                                Thread.sleep(2000);
                                handler.stopBGM();
                                handler.battlePhaseOn();
                                player.battlePhaseOn();
                            }
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    } else {
                        if (obj.getId() == ID.NPC) {
                            //Please becareful when edit below codes
                            //.getDialogue() must be called exactly 1 time
                            String temp_String = obj.getDialogue();
                            if (temp_String != null) {
                                SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                                SFX.play();
                                player.setDialogue(temp_String);
                            } else {
                                handler.uninteracted();
                                player.unInteracted();
                            }
                        } else if (obj.getId() == ID.BattleNPC || obj.id == ID.Boss) {
                            String temp_String = obj.getDialogue();
                            if (temp_String != null) {
                                SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                                SFX.play();
                                player.setDialogue(temp_String);
                            } else {
                                if(obj.id == ID.Boss)
                                {
                                    handler.uninteracted();
                                    player.unInteracted();
                                    //GameExitCode here
                                    handler.gameWin = true;
                                    System.out.println("GameExit");
                                }
                                obj.die();
                                handler.uninteracted();
                                player.unInteracted();
                            }
                        } else if (obj.getId() == ID.Chest) {
                            if(moreString)
                            {
                                String temp_String = obj.getDialogue();
                                if (temp_String != null) {
                                    SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                                    SFX.play();
                                    player.setDialogue(temp_String);
                                    continue;
                                } 
                                else
                                    moreString = false;
                            }
                            
                            Item tempLoot = obj.getLoot();
                            if (tempLoot != null) {
                                if (tempLoot instanceof MoneyItem) {
                                    player.inventory.addMoney(tempLoot.itemPrice);
                                    SFX.setSoundDirectory("..\\resources\\sfx\\coin.wav");
                                    SFX.setVolume(0);
                                    SFX.play();
                                } else {
                                    player.addItem(tempLoot);
                                }
                                player.setDialogue("You got " + tempLoot.itemName);
                                SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                                SFX.play();
                            } else {
                                obj.die();
                                System.out.println(obj.alive());
                                moreString = true;
                                //handler.removeColisionObject(obj.name);
                                handler.uninteracted();
                                player.unInteracted();
                            }
                        } else if (obj.getId() == ID.Merchant) {
                            if (merchantCursorPos == -1) {
                                player.unInteracted();
                                handler.uninteracted();
                                merchantCursorPos = 0;
                            } else if (handler.merchantStatus() == false) {
                                String temp_String = obj.getDialogue();
                                if (temp_String != null) {
                                    SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                                    SFX.play();
                                    merchantCursor.setX(player.x + 375);
                                    merchantCursor.setY(player.y + 275);
                                    player.setDialogue(temp_String);
                                    handler.merchantOpen();
                                }
                            } else if (handler.merchantStatus() == true) {
                                merchantCursor.setY(1000);
                                if (merchantCursorPos == 0) {
                                    if (player.inventory.currentMoney > obj.merchantCondition) {
                                        player.addItem(obj.lootList.get(0));
                                        player.inventory.reduceMoney(obj.merchantCondition);
                                        player.setDialogue("Mei: Thank you for your patronage!!!");

                                        SFX.setSoundDirectory("..\\resources\\sfx\\coin.wav");
                                        SFX.setVolume(-10);
                                        SFX.play();

                                    } else {
                                        SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                                        SFX.play();
                                        player.setDialogue("Mei: You do not have enough money!!!");
                                    }
                                } else {
                                    SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                                    SFX.play();
                                    player.setDialogue("Mei: Too bad, see you next time!!!");
                                }

                                merchantCursorPos = -1;
                                merchantCursor.setY(5000);
                                handler.merchantClose();
                            }
                        }
                    }
                }
            }
        }
        
        //Check for alive member of the enemyParty
        
        
        for (int i = 0; i < enemyParty.memberList.size(); i++) {
            if (!enemyParty.memberList.get(i).entity.alive()) {
                System.out.println("Dead: " + enemyParty.memberList.get(i).entity.charName);
                enemyParty.deleteMember(i);
            }
        }
        
        for (int i = 0; i < playerParty.memberList.size(); i++) {
            if (!playerParty.memberList.get(i).entity.alive()) {
                System.out.println("Dead: " + playerParty.memberList.get(i).entity.charName);
                playerParty.deleteMember(i);
            }
        }
        

        
        if (enemyParty.memberList.size() <= 0) {
            PopUp = false;
            System.out.println("Exit Battle Phase");
            handler.battlePhaseOff();
            player.battlePhaseOff();
            handler.stopBGM();
        }
        
        
            /*
            for (WorldPhaseEntity obj : handler.colisionList) {
                WorldPhaseEntity temp = player.getInteractArea();
                if (obj.checkColision(temp)) {
                    if (obj.getId() == ID.BattleNPC) {
                        String tempString = obj.getDialogue();
                        System.out.println(tempString);
                        player.setDialogue(tempString);
                    }
                }
            }

        }
        */
        
        
        if (playerParty.memberList.size() <= 0){
            PopUp = false;
            skillListSelect = false;
            System.out.println("Exit Battle Phase: Player NOOB");
            handler.battlePhaseOff();
            player.battlePhaseOff();
            player.unInteracted();
            
            handler.stopBGM();
            handler.setBGM("..\\resources\\music\\SadViolin.wav");
            handler.playBGM();
            handler.gameOver = true;
        }
                
        

        player.setVelX(0);
        player.setVelY(0);
    }

    public void setPlayerHUD(HUD playerHUD) {
        this.playerHUD = playerHUD;
    }

    public void setEnemyHUD(HUD enemyHUD) {
        this.enemyHUD = enemyHUD;
    }

    public void setMerchantCursor(Menu e) {
        merchantCursor = e;
    }
}
