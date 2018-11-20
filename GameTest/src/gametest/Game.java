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

    class ActionControl extends KeyAdapter
    {
        private Handler handler;
        private boolean PopUp = false;
        private int cursorPos = 0, finalPos = 660;
        //boolean movePlayer = false;
        boolean colision = false;
        
        private int mapSpeed = 1;
               
        Party playerParty;
        Party enemyParty;
        WorldPhaseEntity player;
        Map currentMap;
        
        public ActionControl(Handler handler, Party player, Party enemy, WorldPhaseEntity playerUnit) {
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

            if(BattlePhase)
            {
                /*Movement controls*/
                for (int i = 0; i < handler.object.size(); i++) {

                    GameObject tempObject = handler.object.get(i);

                    if(tempObject.getId() == ID.PopUp){
                        if(key == KeyEvent.VK_ENTER){
                            if(tempObject.getY() > 550){
                                tempObject.setY(580);
                                PopUp = true;
                            }
                        }
                        if(key == KeyEvent.VK_BACK_SPACE){
                            tempObject.setY(1000);
                            PopUp = false;
                        }
                    }

                    if(tempObject.getId() == ID.Cursor){
                        if(PopUp == true){
                            if(key == KeyEvent.VK_DOWN){
                                if(cursorPos < 2)
                                    cursorPos++;

                                finalPos = (660 + cursorPos * 50);
                            }

                            else if(key == KeyEvent.VK_UP){
                                if(cursorPos != 0)
                                    cursorPos--;

                                finalPos = (660 + cursorPos * 50);
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
                            tempObject.setY(1000);
                        }
                    }
                    
                    if (key == KeyEvent.VK_ESCAPE) {
                            System.out.println("Exit Battle Phase");
                            BattlePhase = false;
                    }
                }
                
            }   
            else //WorldPhase part
            {
               // System.out.println("CurrentMap x: " + currentMap.x);
               // System.out.println("CurrentMap y:" + currentMap.y);
               // System.out.println("Player x:" + player.x);
               // System.out.println("Player y:" + player.y);
             
                if (handler.colisionDetection(player))
                {
                    colision = true;
                    System.out.println("Colision detected!!!");
                }
                else
                {
                    colision = false;
                    System.out.println("Colision not detected!!!");
                }
                if (key == KeyEvent.VK_B)
                {
                    System.out.println("Entering Battle Phase");
                    BattlePhase = true;
                }
                
                if (key == KeyEvent.VK_W)
                {
                    //System.out.println("Character Moving Up");
                    //move colisionObject
                    for(int i = 0; i < handler.colisionObject.size(); i++)
                    {
                        if(colision)
                            handler.colisionObject.get(i).velY = -mapSpeed;
                        else
                            handler.colisionObject.get(i).velY = mapSpeed;
                        handler.colisionObject.get(i).velX = 0;
                    }
                    
                    if(colision)
                        currentMap.setVelY(-mapSpeed);
                    else
                        currentMap.setVelY(mapSpeed);
                    
                    currentMap.setVelX(0);
                }
                
                if (key == KeyEvent.VK_A)
                {
                    //System.out.println("Character Moving Left");
                    for(int i = 0; i < handler.colisionObject.size(); i++)
                    {
                        handler.colisionObject.get(i).velY = 0;
                        if(colision)
                            handler.colisionObject.get(i).velX = -mapSpeed;
                        else
                            handler.colisionObject.get(i).velX = mapSpeed;
                    }
                                        
                    if(colision)
                       currentMap.setVelX(-mapSpeed);
                    else
                       currentMap.setVelX(mapSpeed);
                    currentMap.setVelY(0);
                    
                    player.setImageDirectory("..\\resources\\characters\\genji_1.png");
                }
                
                else if (key == KeyEvent.VK_S)
                {
                    //System.out.println("Character Moving Down");
                    for(int i = 0; i < handler.colisionObject.size(); i++)
                    {
                        if(colision)
                            handler.colisionObject.get(i).velY = mapSpeed;
                        else
                            handler.colisionObject.get(i).velY = -mapSpeed;
                        handler.colisionObject.get(i).velX = 0;
                    }
                    
                    
                    if(colision)
                        currentMap.setVelY(mapSpeed);
                    else
                        currentMap.setVelY(-mapSpeed);
                    currentMap.setVelX(0);
                }
                
                else if (key == KeyEvent.VK_D)
                {
                    //System.out.println("Character Moving Right");
                    for(int i = 0; i < handler.colisionObject.size(); i++)
                    {
                        handler.colisionObject.get(i).velY = 0;
                        if(colision)
                            handler.colisionObject.get(i).velX = mapSpeed;
                        else
                            handler.colisionObject.get(i).velX = -mapSpeed;
                    }
                    
                    if(colision)
                        currentMap.setVelX(mapSpeed);
                    else
                        currentMap.setVelX(-mapSpeed);
                    currentMap.setVelY(0);
                    
                    player.setImageDirectory("..\\resources\\characters\\genji_2.png");
                }
                   

                //Temporary exit game method
                else if (key == KeyEvent.VK_ESCAPE) {
                    System.exit(1);
                }
            }
        }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
            }
            //player.setVelX(0);
            //player.setVelY(0);
            currentMap.setVelX(0);
            currentMap.setVelY(0);
            for(int i = 0; i < handler.colisionObject.size(); i++)
            {
                handler.colisionObject.get(i).velY = 0;
                handler.colisionObject.get(i).velX = 0;
            }
            
        }
    }
    
    public static final int WIDTH = 1600, HEIGHT = 900;

    private Thread thread;
    private boolean running = false;

    private Random r;
    private Handler BattleControlHandler;
    private Handler WorldRenderHandler;
    private HUD playerHUD, enemyHUD;
    
    private boolean BattlePhase = false;

    public Game() {
        BattleControlHandler = new Handler();  
        WorldRenderHandler = new Handler();
        
        new Window(WIDTH, HEIGHT, "Overwatch RPG Test", this);
        
        //WorldPhase Part-----------------------------------------------------------------------------------------------------
        WorldPhaseEntity player = new WorldPhaseEntity(800, 450, ID.Player, 92, 50, "..\\resources\\characters\\genji_1.png", "Genji");
        Map testMap = new Map(-1400, -7200, ID.Background, 9600, 9600, "..\\resources\\maps\\open_world_extra_border.png");
        WorldRenderHandler.addObject(testMap);
        WorldRenderHandler.addObject(player);
        
        //testColisionDetection
        ColisionObject test = new ColisionObject(775, 250, ID.Background, 200, 100);
        test.setImageDirectory("..\\resources\\maps\\spawn_wall.png");
        
        WorldRenderHandler.addObject(test);
        BattleControlHandler.addColisionObject(test);
        
        
        //BattlePhase Part----------------------------------------------------------------------------------------------------
        int posX1,posX2,posX3,posX4,posY1,posY2,posY3,posY4;
        posX1 = WIDTH/2 + 200; posY1 = HEIGHT/2 - 500;
        posX2 = WIDTH/2 + 250; posY2 = HEIGHT/2 - 150;
        posX3 = WIDTH/2 + 400; posY3 = HEIGHT/2 - 300;
        posX4 = WIDTH/2 - 50; posY4 = HEIGHT/2 - 300;
        
        int enemyX1, enemyX2, enemyX3, enemyX4, enemyX5, enemyX6, enemyY1, enemyY2, enemyY3;
        enemyX1 = WIDTH/2 - 800; enemyX2 = WIDTH/2 - 600;
        enemyY1 = HEIGHT/2 - 500; enemyY2 = HEIGHT/2 - 300; enemyY3 = HEIGHT/2 - 100;
        
        BattlePhaseEntity genji = new BattlePhaseEntity(posX2,posY2, ID.Genji, 400, 400, "..\\resources\\characters\\genji_1.png", 200, 100, "Genji", 40, 10, 100, 40);
        BattlePhaseEntity mccree = new BattlePhaseEntity(posX1,posY1, ID.Doom, 400, 400, "..\\resources\\characters\\mccree_1.png", 250, 200, "Mccree", 40, 10, 100, 40);
        BattlePhaseEntity mercy = new BattlePhaseEntity(posX3,posY3, ID.Mercy, 400, 400, "..\\resources\\characters\\mercy_1.png", 200, 150, "Mercy", 40, 10, 100, 40);
        BattlePhaseEntity reinhardt = new BattlePhaseEntity(posX4,posY4, ID.Rein, 350, 350, "..\\resources\\characters\\rein_1.png", 500, 150, "Reinhardt", 40, 10, 100, 40);
        
        BattlePhaseEntity doomfist = new BattlePhaseEntity(enemyX1, enemyY1, ID.Doom, 300, 300, "..\\resources\\characters\\doom_2.png", 250, 200, "Doomfist", 40, 10, 100, 40);
        BattlePhaseEntity widowmaker = new BattlePhaseEntity(enemyX1, enemyY2, ID.Widow, 300, 300, "..\\resources\\characters\\widow_2.png", 200, 200, "Widowmaker", 40, 10, 100, 40);
        BattlePhaseEntity reaper = new BattlePhaseEntity(enemyX1, enemyY3, ID.Reaper, 300, 300, "..\\resources\\characters\\reaper_2.png", 200, 200, "Reaper", 40, 10, 100, 40);
        BattlePhaseEntity moira = new BattlePhaseEntity(enemyX2, enemyY1, ID.Moira, 300, 300, "..\\resources\\characters\\reaper_2.png", 200, 200, "Moira", 40, 10, 100, 40);
        BattlePhaseEntity sombra = new BattlePhaseEntity(enemyX2, enemyY2, ID.Sombra, 300, 300, "..\\resources\\characters\\sombra_2.png", 200, 200, "Sombra", 40, 10, 100, 40);
        BattlePhaseEntity bastion = new BattlePhaseEntity(enemyX2, enemyY3, ID.Bastion, 300, 300, "..\\resources\\characters\\bastion_2.png", 200, 200, "Bastion", 40, 10, 100, 40);
        
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
       
        
        BattleControlHandler.addObject(background);
        BattleControlHandler.addObject(menu);
        BattleControlHandler.addObject(popUp);
        BattleControlHandler.addObject(cursor);

        for (int i = 0; i < playerParty.memberList.size(); i++) {
            //handler.addObject(party.get(i));
            BattleControlHandler.addObject(playerParty.memberList.get(i).entity);
        }

        for (int i = 0; i < enemyParty.memberList.size(); i++) {
            //handler.addObject(enemyParty.searchMember(i));
            BattleControlHandler.addObject(enemyParty.memberList.get(i).entity);
        }
            
        playerHUD = new HUD(1250, 1000, -200, 15, -5, 590,50, playerParty);
        enemyHUD = new HUD(150, 1000, -100, -5, -5, 600,60, enemyParty);
        
        Game.ActionControl control = new Game.ActionControl(BattleControlHandler, playerParty, enemyParty, player);
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
        WorldRenderHandler.tick();
        BattleControlHandler.tick();
        
        if (BattlePhase)
        {
            playerHUD.tick();
            enemyHUD.tick();
        }
        
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
  
        if (BattlePhase)
        {
            BattleControlHandler.render(g);
            playerHUD.render(g);
            enemyHUD.render(g);
        }
        else
        {
            WorldRenderHandler.render(g);
        }

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
