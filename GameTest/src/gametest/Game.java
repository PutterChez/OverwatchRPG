package gametest;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

//Main game class (main class)
public class Game extends Canvas implements Runnable {
    
    public static final int WIDTH = 1600, HEIGHT = 900;

    private Thread thread;
    private boolean running = false;

    private Random r;
    private Handler handler;
    private HUD playerHUD, enemyHUD;
    

    public Game() {
        handler = new Handler();
        
        new Window(WIDTH, HEIGHT, "Overwatch RPG Test", this);
        
        //WorldPhase Part-----------------------------------------------------------------------------------------------------
        WorldPhaseEntity player = new WorldPhaseEntity(800, 450, 92, 50, "Genji", "..\\resources\\characters\\genji_1.png", ID.Player);
        Map testMap = new Map(-1400, -7200, ID.Background, 9600, 9600, "..\\resources\\maps\\open_world_extra_border.png");
        
        handler.addWorldPhaseObject(testMap);
        handler.addWorldPhaseObject(player);
        
        //testColisionDetection
        ColisionObject test = new ColisionObject(775, 250, ID.Background, 200, 100);
        test.setImageDirectory("..\\resources\\maps\\spawn_wall.png");
            
        //BattlePhase Part----------------------------------------------------------------------------------------------------
        int posX1,posX2,posX3,posX4,posY1,posY2,posY3,posY4;
        posX1 = WIDTH/2 + 200; posY1 = HEIGHT/2 - 500;
        posX2 = WIDTH/2 + 250; posY2 = HEIGHT/2 - 150;
        posX3 = WIDTH/2 + 400; posY3 = HEIGHT/2 - 300;
        posX4 = WIDTH/2 - 50; posY4 = HEIGHT/2 - 300;
        
        int enemyX1, enemyX2, enemyX3, enemyX4, enemyX5, enemyX6, enemyY1, enemyY2, enemyY3;
        enemyX1 = WIDTH/2 - 800; enemyX2 = WIDTH/2 - 600;
        enemyY1 = HEIGHT/2 - 500; enemyY2 = HEIGHT/2 - 300; enemyY3 = HEIGHT/2 - 100;
        
        BattlePhaseEntity genji = new BattlePhaseEntity(posX2,posY2, ID.Ally, 400, 400, "..\\resources\\characters\\genji_1.png", 200, 100, "Genji", 40, 10, 100, 40);
        BattlePhaseEntity mccree = new BattlePhaseEntity(posX1,posY1, ID.Ally, 400, 400, "..\\resources\\characters\\mccree_1.png", 250, 200, "Mccree", 40, 10, 100, 40);
        BattlePhaseEntity mercy = new BattlePhaseEntity(posX3,posY3, ID.Ally, 400, 400, "..\\resources\\characters\\mercy_1.png", 200, 150, "Mercy", 40, 10, 100, 40);
        BattlePhaseEntity reinhardt = new BattlePhaseEntity(posX4,posY4, ID.Ally, 350, 350, "..\\resources\\characters\\rein_1.png", 500, 150, "Reinhardt", 40, 10, 100, 40);
        
        BattlePhaseEntity doomfist = new BattlePhaseEntity(enemyX1, enemyY1, ID.Enemy, 300, 300, "..\\resources\\characters\\doom_2.png", 250, 200, "Doomfist", 40, 10, 100, 40);
        BattlePhaseEntity widowmaker = new BattlePhaseEntity(enemyX1, enemyY2, ID.Enemy, 300, 300, "..\\resources\\characters\\widow_2.png", 200, 200, "Widowmaker", 40, 10, 100, 40);
        BattlePhaseEntity reaper = new BattlePhaseEntity(enemyX1, enemyY3, ID.Enemy, 300, 300, "..\\resources\\characters\\reaper_2.png", 200, 200, "Reaper", 40, 10, 100, 40);
        BattlePhaseEntity moira = new BattlePhaseEntity(enemyX2, enemyY1, ID.Enemy, 300, 300, "..\\resources\\characters\\reaper_2.png", 200, 200, "Moira", 40, 10, 100, 40);
        BattlePhaseEntity sombra = new BattlePhaseEntity(enemyX2, enemyY2, ID.Enemy, 300, 300, "..\\resources\\characters\\sombra_2.png", 200, 200, "Sombra", 40, 10, 100, 40);
        BattlePhaseEntity bastion = new BattlePhaseEntity(enemyX2, enemyY3, ID.Enemy, 300, 300, "..\\resources\\characters\\bastion_2.png", 200, 200, "Bastion", 40, 10, 100, 40);
        
        Skill swiftStrike = new Skill("Switft Strike",50,60,80);
        swiftStrike.setDescription("Genji darts forward, slashing with his katana and passing through foes in his path.");
        genji.addSkill(swiftStrike);
        
        Skill headShot = new Skill("Head Shot", 200,40, 200 );
        headShot.setDescription("Mccree click the head");
        mccree.addSkill(headShot);
        
        Menu menu = new Menu(WIDTH / 2 - 700, 1000, ID.Menu, 1400, 300, "..\\resources\\maps\\hud_1.png");
        Menu popUp = new Menu(WIDTH / 2 - 170, 1000, ID.PopUp, 500, 300, "..\\resources\\maps\\hud_box.png");
        Menu cursor = new Menu(WIDTH / 2 + 150, 1000, ID.Cursor, 30, 30, "..\\resources\\ui\\cursor.png");
        Menu background = new Menu(0, 0, ID.Background, WIDTH, HEIGHT, null);

        
        //0 = North, 1 = West, 2 = South, 3 = East
        Party playerParty = new Party();
        playerParty.addMember(mccree, 0);
        playerParty.addMember(reinhardt, 1);
        playerParty.addMember(genji, 2);
        playerParty.addMember(mercy, 3);
        
        //Need to change the HUD if going to use the Party class instead of ArrayList(Punypuny :3)
        /*
        ArrayList<Entity> enemyList = new ArrayList();
        enemyList.add(doomfist);
        enemyList.add(widowmaker);
        enemyList.add(reaper);
        */
        
        
        //Position
        //0 1
        //2 3
        //4 5
        Party enemyParty = new Party();
        enemyParty.addMember(doomfist, 0);
        enemyParty.addMember(widowmaker, 2);
        enemyParty.addMember(reaper, 4);
        enemyParty.addMember(moira, 1);
        enemyParty.addMember(sombra, 3);
        enemyParty.addMember(bastion, 5);
       
        
        handler.addBattlePhaseObject(background);
        handler.addBattlePhaseObject(menu);
        handler.addBattlePhaseObject(popUp);
        handler.addBattlePhaseObject(cursor);

        for (int i = 0; i < playerParty.memberList.size(); i++) {
            //handler.addObject(party.get(i));
            handler.addBattlePhaseObject(playerParty.memberList.get(i).entity);
        }

        for (int i = 0; i < enemyParty.memberList.size(); i++) {
            //handler.addObject(enemyParty.searchMember(i));
            handler.addBattlePhaseObject(enemyParty.memberList.get(i).entity);
        }
            
        playerHUD = new HUD(1250, 1000, -200, 15, -5, 590,50, playerParty);
        enemyHUD = new HUD(150, 1000, -100, -5, -5, 600,60, enemyParty);
        
        handler.addBattlePhaseObject(playerHUD);
        handler.addBattlePhaseObject(enemyHUD);
        
        ActionControl control = new ActionControl(handler, playerParty, enemyParty, player);
        control.setMap(testMap);
        this.addKeyListener(control);

    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 360.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        handler.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
  
        handler.render(g);

        g.dispose();
        bs.show();
    }

    public static int clamp(int var, int min, int max) {
        if (var >= max) {
            return var = max;
        } else if (var <= min) {
            return var = min;
        } else {
            return var;
        }
    }

    public static void main(String[] args) {
        new Game();
    }

}
