package gametest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */

import java.util.*;
import java.awt.Graphics;


//Handler for rendering objects retrived from an objectlist
public class Handler {
    boolean interacting = false;
    boolean battlePhase = false;
    boolean colision = false;
    boolean inventory = false;
    boolean merchantInteracting = false;
    boolean openUI = false;
    boolean bgmStatus = false;
    
    
    boolean mainPhase = true;
    boolean controlsPhase = false;
    boolean gameOver = false;
    
    Sound bgm;
    
    RenderModule battleRender;
    RenderModule worldRender;
    
    Menu mainMenu;
    Menu controlsPage1;
    Menu mainCursor;
    int mainCursorPos = 0;
    
    Menu gameOverScreen;
    
    LinkedList<GameObject> objectList;
    LinkedList<WorldPhaseEntity> colisionList; 
    
    protected String controlsPath = "..\\resources\\misc\\controls_open_world.png";
    
    Handler()
    {
        this.battleRender = new RenderModule();
        this.worldRender = new RenderModule();
        objectList = new LinkedList<>();
        colisionList = new LinkedList<>();
        bgm = new Sound();
        
        gameOverScreen = new Menu(0, 0, ID.Menu, 1600, 900, "..\\resources\\misc\\GameOver.png");
        mainMenu = new Menu(0, 0, ID.Menu, 1600, 900, "..\\resources\\misc\\main_menu.png");
        controlsPage1 = new Menu(0, 0, ID.Menu, 1620, 900, controlsPath);
        mainCursor = new Menu(5000, 5000, ID.Cursor, 50, 50, "..\\resources\\misc\\cursor_E_white.png");
    }
    
    public void tick() {
        for (GameObject obj : objectList)
        {
            obj.tick();
        }   
    }

    public void render(Graphics g) {
        if(battlePhase)  
            battleRender.render(g);
        else if(gameOver)
        {
            gameOverScreen.render(g);
        }
        else if(mainPhase)
        {
            mainMenu.render(g);
            mainCursor.render(g);
        }
        else if(controlsPhase){
            controlsPage1.render(g);
        }
        else
            worldRender.render(g);          
    }
    
    public void controlsPhaseNext(){
        if(controlsPath.equals("..\\resources\\misc\\controls_open_world.png")){
            controlsPath = "..\\resources\\misc\\controls_battle_phase.png";
        }
        else if(controlsPath.equals("..\\resources\\misc\\controls_battle_phase.png")){
            controlsPath = "..\\resources\\misc\\controls_open_world.png";
        }
        System.out.println(controlsPath);
    }
    
    public void updateBattleObject(WorldPhaseEntity player)
    {
        for(GameObject obj : battleRender.renderList)
        {
            obj.x += player.velX;
            obj.y += player.velY;
        }
    }
    
    public void addBattlePhaseObject(GameObject p)
    {
       battleRender.add(p);
       objectList.add(p);
    }
    
    //add PassableObject to WorldPhase
    public void addWorldPhaseObject(GameObject p)
    {
        worldRender.add(p);
        objectList.add(p);
    }
    
    //add ImpassableObject to WorldPhase
    public void addWorldColisionObject(WorldPhaseEntity p)
    {
        worldRender.add(p);
        colisionList.add(p);
    }
    
    public void removeObject(String name)
    {
        worldRender.remove(name);
        battleRender.remove(name);
        
        for(GameObject o : objectList)
            if (o.getName().equals(name))
            {
                objectList.remove(o);
                break;
            }
    }
    
    public void removeColisionObject(String name)
    {
        for(WorldPhaseEntity o : colisionList)
            if(o.getName().equals(name))
            {
                colisionList.remove(o);
                break;
            }
    }
    
    public boolean checkColision(WorldPhaseEntity p)
    {
        for(WorldPhaseEntity c: colisionList)
            if(c.checkColision(p))
                return true;
        return false;
    }
    
    public void playBGM()
    {
        bgm.clip.setLoopPoints(0, -1);
        bgm.clip.loop(10);
        bgmStatus = true;
    }
    
    public void stopBGM()
    {
        bgm.stop();
        bgmStatus = false;
    }
    
    public void setBGM(String url)
    {
        bgm.setSoundDirectory(url);
    }
      
    public void battlePhaseOn(){ battlePhase = true;}
    public void battlePhaseOff(){ battlePhase = false;}
    public boolean battlePhaseStatus(){ return battlePhase; }
    
    public void colisionDetected(){ colision = true; }
    public void colisionNotDetected(){ colision = false; }
    public boolean colisionStatus(){ return colision; }
    
    public void interacted(){ interacting = true; }
    public void uninteracted() { interacting = false; }
    public boolean interactStatus() { return interacting; }
    
    public void inventoryOpen(){ inventory = true; }
    public void inventoryClose() { inventory = false; }
    public boolean inventoryStatus() { return inventory; }
    
    public void merchantOpen(){ merchantInteracting = true; }
    public void merchantClose() { merchantInteracting = false; }
    public boolean merchantStatus() { return merchantInteracting; }
    
    public void UIOpen(){ openUI = true; }
    public void UIClose(){ openUI = false; }
    public boolean UIStatus(){ return openUI; }

    public void MainPhaseOn() { 
        bgm.setSoundDirectory("..\\resources\\music\\Overtime.wav");
        bgm.setVolume(-5);
        playBGM();
        
        mainCursor.x = mainMenu.x + 40;
        mainCursor.y = mainMenu.y + 300 + (mainCursorPos * 100);
        mainPhase = true; 
    }
    public void MainPhaseOff() { 
        mainPhase = false; 
    }
    
    public void ControlsPhaseOn() { 
        controlsPhase = true; 
    }
    public void ControlsPhaseOff() { 
        controlsPhase = false; 
    }
    public boolean MainPhaseStatus() {return mainPhase;}
    
    public boolean ControlsPhaseStatus() {return controlsPhase;}

    public boolean getBgmStatus() {
        return bgmStatus;
    }
    
    
    
}

