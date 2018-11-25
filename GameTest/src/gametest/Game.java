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

    public static final Coordinate POS1 = new Coordinate(WIDTH / 2 - 700, HEIGHT / 2 - 400);
    public static final Coordinate POS2 = new Coordinate(WIDTH / 2 - 700, HEIGHT / 2 - 200);
    public static final Coordinate POS3 = new Coordinate(WIDTH / 2 - 700, HEIGHT / 2 - 0);
    public static final Coordinate POS4 = new Coordinate(WIDTH / 2 - 500, HEIGHT / 2 - 400);
    public static final Coordinate POS5 = new Coordinate(WIDTH / 2 - 500, HEIGHT / 2 - 200);
    public static final Coordinate POS6 = new Coordinate(WIDTH / 2 - 500, HEIGHT / 2 - 0);

    public static final Coordinate P_POS1 = new Coordinate(WIDTH / 2 + 200, HEIGHT / 2 - 400);
    public static final Coordinate P_POS2 = new Coordinate(WIDTH / 2 + 250, HEIGHT / 2 - 50);
    public static final Coordinate P_POS3 = new Coordinate(WIDTH / 2 + 400, HEIGHT / 2 - 200);
    public static final Coordinate P_POS4 = new Coordinate(WIDTH / 2 - 50, HEIGHT / 2 - 200);

    private Camera cam;
    private Thread thread;
    private boolean running = false;

    private Handler handler;
    private HUD playerHUD, enemyHUD;
    Player player;
    ActionControl control;
    //private WorldPhaseEntity player;
    //private boolean BattlePhase = false;
    public Game() {
        handler = new Handler();

        new Window(WIDTH, HEIGHT, "Overwatch RPG Test", this);

        //WorldPhase Part-----------------------------------------------------------------------------------------------------
        player = new Player(800, 450, ID.Player, 50, 60, "..\\resources\\characters\\RedSquare.png", "Player");
        player.inventory.addMoney(6000);
        
        HPItem test1 = new HPItem(ID.Item, "HP Potion1", 100);
        HPItem test2 = new HPItem(ID.Item, "MP Potion1", 200);
        HPItem test3 = new HPItem(ID.Item, "HP Potion2", 100);
        HPItem test4 = new HPItem(ID.Item, "HP Potion3", 100);
        HPItem test5 = new HPItem(ID.Item, "MP Potion2", 100);

        //Inventory limit
        // Not more than 20 items
        player.addItem(test1);
        player.addItem(test2);
        player.addItem(test3);
        player.addItem(test4);
        player.addItem(test5);
        player.addItem(test5);
        
        /*
        player.addItem(test5);
        player.addItem(test5);
        player.addItem(test5);
        player.addItem(test5);
        player.addItem(test5);
        player.addItem(test5);
        player.addItem(test5);
        player.addItem(test5);
        player.addItem(test5);
        player.addItem(test5);
        player.addItem(test5);
        player.addItem(test5);
        player.addItem(test5);
        player.addItem(test5);
        */
        //player.removeItem("HP Potion1");
        
        //Test Map
        Map testMap = new Map(-1400, -7200, ID.Background, 9600, 9600, "..\\resources\\maps\\open_world_extra_border_2.png");
        cam = new Camera(0, 0, ID.Camera, 0, 0, player);

        //handler.addWorldPhaseObject(player); has been move down to the end of function
        handler.addWorldPhaseObject(testMap);
        handler.addWorldPhaseObject(cam);

        //String path = "..\\resources\\maps\\spawn_wall.png";
        String path = null;

        WorldPhaseEntity spawn_wall_left = new WorldPhaseEntity(65, -300, ID.Box, 200, 1030, path, "Spawn_LeftWall");
        WorldPhaseEntity spawn_wall_right = new WorldPhaseEntity(1480, -300, ID.Box, 200, 1030, path, "Spawn_RightWall");
        WorldPhaseEntity spawn_wall_bottom = new WorldPhaseEntity(270, 740, ID.Box, 1200, 200, path, "Spawn_BottomWall");
        WorldPhaseEntity spawn_fountains_left = new WorldPhaseEntity(720, 0, ID.Box, 45, 460, path, "Spawn_LeftFountains");
        WorldPhaseEntity spawn_fountains_right = new WorldPhaseEntity(980, 0, ID.Box, 45, 460, path, "Spawn_RightFountains");
        WorldPhaseEntity spawn_walls_top1 = new WorldPhaseEntity(266, -300, ID.Box, 440, 100, path, "Spawn_TopWall1");
        WorldPhaseEntity spawn_walls_top2 = new WorldPhaseEntity(1036, -300, ID.Box, 440, 100, path, "Spawn_TopWall2");

        WorldPhaseEntity house1 = new WorldPhaseEntity(390, 80, ID.Box, 260, 300, path, "House1");
        WorldPhaseEntity house2 = new WorldPhaseEntity(1100, 80, ID.Box, 245, 300, path, "House2");
        WorldPhaseEntity house1_left_roof = new WorldPhaseEntity(340, 125, ID.Box, 55, 130, "..\\resources\\maps\\nihon_roof_left.png", "House1_Roof_Left");
        WorldPhaseEntity house2_left_roof = new WorldPhaseEntity(1050, 125, ID.Box, 55, 130, "..\\resources\\maps\\nihon_roof_left.png", "House2_Roof_Left");
        WorldPhaseEntity house1_right_roof = new WorldPhaseEntity(640, 125, ID.Box, 55, 130, "..\\resources\\maps\\nihon_roof_right.png", "House1_Roof_Right");
        WorldPhaseEntity house2_right_roof = new WorldPhaseEntity(1350, 125, ID.Box, 55, 130, "..\\resources\\maps\\nihon_roof_right.png", "House2_Roof_Right");

        handler.addWorldColisionObject(spawn_wall_left);
        handler.addWorldColisionObject(spawn_wall_right);
        handler.addWorldColisionObject(spawn_wall_bottom);
        handler.addWorldColisionObject(spawn_fountains_left);
        handler.addWorldColisionObject(spawn_fountains_right);
        handler.addWorldColisionObject(spawn_walls_top1);
        handler.addWorldColisionObject(spawn_walls_top2);

        handler.addWorldColisionObject(house1);
        handler.addWorldColisionObject(house2);

        /*
        handler.addWorldPhaseObject(house1_left_roof);
        handler.addWorldPhaseObject(house2_left_roof);
        handler.addWorldPhaseObject(house1_right_roof);
        handler.addWorldPhaseObject(house2_right_roof);
        */

        //Object Interaction
        WorldPhaseEntity obj_box = new WorldPhaseEntity(275, 380, ID.NPC, 45, 70, null, "obj_left_fountain");
        obj_box.addDialogue("It's Roadhog's fountain.");
        obj_box.addDialogue("It seems like the water hasn't been changed in days.");

        handler.addWorldColisionObject(obj_box);
        
        WorldPhaseEntity obj_box2 = new WorldPhaseEntity(1440, 380, ID.NPC, 45, 70, "..\\\\resources\\\\maps\\\\spawn_wall.png", "obj_right_fountain");
        obj_box2.addDialogue("It's DVA's fountain.");
        obj_box2.addDialogue("The water looks very clear.");

        handler.addWorldColisionObject(obj_box2);
        //Interaction Test----------------------------------------------------------------------------------------------------
        WorldPhaseEntity testNPC = new WorldPhaseEntity(420, 480, ID.NPC, 150, 150, "..\\resources\\characters_fixed\\hog_2.png", "Hog");
        testNPC.addDialogue("Roadhog : Hello World");
        testNPC.addDialogue("Roadhog: Whole HOG !!!");
        testNPC.addDialogue("Roadhog: Hook!!!");
        handler.addWorldColisionObject(testNPC);

        //Battle NPC Test-----------------------------------------------------------------------------------------------------
        WorldPhaseEntity testBattleNPC = new WorldPhaseEntity(1000, 450, ID.BattleNPC, 150, 130, "..\\resources\\characters_world\\dva_1.png", "DvaBattle");
        //Pre - Battle Dialogue
        testBattleNPC.addDialogue("Love DVA!!!");
        //Post - Battle Dialogue
        testBattleNPC.addDialogue("Dva 1 : 0 Enemy");
        testBattleNPC.addDialogue("GitGud");
        handler.addWorldColisionObject(testBattleNPC);

        //Add Enemy into the Party
        BattlePhaseEntity dva = new BattlePhaseEntity(POS1.x, POS1.y, ID.Enemy, 200, 200, "..\\resources\\characters_fixed\\dva_2.png", 250, 200, "Enemy_Dva", 40, 10, 100, 40);
        testBattleNPC.addEnemyPartyMember(dva, 0);

        //Item Test-----------------------------------------------------------------------------------------------------------
        HPItem itemTest = new HPItem(ID.Item, "AtkBoost", 10);
        
        //Chest Test-----------------------------------------------------------------------------------------------------------
        WorldPhaseEntity testChest = new WorldPhaseEntity(800, 0, ID.Chest, 50, 50, "..\\resources\\misc\\chest_closed.png", "TreasureChest");
        testChest.addLoot(new HPItem(ID.Item, "Loot1", 100));
        testChest.addLoot(new HPItem(ID.Item, "Loot2", 200));
        testChest.addLoot(new MoneyItem(ID.Item, "100 $", 100));
        
        handler.addWorldColisionObject(testChest);
        
        //Merchant Test-------------------------------------------------------------------------------------------------------
        //Merchant must have only 1 item in the itemList
        WorldPhaseEntity testMerchant = new WorldPhaseEntity(800, 600, ID.Merchant, 100, 80, "..\\resources\\characters_world\\mei_1.png", "MeiMerchant");
        testMerchant.addLoot(new HPItem(ID.Item, "Snowy Robot", 1700));
        
        handler.addWorldColisionObject(testMerchant);
        
        

        //BattlePhase Part----------------------------------------------------------------------------------------------------
        BattlePhaseEntity genji = new BattlePhaseEntity(P_POS1.x, P_POS1.y, ID.Ally, 230, 230, "..\\resources\\characters_fixed\\genji_1.png", 200, 100, "Genji", 40, 10, 100, 40);
        BattlePhaseEntity mccree = new BattlePhaseEntity(P_POS2.x, P_POS2.y, ID.Ally, 170, 170, "..\\resources\\characters_fixed\\mccree_1.png", 250, 200, "Mccree", 40, 10, 100, 40);
        BattlePhaseEntity mercy = new BattlePhaseEntity(P_POS3.x, P_POS3.y, ID.Ally, 230, 230, "..\\resources\\characters_fixed\\mercy_1.png", 200, 150, "Mercy", 40, 10, 100, 40);
        BattlePhaseEntity reinhardt = new BattlePhaseEntity(P_POS4.x, P_POS4.y, ID.Ally, 280, 250, "..\\resources\\characters_fixed\\rein_1.png", 500, 150, "Reinhardt", 40, 10, 100, 40);
        
        genji.setCharIcon("..\\resources\\character_heads\\genji_ult.png");
        mccree.setCharIcon("..\\resources\\character_heads\\mccree_ult.png");
        mercy.setCharIcon("..\\resources\\character_heads\\mercy_ult.png");
        reinhardt.setCharIcon("..\\resources\\character_heads\\rein_ult.png");

        BattlePhaseEntity doomfist = new BattlePhaseEntity(POS1.x, POS1.y, ID.Enemy, 170, 170, "..\\resources\\characters_fixed\\doom_2.png", 250, 200, "Doomfist", 40, 10, 100, 40);
        BattlePhaseEntity widowmaker = new BattlePhaseEntity(POS2.x, POS2.y, ID.Enemy, 170, 170, "..\\resources\\characters_fixed\\widow_2.png", 200, 200, "Widowmaker", 40, 10, 100, 40);
        BattlePhaseEntity reaper = new BattlePhaseEntity(POS3.x, POS3.y, ID.Enemy, 170, 170, "..\\resources\\characters_fixed\\reaper_2.png", 200, 200, "Reaper", 40, 10, 100, 40);
        BattlePhaseEntity junkrat = new BattlePhaseEntity(POS4.x, POS4.y, ID.Enemy, 170, 170, "..\\resources\\characters_fixed\\jake_2.png", 200, 200, "Junkrat", 40, 10, 100, 40);
        BattlePhaseEntity sombra = new BattlePhaseEntity(POS5.x, POS5.y, ID.Enemy, 170, 170, "..\\resources\\characters_fixed\\sombra_2.png", 200, 200, "Sombra", 40, 10, 100, 40);
        BattlePhaseEntity bastion = new BattlePhaseEntity(POS6.x, POS6.y, ID.Enemy, 170, 170, "..\\resources\\characters_fixed\\bastion_2.png", 200, 200, "Bastion", 40, 10, 100, 40);
        
        //Genji Skills
        Skill swiftStrike = new Skill("Swift Strike", 50, 60, 80);
        swiftStrike.setDescription("Genji darts forward, slashing with his katana and passing through foes in his path");
        Skill shuriken = new Skill("Shuriken", 28, 20, 100);
        swiftStrike.setDescription("Genji looses three deadly throwing stars in quick succession.");
        Skill dragonBlade = new Skill("Dragonblade", 120, 100, 200);
        dragonBlade.setDescription("Genji brandishes his katana for a brief period of time");
        dragonBlade.setColor(Color.GREEN);
        dragonBlade.setUltimate(true);
        genji.addSkill(swiftStrike);
        genji.addSkill(shuriken);
        genji.addSkill(dragonBlade);
        
        //Mccree Skills
        Skill normalShot = new Skill("Normal Shot", 70, 20, 100);
        normalShot.setDescription("McCree fires off a round from his trusty six-shooter.");
        Skill headShot = new Skill("Head Shot", 200, 40, 200);
        headShot.setDescription("Mccree aims for the target's head and fires with accuracy");
        Skill deadeye = new Skill("Deadeye",1000,100,200);
        deadeye.setDescription("Focus. Mark. Draw. McCree shoots every enemy in his line of sight.");
        deadeye.setUltimate(true);
        deadeye.setColor(Color.RED);
        mccree.addSkill(normalShot);
        mccree.addSkill(headShot);
        mccree.addSkill(deadeye);
        
        //Mercy Skills
        Skill blaster = new Skill("Caduceus Blaster", 20, 10, 100);
        blaster.setDescription("Mercy shoots a round from her sidearm");
        Skill heal = new Skill("Heal", 100, 50, 200);
        heal.setDescription("Mercy maintains a beam of healing onto and ally, healing them for 100 health");
        Skill damageBoost = new Skill("Damage Boost", 30, 50, 100);
        damageBoost.setDescription("Mercy maintains a beam of damage boost onto and ally, increasing their damage by 30%");
        Skill valkyrie = new Skill("Valkyrie", 150, 100, 200);
        valkyrie.setDescription("Mercy transforms, enchancing her ablitites and damaging the target by 150 points");
        valkyrie.setUltimate(true);
        valkyrie.setColor(Color.YELLOW);
        mercy.addSkill(blaster);
        mercy.addSkill(heal);
        mercy.addSkill(damageBoost);
        mercy.addSkill(valkyrie);
       
        //Reinhardt Skills
        Skill hammer = new Skill("Rocket Hammer", 75, 40, 150);
        hammer.setDescription("Reinhardt swings his hammer in a wide arc");
        Skill firestrike = new Skill("Fire Strike", 100, 60, 60);
        firestrike.setDescription("By whipping his Rocket Hammer forward, Reinhardt slings a flaming projectile.");
        Skill earthshatter = new Skill("Earthshatter", 200, 100, 100);
        earthshatter.setDescription("Reinhardt forcefully slams his rocket hammer into the ground, damaging all enemies in front of him.");
        earthshatter.setUltimate(true);
        earthshatter.setColor(Color.ORANGE);
        reinhardt.addSkill(hammer);
        reinhardt.addSkill(firestrike);
        reinhardt.addSkill(earthshatter);
        

        Menu menu = new Menu(WIDTH / 2 - 700, 600, ID.Menu, 1400, 300, "..\\resources\\maps\\hud_1.png");
        Menu popUp = new Menu(WIDTH / 2 - 170, 1000, ID.PopUp, 500, 300, "..\\resources\\maps\\hud_box.png");
        Menu cursor = new Menu(WIDTH / 2 + 150, 1000, ID.Cursor, 30, 30, "..\\resources\\ui\\cursor.png");
        Menu merCursor = new Menu(WIDTH / 2 + 150, 3000, ID.Menu, 30, 30, "..\\resources\\misc\\cursor_E.png");
        
        //IMPORTANT DO NOT CHANGE THESE NAME
        cursor.setName("SelectionCursor");
        merCursor.setName("MerchantCursor");
        
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

        playerHUD = new HUD(1250, 610, -200, 15, -5, 590, 50, playerParty);
        enemyHUD = new HUD(150, 610, -100, -5, -5, 600, 60, enemyParty);

        handler.addBattlePhaseObject(playerHUD);
        handler.addBattlePhaseObject(enemyHUD);
        handler.addBattlePhaseObject(cursor);

        control = new ActionControl(handler, playerParty, enemyParty, player);
        control.setPlayerHUD(playerHUD);
        control.setEnemyHUD(enemyHUD);
        control.setMerchantCursor(merCursor);
        
        player.setPlayerParty(playerParty);

        //Render Player
        handler.addWorldPhaseObject(player);
        
        //Object that on top of player
        handler.addWorldPhaseObject(house1_left_roof);
        handler.addWorldPhaseObject(house2_left_roof);
        handler.addWorldPhaseObject(house1_right_roof);
        handler.addWorldPhaseObject(house2_right_roof);
        handler.addWorldPhaseObject(merCursor);

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

        for (WorldPhaseEntity obj : handler.colisionList) {
            if (obj.checkColision(player)) {    
                obj.getOutOfHere(player);
                colision = true;
                break;
                //System.out.println(obj);
            }
        }

        if (!colision) {
            handler.updateBattleObject(player);
        }
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

        g2d.translate(cam.getX(), cam.getY());
        
        handler.render(g);
        if(!handler.battlePhaseStatus())
        {
            player.renderMenu(g);
            for (GameObject o : handler.worldRender.renderList)
                if(o.getName().equals("MerchantCursor"))
                    o.render(g);
        }

        g2d.translate(-cam.getX(), -cam.getY());

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
