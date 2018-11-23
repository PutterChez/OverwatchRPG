package gametest;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

//Main game class (main class)
public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 1600, HEIGHT = 900;
    
    public static final Coordinate POS1 = new Coordinate(WIDTH/2 - 800,HEIGHT/2 - 475);
    public static final Coordinate POS2 = new Coordinate(WIDTH/2 - 800,HEIGHT/2 - 275);
    public static final Coordinate POS3 = new Coordinate(WIDTH/2 - 800,HEIGHT/2 - 75);
    public static final Coordinate POS4 = new Coordinate(WIDTH/2 - 600,HEIGHT/2 - 475);
    public static final Coordinate POS5 = new Coordinate(WIDTH/2 - 600,HEIGHT/2 - 275);
    public static final Coordinate POS6 = new Coordinate(WIDTH/2 - 600,HEIGHT/2 - 75);
        
    public static final Coordinate P_POS1 = new Coordinate(WIDTH/2 + 200,HEIGHT/2 - 500);
    public static final Coordinate P_POS2 = new Coordinate(WIDTH/2 + 250,HEIGHT/2 - 150);
    public static final Coordinate P_POS3 = new Coordinate(WIDTH/2 + 400,HEIGHT/2 - 300);
    public static final Coordinate P_POS4 = new Coordinate(WIDTH/2 - 50,HEIGHT/2 - 300);
    
    private Camera cam;
    private Thread thread;
    private boolean running = false;

    private Handler handler;
    private HUD playerHUD, enemyHUD;
    Player player;
    
    //private WorldPhaseEntity player;
    //private boolean BattlePhase = false;

    public Game() {
        handler = new Handler();

        new Window(WIDTH, HEIGHT, "Overwatch RPG Test", this);
        
        //WorldPhase Part-----------------------------------------------------------------------------------------------------
        player = new Player(800, 450, ID.Player, 50, 60, "..\\resources\\characters\\RedSquare.png", "Player");
        
        Map testMap = new Map(-1400, -7200, ID.Background, 9600, 9600, "..\\resources\\maps\\open_world_extra_border.png");
        cam = new Camera(0, 0,ID.Camera,0,0,player);
        
        handler.addWorldPhaseObject(testMap);
        handler.addWorldPhaseObject(player);
        handler.addWorldPhaseObject(cam);
        
        //String path = "..\\resources\\maps\\spawn_wall.png";
        String path = null;
        
        WorldPhaseEntity spawn_wall_left = new WorldPhaseEntity(65, -300, ID.Box, 200, 1030, path, "Spawn_LeftWall");
        WorldPhaseEntity spawn_wall_right = new WorldPhaseEntity(1480, -300, ID.Box, 200, 1030, path, "Spawn_RightWall");
        WorldPhaseEntity spawn_wall_bottom = new WorldPhaseEntity(270, 740, ID.Box, 1200, 200, path, "Spawn_BottomWall");
        WorldPhaseEntity spawn_fountains_left = new WorldPhaseEntity(720, 0, ID.Box, 50, 480, path, "Spawn_LeftFountains");
        WorldPhaseEntity spawn_fountains_right = new WorldPhaseEntity(980, 0, ID.Box, 50, 480, path, "Spawn_RightFountains");
        
        handler.addWorldColisionObject(spawn_wall_left);
        handler.addWorldColisionObject(spawn_wall_right);
        handler.addWorldColisionObject(spawn_wall_bottom);
        handler.addWorldColisionObject(spawn_fountains_left);
        handler.addWorldColisionObject(spawn_fountains_right);
        
        //Interaction Test----------------------------------------------------------------------------------------------------
        WorldPhaseEntity testNPC = new WorldPhaseEntity(400, 450, ID.NPC, 100, 100, "..\\resources\\characters_fixed\\hog_1.png", "Hog");
        testNPC.addDialogue("First Dialogue : Hello World");
        testNPC.addDialogue("Second Dialogue : Whole HOG !!!");
        testNPC.addDialogue("Third Dialogue: Hook!!!");
        handler.addWorldColisionObject(testNPC);
        
        //Normal NPC can talk in long dialogue ( use addDialogue Method )
        //Battle NPC must have only 1 dialuge ( use setDialogue Method )
        
        //Battle NPC Test-----------------------------------------------------------------------------------------------------
        WorldPhaseEntity testBattleNPC = new WorldPhaseEntity(1000, 450, ID.BattleNPC, 200, 200, "..\\resources\\characters\\dva_1.png", "DvaBattle");
        testBattleNPC.setDialogue("Love DVA!!!");
        handler.addWorldColisionObject(testBattleNPC);
        
        
        //Add Enemy into the Party
        BattlePhaseEntity dva = new BattlePhaseEntity(POS1.x + 200, POS1.y + 200, ID.Enemy, 300, 300, "..\\resources\\characters\\dva_2.png", 250, 200, "Enemy_Dva", 40, 10, 100, 40);
        testBattleNPC.addEnemyPartyMember(dva, 0);
        
        //Item Test-----------------------------------------------------------------------------------------------------------
        HPItem itemTest = new HPItem(ID.Item, "AtkBoost");
        

        //BattlePhase Part----------------------------------------------------------------------------------------------------

        BattlePhaseEntity genji = new BattlePhaseEntity(P_POS1.x,P_POS1.y, ID.Ally, 400, 400, "..\\resources\\characters\\genji_1a.png", 200, 100, "Genji", 40, 10, 100, 40);
        BattlePhaseEntity mccree = new BattlePhaseEntity(P_POS2.x,P_POS2.y, ID.Ally, 400, 400, "..\\resources\\characters\\mccree_1.png", 250, 200, "Mccree", 40, 10, 100, 40);
        BattlePhaseEntity mercy = new BattlePhaseEntity(P_POS3.x,P_POS3.y, ID.Ally, 400, 400, "..\\resources\\characters\\mercy_1.png", 200, 150, "Mercy", 40, 10, 100, 40);
        BattlePhaseEntity reinhardt = new BattlePhaseEntity(P_POS4.x,P_POS4.y, ID.Ally, 350, 350, "..\\resources\\characters\\rein_1.png", 500, 150, "Reinhardt", 40, 10, 100, 40);

        BattlePhaseEntity doomfist = new BattlePhaseEntity(POS1.x, POS1.y, ID.Enemy, 300, 300, "..\\resources\\characters\\doom_2.png", 250, 200, "Doomfist", 40, 10, 100, 40);
        BattlePhaseEntity widowmaker = new BattlePhaseEntity(POS2.x, POS2.y, ID.Enemy, 300, 300, "..\\resources\\characters\\widow_2.png", 200, 200, "Widowmaker", 40, 10, 100, 40);
        BattlePhaseEntity reaper = new BattlePhaseEntity(POS3.x, POS3.y, ID.Enemy, 300, 300, "..\\resources\\characters\\reaper_2.png", 200, 200, "Reaper", 40, 10, 100, 40);
        BattlePhaseEntity junkrat = new BattlePhaseEntity(POS4.x, POS4.y, ID.Enemy, 300, 300, "..\\resources\\characters\\jake_2.png", 200, 200, "Junkrat", 40, 10, 100, 40);
        BattlePhaseEntity sombra = new BattlePhaseEntity(POS5.x, POS5.y, ID.Enemy, 300, 300, "..\\resources\\characters\\sombra_2.png", 200, 200, "Sombra", 40, 10, 100, 40);
        BattlePhaseEntity bastion = new BattlePhaseEntity(POS6.x, POS6.y, ID.Enemy, 300, 300, "..\\resources\\characters\\bastion_2.png", 200, 200, "Bastion", 40, 10, 100, 40);

        Skill swiftStrike = new Skill("Swift Strike",50,60,80);
        swiftStrike.setDescription("Genji darts forward, slashing with his katana and passing through foes in his path");
        Skill dragonBlade = new Skill("Dragonblade",120,100,200);
        dragonBlade.setDescription("Genji brandishes his katana for a brief period of time");
        genji.addSkill(swiftStrike);
        genji.addSkill(dragonBlade);

        Skill headShot = new Skill("Head Shot", 200,40, 200 );
        headShot.setDescription("Mccree aims for the target's head and fires with accuracy");
        mccree.addSkill(headShot);
        
        Skill blaster = new Skill("Caduceus Blaster",20,10,100);
        blaster.setDescription("Mercy shoots a round from her sidearm");
        mercy.addSkill(blaster);
        
        Skill hammer = new Skill("Rocket Hammer",75,40,150);
        hammer.setDescription("Reinhardt swings his hammer in a wide arc");
        reinhardt.addSkill(hammer);

        Menu menu = new Menu(WIDTH / 2 - 700,  600, ID.Menu, 1400, 300, "..\\resources\\maps\\hud_1.png");
        Menu popUp = new Menu(WIDTH / 2 - 170, 1000, ID.PopUp, 500, 300, "..\\resources\\maps\\hud_box.png");
        Menu cursor = new Menu(WIDTH / 2 + 150, 1000, ID.Cursor, 30, 30, "..\\resources\\ui\\cursor.png");
        Menu background = new Menu(20, 0, ID.Background, WIDTH, HEIGHT, "..\\resources\\maps\\battle_bg.png");


        //0 = North, 1 = West, 2 = South, 3 = East
        Party playerParty = new Party();
        playerParty.addMember(mccree, 0);
        playerParty.addMember(reinhardt, 1);
        playerParty.addMember(genji, 2);
        playerParty.addMember(mercy, 3);


        //Position
        //0 1
        //2 3
        //4 5
        Party enemyParty = new Party();
        enemyParty.addMember(doomfist, 0);
        enemyParty.addMember(widowmaker, 2);
        enemyParty.addMember(reaper, 4);
        enemyParty.addMember(junkrat, 1);
        enemyParty.addMember(sombra, 3);
        enemyParty.addMember(bastion, 5);
  
        handler.addBattlePhaseObject(background);
        handler.addBattlePhaseObject(menu);
        handler.addBattlePhaseObject(popUp);

        for (int i = 0; i < playerParty.memberList.size(); i++) {
            handler.addBattlePhaseObject(playerParty.memberList.get(i).entity);
        }

        for (int i = 0; i < enemyParty.memberList.size(); i++) {
            handler.addBattlePhaseObject(enemyParty.memberList.get(i).entity);
        }

        playerHUD = new HUD(1250, 610, -200, 15, -5, 590,50, playerParty);
        enemyHUD = new HUD(150, 610, -100, -5, -5, 600,60, enemyParty);
        
        handler.addBattlePhaseObject(playerHUD);
        handler.addBattlePhaseObject(enemyHUD);
        handler.addBattlePhaseObject(cursor);
        
        ActionControl control = new ActionControl(handler, playerParty, enemyParty, player);
        control.setPlayerHUD(playerHUD);
        control.setEnemyHUD(enemyHUD);
        
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
        double amountOfTicks = 60;
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
        boolean colision = false;
        handler.tick();
            
        for(WorldPhaseEntity obj : handler.colisionList)
        {
            if(obj.checkColision(player))
            {
               obj.getOutOfHere(player);
               colision = true;
               break;
               //System.out.println(obj);
            }
        }
        
        if(!colision)
            handler.updateBattleObject(player);
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g2d.translate(cam.getX(),cam.getY());
        handler.render(g);
        g2d.translate(-cam.getX(),-cam.getY());


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
