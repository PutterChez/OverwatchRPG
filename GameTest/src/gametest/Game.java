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
        
        Party playerParty;
        Party enemyParty;
        
        public ActionControl(Handler handler, Party player, Party enemy) {
            this.handler = handler;
            this.playerParty = player;
            this.enemyParty = enemy;
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
                            BattlePhase = false;
                    }
                }
                
            }   
            else
            {
                if (key == KeyEvent.VK_B)
                {
                    System.out.println("Entering Battle Phase");
                    BattlePhase = true;
                    
                }
                
                if (key == KeyEvent.VK_UP)
                {
                    System.out.println("Character move up");
                }
                   

                //Temporary exit game method
                if (key == KeyEvent.VK_ESCAPE) {
                    System.exit(1);
                }
            }
        }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
            }
        }
        
        public int getKeyPressed(KeyEvent e)
        {
            return e.getKeyCode();
        }
    }
    
    public static final int WIDTH = 1600, HEIGHT = 900;

    private Thread thread;
    private boolean running = false;

    private Random r;
    private Handler battleHandler;
    private HUD playerHUD, enemyHUD;
    
    private boolean BattlePhase = false;

    public Game() {
        ;
    }
    
    public void BattlePhase()
    {
        battleHandler = new Handler();

        new Window(WIDTH, HEIGHT, "Overwatch RPG Test", this);
        
        int posX1,posX2,posX3,posX4,posY1,posY2,posY3,posY4;
        posX1 = WIDTH/2 + 200; posY1 = HEIGHT/2 - 500;
        posX2 = WIDTH/2 + 250; posY2 = HEIGHT/2 - 150;
        posX3 = WIDTH/2 + 400; posY3 = HEIGHT/2 - 300;
        posX4 = WIDTH/2 - 50; posY4 = HEIGHT/2 - 300;
        
        int enemyX1, enemyX2, enemyX3, enemyX4, enemyX5, enemyX6, enemyY1, enemyY2, enemyY3;
        enemyX1 = WIDTH/2 - 800; enemyX2 = WIDTH/2 - 600;
        enemyY1 = HEIGHT/2 - 500; enemyY2 = HEIGHT/2 - 300; enemyY3 = HEIGHT/2 - 100;
        
        Entity genji = new Entity(posX2,posY2, ID.Genji, 400, 400, "..\\resources\\characters\\genji_1.png", 200, 100, "Genji", 40, 10, 100, 40);
        Entity mccree = new Entity(posX1,posY1, ID.Doom, 400, 400, "..\\resources\\characters\\mccree_1.png", 250, 200, "Mccree", 40, 10, 100, 40);
        Entity mercy = new Entity(posX3,posY3, ID.Mercy, 400, 400, "..\\resources\\characters\\mercy_1.png", 200, 150, "Mercy", 40, 10, 100, 40);
        Entity reinhardt = new Entity(posX4,posY4, ID.Rein, 350, 350, "..\\resources\\characters\\rein_1.png", 500, 150, "Reinhardt", 40, 10, 100, 40);
        
        Entity doomfist = new Entity(enemyX1, enemyY1, ID.Doom, 300, 300, "..\\resources\\characters\\doom_2.png", 250, 200, "Doomfist", 40, 10, 100, 40);
        Entity widowmaker = new Entity(enemyX1, enemyY2, ID.Widow, 300, 300, "..\\resources\\characters\\widow_2.png", 200, 200, "Widowmaker", 40, 10, 100, 40);
        Entity reaper = new Entity(enemyX1, enemyY3, ID.Reaper, 300, 300, "..\\resources\\characters\\reaper_2.png", 200, 200, "Reaper", 40, 10, 100, 40);
        Entity moira = new Entity(enemyX2, enemyY1, ID.Moira, 300, 300, "..\\resources\\characters\\reaper_2.png", 200, 200, "Moira", 40, 10, 100, 40);
        Entity sombra = new Entity(enemyX2, enemyY2, ID.Sombra, 300, 300, "..\\resources\\characters\\sombra_2.png", 200, 200, "Sombra", 40, 10, 100, 40);
        Entity bastion = new Entity(enemyX2, enemyY3, ID.Bastion, 300, 300, "..\\resources\\characters\\bastion_2.png", 200, 200, "Bastion", 40, 10, 100, 40);
        
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
       
        
        battleHandler.addObject(background);
        battleHandler.addObject(menu);
        battleHandler.addObject(popUp);
        battleHandler.addObject(cursor);

        for (int i = 0; i < playerParty.memberList.size(); i++) {
            //handler.addObject(party.get(i));
            battleHandler.addObject(playerParty.memberList.get(i).entity);
        }

        for (int i = 0; i < enemyParty.memberList.size(); i++) {
            //handler.addObject(enemyParty.searchMember(i));
            battleHandler.addObject(enemyParty.memberList.get(i).entity);
        }
            
        playerHUD = new HUD(1250, 1000, -200, 15, -5, 590,50, playerParty);
        enemyHUD = new HUD(150, 1000, -100, -5, -5, 600,60, enemyParty);
        
        this.addKeyListener(new Game.ActionControl(battleHandler, playerParty, enemyParty));

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
        double amountOfTicks = 60.0;
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
        battleHandler.tick();
        
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
            battleHandler.render(g);
            playerHUD.render(g);
            enemyHUD.render(g);
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
        Game test = new Game();
        test.BattlePhase();
    }

}
