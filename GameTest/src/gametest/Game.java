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
        player.inventory.addMoney(200);

        
        //ItemList----------------------------------------------------------------------------------------
        HPItem HP1 = new HPItem(ID.Item, "HP Potion(S)", 100);
        HP1.setAttributeHP(40);

        HPItem HP2 = new HPItem(ID.Item, "HP Potion(L)", 200);
        HP2.setAttributeHP(100);

        HPItem MP1 = new HPItem(ID.Item, "MP Potion(S)", 100);
        MP1.setAttributeMP(40);

        HPItem MP2 = new HPItem(ID.Item, "MP Potion(L)", 200);
        MP2.setAttributeMP(100);

        HPItem Elixir = new HPItem(ID.Item, "Elixr", 1000);
        Elixir.setAttributeHP(1000);
        Elixir.setAttributeMP(1000);

        HPItem defenseDrug = new HPItem(ID.Item, "DefenseDrug", 400);
        defenseDrug.setAttributeDef(100);
        
        HPItem demonDrug = new HPItem(ID.Item, "Demon Drug", 400);
        demonDrug.setAttributeAtk(100);
        
        /*
        player.addItem(HP1);
        player.addItem(HP1);
        player.addItem(HP1);
        player.addItem(HP1);
        player.addItem(HP2);
        player.addItem(HP2);
        player.addItem(MP1);
        player.addItem(MP1);
        player.addItem(MP2);
        player.addItem(MP2);

        player.addItem(Elixir);
        */


        /*
        player.addItem(test5);
        player.addItem(test5);
        player.addItem(test5);
        player.addItem(test5);
        player.addItem(test5);
        */
        //player.removeItem("HP Potion1");

        //Test Map
        Map testMap = new Map(-1400, -7200, ID.Background, 9600, 9600, "..\\resources\\maps\\open_world_extended.png");
        cam = new Camera(0, 0, ID.Camera, 0, 0, player);

        //handler.addWorldPhaseObject(player); has been move down to the end of function
        handler.addWorldPhaseObject(testMap);
        handler.addWorldPhaseObject(cam);

        //String path = "..\\resources\\maps\\spawn_wall.png";
        String path = null;
        String path2 = "..\\\\resources\\\\maps\\\\spawn_wall.png";

        WorldPhaseEntity spawn_wall_left = new WorldPhaseEntity(65, -300, ID.Box, 200, 1030, path, "Spawn_LeftWall");
        WorldPhaseEntity spawn_wall_right = new WorldPhaseEntity(1480, -300, ID.Box, 200, 1030, path, "Spawn_RightWall");
        WorldPhaseEntity spawn_wall_bottom = new WorldPhaseEntity(270, 740, ID.Box, 1200, 200, path, "Spawn_BottomWall");
        WorldPhaseEntity spawn_fountains_left = new WorldPhaseEntity(720, 0, ID.Box, 45, 460, path, "Spawn_LeftFountains");
        WorldPhaseEntity spawn_fountains_right = new WorldPhaseEntity(980, 0, ID.Box, 45, 460, path, "Spawn_RightFountains");
        WorldPhaseEntity spawn_walls_top1 = new WorldPhaseEntity(266, -300, ID.Box, 430, 100, path, "Spawn_TopWall1");
        WorldPhaseEntity spawn_walls_top2 = new WorldPhaseEntity(1036, -300, ID.Box, 430, 100, path, "Spawn_TopWall2");

        WorldPhaseEntity walkway_left = new WorldPhaseEntity(645, -1940, ID.Box, 60, 1740,path , "Walkway_Left");
        WorldPhaseEntity walkway_right = new WorldPhaseEntity(1040, -1300, ID.Box, 60, 1100,path , "Walkway_Right");
        WorldPhaseEntity walkway_right_top = new WorldPhaseEntity(1040, -1940, ID.Box, 60, 300,path , "Walkway_Right_Top");

        WorldPhaseEntity graveyard_top = new WorldPhaseEntity(1100, -1700, ID.Box, 2800, 60,path , "Graveyard_Top");
        WorldPhaseEntity graveyard_bottom = new WorldPhaseEntity(1100, -1300, ID.Box, 2800, 50,path , "Graveyard_Bottom");
        WorldPhaseEntity graveyard_back = new WorldPhaseEntity(4560, -1665, ID.Box, 50, 340,path , "Graveyard_Bottom");
        WorldPhaseEntity graveyard_top1 = new WorldPhaseEntity(3900, -1765, ID.Box, 130, 60,path , "Graveyard_Top1");
        WorldPhaseEntity graveyard_bottom1 = new WorldPhaseEntity(3900, -1255, ID.Box, 130, 60,path , "Graveyard_Bottom1");
        WorldPhaseEntity graveyard_top2 = new WorldPhaseEntity(4300, -1765, ID.Box, 130, 60,path , "Graveyard_Top2");
        WorldPhaseEntity graveyard_bottom2 = new WorldPhaseEntity(4300, -1255, ID.Box, 130, 60,path , "Graveyard_Bottom2");
        WorldPhaseEntity graveyard_top3 = new WorldPhaseEntity(4430, -1315, ID.Box, 130, 60,path, "Graveyard_Top3");
        WorldPhaseEntity graveyard_bottom3 = new WorldPhaseEntity(4430, -1705, ID.Box, 130, 60,path , "Graveyard_Bottom3");
        WorldPhaseEntity graveyard_top4 = new WorldPhaseEntity(4030, -1830, ID.Box, 250, 60,path , "Graveyard_Top4");
        WorldPhaseEntity graveyard_bottom4 = new WorldPhaseEntity(4030, -1190, ID.Box, 250, 60,path , "Graveyard_Bottom4");

        WorldPhaseEntity shop_top = new WorldPhaseEntity(700, -2350, ID.Box, 350, 60,path, "Shop_Top");
        WorldPhaseEntity shop_left = new WorldPhaseEntity(1170, -2200, ID.Box, 60, 200,path , "Shop_Left");
        WorldPhaseEntity shop_right = new WorldPhaseEntity(530, -2175, ID.Box, 60, 200,path , "Shop_Right");

        WorldPhaseEntity shop_block1 = new WorldPhaseEntity(600, -2295, ID.Box, 60, 130,path , "Shop_Block1");
        WorldPhaseEntity shop_block2 = new WorldPhaseEntity(650, -2350, ID.Box, 60, 100,path , "Shop_Block2");
        WorldPhaseEntity shop_block3 = new WorldPhaseEntity(1045, -2350, ID.Box, 60, 100,path , "Shop_Block3");
        WorldPhaseEntity shop_block4 = new WorldPhaseEntity(1110, -2295, ID.Box, 60, 130,path , "Shop_Block4");
        WorldPhaseEntity shop_block5 = new WorldPhaseEntity(590, -2000, ID.Box, 60, 60,path , "Shop_Block5");
        WorldPhaseEntity shop_block6 = new WorldPhaseEntity(1113, -2000, ID.Box, 60, 60,path , "Shop_Block6");

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

        handler.addWorldColisionObject(walkway_left);
        handler.addWorldColisionObject(walkway_right);
        handler.addWorldColisionObject(walkway_right_top);

        handler.addWorldColisionObject(graveyard_top);
        handler.addWorldColisionObject(graveyard_bottom);
        handler.addWorldColisionObject(graveyard_back);
        handler.addWorldColisionObject(graveyard_top1);
        handler.addWorldColisionObject(graveyard_bottom1);
        handler.addWorldColisionObject(graveyard_top2);
        handler.addWorldColisionObject(graveyard_bottom2);
        handler.addWorldColisionObject(graveyard_top3);
        handler.addWorldColisionObject(graveyard_bottom3);
        handler.addWorldColisionObject(graveyard_top4);
        handler.addWorldColisionObject(graveyard_bottom4);

        handler.addWorldColisionObject(shop_top);
        handler.addWorldColisionObject(shop_left);
        handler.addWorldColisionObject(shop_right);
        handler.addWorldColisionObject(shop_block1);
        handler.addWorldColisionObject(shop_block2);
        handler.addWorldColisionObject(shop_block3);
        handler.addWorldColisionObject(shop_block4);
        handler.addWorldColisionObject(shop_block5);
        handler.addWorldColisionObject(shop_block6);

        handler.addWorldColisionObject(house1);
        handler.addWorldColisionObject(house2);

        //Object Interaction
        WorldPhaseEntity obj_tree1 = new WorldPhaseEntity(680, -63, ID.NPC, 65, 65, path, "obj_tree1");
        obj_tree1.addDialogue("It's a large leafless tree");

        handler.addWorldColisionObject(obj_tree1);

        WorldPhaseEntity obj_tree2 = new WorldPhaseEntity(1002, -63, ID.NPC, 65, 65, path, "obj_tree2");
        obj_tree2.addDialogue("It appears that winter is coming.");

        handler.addWorldColisionObject(obj_tree2);

        WorldPhaseEntity obj_tree3 = new WorldPhaseEntity(395, -170, ID.NPC, 50, 60, path, "obj_tree3");
        obj_tree3.addDialogue("It's a withering tree.");

        handler.addWorldColisionObject(obj_tree3);

        WorldPhaseEntity obj_tree4 = new WorldPhaseEntity(531, -170, ID.NPC, 50, 60, path, "obj_tree4");
        obj_tree4.addDialogue("It's a withering tree,");

        handler.addWorldColisionObject(obj_tree4);

        WorldPhaseEntity obj_tree5 = new WorldPhaseEntity(1171, -170, ID.NPC, 50, 60, path, "obj_tree5");
        obj_tree5.addDialogue("It's an old tree.");

        handler.addWorldColisionObject(obj_tree5);

        WorldPhaseEntity obj_tree6 = new WorldPhaseEntity(1297, -170, ID.NPC, 50, 60, path, "obj_tree6");
        obj_tree6.addDialogue("It's an old tree.");

        handler.addWorldColisionObject(obj_tree6);

        WorldPhaseEntity obj_box = new WorldPhaseEntity(275, 380, ID.NPC, 45, 70, null, "obj_left_fountain");
        obj_box.addDialogue("It's Roadhog's fountain.");
        obj_box.addDialogue("It seems like the water hasn't been changed in days.");

        handler.addWorldColisionObject(obj_box);

        WorldPhaseEntity obj_box2 = new WorldPhaseEntity(1440, 380, ID.NPC, 45, 70, path, "obj_right_fountain");
        obj_box2.addDialogue("It's DVA's fountain.");
        obj_box2.addDialogue("The water looks very clear.");


        handler.addWorldColisionObject(obj_box2);

        WorldPhaseEntity mei_plant = new WorldPhaseEntity(570, -2080, ID.NPC, 50, 60, path, "obj_mei_plant");
        mei_plant.addDialogue("It's Mei's plant.");
        mei_plant.addDialogue("It looks very healthy!");

        handler.addWorldColisionObject(mei_plant);

        WorldPhaseEntity mei_stool = new WorldPhaseEntity(1107, -2080, ID.NPC, 65, 70, path, "obj_mei_stool");
        mei_stool.addDialogue("An old wooden stool.");

        handler.addWorldColisionObject(mei_stool);

        WorldPhaseEntity mei_log = new WorldPhaseEntity(1035, -2210, ID.NPC, 60, 60, path, "obj_mei_stool");
        mei_log.addDialogue("A pile of cut wood.");
        mei_log.addDialogue("The timber looks high-quality.");

        handler.addWorldColisionObject(mei_log);
        

        WorldPhaseEntity mei_pots = new WorldPhaseEntity(640, -2230, ID.NPC, 60, 60, path, "obj_mei_stool");
        mei_pots.addDialogue("It looks like Mei's growing something.");

        handler.addWorldColisionObject(mei_pots);

        //Interaction Test----------------------------------------------------------------------------------------------------
        WorldPhaseEntity s76 = new WorldPhaseEntity(800, 630, ID.NPC, 120, 94, "..\\resources\\characters_world\\76_2.png", "S76");
        s76.addDialogue("Soldier 76: Recruit, your mission is to eliminate Talon's leader: Reaper!");
        s76.addDialogue("Soldier 76: Agents Genji, Reinhardt, Mercy, and McCree are on your team.");
        s76.addDialogue("Soldier 76: Get to it! Ask the people around here about his whereabouts.");
        s76.addDialogue("Soldier 76: Oh, and go to Agent D.VA for your training.");
        handler.addWorldColisionObject(s76);

        WorldPhaseEntity roadHog = new WorldPhaseEntity(420, 480, ID.NPC, 150, 150, "..\\resources\\characters_world\\hog_2.png", "Hog");
        roadHog.addDialogue("Roadhog: Now where's that Junkrat gone off to?");
        roadHog.addDialogue("Roadhog: What? I don't know anything about Talon.");
        roadHog.addDialogue("Roadhog: Now get off my lawn!!");
        handler.addWorldColisionObject(roadHog);

        WorldPhaseEntity meiNPC = new WorldPhaseEntity(820, -2200, ID.NPC, 130, 94, "..\\resources\\characters_world\\mei_2.png", "Mei");
        meiNPC.addDialogue("Mei: I've been getting a lot less customers.");
        meiNPC.addDialogue("Mei: Those Talon guys at the graveyard won't leave either.");
        meiNPC.addDialogue("Mei: Only if Soldier 76 did something about it!");
        handler.addWorldColisionObject(meiNPC);

        WorldPhaseEntity  brigNPC = new WorldPhaseEntity(1028 , -1639, ID.NPC, 130, 98, "..\\resources\\characters_world\\brig_2.png", "Brigitte");
        brigNPC.addDialogue("Brigitte: Hey recruit! Thanks for driving Reaper away." );
        brigNPC.addDialogue("Brigitte: The shop is up there, and Reaper ran to the right." );
        brigNPC.addDialogue("Brigitte: Be carefull of Ashe though, she's blocking the way!" );
        handler.addWorldColisionObject(brigNPC);

        //Battle NPC Test-----------------------------------------------------------------------------------------------------
        WorldPhaseEntity Dva = new WorldPhaseEntity(1150, 490, ID.BattleNPC, 150, 130, "..\\resources\\characters_world\\dva_1.png", "DvaBattle");
        //Pre - Battle Dialogue
        Dva.addDialogue("D.Va: Soldier asked me to train you! Time to raise my APM!");
        //Post - Battle Dialogue
        Dva.addDialogue("D.Va: Nice one!");
        Dva.addDialogue("D.Va: Good luck! Love, D.VA! >:3");
        handler.addWorldColisionObject(Dva);

        //Add Enemy into the Party
        BattlePhaseEntity dva = new BattlePhaseEntity(POS1.x, POS1.y, ID.Enemy, 200, 200, "..\\resources\\characters_fixed\\dva_2.png", 250, 200, "D.Va", 40, 10, 100, 10);
        Skill fusionCannons = new Skill("Fusion Cannons", 100, 40, 80);
        fusionCannons.setDescription("D.Va shoots her twin short-range rotating cannons");
        Skill microMissiles = new Skill("Micro Missiles", 150, 80, 100);
        microMissiles.setDescription("D.Va launches a volley of explosive rockets.");
        Skill boosters = new Skill("Boost", 60, 20, 120);
        boosters.setDescription("D.Va boosts into the enemy!");
        dva.addSkill(fusionCannons);
        dva.addSkill(microMissiles);
        dva.addSkill(boosters);
        Dva.addEnemyPartyMember(dva, 0);


        //Item Test-----------------------------------------------------------------------------------------------------------

        //Chest Test-----------------------------------------------------------------------------------------------------------
        WorldPhaseEntity behindHouseChest = new WorldPhaseEntity(465, -150, ID.Chest, 50, 50, "..\\resources\\misc\\chest_closed.png", "BehindHouseChest");
        behindHouseChest.addDialogue("Is this Roadhog's chest?");
        behindHouseChest.addDialogue(" 'You can take anything you want from this chest' - Mr.Goodaim");
        behindHouseChest.addLoot(HP1);
        behindHouseChest.addLoot(MP1);
        handler.addWorldColisionObject(behindHouseChest);

        WorldPhaseEntity behindHouseChest2 = new WorldPhaseEntity(1230, -150, ID.Chest, 50, 50, "..\\resources\\misc\\chest_closed.png", "BehindHouseChest2");
        behindHouseChest2.addDialogue("Dva's secret stash");
        behindHouseChest2.addDialogue(" 'Goodluck on your mission' - Love, D.Va");
        behindHouseChest2.addLoot(new MoneyItem(ID.Item, "1000 $", 1000));
        handler.addWorldColisionObject(behindHouseChest2);
        
        WorldPhaseEntity meiChest = new WorldPhaseEntity(590, -2150, ID.Chest, 50, 50, "..\\resources\\misc\\chest_closed.png", "Mei Chest" );
        meiChest.addDialogue("Mei's Ultimate Drug");
        meiChest.addLoot(demonDrug);
        
        handler.addWorldColisionObject(meiChest);

        //Merchant Test-------------------------------------------------------------------------------------------------------
        //Merchant must have only 1 item in the itemList
        //Mei Merchant
        //WorldPhaseEntity testMerchant = new WorldPhaseEntity(815, -2185, ID.Merchant, 100, 80, "..\\resources\\characters_world\\mei_1.png", "MeiMerchant");
        //testMerchant.addLoot(new HPItem(ID.Item, "Snowy Robot", 1700));

        //handler.addWorldColisionObject(testMerchant);

        WorldPhaseEntity hpBox = new WorldPhaseEntity(777, -2125, ID.Merchant, 62, 92, "..\\resources\\misc\\fruit_1.png", "HPBox");
        hpBox.addLoot(HP1);

        handler.addWorldColisionObject(hpBox);

        WorldPhaseEntity mpBox = new WorldPhaseEntity(839, -2125, ID.Merchant, 62, 92, "..\\resources\\misc\\fruit_2.png", "HPBox");
        mpBox.addLoot(MP1);

        handler.addWorldColisionObject(mpBox);

        WorldPhaseEntity elixirBox = new WorldPhaseEntity(904, -2125, ID.Merchant, 62, 92, "..\\resources\\misc\\fruit_3.png", "HPBox");
        elixirBox.addLoot(Elixir);

        handler.addWorldColisionObject(elixirBox);


        //BattlePhase Part----------------------------------------------------------------------------------------------------
        BattlePhaseEntity genji = new BattlePhaseEntity(P_POS1.x, P_POS1.y, ID.Ally, 230, 230, "..\\resources\\characters_fixed\\genji_1.png", 200, 200, "Genji", 999, 10, 100, 60);
        BattlePhaseEntity mccree = new BattlePhaseEntity(P_POS2.x, P_POS2.y, ID.Ally, 170, 170, "..\\resources\\characters_fixed\\mccree_1.png", 250, 200, "Mccree", 999, 10, 70, 30);
        BattlePhaseEntity mercy = new BattlePhaseEntity(P_POS3.x, P_POS3.y, ID.Ally, 230, 230, "..\\resources\\characters_fixed\\mercy_1.png", 200, 150, "Mercy", 999, 10, 80, 50);
        BattlePhaseEntity reinhardt = new BattlePhaseEntity(P_POS4.x, P_POS4.y, ID.Ally, 280, 250, "..\\resources\\characters_fixed\\rein_1.png", 500, 150, "Reinhardt", 999, 10, 40, 10);

        genji.setCharIcon("..\\resources\\character_heads\\genji_ult.png");
        mccree.setCharIcon("..\\resources\\character_heads\\mccree_ult.png");
        mercy.setCharIcon("..\\resources\\character_heads\\mercy_ult.png");
        reinhardt.setCharIcon("..\\resources\\character_heads\\rein_ult.png");
 
        BattlePhaseEntity reaper = new BattlePhaseEntity(POS1.x, POS1.y, ID.Enemy, 170, 170, "..\\resources\\characters_fixed\\reaper_2.png", 200, 200, "Reaper", 999, 10, 60, 10);
        BattlePhaseEntity widowmaker = new BattlePhaseEntity(POS2.x, POS2.y, ID.Enemy, 170, 170, "..\\resources\\characters_fixed\\widow_2.png", 200, 200, "Widowmaker", 999, 10, 100, 10);
        BattlePhaseEntity doomfist = new BattlePhaseEntity(POS3.x, POS3.y, ID.Enemy, 170, 170, "..\\resources\\characters_fixed\\doom_2.png", 250, 200, "Doomfist", 999, 10, 60, 10);
        BattlePhaseEntity sombra = new BattlePhaseEntity(POS4.x, POS4.y, ID.Enemy, 170, 170, "..\\resources\\characters_fixed\\sombra_2.png", 200, 200, "Sombra", 999, 10, 60, 10);
        

        BattlePhaseEntity ashe = new BattlePhaseEntity(POS1.x, POS1.y, ID.Enemy, 170, 170, "..\\resources\\characters_fixed\\ashe_2.png", 200, 200, "Ashe", 40, 10, 50, 30);
        BattlePhaseEntity bastion = new BattlePhaseEntity(POS2.x, POS2.y, ID.Enemy, 170, 170, "..\\resources\\characters_fixed\\bastion_2.png", 200, 200, "Bastion", 40, 10, 40, 10);
        BattlePhaseEntity junkrat = new BattlePhaseEntity(POS3.x, POS3.y, ID.Enemy, 170, 170, "..\\resources\\characters_fixed\\jake_2.png", 200, 200, "Junkrat", 40, 10, 50, 30);
        
        //Sombra skill
        Skill smg = new Skill("SMG", 100, 10, 120);
        Skill hack = new Skill("Hack", 120, 40, 80);
        Skill emp = new Skill("EMP", 160, 100, 140);
        sombra.addSkill(smg);
        sombra.addSkill(hack);
        sombra.addSkill(emp);
        
        //Bastion skill
        Skill reconShot = new Skill("Recon Shot", 90, 10, 90);
        Skill sentryBarrage = new Skill("Sentry Barrage", 150, 40, 100);
        Skill tankShot = new Skill("Tank Shot", 200, 100, 90);
        bastion.addSkill(reconShot);
        bastion.addSkill(sentryBarrage);
        bastion.addSkill(tankShot);

        //Ashe skill
        Skill viperHip = new Skill("Viper Hip Shot", 80, 10, 80);
        viperHip.setDescription("Ashe's semi-automatic rifle fires quick shots.");
        Skill viperScope = new Skill("Viper Scoped Shot", 150, 40, 120);
        viperScope.setDescription("Ashe can use her aim-down sights for a more damaging, precise shot.");
        Skill dynamite = new Skill("Dynamite", 180, 100, 100);
        dynamite.setDescription("Ashe throws an explosive that detonates!");
        ashe.addSkill(viperHip);
        ashe.addSkill(viperScope);
        ashe.addSkill(dynamite);

        //doomfistSkill
        Skill normalPunch = new Skill("Normal Punch", 100, 20, 100);
        normalPunch.setDescription("Doomfist punches the enemy with his fist.");
        Skill PowerPunch = new Skill("Power Punch", 150, 40, 120);
        PowerPunch.setDescription("Doomfist punches with great force.");
        Skill handCannon = new Skill("Hand Cannon", 60, 10, 150);
        handCannon.setDescription("Doomfist fires a short-range burst from the knuckles of his fist.");
        Skill meteorStrike = new Skill("Meteor Strike", 220, 100, 180);
        meteorStrike.setDescription("Doomfist leaps into the sky, then crashes to the ground, dealing significant damage.");
        doomfist.addSkill(normalPunch);
        doomfist.addSkill(PowerPunch);
        doomfist.addSkill(handCannon);
        doomfist.addSkill(meteorStrike);

        //widowmakerSkill
        Skill scopeShot = new Skill("Scope Shot", 100, 40, 200);
        scopeShot.setDescription("Click the head");
        Skill jumpShot = new Skill("Jump Shot", 250, 20, 50);
        jumpShot.setDescription("Cool, Powerful but ez to miss");
        widowmaker.addSkill(scopeShot);
        widowmaker.addSkill(jumpShot);

        //Reaper Final Form Skill
        Skill spreadShot = new Skill("Spread Shot", 100, 30, 90);
        spreadShot.setDescription("Reaper shoots his both his shotguns at the same time.");
        Skill pointBlank = new Skill("Point Blank", 150, 100, 140);
        pointBlank.setDescription("Repear Blasts the enemy at point black range!");
        Skill wraithForm = new Skill("Wraith Form", 200, 120, 120);
        wraithForm.setDescription("Reaper telelports behind the enemy and fires his shotguns");
        Skill deathBlossom = new Skill("Death Blossom", 230, 150, 160);
        wraithForm.setDescription("In a blur of motion, Reaper empties both Hellfire Shotguns at breakneck speed, ");
        reaper.addSkill(spreadShot);
        reaper.addSkill(pointBlank);
        reaper.addSkill(wraithForm);
        reaper.addSkill(deathBlossom);

        //junKratSkill
        Skill normalBomb = new Skill("Normal Bomb", 120, 30, 30);
        normalBomb.setDescription("Junkrat fires off a grenade.");
        Skill handBomb = new Skill("Hand Bomb", 100, 10, 70);
        handBomb.setDescription("Junkrat throws out a hand bomb.");
        junkrat.addSkill(normalBomb);
        junkrat.addSkill(handBomb);

        //Melee Skill
        Skill melee = new Skill("Melee", 60, 0, 100);
        melee.setDescription("A standard melee attack.");

        //Genji Skills
        Skill swiftStrike = new Skill("Swift Strike", 120, 60, 80);
        swiftStrike.setDescription("Genji darts forward, slashing with his katana and passing through foes in his path");
        Skill shuriken = new Skill("Shuriken", 90, 20, 100);
        swiftStrike.setDescription("Genji looses three deadly throwing stars in quick succession.");
        Skill dragonBlade = new Skill("Dragonblade", 240, 100, 200);
        dragonBlade.setDescription("Genji brandishes his katana for a brief period of time");
        dragonBlade.setColor(Color.GREEN);
        dragonBlade.setUltimate(true);
        genji.addSkill(swiftStrike);
        genji.addSkill(shuriken);
        genji.addSkill(melee);
        genji.addSkill(dragonBlade);


        //Mccree Skills
        Skill normalShot = new Skill("Normal Shot", 70, 20, 100);
        normalShot.setDescription("McCree fires off a round from his trusty six-shooter.");
        Skill headShot = new Skill("Head Shot", 200, 40, 200);
        headShot.setDescription("Mccree aims for the target's head and fires with accuracy");
        Skill deadeye = new Skill("Deadeye",240,100,200);
        deadeye.setDescription("Focus. Mark. Draw. McCree shoots every enemy in his line of sight.");
        deadeye.setUltimate(true);
        deadeye.setColor(Color.RED);
        mccree.addSkill(normalShot);
        mccree.addSkill(headShot);
        mccree.addSkill(melee);
        mccree.addSkill(deadeye);


        //Mercy Skills
        Skill blaster = new Skill("Caduceus Blaster", 20, 10, 1);
        blaster.setDescription("Mercy shoots a round from her sidearm");
        HealSkill heal = new HealSkill("Heal", 1, 50, 200);
        heal.setDescription("Mercy maintains a beam of healing onto and ally, healing them for 40 health");
        HealSkill valkyrie = new HealSkill("Valkyrie", 3, 100, 120);
        valkyrie.setDescription("Mercy transforms and heals the entire party by 120 points");
        valkyrie.setUltimate(true);
        valkyrie.setColor(Color.YELLOW);
        mercy.addSkill(blaster);
        mercy.addSkill(heal);
        mercy.addSkill(melee);
        mercy.addSkill(valkyrie);

        //Reinhardt Skills
        Skill hammer = new Skill("Rocket Hammer", 75, 40, 1);
        hammer.setDescription("Reinhardt swings his hammer in a wide arc");
        Skill firestrike = new Skill("Fire Strike", 100, 60, 60);
        firestrike.setDescription("By whipping his Rocket Hammer forward, Reinhardt slings a flaming projectile.");
        Skill earthshatter = new Skill("Earthshatter", 230, 100, 100);
        earthshatter.setDescription("Reinhardt forcefully slams his rocket hammer into the ground, damaging all enemies in front of him.");
        earthshatter.setUltimate(true);
        earthshatter.setColor(Color.ORANGE);
        reinhardt.addSkill(hammer);
        reinhardt.addSkill(firestrike);
        reinhardt.addSkill(melee);
        reinhardt.addSkill(earthshatter);

        //Battle NPC Test-----------------------------------------------------------------------------------------------------
        /*
        WorldPhaseEntity Dva = new WorldPhaseEntity(1150, 490, ID.BattleNPC, 150, 130, "..\\resources\\characters_world\\dva_1.png", "DvaBattle");
        //Pre - Battle Dialogue
        Dva.addDialogue("D.Va: Soldier asked me to train you! Time to raise my APM!");
        //Post - Battle Dialogue
        Dva.addDialogue("D.Va: Nice one!");
        Dva.addDialogue("D.Va: Good luck! Love, D.VA! >:3");
        handler.addWorldColisionObject(Dva);
        */

        /*
        //Add Enemy into the Party
        BattlePhaseEntity dva = new BattlePhaseEntity(POS1.x, POS1.y, ID.Enemy, 200, 200, "..\\resources\\characters_fixed\\dva_2.png", 250, 200, "D.Va", 40, 10, 100, 10);
        Skill fusionCannons = new Skill("Fusion Cannons", 100, 40, 80);
        fusionCannons.setDescription("D.Va shoots her twin short-range rotating cannons");
        Skill microMissiles = new Skill("Micro Missiles", 150, 80, 100);
        microMissiles.setDescription("D.Va launches a volley of explosive rockets.");
        Skill boosters = new Skill("Boost", 60, 20, 120);
        boosters.setDescription("D.Va boosts into the enemy!");
        dva.addSkill(fusionCannons);
        dva.addSkill(microMissiles);
        dva.addSkill(boosters);
        Dva.addEnemyPartyMember(dva, 0);
        */

        //Reaper Encounter 1
        WorldPhaseEntity reaper_noob = new WorldPhaseEntity(820, -620, ID.BattleNPC, 120, 120, "..\\resources\\characters_world\\reaper_1.png", "ReaperBattle");
        //Pre - Battle Dialogue
        reaper_noob.addDialogue("Reaper: Don't think you can catch me so easily!");
        //Post - Battle Dialogue
        reaper_noob.addDialogue("Reaper: Damn, I underestimated you!");
        reaper_noob.addDialogue("Reaper: Fall back!");
        handler.addWorldColisionObject(reaper_noob);

        BattlePhaseEntity reaper_noob_battle = new BattlePhaseEntity(POS1.x, POS1.y, ID.Enemy, 150, 150, "..\\resources\\characters_fixed\\reaper_2.png", 250, 200, "Reaper", 50, 15, 40, 10);
        reaper_noob_battle.addSkill(spreadShot);
        reaper_noob_battle.addSkill(pointBlank);
        BattlePhaseEntity widowmaker_noob = new BattlePhaseEntity(POS2.x, POS2.y, ID.Enemy, 170, 170, "..\\resources\\characters_fixed\\widow_2.png", 200, 200, "Widowmaker", 40, 10, 50, 20);
        widowmaker_noob.addSkill(scopeShot);
        widowmaker_noob.addSkill(jumpShot);
        reaper_noob.addEnemyPartyMember(reaper_noob_battle, 0);
        reaper_noob.addEnemyPartyMember(widowmaker_noob, 1);

        //Ashe 1
        WorldPhaseEntity ashe_world = new WorldPhaseEntity(2300, -1550, ID.BattleNPC, 120, 120, "..\\resources\\characters_world\\ashe_1.png", "AsheBattle");
        //Pre - Battle Dialogue
        ashe_world.addDialogue("Ashe: Where you ya think you're going McCree?");
        //Post - Battle Dialogue
        ashe_world.addDialogue("Ashe: I'll get you next time!");
        handler.addWorldColisionObject(ashe_world);

        ashe_world.addEnemyPartyMember(ashe, 0);
        ashe_world.addEnemyPartyMember(bastion, 1);
        ashe_world.addEnemyPartyMember(junkrat, 2);
        
        //FinalBoss
        WorldPhaseEntity reaper_final = new WorldPhaseEntity(4160, -1490, ID.Boss, 180, 180, "..\\resources\\characters_world\\reaper_1.png", "ReaperFinalBattle");
        //Pre - Battle Dialogue
        reaper_final.addDialogue("Reaper: I'm not going down without a fight!");
        //Post - Battle Dialogue
        reaper_final.addDialogue("Reaper: I'll get you next time!");
        handler.addWorldColisionObject(reaper_final);

        reaper_final.addEnemyPartyMember(reaper, 0);
        reaper_final.addEnemyPartyMember(widowmaker, 1);
        reaper_final.addEnemyPartyMember(doomfist, 2);
        reaper_final.addEnemyPartyMember(sombra, 3);

        //Item Test-----------------------------------------------------------------------------------------------------------

        //Chest Test-----------------------------------------------------------------------------------------------------------
        /*
        WorldPhaseEntity behindHouseChest = new WorldPhaseEntity(465, -150, ID.Chest, 50, 50, "..\\resources\\misc\\chest_closed.png", "BehindHouseChest");
        behindHouseChest.addDialogue("Is this Roadhog's chest?");
        behindHouseChest.addDialogue(" 'You can take anything you want from this chest' - Mr.Goodaim");
        behindHouseChest.addLoot(HP1);
        behindHouseChest.addLoot(MP1);
        handler.addWorldColisionObject(behindHouseChest);

        WorldPhaseEntity behindHouseChest2 = new WorldPhaseEntity(1230, -150, ID.Chest, 50, 50, "..\\resources\\misc\\chest_closed.png", "BehindHouseChest2");
        behindHouseChest2.addDialogue("Dva's secret stash");
        behindHouseChest2.addDialogue(" 'Goodluck on your mission' - Love, D.Va");
        behindHouseChest2.addLoot(new MoneyItem(ID.Item, "1000 $", 1000));
        handler.addWorldColisionObject(behindHouseChest2);
        */

        //Merchant Test-------------------------------------------------------------------------------------------------------
        //Merchant must have only 1 item in the itemList
        //Mei Merchant
        //WorldPhaseEntity testMerchant = new WorldPhaseEntity(815, -2185, ID.Merchant, 100, 80, "..\\resources\\characters_world\\mei_1.png", "MeiMerchant");
        //testMerchant.addLoot(new HPItem(ID.Item, "Snowy Robot", 1700));

        //handler.addWorldColisionObject(testMerchant);

        /*
        WorldPhaseEntity hpBox = new WorldPhaseEntity(777, -2125, ID.Merchant, 62, 92, "..\\resources\\misc\\fruit_1.png", "HPBox");
        hpBox.addLoot(HP1);

        handler.addWorldColisionObject(hpBox);

        WorldPhaseEntity mpBox = new WorldPhaseEntity(839, -2125, ID.Merchant, 62, 92, "..\\resources\\misc\\fruit_2.png", "HPBox");
        mpBox.addLoot(MP1);

        handler.addWorldColisionObject(mpBox);

        WorldPhaseEntity elixirBox = new WorldPhaseEntity(904, -2125, ID.Merchant, 62, 92, "..\\resources\\misc\\fruit_3.png", "HPBox");
        elixirBox.addLoot(Elixir);

        handler.addWorldColisionObject(elixirBox);
        */


        //END OF NPCS


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
        BattlePhaseEntity temp = new BattlePhaseEntity(POS2.x, POS2.y, ID.Enemy, 170, 170, "..\\resources\\characters_fixed\\widow_2.png", 200, 200, "Widowmaker", 40, 10, 50, 20);
        enemyParty.addMember(temp, 1);
        //PunPun Lazy af
        //enemyParty.addMember(sombra, 3);
        //enemyParty.addMember(bastion, 5);

        handler.addBattlePhaseObject(background);
        handler.addBattlePhaseObject(menu);
        handler.addBattlePhaseObject(popUp);

        for (int i = 0; i < playerParty.memberList.size(); i++) {
            handler.addBattlePhaseObject(playerParty.memberList.get(i).entity);
        }

        for (int i = 0; i < enemyParty.memberList.size(); i++) {
            handler.addBattlePhaseObject(enemyParty.memberList.get(i).entity);
        }

        playerHUD = new HUD(1250, 610, -200, 15, -5, 590, 50, playerParty,"Player Party");
        enemyHUD = new HUD(150, 610, -100, -5, -5, 600, 60, enemyParty,"Enemy Party");

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


       handler.MainPhaseOn();
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

        handler.mainMenu.x = player.x - 775;
        handler.mainMenu.y = player.y - 450;

        handler.gameWinScreen.x = player.x - 775;
        handler.gameWinScreen.y = player.y - 450;
        
        handler.gameOverScreen.x = player.x - 775;
        handler.gameOverScreen.y = player.y - 450;

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
        player.renderMenu(g);

        /*
        if(handler.battlePhaseStatus())
        {
            for (GameObject o : handler.battleRender.renderList)
                o.render(g);

            if(handler.inventoryStatus())
                player.inventory.itemViewer.render(g);
        }
        */

        if(!handler.battlePhaseStatus())
        {
            for (GameObject o : handler.worldRender.renderList)
                if(o.getName().equals("MerchantCursor"))
                    o.render(g);
        }
        if(handler.inventoryStatus())
            for (GameObject o : handler.battleRender.renderList)
                if(o.getName().equals("SelectionCursor"))
                    o.render(g);

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
