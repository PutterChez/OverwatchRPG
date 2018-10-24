package gametest;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
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
    private HUD hud;

    public Game() {
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));

        new Window(WIDTH, HEIGHT, "Overwatch RPG Test", this);

        r = new Random();
        
        int posX1,posX2,posX3,posX4,posY1,posY2,posY3,posY4;
        posX1 = WIDTH/2 + 200; posY1 = HEIGHT/2 - 500;
        posX2 = WIDTH/2 + 250; posY2 = HEIGHT/2 - 150;
        posX3 = WIDTH/2 + 400; posY3 = HEIGHT/2 - 300;
        posX4 = WIDTH/2 - 50; posY4 = HEIGHT/2 - 300;
        
<<<<<<< HEAD
        int enemyX1, enemyX2, enemyX3, enemyX4, enemyX5, enemyX6, enemyY1, enemyY2, enemyY3, enemyY4, enemyY5, enemyY6;
        enemyX1 = WIDTH/2 - 800; enemyY1 = HEIGHT/2 - 500;
        enemyX2 = WIDTH/2 - 800; enemyY2 = HEIGHT/2 - 300;
        enemyX3 = WIDTH/2 - 800; enemyY3 = HEIGHT/2 - 100;
        enemyX4 = WIDTH/2 - 300; enemyY4 = HEIGHT/2 - 500;
        enemyX5 = WIDTH/2 - 300; enemyY5 = HEIGHT/2 - 500;
        enemyX6 = WIDTH/2 - 300; enemyY6 = HEIGHT/2 - 500;
=======
        Entity genji = new Entity(posX2,posY2, ID.Genji, 400, 400, "..//resources//characters//genji_1.png", 200, 100, "Genji");
        Entity doomfist = new Entity(posX2,posY2, ID.Doom, 300, 300, "C:\\Users\\PRO_10\\Documents\\GitHub\\OverwatchRPG\\resources\\characters\\doom_1.png", 250, 200, "Doomfist");
        Entity mercy = new Entity(posX3,posY3, ID.Mercy, 400, 400, "C:\\Users\\PRO_10\\Documents\\GitHub\\OverwatchRPG\\resources\\characters\\mercy_1.png", 200, 150, "Mercy");
        Entity reinhardt = new Entity(posX4,posY4, ID.Rein, 350, 350, "C:\\Users\\PRO_10\\Documents\\GitHub\\OverwatchRPG\\resources\\characters\\rein_1.png", 500, 150, "Reinhardt");
>>>>>>> 88e341f2546dd51f51a8d62e9a9629b72a80d083
        
        
        Entity genji = new Entity(posX2,posY2, ID.Genji, 400, 400, "..\\resources\\characters\\genji_1.png", 200, 100, "Genji");
        Entity mercy = new Entity(posX3,posY3, ID.Mercy, 400, 400, "..\\resources\\characters\\mercy_1.png", 200, 150, "Mercy");
        Entity reinhardt = new Entity(posX4,posY4, ID.Rein, 350, 350, "..\\resources\\characters\\rein_1.png", 500, 150, "Reinhardt");
        Entity mccree = new Entity(posX1,posY1, ID.Mccree, 400, 400, "..\\resources\\characters\\mccree_1.png", 200, 100, "Mccree");
        
        Entity doomfist = new Entity(enemyX1, enemyY1, ID.Doom, 300, 300, "..\\resources\\characters\\doom_2.png", 250, 200, "Doomfist");
        Entity widowmaker = new Entity(enemyX2, enemyY2, ID.Widow, 300, 300, "..\\resources\\characters\\widow_2.png", 250, 200, "Widowmaker");
        Entity reaper = new Entity(enemyX3, enemyY3, ID.Reaper, 300, 300, "..\\resources\\characters\\reaper_2.png", 250, 200, "Reaper");
        
        Skill swiftStrike = new Skill("Switft Strike",50,60,80);
        swiftStrike.setDescription("Genji darts forward, slashing with his katana and passing through foes in his path.");
        genji.addSkill(swiftStrike);
        
        Menu menu = new Menu(WIDTH / 2 - 700, 1000, ID.Menu, 1400, 300, "..\\resources\\maps\\hud_1.png");
        Menu popUp = new Menu(WIDTH / 2 - 450, 1000, ID.PopUp, 500, 300, "..\\resources\\maps\\hud_box.png");
        Menu cursor = new Menu(WIDTH / 2 - 50, 1000, ID.Cursor, 30, 30, "..\\resources\\ui\\cursor.png");
        Menu background = new Menu(0, 0, ID.Background, WIDTH, HEIGHT, null);

        genji.setHP(0);
        doomfist.setHP(0);
        mercy.setHP(0);
        reinhardt.setHP(0);
        
        genji.setMP(0);
        doomfist.setMP(0);
        mercy.setMP(0);
        reinhardt.setMP(0);

        ArrayList<Entity> party = new ArrayList();
        party.add(genji);
        party.add(mccree);
        party.add(mercy);
        party.add(reinhardt);
        
        ArrayList<Entity> enemyList = new ArrayList();
        enemyList.add(doomfist);
        enemyList.add(widowmaker);
        enemyList.add(reaper);
        
        handler.addObject(background);
        handler.addObject(menu);
        handler.addObject(popUp);
        handler.addObject(cursor);
            
        for (int i = 0; i < party.size(); i++) {
            handler.addObject(party.get(i));
        }
        
        for (int i = 0; i < enemyList.size(); i++) {
            handler.addObject(enemyList.get(i));
        }

        hud = new HUD(1200, 1000, party);

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
        handler.tick();
        hud.tick();
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

        hud.render(g);

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
