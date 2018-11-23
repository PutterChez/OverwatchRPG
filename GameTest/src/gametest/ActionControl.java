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
    ArrayList<Coordinate> coord_list;
    ArrayList<Coordinate> player_coord_list;
    ArrayList<Skill> attack_list;
    int cursorPos = 0, selectPos = 0, selectSkill = 0, finalPosX = 950, finalPosY = 660;

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
                        if (key == KeyEvent.VK_ENTER) {
                            tempObject.setY(player.getY() + 150);
                            PopUp = true;
                        } else {
                            tempObject.setY(player.getY() + 1000);
                        }
                    }
                }

                if (tempObject.getId() == ID.Cursor) {
                    if (PopUp == true) {

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
                                playerSelect = true;
                                PopUp = false;
                            } else if (cursorPos == 1) {
                                System.out.println("Items");
                            } else if (cursorPos == 2) {
                                System.out.println("Run");
                            }
                        }
                        tempObject.setY(finalPosY);
                        tempObject.setX(finalPosX);
                    } else if (playerSelect == true) {
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
                            for (int k = 0; k < playerParty.memberList.size(); k++) {
                                System.out.println(playerParty.memberList.get(k).entity.getCharName() + " attacked " + enemyParty.memberList.get(selectPos).entity.getCharName());
                                Action.attack(playerParty.memberList.get(k).entity, playerParty.memberList.get(k).entity.getSelectSkill(), enemyParty.memberList.get(selectPos).entity);
                            }
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
        } 
        else //WorldPhase part
        {
            //System.out.println("CurrentMap x: " + currentMap.x);
            //System.out.println("CurrentMap y:" + currentMap.y);
            //System.out.println("Player x:" + player.x);
            //System.out.println("Player y:" + player.y);

            if (key == KeyEvent.VK_B) {
                System.out.println("Entering Battle Phase");
                handler.battlePhaseOn();
            }

            if (!handler.interactStatus()) {
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
                } else if (!handler.interactStatus() && handler.inventoryStatus()) {
                    handler.inventoryClose();
                    player.inventoryClose();
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

}
