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
    Player player;
    Map currentMap;

    HUD playerHUD;
    HUD enemyHUD;

    Sound SFX;

    boolean PopUp = false;
    boolean select = false;
    boolean skillListSelect = false;
    boolean skillSelect = false;
    ArrayList<Coordinate> coord_list;
    ArrayList<Coordinate> player_coord_list;
    ArrayList<Skill> attack_list;
    int cursorPos = 0, selectPos = 0, selectSkillNum = 0,selectedPlayer = 0, finalPosX = 950, finalPosY = 660;

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
        if (handler.battlePhaseStatus()) {
            if(handler.getBgmStatus() == false)
            {
                handler.setBGM("..\\resources\\music\\Hanamura.wav");
                handler.playBGM();
            }

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
                        if ((key == KeyEvent.VK_ENTER) && (skillSelect == false) && (skillListSelect == false) && (select == false)) {
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
                            skillListSelect = true;
                            tempObject.setY(player.getY() + 450);
                        }

                        tempObject.setX(playerParty.memberList.get(selectPos).entity.getX() + 60);
                        tempObject.setY(playerParty.memberList.get(selectPos).entity.getY() + 70);

                    }
                    else if (skillListSelect == true) {
                        System.out.println("Skill Select Enter");
                        playerHUD.setShowSkills(true);
                        playerHUD.setSelectedPlayer(selectedPlayer);
                        int skillListSize = playerParty.memberList.get(selectedPlayer).entity.skillList.size() - 1;

                        if (key == KeyEvent.VK_DOWN) {
                            if(selectPos == skillListSize){
                                selectPos = 0;
                            }

                            else if (selectPos < skillListSize) {
                                selectPos++;
                            }
                        }

                        if (key == KeyEvent.VK_UP) {
                            if(selectPos == 0){
                                selectPos = skillListSize;
                            }

                            else if (selectPos > 0) {
                                selectPos--;
                            }
                        }

                        if (key == KeyEvent.VK_A) {
                            selectSkillNum = selectPos;

                            Skill selectedSkill = playerParty.memberList.get(selectedPlayer).entity.skillList.get(selectSkillNum);
                            playerParty.memberList.get(selectedPlayer).entity.setSelectSkill(selectedSkill);
                            attack_list.add(selectedSkill);

                            System.out.println("Selected Skill: " + selectedSkill.getSkillName());
                            selectPos = 0;
                            PopUp = false;
                            select = true;
                            skillListSelect = false;
                            tempObject.setY(player.getY() + 450);
                        }

                        tempObject.setX(player.getX() + 60);
                        tempObject.setY(player.getY() + 250 + (35*selectPos));

                    } else if (select == true) {
                        System.out.println("Enter Enemy Select");
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
                            System.out.println(playerParty.memberList.get(selectedPlayer).entity.getCharName() + " attacked " + enemyParty.memberList.get(selectPos).entity.getCharName()
                            + " using " + playerParty.memberList.get(selectedPlayer).entity.getSelectSkill().skillName);
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
        } else //WorldPhase part-----------------------------------------------------------------------------------------------------------
        {
            //System.out.println("CurrentMap x: " + currentMap.x);
            //System.out.println("CurrentMap y:" + currentMap.y);
            //System.out.println("Player x:" + player.x);
            //System.out.println("Player y:" + player.y);
            if(handler.getBgmStatus() == false)
            {
                handler.setBGM("..\\resources\\music\\Chiisana Koi no Uta.wav");
                handler.playBGM();
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
                    SFX.setSoundDirectory("..\\resources\\sfx\\UIOpen.wav");
                    SFX.setVolume(-10);
                    SFX.play();

                    handler.inventoryOpen();
                    player.inventoryOpen();
                    handler.UIOpen();
                } else if (!handler.interactStatus() && handler.inventoryStatus()) {
                    SFX.setSoundDirectory("..\\resources\\sfx\\UIOpen.wav");
                    SFX.setVolume(-10);
                    SFX.play();

                    handler.inventoryClose();
                    handler.UIClose();
                    player.inventoryClose();
                }
            }

            if (key == KeyEvent.VK_P)
            {
                if(!handler.interactStatus() && !handler.UIStatus())
                {
                    SFX.setSoundDirectory("..\\resources\\sfx\\UIOpen.wav");
                    SFX.setVolume(-10);
                    SFX.play();

                    player.partyViewOpen();
                    handler.UIOpen();
                }
                else
                {
                    SFX.setSoundDirectory("..\\resources\\sfx\\UIOpen.wav");
                    SFX.setVolume(-10);
                    SFX.play();

                    player.partyViewClosed();
                    handler.UIClose();

                }
            }

            //Temporary exit game method
            if (key == KeyEvent.VK_ESCAPE) {
                System.exit(1);
            }

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

                            SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                            SFX.play();
                        }

                        if (obj.id == ID.Chest) {
                            handler.interacted();
                            Item tempLoot = obj.getLoot();
                            obj.setImageDirectory("..\\resources\\misc\\chest_opened.png");
                            obj.addDialogue("You got " + tempLoot.itemName);

                            player.addItem(tempLoot);
                            player.setDialogue(obj.getDialogue());
                            player.interacted();

                            SFX.setSoundDirectory("..\\resources\\sfx\\ChestOpen.wav");
                            SFX.setVolume(-10);
                            SFX.play();
                        }

                        if (obj.id == ID.Merchant) {
                            handler.interacted();

                            Item tempLoot = obj.lootList.get(0);
                            obj.addDialogue("I'm selling " + tempLoot.itemName + " for " + tempLoot.itemPrice + " $");
                            obj.addDialogue("Would you like to buy it?                                                 Yes          No");
                            obj.setPriceCondition(tempLoot.itemPrice);

                            player.setDialogue(obj.getDialogue());
                            player.interacted();

                            SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                            SFX.play();
                        }

                        try {
                            if (obj.id == ID.BattleNPC) {
                                handler.interacted();
                                player.setDialogue(obj.getDialogue());
                                player.interacted();

                                SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                                SFX.play();

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
                                handler.stopBGM();
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
                                SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                                SFX.play();
                                player.setDialogue(temp_String);
                            } else {
                                handler.uninteracted();
                                player.unInteracted();
                            }
                        } else if (obj.getId() == ID.BattleNPC) {
                            String temp_String = obj.getDialogue();
                            if (temp_String != null) {
                                SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                                SFX.play();
                                player.setDialogue(temp_String);
                            } else {
                                handler.uninteracted();
                                player.unInteracted();
                            }
                        } else if (obj.getId() == ID.Chest) {
                            Item tempLoot = obj.getLoot();
                            if (tempLoot != null) {
                                if(tempLoot instanceof MoneyItem)
                                    player.inventory.addMoney(tempLoot.itemPrice);
                                else
                                    player.addItem(tempLoot);
                                player.setDialogue("You got " + tempLoot.itemName);
                                SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                                SFX.play();
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
                                merchantCursor.setY(3000);
                                merchantCursorPos = 0;
                            }

                            else if (handler.merchantStatus() == false) {
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
                                        player.setDialogue("Thank you for your patronage!!!");

                                        SFX.setSoundDirectory("..\\resources\\sfx\\coin.wav");
                                        SFX.setVolume(-10);
                                        SFX.play();

                                    } else {
                                        SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                                        SFX.play();
                                        player.setDialogue("You do not have enough money!!!");
                                    }
                                } else {
                                    SFX.setSoundDirectory("..\\resources\\sfx\\DialogueChange.wav");
                                    SFX.play();
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
