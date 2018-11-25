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
    Player player;
    Map currentMap;

    HUD playerHUD;
    HUD enemyHUD;
    
    boolean PopUp = false;
    boolean select = false;
    boolean playerSelect = false;
    boolean skillSelect = false;
    ArrayList<Coordinate> coord_list;
    ArrayList<Coordinate> player_coord_list;
    ArrayList<Skill> attack_list;
    int cursorPos = 0, selectPos = 0, selectSkill = 0,selectedPlayer = 0, finalPosX = 950, finalPosY = 660;

    Menu merchantCursor;
    int merchantCursorPos = 0;

    public ActionControl(Handler handler, Party player, Party enemy, Player playerUnit) {
        this.handler = handler;
        this.playerParty = player;
        this.enemyParty = enemy;
        this.player = playerUnit;

        coord_list = new ArrayList<Coordinate>();
        player_coord_list = new ArrayList<Coordinate>();
        attack_list = new ArrayList<Skill>();

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
        if (handler.battlePhaseStatus()) {
            /*Movement controls*/
            for (int i = 0; i < handler.objectList.size(); i++) {

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
                        if ((key == KeyEvent.VK_ENTER) && (skillSelect == false) && (playerSelect == false) && (select == false)) {
                            tempObject.setY(player.getY() + 150);
                            PopUp = true;
                        } else {
                            tempObject.setY(player.getY() + 1000);
                        }
                    }
                }

                if (tempObject.getId() == ID.Cursor) {
                    if (PopUp == true) {
                        playerHUD.setShowSkills(false);
                        if (key == KeyEvent.VK_DOWN) {
                            if (cursorPos < 2) {
                                cursorPos++;
                            } else if (cursorPos > 2) {
                                cursorPos = 0;
                            }
                        } else if (key == KeyEvent.VK_UP) {
                            if (cursorPos > 0) {
                                cursorPos--;
                            }
                        }

                        finalPosY += cursorPos * 60;

                        if (key == KeyEvent.VK_A) {
                            if (cursorPos == 0) {
                                //playerSelect = true;
                                skillSelect = true;
                                PopUp = false;
                            } else if (cursorPos == 1) {
                                System.out.println("Items");
                            } else if (cursorPos == 2) {
                                System.out.println("Run");
                            }
                        }
                        tempObject.setY(finalPosY);
                        tempObject.setX(finalPosX);
                    } 
                    else if (skillSelect == true){
                        if (key == KeyEvent.VK_LEFT) {
                            if(selectPos == 3){
                                selectPos = 0;
                            }
                            
                            else if (selectPos < playerParty.memberList.size() - 1) {
                                selectPos++;
                            }
                            System.out.println(selectPos);
                        }

                        if (key == KeyEvent.VK_RIGHT) {
                            if(selectPos == 0){
                                selectPos = 3;
                            }
                            
                            else if (selectPos > 0) {
                                selectPos--;
                            }
                        }
                        
                        if (key == KeyEvent.VK_E) {
                            selectedPlayer = selectPos;
                            selectPos = 0;
                            skillSelect = false;
                            playerSelect = true;
                            tempObject.setY(player.getY() + 450);
                        }
                        
                        tempObject.setX(playerParty.memberList.get(selectPos).entity.getX() + 60);
                        tempObject.setY(playerParty.memberList.get(selectPos).entity.getY() + 70);
                        
                    }
                    else if (playerSelect == true) {
                        for (int k = 0; k < playerParty.memberList.size(); k++) {
                            System.out.println(playerParty.memberList.get(k).entity.getCharName());
                            System.out.println("--------------------------------");

                            //Print Skills
                            for (int j = 0; j < playerParty.memberList.get(k).entity.skillList.size(); j++) {
                                Skill selectedSkill = playerParty.memberList.get(k).entity.skillList.get(j);
                                System.out.println(selectedSkill.getSkillName());
                                System.out.println(selectedSkill.getDescription());
                                System.out.println("********************************");
                            }

                            System.out.println("--------------------------------\n");

                            //Select First Skill by automatic TEST
                            playerHUD.setShowSkills(true);
                            playerHUD.setSelectedPlayer(selectedPlayer);
                            Skill selectedSkill = playerParty.memberList.get(k).entity.skillList.get(0);
                            playerParty.memberList.get(k).entity.setSelectSkill(selectedSkill);
                            attack_list.add(selectedSkill);
                        }
                        tempObject.setY(player.getY() + 450);
                        PopUp = false;
                        select = true;
                        playerSelect = false;
                    } else if (select == true) {
                        System.out.println("Enter select");
                        //tempObject.setY(player.getY() + 210);
                        //tempObject.setX(player.getX() + 150);

                        if (key == KeyEvent.VK_RIGHT) {
                            if (selectPos < enemyParty.memberList.size() - 1) {
                                selectPos++;
                            }
                        }

                        if (key == KeyEvent.VK_LEFT) {
                            if (selectPos > 0) {
                                selectPos--;
                            }
                        }

                        System.out.println("selectPos: " + selectPos);

                        System.out.println("X: " + enemyParty.memberList.get(selectPos).entity.getX());
                        System.out.println("Y: " + enemyParty.memberList.get(selectPos).entity.getY());

                        //CHEZBALL WHY DO U NEED FINAL POS AND STUFF
                        //tempObject.setX(finalPosX + enemyParty.memberList.get(selectPos).entity.getX() - 700);
                        //tempObject.setY(finalPosY + enemyParty.memberList.get(selectPos).entity.getY() - 550); 
                        tempObject.setX(enemyParty.memberList.get(selectPos).entity.getX() + 60);
                        tempObject.setY(enemyParty.memberList.get(selectPos).entity.getY() + 70);

                        if (key == KeyEvent.VK_E) {
                            System.out.println(playerParty.memberList.get(selectedPlayer).entity.getCharName() + " attacked " + enemyParty.memberList.get(selectPos).entity.getCharName());
                            Action.attack(playerParty.memberList.get(selectedPlayer).entity, playerParty.memberList.get(selectedPlayer).entity.getSelectSkill(), enemyParty.memberList.get(selectPos).entity);
                            
                            selectPos = 0;
                            select = false;
                            tempObject.setY(player.getY() + 450);
                        }
                    } else {
                        tempObject.setY(player.getY() + 450);
                    }
                }
            }

            //Change to battle finishd function later
            if (key == KeyEvent.VK_ESCAPE) {
                PopUp = false;
                System.out.println("Exit Battle Phase");
                handler.battlePhaseOff();

                for (WorldPhaseEntity obj : handler.colisionList) {
                    WorldPhaseEntity temp = player.getInteractArea();
                    if (obj.checkColision(temp)) {
                        if (obj.getId() == ID.BattleNPC) {
                            player.setDialogue(obj.getDialogue());
                        }
                    }
                }
            }
            //System.out.println(cursorPos);      
        } else //WorldPhase part-----------------------------------------------------------------------------------------------------------
        {
            //System.out.println("CurrentMap x: " + currentMap.x);
            //System.out.println("CurrentMap y:" + currentMap.y);
            //System.out.println("Player x:" + player.x);
            //System.out.println("Player y:" + player.y);

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
            }

            if (!handler.interactStatus() && !handler.UIStatus()) {
                if (key == KeyEvent.VK_W) {
                    //System.out.println("Character Moving Up");
                    player.setDirection(0);
                    player.setVelY(-player.currentSpeed);
                    player.setVelX(0);
                } else if (key == KeyEvent.VK_A) {
                    //System.out.println("Character Moving Left")
                    player.setDirection(1);
                    player.setVelX(-player.currentSpeed);
                    player.setVelY(0);
                } else if (key == KeyEvent.VK_S) {
                    //System.out.println("Character Moving Down");
                    player.setDirection(2);
                    player.setVelY(player.currentSpeed);
                    player.setVelX(0);
                } else if (key == KeyEvent.VK_D) {
                    //System.out.println("Character Moving Right");
                    player.setDirection(3);
                    player.setVelX(player.currentSpeed);
                    player.setVelY(0);
                }

                if (key == KeyEvent.VK_E) {
                    System.out.println(player.x + " , " + player.y);
                }
            }

            if (key == KeyEvent.VK_I) {
                if (!handler.interactStatus() && !handler.inventoryStatus()) {
                    handler.inventoryOpen();
                    player.inventoryOpen();
                    handler.UIOpen();
                } else if (!handler.interactStatus() && handler.inventoryStatus()) {
                    handler.inventoryClose();
                    handler.UIClose();
                    player.inventoryClose();
                }
            }
            
            if (key == KeyEvent.VK_P)
            {
                if(!handler.interactStatus() && !handler.UIStatus())
                {
                    player.partyViewOpen();
                    handler.UIOpen();
                }
                else
                {
                    player.partyViewClosed();
                    handler.UIClose();

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

        if (key == KeyEvent.VK_SPACE && !handler.battlePhaseStatus()) {
            for (WorldPhaseEntity obj : handler.colisionList) {
                WorldPhaseEntity temp = player.getInteractArea();
                if (obj.checkColision(temp)) {
                    if (!handler.interactStatus()) {
                        if (obj.id == ID.NPC) {
                            handler.interacted();
                            player.setDialogue(obj.getDialogue());
                            player.interacted();
                        }

                        if (obj.id == ID.Chest) {
                            handler.interacted();
                            Item tempLoot = obj.getLoot();
                            obj.setImageDirectory("..\\resources\\misc\\chest_opened.png");
                            obj.addDialogue("You got " + tempLoot.itemName);

                            player.addItem(tempLoot);
                            player.setDialogue(obj.getDialogue());
                            player.interacted();
                        }

                        if (obj.id == ID.Merchant) {
                            handler.interacted();

                            Item tempLoot = obj.lootList.get(0);
                            obj.addDialogue("I'm selling " + tempLoot.itemName + " for " + tempLoot.itemPrice + " $");
                            obj.addDialogue("Would you like to buy it?                                                 Yes          No");
                            obj.setPriceCondition(tempLoot.itemPrice);

                            player.setDialogue(obj.getDialogue());
                            player.interacted();
                        }

                        try {
                            if (obj.id == ID.BattleNPC) {
                                handler.interacted();
                                player.setDialogue(obj.getDialogue());
                                player.interacted();

                                for (int i = 0; i < enemyParty.memberList.size(); i++) {
                                    String tempName = enemyParty.memberList.get(i).entity.getCharName();
                                    handler.removeObject(tempName);
                                }

                                enemyParty = obj.getEnemyParty();
                                setEnemyHUDParty(enemyParty);

                                for (int i = 0; i < enemyParty.memberList.size(); i++) {
                                    //800, 450 = PlayerOrigin (Spawn)
                                    enemyParty.memberList.get(i).entity.x += player.x - 800;
                                    enemyParty.memberList.get(i).entity.y += player.y - 450;

                                    handler.addBattlePhaseObject(enemyParty.memberList.get(i).entity);
                                }

                                Thread.sleep(2000);
                                handler.battlePhaseOn();
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
                                player.setDialogue(temp_String);
                            } else {
                                handler.uninteracted();
                                player.unInteracted();
                            }
                        } else if (obj.getId() == ID.BattleNPC) {
                            String temp_String = obj.getDialogue();
                            if (temp_String != null) {
                                player.setDialogue(temp_String);
                            } else {
                                handler.uninteracted();
                                player.unInteracted();
                            }
                        } else if (obj.getId() == ID.Chest) {
                            Item tempLoot = obj.getLoot();
                            if (tempLoot != null) {
                                player.addItem(tempLoot);
                                player.setDialogue("You got " + tempLoot.itemName);
                            } else {
                                obj.die();
                                handler.removeColisionObject(obj.name);
                                handler.uninteracted();
                                player.unInteracted();
                            }
                        } else if (obj.getId() == ID.Merchant) {
                            if(merchantCursorPos == -1)
                            {
                                player.unInteracted();
                                handler.uninteracted();
                                merchantCursorPos = 0;
                            }
                            
                            else if (handler.merchantStatus() == false) {
                                String temp_String = obj.getDialogue();
                                if (temp_String != null) {
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
                                        player.setDialogue("Thank you for your patronage!!!");
                                    } else {
                                        player.setDialogue("You do not have enough money!!!");
                                    }
                                } else {
                                    player.setDialogue("Too bad, see you next time!!!");
                                }
                                
                                merchantCursorPos = -1;
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

        if (enemyParty.memberList.size() <= 0) {
            PopUp = false;
            System.out.println("Exit Battle Phase");
            handler.battlePhaseOff();

            for (WorldPhaseEntity obj : handler.colisionList) {
                WorldPhaseEntity temp = player.getInteractArea();
                if (obj.checkColision(temp)) {
                    if (obj.getId() == ID.BattleNPC) {
                        player.setDialogue(obj.getDialogue());
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

    public void setMerchantCursor(Menu e) {
        merchantCursor = e;
    }
}
