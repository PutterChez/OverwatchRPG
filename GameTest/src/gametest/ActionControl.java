/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        
        public ActionControl(Handler handler, Party player, Party enemy, Player playerUnit) {
            this.handler = handler;
            this.playerParty = player;
            this.enemyParty = enemy;
            this.player = playerUnit;
        }
        
        public void setMap(Map e)
        {
            this.currentMap = e;
        }

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            boolean PopUp = false;
            if(handler.battlePhaseStatus())
            {
                int cursorPos = 0, finalPos = 660;
                int playerY = 450;
                
                /*Movement controls*/
                for (int i = 0; i < handler.objectList.size(); i++) {

                    GameObject tempObject = handler.objectList.get(i);
                    finalPos = playerY + 210;
                    
                    if(tempObject.getId() == ID.Player){
                        playerY = tempObject.getY();
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
                        System.out.println(cursorPos);
                        System.out.println("PopUp: " + PopUp);
                        if(PopUp == true){
                            if(key == KeyEvent.VK_DOWN){
                                if(cursorPos < 2)
                                    cursorPos++;

                                finalPos += cursorPos * 50;
                            }

                            else if(key == KeyEvent.VK_UP){
                                if(cursorPos != 0)
                                    cursorPos--;

                                finalPos += cursorPos * 50;
                                System.out.println(finalPos);
                            }

                            if(key == KeyEvent.VK_A){
                                if(cursorPos == 0){
                                    System.out.println();
                                    System.out.println("Mccree HP: " +  playerParty.memberList.get(0).entity.getHP());
                                    System.out.println("Mccree MP: " +  playerParty.memberList.get(0).entity.getMP());
                                    System.out.println("Doomfist HP: " + enemyParty.memberList.get(0).entity.getHP());
                                    System.out.println("Doomfist MP: " + enemyParty.memberList.get(0).entity.getMP());
                                    System.out.println("---------------------------------------------------------");

                                    System.out.println(playerParty.memberList.get(0).entity.charName + " use " 
                                            + playerParty.memberList.get(0).entity.skillList.get(0).skillName + " to " + enemyParty.memberList.get(0).entity.charName );
                                    Action.attack(playerParty.memberList.get(0).entity, playerParty.memberList.get(0).entity.skillList.get(0), enemyParty.memberList.get(0).entity);

                                    System.out.println("After");
                                    System.out.println("Mccree HP: " +  playerParty.memberList.get(0).entity.getHP());
                                    System.out.println("Mccree MP: " +  playerParty.memberList.get(0).entity.getMP());
                                    System.out.println("Doomfist HP: " + enemyParty.memberList.get(0).entity.getHP());
                                    System.out.println("Doomfist MP: " + enemyParty.memberList.get(0).entity.getMP());

                                }
                                else if(cursorPos == 1){
                                    System.out.println("Items");
                                }
                                else if(cursorPos == 2){
                                    System.out.println("Run");
                                }
                             
                            }
                            tempObject.setY(finalPos);
                        }
                         
                        else{
                            tempObject.setY(playerY + 450);
                        }
                    }
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    PopUp = false;
                    System.out.println("Exit Battle Phase");
                    handler.battlePhaseOff();
                }
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
            
            if (key == KeyEvent.VK_ENTER)
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
                            }
                            else
                            {
                                handler.uninteracted();
                                player.unInteracted();
                            }
                        }
                    }
                }
            
            player.setVelX(0);
            player.setVelY(0);
        }
 }