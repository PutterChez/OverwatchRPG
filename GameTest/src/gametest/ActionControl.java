/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
 class ActionControl extends KeyAdapter
    {
        private Handler handler;

        boolean colision = false;
        
        //private int mapSpeed = 1;
               
        Party playerParty;
        Party enemyParty;
        Player player;
        Map currentMap;
        BattlePhaseEntity selectedEntity;
        
        HUD playerHUD;
        HUD enemyHUD;
        
        boolean PopUp = false;
        boolean select = false;
        boolean playerSelect = false;
        ArrayList<Coordinate> coord_list;
        ArrayList<Coordinate> player_coord_list;
        int cursorPos = 0,selectPos = 0, finalPosX = 950,finalPosY = 660;
        
        public ActionControl(Handler handler, Party player, Party enemy, Player playerUnit) {
            this.handler = handler;
            this.playerParty = player;
            this.enemyParty = enemy;
            this.player = playerUnit;
            
            coord_list = new ArrayList<Coordinate>();
            player_coord_list = new ArrayList<Coordinate>();
            coord_list.add(Game.POS1); coord_list.add(Game.POS2); coord_list.add(Game.POS3);
            coord_list.add(Game.POS4); coord_list.add(Game.POS5); coord_list.add(Game.POS6);
            
            player_coord_list.add(Game.P_POS1); player_coord_list.add(Game.P_POS2); player_coord_list.add(Game.P_POS3);
            player_coord_list.add(Game.P_POS4);
        }
        
        public void setMap(Map e)
        {
            this.currentMap = e;
        }
        
        public void setPlayerHUDParty(Party p) { this.playerHUD.party = p; }
        public void setEnemyHUDParty(Party p) { this.enemyHUD.party = p; }

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(handler.battlePhaseStatus())
            {
                int playerY = 450;
                int playerX = 740;
                
                /*Movement controls*/
                for (int i = 0; i < handler.objectList.size(); i++) {

                    GameObject tempObject = handler.objectList.get(i);
                    finalPosY = playerY + 230;
                    
                    if(tempObject.getId() == ID.Player){
                        playerY = tempObject.getY();
                        playerX = tempObject.getX();
                    }
                    
                    if(tempObject.getId() == ID.PopUp){
                        if(PopUp == true){
                            if(key == KeyEvent.VK_BACK_SPACE){
                                tempObject.setY(playerY + 550);
                                PopUp = false;
                            }
                        }
                        
                        else{
                            if(key == KeyEvent.VK_ENTER){
                                tempObject.setY(playerY + 150);
                                PopUp = true;
                            }
                            else{
                                tempObject.setY(playerY + 1000);
                            }
                        }
                    }

                    if(tempObject.getId() == ID.Cursor){
                        if(PopUp == true){
                            if(key == KeyEvent.VK_DOWN){
                                if(cursorPos < 2)
                                    cursorPos++;
                                else if (cursorPos >= 2)
                                    cursorPos = 0;
                            }

                            else if(key == KeyEvent.VK_UP){
                                if(cursorPos >= 0)
                                    cursorPos--;
                            }
                            
                            finalPosY += cursorPos * 60;
                            

                            if(key == KeyEvent.VK_A){
                                if(cursorPos == 0){
                                    playerSelect = true;
                                    PopUp = false;

                                }
                                else if(cursorPos == 1){
                                    System.out.println("Items");
                                }
                                else if(cursorPos == 2){
                                    System.out.println("Run");
                                }
                            }
                            tempObject.setY(finalPosY);
                            tempObject.setX(finalPosX);
                        }
                        
                        else if(playerSelect == true){
                            System.out.println("Player Select Mode");
                            
                            if(playerSelect == true){
                                if(key == KeyEvent.VK_RIGHT){
                                    if(selectPos < playerParty.memberList.size()-1){
                                        selectPos++;
                                    }
                                }

                                if(key == KeyEvent.VK_LEFT){
                                    if(selectPos > 0)
                                        selectPos--;
                                }

                                if(key == KeyEvent.VK_E){
                                    System.out.println(selectPos);
                                    selectedEntity = playerParty.memberList.get(selectPos).entity;
                                    select = true;
                                    playerSelect = false;
                                    selectPos = 0;
                                }

                                tempObject.setX(player_coord_list.get(selectPos).x + 200);
                                tempObject.setY(player_coord_list.get(selectPos).y + 200); 
                            }
                            
                            
                            
                            if(key == KeyEvent.VK_BACK_SPACE){
                                    select = false;
                                    PopUp = false;
                                    tempObject.setY(playerY + 450);
                            }
                        }
                        
                        else if(select == true){
                            System.out.println("Enemy Select Mode");
                            if(key == KeyEvent.VK_RIGHT){
                                if(selectPos < enemyParty.memberList.size()-1){
                                   selectPos++;
                                }
                            }

                            if(key == KeyEvent.VK_LEFT){
                                if(selectPos > 0)
                                    selectPos--;
                            }

                            if(key == KeyEvent.VK_E){
                                System.out.println(selectedEntity.getCharName() + " attacked " + enemyParty.memberList.get(selectPos).entity.getCharName());
                                Action.attack(selectedEntity, selectedEntity.skillList.get(0), enemyParty.memberList.get(selectPos).entity);
                            }

                            tempObject.setX(coord_list.get(selectPos).x + 200);
                            tempObject.setY(coord_list.get(selectPos).y + 200); 
                        }
                        
                        else{
                            tempObject.setY(playerY + 450);
                        }
                    }
                }
                if (key == KeyEvent.VK_ESCAPE){
                    PopUp = false;
                    System.out.println("Exit Battle Phase");
                    handler.uninteracted();
                    player.unInteracted();
                    handler.battlePhaseOff();
                }
                //System.out.println(cursorPos);
            }   
            else //WorldPhase part
            {
               //System.out.println("CurrentMap x: " + currentMap.x);
               //System.out.println("CurrentMap y:" + currentMap.y);
               //System.out.println("Player x:" + player.x);
               //System.out.println("Player y:" + player.y);
             
                if (key == KeyEvent.VK_B)
                {
                    System.out.println("Entering Battle Phase");
                    handler.battlePhaseOn();
                }
                
                if(!handler.interactStatus())
                {
                    if (key == KeyEvent.VK_W)
                    {
                        //System.out.println("Character Moving Up");
                        player.setDirection(0);
                        player.setVelY(-player.currentSpeed);
                        player.setVelX(0);
                    }
                    else if (key == KeyEvent.VK_A)
                    {
                        //System.out.println("Character Moving Left")
                        player.setDirection(1);
                        player.setVelX(-player.currentSpeed);
                        player.setVelY(0);
                    }

                    else if (key == KeyEvent.VK_S)
                    {
                        //System.out.println("Character Moving Down");
                        player.setDirection(2);
                        player.setVelY(player.currentSpeed);
                        player.setVelX(0);
                    }

                    else if (key == KeyEvent.VK_D)
                    {
                        //System.out.println("Character Moving Right");
                        player.setDirection(3);
                        player.setVelX(player.currentSpeed);
                        player.setVelY(0);
                    }
                    
                    if (key == KeyEvent.VK_E)
                    {
                        System.out.println(player.x + " , " + player.y);
                    }
                }
                
                //Temporary exit game method
                if (key == KeyEvent.VK_ESCAPE) {
                    System.exit(1);
                }
                
                //System.out.println(player.direction);
                
            }
        }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            
            if (key == KeyEvent.VK_SPACE)
                {
                    for (WorldPhaseEntity obj : handler.colisionList)
                    {
                        WorldPhaseEntity temp = player.getInteractArea();
                        if(obj.checkColision(temp)){
                            if(!handler.interactStatus())
                            {
                                if(obj.id == ID.NPC)
                                {
                                    handler.interacted();
                                    player.setDialogue(obj.getDialogue());
                                    player.interacted(); 
                                }
                                
                                try
                                {
                                    if(obj.id == ID.BattleNPC)
                                    {
                                        handler.interacted();
                                        player.setDialogue(obj.getDialogue());
                                        player.interacted();
                                        
                                        for(int i = 0; i < enemyParty.memberList.size(); i++)
                                        {
                                            String tempName = enemyParty.memberList.get(i).entity.getCharName();
                                            handler.removeObject(tempName);
                                        }

                                        enemyParty= obj.getEnemyParty();
                                        setEnemyHUDParty(enemyParty);
                                        
                                        for(int i = 0; i < enemyParty.memberList.size(); i++)
                                            handler.addBattlePhaseObject(enemyParty.memberList.get(i).entity);
                                        
                                        Thread.sleep(2000);
                                        handler.battlePhaseOn();
                                    }
                                }
                                catch(InterruptedException ex) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                            else
                            {
                                if(obj.getId() == ID.NPC)
                                {
                                    //Please becareful when edit below codes
                                    //.getDialogue must be called exactly 1 time
                                    String temp_String = obj.getDialogue();
                                    if(temp_String != null)
                                        player.setDialogue(temp_String);
                                    else
                                    {
                                        handler.uninteracted();
                                        player.unInteracted();
                                    }
                                }
                                else if (obj.getId() == ID.BattleNPC)
                                {
                                    handler.uninteracted();
                                    player.unInteracted();
                                }
                            }
                        }
                    }
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
        
        
 }